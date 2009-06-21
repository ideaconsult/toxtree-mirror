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
package toxTree.tree.rules;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRuleEditor;
import toxTree.core.Introspection;
import toxTree.exceptions.DRuleNotImplemented;
import toxTree.exceptions.DecisionMethodException;
import toxTree.io.Tools;
import toxTree.tree.AbstractRule;
import toxTree.ui.tree.rules.RuleStructuresPanel;

/**
 * A rule, which returns true if the query is isomorphic to one of the structures 
 * read from a preconfigured file of a type SDF, SMI, CSV 
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-6
 */
public class RuleStructuresList extends AbstractRule {
	
	protected transient LookupFile lookupFile;
    protected String filename = null;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1445819316236082148L;
	/**
	 * 
	 */
	public RuleStructuresList() {
		this(new File(Introspection.getToxTreeRoot()+"bodymol.sdf"));
		
	}
	public RuleStructuresList(String resource) {
		this(Tools.getFileFromResourceSilent(resource));
	}

	public RuleStructuresList(File file) {
		super();
		
		try {
			setFile(file);
		} catch (IOException x) {
			try {setFile(Tools.getFileFromResource(file.getName()));} catch (Exception xx) { lookupFile = null;}
			logger.error(x);
		}
		setExplanation("Returns true if the query is isomorphic to one of the structures loaded from a preconfigured file of a type SDF, SMI, CSV ");
		setTitle("Exact search");
        
	}	
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (lookupFile == null) throw new DRuleNotImplemented();
		return lookupFile.find(mol);

		
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		if (lookupFile==null) return false;
		File file = lookupFile.getFile(); 
		return (file !=null) && (file.exists());
	}

	/**
	 * @return Returns the file.
	 */
	public synchronized File getFile() {
		return lookupFile.getFile();
	}
	/**
	 * @param file The file to set.
	 */
	public synchronized void setFile(File file) throws IOException {
        
		lookupFile = new LookupFile(file);
		lookupFile.setLogger(logger);
        setFilename(file.getAbsolutePath());
		logger.debug("Will be using file\t",file.getAbsoluteFile());		
		
	}
	/**
	 * @return Returns the useCache.
	 */
	public synchronized boolean isUsingCache() {
		return lookupFile.isUseCache();
	}
	/**
	 * @param useCache The useCache to set.
	 */
	public synchronized void setUsingCache(boolean useCache) {
		lookupFile.setUseCache(useCache);
	}
	@Override
	public IDecisionRuleEditor getEditor() {
		return new RuleStructuresPanel(this);
	}
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
            try {
                lookupFile = new LookupFile(getFilename());
            } catch (Exception x) {
                logger.error(x);
                lookupFile = null;
            }
    }
    public synchronized String getFilename() {
        return filename;
    }
    public synchronized void setFilename(String filename) {
        this.filename = filename;
    }    
}
/*
class RecordCache implements Comparable {
	protected long offset= 0;
	protected long recNum = 0;
	protected String formula = "";
	protected boolean aromatic = false;
	
	public RecordCache(long offset, long recNum, String formula) {
		super();
		this.offset = offset;
		this.recNum = recNum;
		this.formula = formula;
	}
	public RecordCache(long offset, long recNum) {
		super();
		this.offset = offset;
		this.recNum = recNum;
	
	}	
	
	public boolean equals(Object obj) {
		RecordCache o = (RecordCache) obj;
		return formula.equals(o.formula); 
			
	}
	
	public int compareTo(Object o) {
		return formula.compareTo(((RecordCache) o).formula);
	}
	
	
	
	public synchronized String getFormula() {
		return formula;
	}

	public synchronized void setFormula(String formula) {
		this.formula = formula;
	}
	
	public synchronized boolean isAromatic() {
		return aromatic;
	}

	public synchronized void setAromatic(boolean aromatic) {
		this.aromatic = aromatic;
	}
}

*/
