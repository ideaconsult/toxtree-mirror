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
package toxTree.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.vecmath.Vector2d;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemFile;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.io.DefaultChemObjectWriter;
import org.openscience.cdk.io.IChemObjectReader;
import org.openscience.cdk.io.MDLV2000Reader;
import org.openscience.cdk.io.ReaderFactory;
import org.openscience.cdk.io.SMILESReader;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;

import toxTree.core.IMoleculesIterator;
import toxTree.exceptions.ToxTreeIOException;
import toxTree.io.PDFWriter;
import toxTree.logging.TTLogger;
import ambit2.core.io.DelimitedFileFormat;
import ambit2.core.io.DelimitedFileReader;
import ambit2.core.io.DelimitedFileWriter;
import ambit2.core.io.MDLWriter;
import ambit2.core.io.MyIteratingMDLReader;


/**
 * Contains molecules <ul>
 * <li>loaded from a file, 
 * <li>entered by SMILES {@link toxTree.ui.molecule.SmilesEntryPanel}  
 * <li>entered by structure diagram editor {@link toxTree.ui.molecule.JChemPaintDialog}
 * </ul>
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-3
 */
public class MoleculesIterator implements IMoleculesIterator {
	
	private static transient StructureDiagramGenerator sdg = null;
	protected ListOfAtomContainers containers = null;
	protected int currentNo = 0;	
    protected static TTLogger logger = null;
    protected int status = 0; //0-OK 1-reading  2-writing 
    protected String filename = "";
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8781174694308416291L;

	/**
	 * 
	 */
	public MoleculesIterator() {
		super();
		containers = new ListOfAtomContainers();
		
        IMolecule molecule = MoleculeFactory.makeAlkane(6);
        molecule.setProperty(CDKConstants.NAMES,"Created from SMILES");
        molecule.setProperty("SMILES","CCCCCC");
        containers.addAtomContainer(molecule);
        currentNo = 0;        
        if (logger == null) logger = new TTLogger(); 
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		// TODO Auto-generated method stub

	}
	public IAtomContainer getAtomContainer(int index) {
		return getContainers().getAtomContainer(index);
	}
	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		if (getContainers() == null) return false;
		return ((currentNo+1) < getContainers().getAtomContainerCount());
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public Object next() {
        if (getContainers() == null)	return setCurrentNo(0);
        else  return setCurrentNo(currentNo+1); 
	}

	
	/**
	 * @return Returns the molecules.
	 */
	public IAtomContainer[] getMolecules() {
		return getContainers().getAtomContainers();
	}
	/**
	 * @param molecules The molecules to set.
	 */
	public void setMolecules(List molecules) {
		containers.clear();
		for (int i=0; i < molecules.size(); i++ )
			if (molecules.get(i) instanceof IAtomContainer)
			containers.add((IAtomContainer)molecules.get(i));
		currentNo = 0;
	}
	/**
	 * @return Returns the currentNo.
	 */
	public int getCurrentNo() {
		return currentNo;
	}
	public void clear() {
		currentNo = -1;
		containers.clear();
		filename = "";
	}
    public Object prev() {
        if (getContainers() == null)	return setCurrentNo(0);
        else  return setCurrentNo(currentNo-1); 
    }   
	
    public Object first() {
        if (getContainers() == null)	return setCurrentNo(0);
        else  return setCurrentNo(0); 
    }
    public Object last() {
        if (getContainers() == null)	return setCurrentNo(0);
        else  return setCurrentNo(containers.size()-1); 
    }   	
    public IAtomContainer setCurrentNo(int record) {
        if (getContainers() == null) currentNo = 0;
        else { 
        	currentNo = record;
        	if (currentNo < 0) currentNo = 0;
        	else if (currentNo >= getMoleculesCount())
        		currentNo = getMoleculesCount()-1;
        }
        return getMolecule();
    }
	/**
	 * @return Returns the molecule.
	 */
	public IAtomContainer getMolecule() {
		if ((currentNo >= 0) && (currentNo < containers.size()))
			return getContainers().getAtomContainer(currentNo);
		else 
			return null;
	}
	public int getMoleculesCount() {
		return getContainers().getAtomContainerCount();
	}
	public void addMolecule(IAtomContainer mol) {
		if (getContainers() == containers) {
			containers.addAtomContainer(mol);
			last();
		}
	}
	public void setMolecule(IAtomContainer mol) {
		if (getContainers() == containers) {
			containers.set(currentNo,mol);
		}
	}
    protected void useIterativeReader(InputStream in) {
        containers.clear();
        MyIteratingMDLReader reader = new MyIteratingMDLReader(in, DefaultChemObjectBuilder.getInstance());
        int r = 0;
        while (reader.hasNext()) {
            Object o = reader.next();
            if (o instanceof IAtomContainer) {
                containers.add((IAtomContainer)o);
                r++;
            }    
        }
        currentNo = 0;
    }
    public List openFile(File input) throws ToxTreeIOException {
        ReaderFactory factory = new ReaderFactory();
        filename = input.getAbsolutePath();
        try {
        if (!input.isDirectory()) {
        	IChemObjectReader reader=null;
        	logger.info("Trying to read\t",input);
        	String fe = input.toString().toLowerCase(); 
            if (fe.endsWith(".csv")) {  
      	      reader = new DelimitedFileReader(new FileReader(input));
              logger.info("Expecting SMILES format...");
            } else if (fe.endsWith(".txt")) {  
      	      reader = new DelimitedFileReader(new FileReader(input),new DelimitedFileFormat("\t ",'"'));
              logger.info("Expecting TXT format...");              
            } else if (fe.endsWith(".smi")) {  
        	      reader = new SMILESReader(new FileReader(input));
                logger.info("Expecting SMILES format...");
            } else if (fe.endsWith(".sdf")) {  
                
              //reader = new MDLReader(new FileReader(input));
              logger.info("Expecting SDF format...");
              useIterativeReader(new FileInputStream(input));
              return null;
            } else if (fe.endsWith(".mol")) {  
                
                //reader = new MDLReader(new FileReader(input));
                logger.info("Expecting MOL format...");
                reader = new MDLV2000Reader(new FileReader(input));
            
          } else
            	reader = factory.createReader(new FileReader(input));
            
            if (reader != null) {
                try {
                	IChemFile content = (IChemFile)reader.read(DefaultChemObjectBuilder.getInstance().newChemFile());
	                //SetOfMolecules content = (SetOfMolecules) reader.read(new SetOfMolecules());
	                reader.close();
	                if (content == null) {
	                	throw new ToxTreeIOException(MSG_EMPTYFILE,filename);
	                }
	                // apply modifications
//	                AtomContainer[] c = content.getAtomContainers();
	                List c = ChemFileManipulator.getAllAtomContainers(content);
	                if (c.size() <= 0) {
	                	throw new ToxTreeIOException(MSG_EMPTYFILE,filename);
	                } if ((c.size() == 1) && (fe.endsWith(".mol")) && (c.get(0) != null)) {
	                	((IAtomContainer)c.get(0)).setProperty(CDKConstants.NAMES,filename);
	                }
	                if (!(c.get(0) instanceof IAtomContainer)) {
	                	throw new ToxTreeIOException(MSG_EMPTYFILE + " found " + c.get(0).getClass().getName(),filename);
	                }
	                logger.info(MSG_OPENSUCCESS,filename,"\tMolecules read from file\t"+c.size());
	                return c;
                } catch (CDKException x) {
                	throw new ToxTreeIOException(MSG_ERRORONOPEN,x,filename);
                } catch (Exception x) {
                	throw new ToxTreeIOException(MSG_ERRORONOPEN,x,filename);
                }
            } else {
            	throw new ToxTreeIOException(MSG_UNSUPPORTEDFORMAT,filename);
            }
        } else throw new ToxTreeIOException("Input is a directory!",filename);
        } catch (IOException x) {
        	throw new ToxTreeIOException(MSG_ERRORONOPEN,x,filename);
        } catch (Exception x) {
        	throw new ToxTreeIOException(MSG_ERRORONOPEN,x,filename);
        }
    }
    public void saveFile(File output) throws ToxTreeIOException {
    	DefaultChemObjectWriter writer = null;
    	String filename = "";
    	MDLWriter mdlWriter = null;
    	try {
    		filename = output.toString();
	    	if (filename.toLowerCase().endsWith(".sdf")) { 
	    		mdlWriter = new MDLWriter(new FileWriter(output));
	    		mdlWriter.dontWriteAromatic();
	    		writer = mdlWriter;
	    	} else	if (filename.toLowerCase().endsWith(".csv")) { 
		    	writer = new DelimitedFileWriter(new FileWriter(output));
		    	mdlWriter = null;
	    	} else	if (filename.toLowerCase().endsWith(".txt")) { 
		    	writer = new DelimitedFileWriter(new FileWriter(output),new DelimitedFileFormat("\t " ,'"'));
		    	mdlWriter = null;
            } else  if (filename.toLowerCase().endsWith(".pdf")) { 
                writer = new PDFWriter(new FileOutputStream(output));
                mdlWriter = null;
            }
	    	if (writer != null) {
	    		 
	    		//first(); 
	    		int c =0;
	    		for (int record =0; record < getContainers().getAtomContainerCount(); record++) {
	    			IAtomContainer ac = getContainers().getAtomContainer(record);
	    			if (ac !=null) {
	    				c++;
	    				logger.debug("Writing compound \t",Integer.toString(record), "...");
	    				if (mdlWriter != null) {
	    					mdlWriter.setSdFields(ac.getProperties());
	    				}
	    				try {
	    					writer.write(ac);
	    				} catch (Exception x) {
	    					logger.error(ac);
	    				}
	    			}
	    		}
				writer.close();
				logger.info(MSG_SAVESUCCESS,filename,"\tCompounds written\t",Integer.toString(c));
	    	} else {
	    		throw new ToxTreeIOException(MSG_UNSUPPORTEDFORMAT,filename);
	    	}
    	} catch (CDKException x) {
    		//x.printStackTrace();
    		throw new ToxTreeIOException(MSG_ERRORONSAVE,x,filename);    		
    	} catch (IOException x) {
    		throw new ToxTreeIOException(MSG_ERRORONSAVE,x,filename);    		    		
    	
		} catch (Exception x) {
			throw new ToxTreeIOException(MSG_ERRORONSAVE,x,filename);    					
		}
    }
	public boolean isReading() {
		return status == 1;
	}
	public boolean isWriting() {
		return status == 2;
	}	
	public String getStatus() {
		switch (status) {
		case 0: return "Compound(s) loaded\t" + getMoleculesCount();
		case 1: return "Reading file ...";
		case 2: return "Writing file ...";
		default: return "Unknown";
		}
	}
	public void setReading() {
		status = 1;
	}
	public void setWriting() {
		status = 2;
	}
	public void setDone(boolean success) {
		status = 0;
		
		if (!success) {
			if (containers.getAtomContainerCount() == 0)
				filename = "";
		}
	}
	
	@Override
	public String toString() {
		return filename;
	}
	public IMoleculeSet getSetOfAtomContainers() {
		IMoleculeSet c = new MoleculeSet();
		for (int i=0; i< getContainers().getAtomContainerCount();i++)
			c.addAtomContainer(getContainers().getAtomContainer(i));
		return c;
	}
	public IMoleculeSet getMoleculeForEdit() throws Exception {
		sdg = null;
		sdg = new StructureDiagramGenerator();
		IMoleculeSet m = ConnectivityChecker.partitionIntoMolecules(getMolecule());
		//IMoleculeSet m =  new MoleculeSet();
		for (int i=0; i< m.getMoleculeCount();i++) {
			IMolecule a = m.getMolecule(i);
			//if (!GeometryTools.has2DCoordinates(a)) {
				
				sdg.setMolecule((IMolecule)a);
				sdg.generateCoordinates(new Vector2d(0,1));
				m.replaceAtomContainer(i, sdg.getMolecule());
			//}
			//m.addMolecule(molecules[i]);
		}
		return m;		
	}
	public String getFilename() {
		return filename;
	}

	protected ListOfAtomContainers getContainers() {
		return containers;
	}


}
