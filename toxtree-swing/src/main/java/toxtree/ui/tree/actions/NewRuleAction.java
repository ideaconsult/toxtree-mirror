/*
Copyright (C) 2005-2006  

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
package toxtree.ui.tree.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFrame;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.exceptions.DecisionMethodException;
import toxTree.io.Tools;
import toxTree.tree.DecisionMethodsList;
import toxTree.tree.DecisionNode;
import toxTree.tree.DefaultCategory;
import toxTree.tree.UserDefinedTree;
import toxTree.tree.categories.DefaultClass1;
import toxTree.tree.categories.DefaultClass2;
import toxTree.tree.rules.RuleInitAlertCounter;
import toxtree.ui.wizard.DecisionNodeWizard;

/**
 * Shows a list of available rules and adds the selected one to the list of rules.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-9
 */
public class NewRuleAction extends AbstractTreeAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -706728649818509576L;

	/**
	 * @param tree
	 */
	public NewRuleAction(IDecisionMethod tree) {
		this(tree,"New Rule");
	}

	/**
	 * @param tree
	 * @param name
	 */
	public NewRuleAction(IDecisionMethod tree, String name) {
		this(tree, name,Tools.getImage("tree_add.png"));
	}

	/**
	 * @param tree
	 * @param name
	 * @param icon
	 */
	public NewRuleAction(IDecisionMethod tree, String name, Icon icon) {
		super(tree, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Creates a new rule and adds it to the tree.");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		JFrame frame = getParentFrame();
		
		/*
		o = SelectListDialog.selectNewNode(parent,this.getClass().getClassLoader());
		if (o != null) {
			tree.getRules().add(updateNode(tree,(DecisionNode) o));
//			nodePanel.setNode(node);
			}
		*/
		DecisionNodeWizard wizard =  DecisionNodeWizard.createWizard(frame, tree, DecisionNodeWizard.pageRuleOptions);
		wizard.setTitle("Create new rule");
		if (wizard.showModalDialog() == 0) { 
            Object o = wizard.getSelectedObject();
            if (o != null)  {
                if (o instanceof DecisionNode)
                	//tree.getRules().add(updateNode(tree,(DecisionNode) o));
                	updateNode(tree,(DecisionNode) o);
                else if (o instanceof IDecisionRule) {
                	updateNode(tree, new DecisionNode((IDecisionRule)o));
                	//tree.getRules().add(NewRuleAction.updateNode(tree, new DecisionNode((IDecisionRule)o)));
                }
            }   
        }
	}
	public static DecisionNode updateNode(IDecisionMethod tree,DecisionNode node) {
		IDecisionCategories c = tree.getCategories();
		DefaultCategory key = new DefaultCategory("",1);
		IDecisionCategory c1 = c.getCategory(key);
		if (c1 == null) { 
			c1 = new DefaultClass1();
			c.addCategory(c1);
		}
		key.setID(2);
		IDecisionCategory c2 = c.getCategory(key);
		if (c2 == null) { 
			c2 = new DefaultClass2();
			c.addCategory(c2);
		}
		
		node.setCategory(true,c1);
		node.setCategory(false,c2);
		return node;
		
	}
	public static IDecisionMethod treeFromRule(IDecisionRule rule) throws DecisionMethodException {

		IDecisionCategory cno = new DefaultCategory("NO",1);
		IDecisionCategory cyes = new DefaultCategory("YES",2);
		/*
		IDecisionCategory cno = new DefaultCategory("NO "+rule.getID(),1);
		IDecisionCategory cyes = new DefaultCategory("YES " +rule.getID(),2);
		c.add(cno);
		c.add(cyes);
		UserDefinedTree tree = new UserDefinedTree(c,
				new String[] {rule.getClass().getName()}, 
				new int[][] {{0,0,1,2}});
		tree.setTitle(rule.getID());
		*/
		UserDefinedTree tree = new UserDefinedTree();
		DecisionNode node = new DecisionNode(rule);
		node.setID(rule.getID());
		node.setEditable(true);
		node.setCategory(false, cno);
		node.setCategory(true, cyes);
		tree.getRules().add(node);
		IDecisionCategories c = tree.getCategories();
		c.clear();
		c.add(node.getCategory(false));
		c.add(node.getCategory(true));
		
		tree.setExplanation(rule.getTitle());
		tree.setTitle(rule.getID() + "." + rule.getTitle());
		
		return tree;
	}	
	/**
	 * Creates forest of trees, each tree consists of a single rule.
	 * @param tree
	 * @return
	 * @throws DecisionMethodException
	 */
	public static DecisionMethodsList forestFromTree(IDecisionMethod tree) throws DecisionMethodException {
		DecisionMethodsList forest = new DecisionMethodsList(); 
		IDecisionRuleList rules = tree.getRules();
		for (int i=0; i < rules.size();i++) {
			if ((rules.get(i) instanceof DecisionNode)  &&
				((DecisionNode) rules.get(i)).getRule() instanceof RuleInitAlertCounter) continue; 	

			if (rules.get(i) instanceof RuleInitAlertCounter) continue;
			IDecisionMethod newtree = NewRuleAction.treeFromRule(rules.get(i));
			forest.add(newtree);
		}	
		return forest;
	}		

}
