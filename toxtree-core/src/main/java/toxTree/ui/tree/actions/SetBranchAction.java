
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
package toxTree.ui.tree.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.JFrame;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.io.Tools;
import toxTree.tree.DecisionNode;
import toxTree.tree.DecisionNodesList;
import toxTree.ui.tree.ListPanel;
import toxTree.ui.tree.ListTableModel;
import toxTree.ui.tree.rules.DecisionNodesListTableModel;
import toxTree.ui.wizard.DecisionNodeWizard;

/**
 * Modify a tree node branch.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-9
 */
public class SetBranchAction extends AbstractTreeAction implements INodeAction {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1690952370004121255L;
	protected int answer =0;
	protected String answerText[] = {"<No>","<Yes>"};
	protected DecisionNode node = null;
	protected ActionMap ruleActions = null;
	protected ActionMap categoryActions = null;
	
	/**
	 * @param tree
	 */
	public SetBranchAction(IDecisionMethod tree,boolean answer) {
		this(tree,answer,"Set branch");
		
		
	}

	/**
	 * @param tree
	 * @param name
	 */
	public SetBranchAction(IDecisionMethod tree, boolean answer,String name) {
		this(tree, answer, name,Tools.getImage("arrow_divide.png"));
	}

	/**
	 * @param tree
	 * @param name
	 * @param icon
	 */
	public SetBranchAction(IDecisionMethod tree, boolean answer,String name, Icon icon) {
		super(tree, name, icon);
		if (answer) this.answer = 1; else this.answer = 0;
        putValue(SHORT_DESCRIPTION, "Modify a tree node branch.");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (node == null) return;
		/*
		if (ruleActions == null) {
			ruleActions = new ActionMap();
			ruleActions.put("New Rule",new NewRuleAction(tree,"New Rule"));
		}
		
		if (categoryActions == null) {
			categoryActions = new ActionMap();
			categoryActions.put("New Category",new AddCategoryAction(tree.getCategories(),"New Category"));
			
		}
		*/
		
		ListPanel[] panels = new ListPanel[2];
		if (tree.getRules() instanceof DecisionNodesList)
			panels[0] = new ListPanel("Rules",new DecisionNodesListTableModel(tree.getRules()),null);
		else	
			panels[0] = new ListPanel("Rules",new ListTableModel(tree.getRules()),ruleActions);
		panels[1] = new ListPanel("Categories",new ListTableModel(tree.getCategories()),null);
		
		JFrame frame = getParentFrame();
        /*
		Object p = getValue(AbstractTreeAction.PARENTKEY);
		if (p!=null) {
			if (p  instanceof JFrame) frame = (JFrame) p;
			else 
			while (p !=null) {
				p = ((Component) p).getParent();
				if (p instanceof JFrame) {
					frame = (JFrame) p;
					break;
				}
			}
		}
		*/
        DecisionNodeWizard wizard = new DecisionNodeWizard(frame,tree, panels);
        wizard.getDialog().setTitle(getValue(AbstractAction.NAME).toString());
        
        if (wizard.showModalDialog() == 0) { 
            Object o = wizard.getSelectedObject();
            if (o != null)  {
                if (o instanceof DecisionNode)
                    node.setBranch(answer==1,(DecisionNode) o);
                else if (o instanceof IDecisionCategory) {
                    node.setCategory(answer==1,(IDecisionCategory) o);
                } else if (o instanceof IDecisionRule) {
                	node.setBranch(answer==1,
                			NewRuleAction.updateNode(tree, new DecisionNode((IDecisionRule)o)));
                }
            }   
        }    
        else return;
		
        /*
		Object o = SelectListDialog.selectFromList("Select a rule or a category to assign at "+answerText[answer] + " branch",panels);
		if (o != null)  {
			if (o instanceof DecisionNode)
				node.setBranch(answer==1,(DecisionNode) o);
			else if (o instanceof IDecisionCategory) {
				node.setCategory(answer==1,(IDecisionCategory) o);
			}	
		}	
        */
	}
	
	/**
	 * @return Returns the node.
	 */
	public DecisionNode getDecisionNode() {
		return node;
	}
	/**
	 * @param node The node to set.
	 */
	public void setDecisionNode(DecisionNode node) {
		this.node = node;
	}

	public int getAnswer() {
		return answer;
	}
}
