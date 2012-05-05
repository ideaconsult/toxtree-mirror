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
/**
 * Created on 2005-5-2

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree
 * Filename: DecisionNode.java
 */
package toxTree.tree;

import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleEditor;
import toxTree.core.IDecisionTransition;
import toxTree.exceptions.DecisionMethodException;
import ambit2.base.interfaces.IProcessor;

/**
 * A decision node to be used in {@link UserDefinedTree}. Implements {@link IDecisionRule}
 * and {@link toxTree.core.IDecisionTransition}. <p>
 * A decision node consists of a rule {@link IDecisionRule} and decision nodes {@link DecisionNode}
 * to be followed, or categories {@link IDecisionCategory} to be assigned if the rule provides YES or NO answer.  
 *
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class DecisionNode extends Observable implements IDecisionRule,IDecisionTransition, Observer {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 592150483005019453L;
	protected IDecisionRule rule;
	protected DecisionNode nodes[] = {null,null};
	protected IDecisionCategory categories[] = {null,null};
	protected transient boolean visited = false;
	/**
	 * Constructor
	 * 
	 */
	public DecisionNode() {
		super();
	}
	/**
	 * Constructs a decision node with rule, null next nodes.
	 * @param rule
	 */
	public DecisionNode(IDecisionRule rule) {
		this();
		setRule(rule);
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#isEditable()
	 */
	public boolean isEditable() {
		if (rule != null)
			return rule.isEditable();
		else return true;
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#setEditable(boolean)
	 */
	public void setEditable(boolean value) {
		if (rule != null)
			rule.setEditable(value);

	}
	/**
	 * 
	 * Constructs a decision node with rule, nodeNo at branch NO and nodeYes at branch YES.
	 * Categories are assigned null value.
	 * @param rule
	 * @param nodeNo
	 * @param nodeYes
	 */
	public DecisionNode(IDecisionRule rule,
			DecisionNode nodeNo, DecisionNode nodeYes) {
		this(rule);
		setBranch(false,nodeNo);
		setBranch(true,nodeYes);
	}
	/**
	 * 
	 * @param rule - the rule
	 * @param nodeNo - the node at branch "No"
	 * @param nodeYes - the node at branch "Yes"
	 * @param categoryNo - the Category at branch "No"
	 * @param categoryYes - the Category at branch "Yes"
	 */
	public DecisionNode(
			IDecisionRule rule,
			DecisionNode nodeNo, DecisionNode nodeYes,
			IDecisionCategory categoryNo, IDecisionCategory categoryYes) {
		this(rule,nodeNo,nodeYes);
		categories[0] = categoryNo;
		categories[1] = categoryYes;
	}
	/**
	 * 
	 * @return rule
	 */
	public IDecisionRule getRule() {
		return rule;
	}
	/**
	 * Sets rule
	 * @param rule
	 */
	public void setRule(IDecisionRule rule) {
		if ((this.rule != null) && (this.rule instanceof Observable))
			((Observable)this.rule).deleteObserver(this);
		this.rule = rule;
		if ((this.rule != null) && (this.rule instanceof Observable))
			((Observable)this.rule).addObserver(this);		
		setChanged();
		notifyObservers();
	}
	/**
	 * returns the decision rule at the branch No if answer==false or
	 * the decision rule at the branch Yes if answer =true
	 */
	public IDecisionRule getBranch(boolean answer) {
		if (answer) return nodes[1]; else return nodes[0];
	}
	/**
	 * Sets the decision rule at the branch No if answer==false or
	 * the decision rule at the branch Yes if answer =true
	 *  
	 * @param answer  - selects which branch to be set
	 * @param node  - the next node to set
	 */
	public void setBranch(boolean answer, IDecisionRule node) {
		if (node instanceof DecisionNode) {
			int index = 0;
			if (answer) index = 1; else index = 0;
			if (node != null) categories[index] = null;
			nodes[index] = (DecisionNode)node; 
		}
		//else setRule(node);
		setChanged();
		notifyObservers();		
	}	
	/**
	 * returns the decision category at the branch No if answer==false or
	 * the category at the branch Yes if answer =true 
	 */
	public IDecisionCategory getCategory(boolean answer) {
		if (answer) return categories[1]; else return categories[0];
	}
	/**
	/**
	 * Sets the category at the branch No if answer==false or
	 * the category at the branch Yes if answer =true
	 * @param answer
	 * @param category
	 */
	public void setCategory(boolean answer, IDecisionCategory category) {
		if (answer) {
			if (category != null) {
				categories[1] = category;
				nodes[1] = null;
			}
		} else {
			if (category != null) {
				categories[0] = category;
				nodes[0] = null;
			}
		}
		setChanged();
		notifyObservers();		
	}
	/**
	 * Returns string representation of the decision node
	 * if (verbose) returns getId() + "." + getName()
	 * else returns "Q"+getId();
	 * @param verbose  
	 * @return String
	 */
	public String toString(boolean verbose) {
		if (verbose) {
			StringBuffer b = new StringBuffer();
			//b.append(rule.getNum());
			//b.append("\t");
			b.append(rule.toString());
			
			b.append("\tif NO\t");
			if (getBranch(false) == null) b.append(getCategory(false));
			else {
				b.append("Q");
				b.append(getBranch(false).getID());
			}
			b.append("\tif YES\t");
			if (getBranch(true) == null) b.append(getCategory(true));
			else {
				b.append("Q");
				b.append(getBranch(true).getID());
			}
			return b.toString();
		} else return getRule().toString();
	}
	@Override
	public String toString() {
		return toString(false);
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#getExampleMolecule(boolean)
	 */
	public IAtomContainer getExampleMolecule(boolean ruleResult)
			throws DecisionMethodException {
		if (rule == null) return null;
		else return rule.getExampleMolecule(ruleResult);
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#getExplanation()
	 */
	public String getExplanation() {
		if (rule == null) return "";
		else return rule.getExplanation();
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#getID()
	 */
	public String getID() {
		if (rule == null) return "NA";
		else return rule.getID();
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#getName()
	 */
	public String getTitle() {
		if (rule == null) return "";
		else return rule.getTitle();
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#getNum()
	 */
	public int getNum() {
		if (rule == null) return -1;
		else return rule.getNum(); 
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#isImplemented()
	 */
	public boolean isImplemented() {
		if (rule == null) return false;
		else return rule.isImplemented();
	}
	
	public void setExampleMolecule(IAtomContainer mol, boolean ruleResult) {
		if (rule != null) rule.setExampleMolecule(mol,ruleResult);
		setChanged();
		notifyObservers();
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener l) {
		// TODO Auto-generated method stub

	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener l) {
		// TODO Auto-generated method stub

	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#setExplanation(java.lang.String)
	 */
	public void setExplanation(String message) {
		if (rule != null) rule.setExplanation(message);
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#setID(java.lang.String)
	 */
	public void setID(String id) {
		if (rule != null) rule.setID(id);
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#setName(java.lang.String)
	 */
	public void setTitle(String name) {
		if (rule != null) rule.setTitle(name);
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#setNum(int)
	 */
	public void setNum(int no) {
		if (rule != null) rule.setNum(no);
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRule#verifyRule(org.openscience.cdk.AtomContainer)
	 */
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (rule == null) return false;
		else return rule.verifyRule(mol);
	}
	
	/**
	 * @return Returns the visited.
	 */
	public synchronized boolean isVisited() {
		return visited;
	}
	/**
	 * @param visited The visited to set.
	 */
	public synchronized void setVisited(boolean visited) {
		this.visited = visited;
	}
	public void clearFlags(IAtomContainer mol) {
		rule.clearFlags(mol);
		
	}
	public void hideResiduesID(boolean hide) {
		rule.hideResiduesID(hide);
	}
	public boolean isResidueIDHidden() {
		return rule.isResidueIDHidden();
	}
	public IDecisionRuleEditor getEditor() {
		return getRule().getEditor();
	}
	@Override
	public Object clone()  throws CloneNotSupportedException {
		throw new CloneNotSupportedException(this.getClass().getName());
	}
	/*
	public Object clone()  throws CloneNotSupportedException {
		DecisionNode obj = (DecisionNode)super.clone();
		if (rule != null) obj.setRule((IDecisionRule)rule.clone());
		else obj.setRule(null);
		for (int i=0;i<nodes.length;i++) {
			if (nodes[i] != null) {
				
			} else obj.setBranch(i==1,null);
		
		protected DecisionNode nodes[] = {null,null};
		protected IDecisionCategory categories[] = {null,null};
		protected transient boolean visited = false;
		
		return obj;
	}
	*/
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	    if (! (obj instanceof DecisionNode)) return false;
	    return getRule().equals(((DecisionNode) obj).getRule());
	}
	public void update(Observable arg0, Object arg1) {
		setChanged();
		notifyObservers(arg0);
		
	}
    public synchronized IDecisionCategory[] getCategories() {
        return categories;
    }
    public synchronized void setCategories(IDecisionCategory[] categories) {
        this.categories = categories;
    }
    public synchronized DecisionNode[] getNodes() {
        return nodes;
    }
    public synchronized void setNodes(DecisionNode[] nodes) {
        this.nodes = nodes;
    }
    public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
    	return rule.getSelector();
    }
}
