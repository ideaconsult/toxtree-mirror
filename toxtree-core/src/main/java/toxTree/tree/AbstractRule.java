
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

*//**
 * Created on 2005-5-2

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree
 * Filename: AbstractRule.java
 */
package toxTree.tree;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;
import java.util.Observable;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.jchempaint.renderer.selection.IChemObjectSelection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleEditor;
import toxTree.core.Introspection;
import toxTree.core.XMLSerializable;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.XMLDecisionMethodException;
import toxTree.logging.TTLogger;
import toxTree.ui.tree.rules.RulePanel;
import ambit2.base.interfaces.IProcessor;

/**
 * An abstract class implementing {@link IDecisionRule} interface.
 * Used as a base class for all rules in {@link toxTree.apps.ToxTreeApp} application.
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public abstract class AbstractRule extends Observable implements IDecisionRule, XMLSerializable {

	public static transient String  ERR_STRUCTURENOTPREPROCESSED = "Structure should be preprocessed!";
	public static transient String MSG_YES="YES";
	public static transient String MSG_NO="NO";
    protected static transient TTLogger logger =  new TTLogger(AbstractRule.class);
    protected transient PropertyChangeSupport changes = null;
	protected String title = "";
	protected StringBuffer explanation = new StringBuffer();
	protected String id = "NA";
	protected int no = -1;
	//protected Object rule = null;
	protected String[] examples = {"",""};
	protected boolean residueIDHidden = false;
	protected boolean editable = true;
	
	/**
	 * Constructor
	 * 
	 */
	public AbstractRule() {
		super();
		id = "["+getClass().getName()+"]";
		title = "*";
	}

	/**
	 * @see toxTree.core.IDecisionRule#getID()
	 */
	public String getID() {
		return id;
	}

	/**
	 * @see toxTree.core.IDecisionRule#setID(String)
	 */
	public void setID(String id) {
	    //if (changes != null)	        changes.firePropertyChange("Id",this.id,id);
		this.id = id;
		setChanged();
		notifyObservers();		
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#getNo()
	 */
	public int getNum() {
		return no;
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#setNo(int)
	 */
	public void setNum(int no) {
		this.no = no;
		setChanged();
		notifyObservers();

	}
	/**
	 * @see toxTree.core.IDecisionRule#getTitle()
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @see toxTree.core.IDecisionRule#setTitle(java.lang.String)
	 */
	public void setTitle(String name) {
	    //if (changes != null)        changes.firePropertyChange("Name",this.title,title);
		this.title = name;
		setChanged();
		notifyObservers();		
	}

	/**
	 * @see toxTree.core.IDecisionRule#getExplanation()
	 */
	public String getExplanation() {
		return explanation.toString();
	}
	/**
	 * @see toxTree.core.IDecisionRule#setExplanation(java.lang.String)
	 */
	public void setExplanation(String message) {
		StringBuffer b = new StringBuffer(message);
	    if (changes != null)		
	        changes.firePropertyChange("Explanation",this.explanation,b);
		this.explanation = null;
		this.explanation = b;
		setChanged();
		notifyObservers();		
	}
	
	public boolean isImplemented() {
	    return false;
	}

	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("Q");
		b.append(id);
		b.append(".");
		b.append(title);
		if (!isImplemented()) b.append("\t(NOT IMPLEMENTED, ASSUMING FALSE!)");
		return b.toString();
	}
	
	public static IDecisionRule createRule(String className) throws DecisionMethodException {
		/*
		IDecisionRule object = null;
	      try {
	          Class classDefinition = Class.forName(className);
	          object = (IDecisionRule) classDefinition.newInstance();
	      } catch (Exception e) {
	          object = null;
	          throw new DecisionMethodException(e);
	      }
	      return object;
	      */
		try {
			Object object = Introspection.loadCreateObject(className);
			if (object == null) throw new DecisionMethodException("Error creating rule\t"+className);
			else return (IDecisionRule) object;
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}
	/**
	 * This is to ensure each derived class will provide an example of a molecule which resolves to the yes and no rule outcome
	 * @param yes
	 * @return {@link org.openscience.cdk.interfaces.Molecule}
	 */
	public static Molecule makeMolecule(boolean yes) throws DecisionMethodException {
		if (yes)
			throw new DecisionMethodException("This rules has no 'YES' example molecule defined!");
		else
			throw new DecisionMethodException("This rules has no 'NO' example molecule defined!");			
	}
	public void setExampleMolecule(IAtomContainer  mol, boolean ruleResult) {
	    int index;
	    if (ruleResult) index = 1; else index =0;
		SmilesGenerator g = new SmilesGenerator(true);
		//try {
		    if (mol instanceof Molecule)
		        examples[index] = g.createSMILES((org.openscience.cdk.Molecule) mol);
		    else 
		        examples[index] = "";
		setChanged();
		notifyObservers();
		/*
		} catch (CDKException x) {
		    examples[index] = "";
		}
		*/
	}
    public IMolecule getExampleMolecule(boolean ruleResult) throws DecisionMethodException {
	    SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
	    int index;
	    if (ruleResult) index = 1; else index =0;
		    try {
		        IMolecule mol = sp.parseSmiles(examples[index]);
		        if (ruleResult) mol.setID("'YES example'");
		        else mol.setID("'NO example'");
		        return mol;
		    } catch (InvalidSmilesException x) {
		        throw new DecisionMethodException(x);
		    }
	}
	/* (non-Javadoc)
     * @see toxTree.core.IDecisionRule#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        if (changes == null) changes = new PropertyChangeSupport(this);
        changes.addPropertyChangeListener(l);

    }
    /* (non-Javadoc)
     * @see toxTree.core.IDecisionRule#removePropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
	    if (changes != null)
	        changes.removePropertyChangeListener(l);

    }
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	    if (! (obj instanceof IDecisionRule)) return false;
		IDecisionRule r = (IDecisionRule) obj;
		return (r.getID().equals(id)) && (getTitle().equals(r.getTitle())) 
				&& (explanation.equals(explanation));
	}
	public void clearFlags(IAtomContainer mol) {
		if (mol == null) return;
		for (int i=0; i < mol.getAtomCount(); i++) {
			Map props = mol.getAtom(i).getProperties();
            Object[] o = props.keySet().toArray();
            for (int j=0; j < o.length;j++) {
				Object value = props.get(o[j]);
				if (value instanceof String) props.remove(o[j]);
			}
		}
		for (int i=0; i < mol.getBondCount(); i++) {
			Map props = mol.getBond(i).getProperties();
            Object[] o = props.keySet().toArray();
            for (int j=0; j < o.length;j++) {
                Object value = props.get(o[j]);
                if (value instanceof String) props.remove(o[j]);
            }
		}		
			
	}
	/*
	public boolean verifyRule(org.openscience.cdk.interfaces.AtomContainer  mol) throws DecisionMethodException {
		logger.info(toString());
		initialize(mol);
		return false;
	}
	*/
	public void hideResiduesID(boolean hide) {
		residueIDHidden = hide;
	}
	public boolean isResidueIDHidden() {
		return residueIDHidden;
	}
	/**
	 * See {@link IDecisionRule#getEditor()}
	 */
	public IDecisionRuleEditor getEditor() {
		RulePanel p = new RulePanel(this);
		p.setRule(this);
		return p;
	}
	
	@Override
	public Object clone()  throws CloneNotSupportedException {
		IDecisionRule obj = (IDecisionRule)Introspection.createObject(getClass().getName());
		obj.setTitle(title);
		//obj.setNum(no);
		obj.setID(id);
		obj.setExplanation(explanation.toString());
		
		return obj;
	}
	
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean value) {
		editable = value;
	}
	public void fromXML(Element xml)  throws XMLDecisionMethodException{
		// TODO Auto-generated method stub
		
	}
	public Element toShallowXML(Document document)  throws XMLDecisionMethodException {
		Element m = document.createElement(xmltag_RULE);
		m.setAttribute(xmltag_CLASS, getClass().getName());
		return m;
	}		
	public Element toXML(Document document)  throws XMLDecisionMethodException {
		Element e = document.createElement(XMLSerializable.xmltag_RULE);
		e.setAttribute(xmltag_NAME,getTitle());
		e.setAttribute(xmltag_ID,getID());
		Element explanation = document.createElement(XMLSerializable.xmltag_EXPLANATION);
		explanation.setTextContent(getExplanation());
		e.appendChild(explanation);
		return e;		
	}

	public String[] getExamples() {
		return examples;
	}

	public void setExamples(String[] examples) {
		this.examples = examples;
	}		
	
	public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
		return null;
	}
	
}
