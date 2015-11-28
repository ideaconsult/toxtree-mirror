/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package toxTree.io.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionResult;
import toxTree.core.Introspection;
import toxTree.exceptions.DecisionResultException;
import ambit2.core.io.MDLWriter;

/**
 * A {@link toxTree.io.batch.ChemObjectBatchProcessing} descendant , where processing is performed by {@link toxTree.core.IDecisionMethod} 
 * @author Nina Jeliazkova
 * <b>Modified</b> 2008-3-22
 */
public class ToxTreeBatchProcessing extends ChemObjectBatchProcessing {
	
	protected transient IDecisionMethod decisionMethod;
	protected transient IDecisionResult decisionResult;
	
    protected String decisionMethodFile = "decisiontree.tml";
	
	//protected String decisionMethodClassName = "";
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 312980948801505772L;

	/**
	 * 
	 */
	public ToxTreeBatchProcessing() {
		super();
		setDecisionMethod(null);
		decisionResult = null;
	}

	/**
	 * @param input
	 * @param output
	 * @throws BatchProcessingException
	 */
	public ToxTreeBatchProcessing(String input, String output)
			throws BatchProcessingException {
		super(input, output);
		setDecisionMethod(null);
		decisionResult = null;
	}
	public ToxTreeBatchProcessing(File input, File output) throws BatchProcessingException {
		super(input, output);
		setDecisionMethod(null);
		decisionResult = null;		
	}	
	/* (non-Javadoc)
	 * @see toxTree.io.ChemObjectBatchProcessing#processRecord()
	 */
	@Override
	public void processRecord() throws BatchProcessingException {
		logger.finer("Process record");
		if (decisionMethod == null) throw new BatchProcessingException("Decision method not assigned");
		if (decisionResult == null) decisionResult = decisionMethod.createDecisionResult(); 
		if (chemObject instanceof IAtomContainer) {
			
			try {
                decisionResult.getDecisionMethod().setParameters((IAtomContainer) chemObject);
			    IAtomContainer ac = (IAtomContainer) chemObject.clone();
                
				decisionResult.classify(ac);
                decisionResult.assignResult((IAtomContainer)chemObject);

			} catch (DecisionResultException x) {
				throw new BatchProcessingException(x);
			} catch (CloneNotSupportedException x) {
			    throw new BatchProcessingException(x);
			}
		}
	}
	/* (non-Javadoc)
	 * @see toxTree.io.ChemObjectBatchProcessing#writeRecord()
	 */
	@Override
	public void writeRecord() throws BatchProcessingException {
		logger.finer("Write record");
		if (writer instanceof MDLWriter) {
			((MDLWriter) writer).setSdFields(chemObject.getProperties());
		}
		super.writeRecord();
	}
	/**
	 * @return Returns the decisionMethod.
	 */
	public synchronized IDecisionMethod getDecisionMethod() {
		return decisionMethod;
	}
	/**
	 * @param decisionMethod The decisionMethod to set.
	 */
	public synchronized void setDecisionMethod(IDecisionMethod decisionMethod) {
		this.decisionMethod = decisionMethod;
		if (decisionMethod != null) {
			decisionResult = decisionMethod.createDecisionResult();
			
			//decisionMethodClassName = decisionMethod.getClass().getName();
		} //else decisionMethodClassName = "";
		decisionMethodFile = null;
	}
	/**
	 * 
	 */
	/*
	public synchronized void setDecisionMethod() {
		if (decisionMethodClassName != null) {
		      try {
		      		logger.info("Creating decision method from class name\t",decisionMethodClassName);
                    Object object = Introspection.loadCreateObject(decisionMethodClassName);
                    if (object instanceof IDecisionMethod)
                        setDecisionMethod((IDecisionMethod)object);
                    else logger.error("Expected IDecisionMethod instead of "+object.getClass().getName());

		          
		      } catch (Exception e) {
		          logger.error(e);
		          setDecisionMethod(null);
		      }
	
		}
			
	}
	*/
	/* (non-Javadoc)
	 * @see toxTree.io.ChemObjectBatchProcessing#start()
	 */
	@Override
	public void start() throws BatchProcessingException {
		if (getDecisionMethod() == null) throw new BatchProcessingException("Decision Method not assigned!");
		super.start();
	}
	/* (non-Javadoc)
	 * @see toxTree.io.BatchProcessing#saveConfig()
	 */
	@Override
	public void saveConfig() throws BatchProcessingException {
		if (getDecisionMethod() == null) throw new BatchProcessingException("Decision method not assigned!");
		super.saveConfig();
	}
	@Override
	public void saveConfig(OutputStream out) throws BatchProcessingException {
		if (out == null) throw new BatchProcessingException("Can't save batch state!"); 
		try {
			logger.finer("Save state\t"+configFile.toString());
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(this);
			os.close();
		} catch (IOException x) {
			x.printStackTrace();
			throw new BatchProcessingException(x);
		}
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		if (getDecisionMethod() == null)
			throw new IOException("Decision method not assigned!");
		File tmp = null;
		if (decisionMethodFile == null) {
	        tmp = File.createTempFile(getDecisionMethod().getClass().getName(), ".tml");
	        decisionMethodFile = tmp.getAbsolutePath();
		} else
			tmp = new File(decisionMethodFile);
        
        try {
        	FileOutputStream os = new FileOutputStream(tmp);
        	Introspection.saveRulesXML(getDecisionMethod(), os);
        	os.close();
        } catch (Exception x) {
        	throw new IOException(x.getMessage());
        }
		out.defaultWriteObject();
	}


	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
        try {
            IDecisionMethod m = Introspection.loadRulesXML(new FileInputStream(decisionMethodFile), "");
            setDecisionMethod(m);
        } catch (Exception x) {
            setDecisionMethod(null);
            throw new ClassNotFoundException(x.getMessage());
        }
				
	}


	
}
