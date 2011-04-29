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
package toxtree.ui.tree;

import java.awt.Component;

import javax.swing.Action;
import javax.swing.ActionMap;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.tree.DecisionNode;
import toxtree.ui.actions.HelpAction;
import toxtree.ui.tree.actions.AbstractTreeAction;
import toxtree.ui.tree.actions.EditCategoryAction;
import toxtree.ui.tree.actions.EditRuleAction;
import toxtree.ui.tree.actions.EditTreeExplanation;
import toxtree.ui.tree.actions.ICategoryAction;
import toxtree.ui.tree.actions.INodeAction;
import toxtree.ui.tree.actions.IRuleAction;
import toxtree.ui.tree.actions.NewRuleAction;
import toxtree.ui.tree.actions.SaveTreeAction;
import toxtree.ui.tree.actions.SelectCategoryFromListAction;
import toxtree.ui.tree.actions.SetBranchAction;
import toxtree.ui.tree.actions.VerifyUnreachableRulesAction;
import toxtree.ui.tree.actions.VerifyUnusedCategories;

public class EditTreeActions extends ActionMap {
	public final static String ACTION_MODIFYNODE = "Edit rule";

	public final static String ACTION_MODIFYYESBRANCH = "ModifyYesBranch";

	public final static String ACTION_MODIFYNOBRANCH = "ModifyNoBranch";

	public final static String ACTION_MODIFYYESCATEGORY = "ModifyYesCategory";

	public final static String ACTION_MODIFYNOCATEGORY = "ModifyNoCategory";
	
	public final static String ACTION_MODIFYEXPLANATION = "Modify explanation";
	
	public final static String ACTION_NEWRULE = "New rule";

	public final static String ACTION_VERIFY = "Delete rule";

	public final static String ACTION_EDITRULE = "Edit rule";

	public final static String ACTION_NEWCATEGORY = "Select from list";

	public final static String ACTION_EDITCATEGORY = "Edit category";
	public final static String ACTION_VERIFYUNUSED = "Remove unused";
	

	protected IDecisionMethod tree;

	protected DecisionNode node;

	protected ActionMap ruleActions;

	protected ActionMap categoryActions;

	protected ActionMap treeNodeActions;

	protected ActionMap treeActions;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8394309004001206767L;

	public EditTreeActions(IDecisionMethod tree) {
		super();
		this.tree = tree;
		put("Help", new HelpAction());
		node = null;
		ruleActions = createRuleActions();
		ruleActions.setParent(this);
		categoryActions = createCategoryActions();
		categoryActions.setParent(this);
		treeNodeActions = createTreeNodeActions();
		treeNodeActions.setParent(this);
		treeActions = createTreeActions();
		treeActions.setParent(this);
	}

	protected ActionMap createRuleActions() {
		ActionMap a = new ActionMap();
		a.put(ACTION_NEWRULE, new NewRuleAction(tree, ACTION_NEWRULE));
		a.put(ACTION_EDITRULE, new EditRuleAction(tree, ACTION_EDITRULE));
		a.put(ACTION_VERIFY, new VerifyUnreachableRulesAction(tree));
		return a;
	}

	protected ActionMap createCategoryActions() {
		ActionMap a = new ActionMap();
		a.put(ACTION_NEWCATEGORY,
				new SelectCategoryFromListAction(tree, ACTION_NEWCATEGORY));
		a.put(ACTION_EDITCATEGORY, new EditCategoryAction(tree,ACTION_EDITCATEGORY));
		a.put(ACTION_VERIFYUNUSED, new VerifyUnusedCategories(tree,ACTION_VERIFYUNUSED));
		
		return a;
	}

	protected ActionMap createTreeNodeActions() {
		ActionMap a = new ActionMap();
		a.put(ACTION_MODIFYNODE, new EditRuleAction(tree, ACTION_MODIFYNODE));
		a.put(ACTION_MODIFYNOBRANCH, new SetBranchAction(tree, false,
				"Modify <No> branch"));
		a.put(ACTION_MODIFYYESBRANCH, new SetBranchAction(tree, true,
				"Modify <Yes> branch"));
		return a;
	}

	protected ActionMap createTreeActions() {
		ActionMap a = new ActionMap();
		a.put(ACTION_MODIFYEXPLANATION, 
				new EditTreeExplanation(tree,"Modify tree caption"));
        a.put("Save tree", new SaveTreeAction(tree, "Save"));
		return a;
	}	

	public ActionMap getCategoryActions() {
		return categoryActions;
	}

	public void setCategoryActions(ActionMap categoryActions) {
		this.categoryActions = categoryActions;
	}

	public ActionMap getRuleActions() {
		return ruleActions;
	}

	public void setRuleActions(ActionMap ruleActions) {
		this.ruleActions = ruleActions;
	}

	public ActionMap getTreeNodeActions() {
		return treeNodeActions;
	}

	public void setTreeNodeActions(ActionMap treeActions) {
		this.treeNodeActions = treeActions;
	}

	public DecisionNode getNode() {
		return node;
	}

	public void setNode(DecisionNode node) {
		this.node = node;
		Object[] keys = treeNodeActions.keys();
		for (int i = 0; i < keys.length; i++) {
			Action a = treeNodeActions.get(keys[i]);
			if (a instanceof INodeAction)
				((INodeAction) treeNodeActions.get(keys[i])).setDecisionNode(node);
			else if (a instanceof IRuleAction)
				((IRuleAction) treeNodeActions.get(keys[i]))
						.setRule(node.getRule());
		}
		
		
		keys =ruleActions.keys();
		for (int i = 0; i < keys.length; i++) {
			Action a = ruleActions.get(keys[i]);
			if (a instanceof SetBranchAction)
				((INodeAction) ruleActions.get(keys[i])).setDecisionNode(node);
			else if (a instanceof IRuleAction)
				((IRuleAction) ruleActions.get(keys[i]))
						.setRule(node.getRule());
		}
		
		
	}
	public void setCategory(IDecisionCategory category) {
		Object[] keys = categoryActions.keys();
		for (int i = 0; i < keys.length; i++) {
			Action a = categoryActions.get(keys[i]);
			if (a instanceof ICategoryAction)
				((ICategoryAction) a).setCategory(category);
				//((IRuleAction) a).setRule(node.getRule());
		}
		
	}	
	public void setRule(IDecisionRule rule) {
		Object[] keys = treeNodeActions.keys();
		for (int i = 0; i < keys.length; i++) {
			Action a = treeNodeActions.get(keys[i]);
			if (a instanceof IRuleAction)
				((IRuleAction) a).setRule(rule);
				//((IRuleAction) a).setRule(node.getRule());
		}
		
		keys = ruleActions.keys();
		for (int i = 0; i < keys.length; i++) {
			Action a = ruleActions.get(keys[i]);
			if (a instanceof IRuleAction)
				((IRuleAction) a).setRule(rule);
				//((IRuleAction) a).setRule(node.getRule());
		}
		
	}
	
	public void setParentComponent(ActionMap actions, Component parent) {
		Object[] keys = actions.keys();
		for (int i = 0; i < keys.length; i++) {
			Action a = actions.get(keys[i]);
			a.putValue(AbstractTreeAction.PARENTKEY,parent);
		}
	}	
	public synchronized ActionMap getTreeActions() {
		return treeActions;
	}
	public synchronized void setTreeActions(ActionMap treeActions) {
		this.treeActions = treeActions;
	}
}
