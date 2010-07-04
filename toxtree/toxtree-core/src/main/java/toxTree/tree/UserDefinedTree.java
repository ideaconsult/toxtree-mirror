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

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Observable;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.qsar.DescriptorSpecification;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.result.IDescriptorResult;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionInteractive;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.core.IProcessRule;
import toxTree.core.Introspection;
import toxTree.exceptions.DRuleException;
import toxTree.exceptions.DRuleNotImplemented;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionMethodIOException;
import toxTree.exceptions.DecisionResultException;
import toxTree.query.MolFlags;
import ambit2.core.data.ArrayResult;
import ambit2.core.data.StringDescriptorResultType;

/**
 * A default decision tree class, implementing {@link toxTree.core.IDecisionMethod} interface.
 * It stores categories in a {@link toxTree.core.IDecisionCategories} and 
 * the tree itself in a {@link toxTree.tree.DecisionNodesList}.
 * The tree is editable, i.e. rules can be added , removed and modified. 
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-11-10
 */
public class UserDefinedTree extends AbstractTree implements IDecisionInteractive , IMolecularDescriptor {
	protected boolean editable = true;
	protected IDecisionNodesFactory nodesFactory;
    protected UserOptions options = UserOptions.YEStoALL;	
    public UserOptions getOptions() {
		return options;
	}
	public void setOptions(UserOptions options) {
		this.options = options;
	}
	public PropertyChangeListener getListener() {
		return listener;
	}
	public void setListener(PropertyChangeListener listener) {
		this.listener = listener;
	}
	protected PropertyChangeListener listener;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1399565169354017486L;

	/**
	 * 
	 */
	public UserDefinedTree(IDecisionNodesFactory nodesFactory) {
		super(new CategoriesList(), nodesFactory.createNodesList());
		setNodesFactory(new DecisionNodesFactory());
		treeRoot = 0;
	}
	public UserDefinedTree() {
		this(new DecisionNodesFactory());
	}
	
	public UserDefinedTree(IDecisionCategories classes, IDecisionRuleList rules){
		this();
		this.categories = classes;
		this.rules = rules;
		if (rules instanceof Observable) ((Observable)rules).addObserver(this);
		if (categories instanceof Observable) ((Observable)categories).addObserver(this);

	}
	/**
	 * @param classes
	 * @param customRules
	 * @param customTransitions
	 */
	public UserDefinedTree(IDecisionCategories classes, 
			String[] customRules,
			int[][] customTransitions,IDecisionNodesFactory nodesFactory) throws DecisionMethodException  {
		super(classes);
		setNodesFactory(nodesFactory);
		treeRoot = 0;
		this.rules = nodesFactory.createNodesList(getCategories(),customRules,customTransitions);
		if (rules instanceof Observable) ((Observable)rules).addObserver(this);
		if (categories instanceof Observable) ((Observable)categories).addObserver(this);
		
	}
	
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractTree#createRules()
	 */
	@Override
	protected IDecisionRuleList initRules() {
		treeRoot = 0;
		rules = nodesFactory.createNodesList();
		return rules;
	}
	
	/*

	public UserDefinedTree(IDecisionMethod tree) {
		super();
		IDecisionRule rule = tree.getTopRule();
		nodes.add(createFromTree(tree,rule));
		
		for (int i=0; i < nodes.size();i++)
			nodes.getNode(i).setVisited(false);
		
	}
	
	protected DecisionNode createFromTree(IDecisionMethod tree,IDecisionRule rule) {
		System.out.println(rule.getNum());
		IDecisionCategory categoryNo = tree.getCategory(rule,false);
		if (categoryNo != null) categories.addCategory(categoryNo);
		IDecisionCategory categoryYes = tree.getCategory(rule,true);
		if (categoryYes != null) categories.addCategory(categoryYes);
		
		IDecisionRule newRule= null;
		try {
			if (rule instanceof DecisionNode) newRule = (IDecisionRule)((DecisionNode) rule).getRule().clone();
			else newRule = (IDecisionRule)rule.clone();
		} catch (CloneNotSupportedException x) {
			newRule=null;
		}
		DecisionNode node = new DecisionNode(
				newRule,
				null,null,categoryNo,categoryYes);
				
		node.setVisited(true);
		nodes.addNode(node);
		IDecisionRule branch = tree.getBranch(rule,false);
		if (branch != null) 
			node.setBranch(false,createFromTree(tree,branch));
		branch = tree.getBranch(rule,true);
		if (branch != null) 
			node.setBranch(true,createFromTree(tree,branch));
		return node;
	}
	*/
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractTree#setTransitions(int[][])
	 */
	@Override
	protected void setTransitions(int[][] customTransitions) {
		DefaultCategory key = new DefaultCategory("",1);
		if (rules instanceof DecisionNodesList) 
			for (int i =0;i< customTransitions.length;i++) {
				DecisionNode node = ((DecisionNodesList)rules).getNode(i);
				if (customTransitions[i][0] != 0) node.setBranch(false,((DecisionNodesList)rules).getNode(customTransitions[i][0]-1));
				if (customTransitions[i][1] != 0) node.setBranch(true,((DecisionNodesList)rules).getNode(customTransitions[i][1]-1));

				if (customTransitions[i][2] != 0) {
					key.setID(customTransitions[i][2]);
					node.setCategory(false, categories.getCategory(key));
				} 
				if (customTransitions[i][3] != 0) {
					key.setID(customTransitions[i][3]);
					node.setCategory(true,  categories.getCategory(key));
				}
								
			}
	}


	protected void visitRules(DecisionNode rule) {
		
		if (rule==null) return;
		if (rule.isVisited()) return; //already visited;
		rule.setVisited(true);
		DecisionNode node = (DecisionNode )rule.getBranch(true);
		if (node != null) visitRules(node);
		node = (DecisionNode )rule.getBranch(false);
		if (node != null) visitRules(node);
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionMethod#hasUnreachableRules()
	 */
	public IDecisionRuleList hasUnreachableRules() {

		UnvisitedRules p = new UnvisitedRules(nodesFactory.createNodesList());
		try {
			walkRules(getTopRule(),p);
			return p.getUnvisited();
		} catch (Exception x) {
			return null;
		}
		
		/*

		DecisionNodesList nodes = (DecisionNodesList) rules;
		for (int i=0; i < nodes.size();i++) 
			nodes.getNode(i).setVisited(false);
		DecisionNode rule = nodes.getNode(treeRoot);

		visitRules(rule);
		for (int i=0; i < nodes.size();i++)
			if (!nodes.getNode(i).isVisited()) {
				if (unvisited == null) unvisited = nodesFactory.createNodesList();
				unvisited.addNode(nodes.getNode(i));
			}
		return unvisited;
		*/

	}
	
	protected void visitCategories(DecisionNode rule, IDecisionCategories usedCategories) {
		boolean[] b = new boolean[] {false,true};
		if (rule==null) return;
		if (rule.isVisited()) return; //already visited;
		rule.setVisited(true);
		
		for (int i=0; i < b.length;i++) {
			IDecisionCategory c = rule.getCategory(b[i]);
			if (c != null) usedCategories.addCategory(c);
		}
		
		DecisionNode node = (DecisionNode )rule.getBranch(true);
		if (node != null) visitCategories(node,usedCategories);
		node = (DecisionNode )rule.getBranch(false);
		if (node != null) visitCategories(node,usedCategories);
	}
	
	public IDecisionCategories hasUnusedCategories() {
		DecisionNodesList nodes = (DecisionNodesList) rules;
		for (int i=0; i < nodes.size();i++)
			nodes.getNode(i).setVisited(false);
		
		IDecisionCategories usedCategories = new CategoriesList();
		visitCategories(nodes.getNode(treeRoot), usedCategories);
		
		if (getCategories().size() == usedCategories.size()) return null;
		
		IDecisionCategories unusedCategories = new CategoriesList();
		Iterator<IDecisionCategory> iterator = getCategories().iterator();
		while (iterator.hasNext()) {
			IDecisionCategory c = iterator.next();
			if (usedCategories.getCategory(c)==null)
				unusedCategories.addCategory(c);
		}
		return unusedCategories;
		
	}
	
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractTree#verifyRules(org.openscience.cdk.AtomContainer, toxTree.core.IDecisionResult, int)
	 */
	@Override
	protected boolean verifyRules(IAtomContainer mol, IDecisionResult result,
			IDecisionRule topRule) throws DecisionMethodException {
		
		if (mol == null) return false;
		int classID = 0;
		//int ruleIndex = topRule;
		boolean answer = false;
		IDecisionRule rule = topRule;
		
		if (rule == null) throw new DecisionMethodException("Top rule not defined! Possible reason: Empty decision tree!");
		
		IAtomContainerSet molsToAnalyze = null;
		IAtomContainer molToAnalyze = mol;
		IDecisionCategory category_on_error = null;
        
		while (classID == 0) {
            category_on_error = null;
			//yes
			//rule = getRule(ruleIndex-1);
			try {
				if (molsToAnalyze == null) {
					
					rule.clearFlags(molToAnalyze);
					answer = rule.verifyRule(molToAnalyze);
				
			    
					/*
					 * 10.11.2005 - moved after addRuleResult 
					MolFlags mf = (MolFlags) molToAnalyze.getProperty(MolFlags.MOLFLAGS);
					if (mf == null) throw new DecisionMethodException(AbstractRule.ERR_STRUCTURENOTPREPROCESSED);
					molsToAnalyze = mf.getResidues();
					mf.setResidues(null); //clear that ; the next rule will set up them if necessary
					*/ 
				} else {
					//return verifyRules(molsToAnalyze,result,ruleIndex);	
					return verifyResidues(molsToAnalyze,result,rule);
				}
				
				
			} catch (DRuleNotImplemented x) {
				logger.error(x);
				if (falseIfRuleNotImplemented) answer = false;
				else throw new DecisionMethodException(x);
            } catch (DRuleException x) {
                logger.error(x);
                category_on_error =x.getCategory2assign();
                answer = x.isAnswer();
            }
			try {
				result.addRuleResult(rule,answer,mol);

				MolFlags mf = (MolFlags) molToAnalyze.getProperty(MolFlags.MOLFLAGS);
				if (mf == null) throw new DecisionMethodException(AbstractRule.ERR_STRUCTURENOTPREPROCESSED);
				
				molsToAnalyze = mf.getResidues();
				mf.setResidues(null); //clear that ; the next rule will set up them if necessary
				
			} catch (DecisionResultException x)  {
			    throw new DecisionMethodException(x);
			}
			
			IDecisionCategory nextCategory = null;
            if (category_on_error != null) nextCategory = category_on_error;
            else nextCategory = getCategory(rule,answer);
			if (nextCategory != null)
				result.setCategory(nextCategory);
			
			IDecisionRule nextRule = getBranch(rule,answer);
			if (nextRule == null) {
				return true;				
			} else rule = nextRule; 
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionMethod#getBranch(toxTree.core.IDecisionRule, boolean)
	 */
	public IDecisionRule getBranch(IDecisionRule rule, boolean answer) {
		if (rule instanceof DecisionNode) 
			return( (DecisionNode) rule).getBranch(answer);
		else return null; //undefined
	}
	public IDecisionRule getBranch(int ruleId, boolean answer) {
		DecisionNodesList nodes = (DecisionNodesList) rules;
		return (nodes.getNode(ruleId)).getBranch(answer);
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionMethod#getCategory(toxTree.core.IDecisionRule, boolean)
	 */
	public IDecisionCategory getCategory(IDecisionRule rule, boolean answer) {
		if (rule instanceof DecisionNode) 
			return( (DecisionNode) rule).getCategory(answer);
		else return null; //undefined
	}
	public IDecisionCategory getCategory(int ruleId, boolean answer) {
		DecisionNodesList nodes = (DecisionNodesList) rules;		
		return (nodes.getNode(ruleId)).getCategory(answer);
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean value) {
		editable = value;
	}
	@Override
	public IDecisionRule getRule(int id) {
		DecisionNodesList nodes = (DecisionNodesList) rules;
		return nodes.getNode(id);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		//TODO support clone
		throw new CloneNotSupportedException();
	}
	@Override
	public String toString() {
		return getTitle();
	}
    @Override
    public void setParameters(IAtomContainer mol) {
        if (getInteractive()) {
            JComponent c = optionsPanel(mol);
            if (c != null)
                JOptionPane.showMessageDialog(null,c,getTitle(),JOptionPane.PLAIN_MESSAGE);
        } 
    }
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		Thread.currentThread().setContextClassLoader(Introspection.getLoader());
		in.defaultReadObject();
		if (rules instanceof Observable) ((Observable)rules).addObserver(this);
		categories.clear();
		for (int i=0; i < rules.size();i++) {
			IDecisionCategory c = getCategory(rules.get(i), true);
			if ((c !=null) && (categories.indexOf(c)==-1)) categories.add(c);
			c = getCategory(rules.get(i), false);
			if ((c !=null) && (categories.indexOf(c)==-1)) categories.add(c);			
		}
		if (categories instanceof Observable) ((Observable)categories).addObserver(this);
		modified = false;
	}
	public IDecisionNodesFactory getNodesFactory() {
		return nodesFactory;
	}
	public void setNodesFactory(IDecisionNodesFactory nodesFactory) {
		this.nodesFactory = nodesFactory;
	}
	public boolean getInteractive() {
		return options.isInteractive();
	}
	public void setInteractive(boolean value) {
		setOptions(options.setInteractive(value));
	}
	protected ArrayResult createArrayResult(int length) {
		return new ArrayResult<String>(new String[length]);
	}
	protected void setArrayValue(ArrayResult result, int index, IAtomContainer mol,String  propertyName) {
		result.set(index,mol.getProperty(propertyName).toString());
	}
	public DescriptorValue calculate(IAtomContainer mol) {
		String[] descriptorNames = null;
		IDecisionResult result = createDecisionResult();
		try {
			result.classify(mol);
			result.assignResult(mol);
			
			String[] d = result.getResultPropertyNames();
			descriptorNames = new String[d.length+1];			
			ArrayResult value = createArrayResult(descriptorNames.length);
			for (int i=0; i <  d.length;i++)
				try {
					setArrayValue(value, i, mol, d[i]);
					descriptorNames[i]=d[i];
				} catch (Exception x) {
					value.set(i,null);
				}
			StringBuffer b = result.explain(true);	
			value.set(descriptorNames.length-1, b.toString());
			descriptorNames[descriptorNames.length-1] = String.format("%s#explanation", descriptorNames[0]) ;
			return new DescriptorValue(
						getSpecification(),
						getParameterNames(),
						getParameters(),
						value,
						descriptorNames
						);				

		} catch (DecisionResultException x) {
			return new DescriptorValue(
					getSpecification(),
					getParameterNames(),
					getParameters(),
					null,
					descriptorNames,
					x
					);				
		}
	}
	//not sure what to return here; names depend on implementation
	public String[] getDescriptorNames() {
		IDecisionResult result = createDecisionResult();
		return result.getResultPropertyNames();
	}
	public IDescriptorResult getDescriptorResultType() {
		return new StringDescriptorResultType("");
	}
	public String[] getParameterNames() {
		return null;
	}
	public Object[] getParameters() {
		return null;
	}
	public Object getParameterType(String arg0) {
		return null;
	}
	public void setParameters(Object[] arg0) throws CDKException {
	
	}
	public DescriptorSpecification getSpecification() {
        return new DescriptorSpecification(
                "http://toxtree.sourceforge.net",
                getTitle(),
                this.getClass().getName(),                
                "Toxtree plugin");
	}
	public void removeListener() {
		this.listener = null;
		IDecisionRuleList rules = getRules();
		for (int i=0; i < rules.size();i++) {
			IDecisionRule rule = rules.get(i);
			if (rule instanceof DecisionNode) 
				rule = ((DecisionNode)rule).getRule();
			if (rule instanceof IDecisionInteractive) 
				((IDecisionInteractive)rule).removeListener();
		}		
	}
	
}

class UnvisitedRules implements IProcessRule {
	protected DecisionNodesList unvisited;
	protected IDecisionMethod tree;
	public UnvisitedRules(DecisionNodesList unvisited) {
		setUnvisited(unvisited);
		
	}
	public void init(IDecisionMethod method) throws DecisionMethodIOException {
		unvisited.clear();
		setTree(method);
	}
	public Object process(IDecisionMethod method, IDecisionRule rule) throws DecisionMethodIOException {
		if (rule instanceof DecisionNode) {
			((DecisionNode)rule).setVisited(true);
		}
		return null;
	}
	public void done() throws DecisionMethodIOException {
		if (tree == null) return;
		DecisionNodesList nodes = (DecisionNodesList) tree.getRules();
		for (int i=0; i < nodes.size();i++) {
			IDecisionRule rule = nodes.getNode(i);
			if (!nodes.getNode(i).isVisited()) {
				if (unvisited == null) unvisited = new DecisionNodesList();
				unvisited.addNode(nodes.getNode(i));
			}
		}
	}
	public DecisionNodesList getUnvisited() {
		return unvisited;
	}
	public void setUnvisited(DecisionNodesList unvisited) {
		this.unvisited = unvisited;
	}
	public IDecisionMethod getTree() {
		return tree;
	}
	public void setTree(IDecisionMethod tree) {
		this.tree = tree;
		DecisionNodesList nodes = (DecisionNodesList) tree.getRules();
		for (int i=0; i < nodes.size();i++) 
			nodes.getNode(i).setVisited(false);		
	}
	
	
	
	
	
}
