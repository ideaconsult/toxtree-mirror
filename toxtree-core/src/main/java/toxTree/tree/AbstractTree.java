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
package toxTree.tree;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.jchempaint.renderer.selection.IChemObjectSelection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionMethodEditor;
import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.core.IProcessRule;
import toxTree.core.XMLSerializable;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.exceptions.MolAnalyseException;
import toxTree.exceptions.XMLDecisionMethodException;
import toxTree.logging.TTLogger;
import toxTree.query.MolAnalyser;
import toxTree.ui.tree.TreeEditorPanel;
import toxTree.ui.tree.TreeOptions;
import ambit2.base.exceptions.AmbitException;
import ambit2.base.interfaces.IProcessor;
import ambit2.core.io.CompoundImageTools;


/**
 * An astract class, implementing {@link toxTree.core.IDecisionMethod} interface.
 * Used as a base class for the decision trees in {@link toxTree.apps.ToxTreeApp}.
 * The default editor is {@link TreeEditorPanel}.
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-4-30
 */
public abstract class AbstractTree extends Observable implements IDecisionMethod, Observer, XMLSerializable {
	protected static transient TTLogger logger =  new TTLogger(AbstractTree.class);
	protected Dimension imageSize = new Dimension(150,150);
    public Dimension getImageSize() {
		return imageSize;
	}
	public void setImageSize(Dimension imageSize) {
		this.imageSize = imageSize;
	}
	protected transient  PropertyChangeSupport changes = null;
	protected IDecisionRuleList rules;
	
	protected IDecisionCategories categories = null; 
	protected boolean falseIfRuleNotImplemented = true;
	
	protected int treeRoot = 1;
	protected String name = "";
	protected String explanation = "";
	protected transient boolean modified = false;
    protected int priority = Integer.MAX_VALUE;
//	protected transient IDecisionMethodEditor editor = null;
	/**
	 * Constructor
	 * 
	 */
	public AbstractTree() {
		this(new CategoriesList(),new RulesList());
	}
	public AbstractTree(IDecisionCategories categories) {
		this(categories,new RulesList());
	}	
	public AbstractTree(IDecisionCategories categories,IDecisionRuleList rules) {
		super();
		setRules(rules);
		setCategories(categories); 
		setTitle(getClass().getName());
		setExplanation(getTitle());
		setChanged();
		notifyObservers();		
	}	
	/**
	 * 
	 *
	public AbstractTree(
			IDecisionCategories classes,
        	String[] customRules, int[][] customTransitions) throws DecisionMethodException {
	    this(categories,customRules,customTransitions);
	}    
	public AbstractTree(
	        	String[] customRules, int[][] customTransitions) throws DecisionMethodException {
		this();
		setRules(customRules);
		setTransitions(customTransitions);
		setChanged();
		notifyObservers();		
	}
	*/
	/*TODO
	public AbstractTree(IDecisionMethod method) {
		method.getRules()
		method.g
	}
	*/
	protected abstract IDecisionRuleList initRules();
	
	protected void setRules(String[] customRules) throws DecisionMethodException {
		rules.setRules(customRules);
		setChanged();
		notifyObservers();
		if (changes != null ) {
			changes.firePropertyChange("Rules", rules,null);
		}
	}
	protected abstract void setTransitions(int[][] customTransitions);
	
	/**
	 * returns the decision tree title (e.g. "Cramer rules")
	 */
	public String getTitle() {
		return name;
		
	}
	/**
	 * sets decision tree title  (e.g. "Cramer rules")
	 */
	public void setTitle(String value) {
		this.name = value;

	}
	/**
	 * @see toxTree.core.IDecisionMethod#addDecisionRule(toxTree.core.IDecisionRule)
	 */
	public void addDecisionRule(IDecisionRule rule)
			throws DecisionMethodException {
		throw new DecisionMethodException("Not allowed!");

	}
	/**
	 * @see toxTree.core.IDecisionMethod#setDecisionRule(toxTree.core.IDecisionRule)
	 */
	public void setDecisionRule(IDecisionRule rule)
			throws DecisionMethodException {
		throw new DecisionMethodException("Not allowed!");
	}
	/**
	 * @see IDecisionMethod#explainRules(IDecisionResult,boolean)
	 */
	public StringBuffer explainRules(IDecisionResult result,boolean verbose) throws DecisionMethodException{
		try {
			StringBuffer b = result.explain(verbose);
			return b;
		} catch (DecisionResultException x) {
			throw new DecisionMethodException(x);
		}
		
	}
	
	

	protected boolean verifyResidues(IAtomContainerSet mols,IDecisionResult result,IDecisionRule rule) throws DecisionMethodException {		
		logger.info("Start processing residues\t",mols.getAtomContainerCount());
		boolean r = true;
		
		for (int i=0; i < mols.getAtomContainerCount();i++) {
			logger.info("Residue\t",Integer.toString(i+1));
			try {
				MolAnalyser.analyse(mols.getAtomContainer(i));
				
				//r = r && verifyRules(mols.getAtomContainer(i),result,ruleIndex);
				r = r && verifyRules(mols.getAtomContainer(i),result,rule);
			} catch (Exception x) {
				throw new DecisionMethodException(x);
			}
		}
		logger.info("Done processing residues.");
		return r;
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRules(IAtomContainer  mol,IDecisionResult result) throws DecisionMethodException {
		return verifyRules(mol,result,getTopRule());
	}
	
	/**
	 * abstract method, to be implemented in the child class
	 * @param mol  - {@link org.openscience.cdk.interfaces.AtomContainer} to be analyzed 
	 * @param result - {@link IDecisionResult}  the result from the decision tree application  
	 * @param rule - the starting rule 
	 * @return
	 * @throws DecisionMethodException
	 */
	protected abstract boolean verifyRules(IAtomContainer  mol,IDecisionResult result, IDecisionRule rule) throws DecisionMethodException;

	/**
	 * 
	 */
	public boolean classify(IAtomContainer  mol,IDecisionResult result) throws DecisionMethodException {
		result.clear();
		result.setDecisionMethod(this);
		result.setEstimating();
		try {
			MolAnalyser.analyse(mol);
		} catch (MolAnalyseException x) {
		    logger.error(x);
		    throw new DecisionMethodException(x);
		}
		categories.selectAll(false);
		boolean r = verifyRules(mol,result); 		
		//result.setEstimated(true);
		return r;
	}
    /**
     * Can be used to display some options before applying the rules.
     * @param mol
     */
	public abstract void setParameters(IAtomContainer mol)   ;
    public JComponent optionsPanel(IAtomContainer atomContainer) {
    	return new TreeOptions(this,atomContainer);
    	/*
        ArrayList<JComponent> components = new ArrayList<JComponent>();
        for (int i=0;i< rules.size(); i++) {
            IDecisionRule rule = rules.getRule(i);
            if (rule instanceof IDecisionInteractive) {
                JComponent c = ((IDecisionInteractive) rule).optionsPanel(atomContainer);
                if (c != null) components.add(c);
            }
        }    
        if (components.size()>0) {
            JPanel p = new JPanel();
            JOutlookBar outlook = new JOutlookBar();
            outlook.setTabPlacement(JTabbedPane.LEFT);
            p.setLayout(new BoxLayout(p,BoxLayout.PAGE_AXIS));
            for (int i=0; i < components.size();i++)
            	outlook.add(components.get(i).toString(),components.get(i));
            p.add(outlook);
                //p.add(components.get(i));
            return new PropertyEditor(atomContainer,new JScrollPane(p));
        } else return null;
        */
    }

	public int getNumberOfRules() {
		if (rules != null) return rules.size();
		else return 0;
	}
	/**
	 * @see toxTree.core.IDecisionMethod#getRule(int)
	 */
	public IDecisionRule getRule(int id) {
		if (rules == null) return null;
		return rules.getRule(id);
	}
	/**
	 * @see toxTree.core.IDecisionMethod#getRule(java.lang.String)
	 */
	public IDecisionRule getRule(String name) {
		if (rules == null) return null;
		for (int i = 0; i <rules.size(); i++) 
			if (getRule(i).getTitle().equals(name)) 
				return getRule(i);
		return null;	
	}

	/**
	 * 
	 * @param os
	 * @throws IOException
	 */
	public void printToStream(OutputStream os) throws IOException {
		DataOutputStream out = new DataOutputStream(os);
		for (int i = 0; i < rules.size(); i++) {
			out.writeChars(rules.getRule(i).toString());
			out.writeBytes("\n");
	    }	
		
	}
	/**
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public void printResults(String filename) throws IOException {
		FileOutputStream o = new FileOutputStream(filename);
		printToStream(o);
		o.close();
	}
		
	public boolean isFalseIfRuleNotImplemented() {
		return falseIfRuleNotImplemented;
	}
	public void setFalseIfRuleNotImplemented(boolean falseIfRuleNotImplemented) {
		this.falseIfRuleNotImplemented = falseIfRuleNotImplemented;
		setChanged();
		notifyObservers();
	}
	
	/* (non-Javadoc)
     * @see toxTree.core.IDecisionMethod#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        if (changes == null) 		changes = new PropertyChangeSupport(this);
        changes.addPropertyChangeListener(l);
    }
    /* (non-Javadoc)
     * @see toxTree.core.IDecisionMethod#removePropertyChangeListener(java.beans.PropertyChangeListener)
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
		IDecisionMethod m = (IDecisionMethod) obj;
		if (getTitle().equals(m.getTitle()) &&
			(getNumberOfClasses() == m.getNumberOfClasses()) &&
			(getNumberOfRules() == m.getNumberOfRules())) {
			
			if (!categories.equals(m.getCategories())) return false;

			int nr = getNumberOfRules();
			if (nr != m.getNumberOfRules()) return false;
			for (int i = 0; i < nr; i++)
				if (!getRule(i).equals(m.getRule(i))) return false;
			
			//TODO compare transitions
			return true;	
		} else return false;
		
	}
	
	/* (non-Javadoc)
     * @see toxTree.core.IDecisionMethod#getCategories()
     */
    public IDecisionCategories getCategories() {
        return categories;
    }
    @Override
	public String toString() {
        return getTitle();
    }
    /* (non-Javadoc)
	 * @see toxTree.core.IDecisionMethod#createDecisionResult()
	 */
	public IDecisionResult createDecisionResult() {
		IDecisionResult result =  new TreeResult();
		result.setDecisionMethod(this);
		return result;
	}
	public int getNumberOfClasses() {
	    return categories.size();
	}	
	public IDecisionRuleList getRules() {
		return rules;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (o == rules) { //if rules had chenged , fire change event for the tree itself
			setChanged();
			notifyObservers();
		} else if (o instanceof IDecisionCategories) {
			setChanged();
			notifyObservers(o);			
		}

	}
	public IDecisionRule getTopRule() {
		return getRule(0);
	}
	/* (non-Javadoc)
	 * @see java.util.Observable#setChanged()
	 */
	@Override
	protected synchronized void setChanged() {
		super.setChanged();
		//setModified(true);
	}
	/* (non-Javadoc)
	 * @see java.util.Observable#clearChanged()
	 */
	@Override
	protected synchronized void clearChanged() {
		super.clearChanged();
		//setModified(false);
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#isModified()
	 */
	public boolean isModified() {
		return modified;
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#setModified(boolean)
	 */
	public void setModified(boolean value) {
		modified = value;
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		if (rules instanceof Observable) ((Observable)rules).addObserver(this);
		if (categories instanceof Observable) ((Observable)categories).addObserver(this);
		modified = false;
	}
	public IDecisionMethodEditor getEditor() {
        return new TreeEditorPanel(this);
		//return new EditTreeFrame(this);
	}
	public void fromXML(Element xml)  throws XMLDecisionMethodException{
		// TODO Auto-generated method stub
		
	}
	public Element toShallowXML(Document document)  throws XMLDecisionMethodException {
		Element m = document.createElement(xmltag_METHOD);
		m.setAttribute(xmltag_CLASS, getClass().getName());
		return m;
	}		
	public Element toXML(Document document)  throws XMLDecisionMethodException {
		Element e = document.createElement(XMLSerializable.xmltag_METHOD);
		
		e.setAttribute(xmltag_NAME,getTitle());
		Element explanation = document.createElement(XMLSerializable.xmltag_EXPLANATION);
		explanation.setTextContent(getExplanation());
		e.appendChild(explanation);
		e.setAttribute(xmltag_TREEROOT,Integer.toString(treeRoot));
		
		if (rules instanceof XMLSerializable) 
			e.appendChild(((XMLSerializable) rules).toXML(document));
		else throw new XMLDecisionMethodException(rules.getClass().getName() + " not XML serializable!");
		if (categories instanceof XMLSerializable) 
			e.appendChild(((XMLSerializable) categories).toXML(document));
		else throw new XMLDecisionMethodException(categories.getClass().getName() + " not XML serializable!");
		return e;
	}
	/*
	public void readExternal(ObjectInput arg0) throws IOException, ClassNotFoundException {
		
		
	}
	public void writeExternal(ObjectOutput out) throws IOException {
		try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            Element e = toXML(doc);
		    doc.appendChild(e);
            Source source = new DOMSource(doc);
            Result result = new StreamResult((ObjectOutputStream)out);
            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);			            
	} catch (Exception x) {
		x.printStackTrace();
	}		
	}
	*/
	public void setRules(IDecisionRuleList rules) {
		if ((this.rules != null) && (this.rules instanceof Observable)) ((Observable)rules).deleteObserver(this);
		this.rules = rules;
		if ((rules != null) && (rules instanceof Observable)) ((Observable)rules).addObserver(this);
	}
	public void setCategories(IDecisionCategories categories) {
		if ((this.categories != null) && (this.categories instanceof Observable)) ((Observable)categories).deleteObserver(this);
		this.categories = categories;
		if ((categories != null) && (categories instanceof Observable)) ((Observable)categories).addObserver(this);		

	}
    public synchronized int getPriority() {
        return priority;
    }
    public synchronized void setPriority(int priority) {
        this.priority = priority;
    }
    
    public int testRulesWithSelector() throws Exception {
	    int nr = getNumberOfRules();
	    int na = 0;
	    for (int i = 0; i < nr; i++) {
	        IDecisionRule rule = rules.getRule(i);
	        if (rule.getSelector()==null){
	        	System.err.println(rule.toString());
	        	na++;
	        } else {
	        	IAtomContainer a = null;
	        	try {
	        		a = rule.getExampleMolecule(true);
	        		if (a==null) continue;
	        	} catch (Exception x) {
	        		continue;
	        	}
	        	IChemObjectSelection hit = rule.getSelector().process(a);
	        	if (hit==null)
	        		System.out.println(rule.toString());
	        	else if (hit.getConnectedAtomContainer()==null)
	        		System.out.println(rule.toString());
	        	else if (hit.getConnectedAtomContainer().getAtomCount()==0)
	        		System.out.println(rule.toString());

	        }
	    }
	    return na;
	}
    public void walkRules(IDecisionRule rule,IProcessRule processor)throws  DecisionMethodException {
    	ArrayList<Integer> visited = new ArrayList<Integer>();
    	processor.init(this);
    	walkRules(rule,visited,processor);
    	processor.done();
    }
    protected void walkRules(IDecisionRule rule, ArrayList<Integer> visited,IProcessRule processor) throws DecisionMethodException {
    	if (rule == null) return;
    	else {
    		if (visited.indexOf(rule.getNum()) == -1) {
    			visited.add(rule.getNum());
   				processor.process(this, rule);

    	    	final boolean[] answers = {true,false};
    	    	for (int i=0; i < answers.length;i++) {
    	    		IDecisionRule nextrule = getBranch(rule, answers[i]);
    	    		walkRules(nextrule,visited,processor);
    	    	}    	    	
    		} else return; //visited
    	}
    	

    }    
    public BufferedImage getImage(IAtomContainer mol) throws AmbitException {
    	return getImage(mol,null,150,150,false);
    }
    public BufferedImage getImage(IAtomContainer mol,String ruleID,int width,int height,boolean atomnumbers) throws AmbitException {
    	IDecisionRuleList rules = getRules();
    	IProcessor<IAtomContainer,IChemObjectSelection> selector = null;
    	for (int i=0; i < rules.size();i++) {
    		IDecisionRule rule = rules.get(i);
    		if (rule.getID().equals(ruleID)) {
    			selector = rule.getSelector();
    			break;
    		}
    	}
    	CompoundImageTools tools = new CompoundImageTools(new Dimension(width,height));
    	return tools.getImage(mol, selector, true, atomnumbers);
    }
}
