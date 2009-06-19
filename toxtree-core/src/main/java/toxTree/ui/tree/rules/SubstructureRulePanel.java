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
package toxTree.ui.tree.rules;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.ActionMap;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleEditor;
import toxTree.core.IRuleSubstructures;
import toxTree.ui.molecule.Panel2D;
import toxTree.ui.tree.ListPanel;
import toxTree.ui.tree.QueryAtomContainersModel;
import toxTree.ui.tree.actions.SubstructureFromListAction;
import toxTree.ui.tree.actions.SubstructuresFromFileAction;

/**
 * TODO add description
 * @author Vedina
 * <b>Modified</b> 2005-10-12
 */
public class SubstructureRulePanel extends JPanel implements IDecisionRuleEditor {
	protected boolean editable=false; 
	protected RuleSubstructuresEditAction substructureEditAction;
	protected SubstructureFromListAction listSubstructureAction;
    protected SubstructuresFromFileAction fileSubstructureAction;
	protected RuleSubstructuresEditAction newSubstructureAction;
    protected RuleDeleteSubstructureAction deleteSubstructureAction;
	protected IRuleSubstructures rule;
	protected RulePanel rulePanel;
	protected ListPanel listPanel;
	protected Panel2D panel2D;
	protected ActionMap actions;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -4834888295381016875L;

	/**
	 * @param rule
	 */
	public SubstructureRulePanel(IRuleSubstructures rule) {
		super();
		this.rule = rule;
		addWidgets();
	}
	protected void addWidgets() {
		actions = null;
		if (rule.isEditable()) {
			actions = new ActionMap();
			newSubstructureAction = new RuleSubstructuresEditAction(null,"New substructures");
			newSubstructureAction.setCreateNewMolecule(true);
			newSubstructureAction.setRule(getRule());
			actions.put("New substructure",newSubstructureAction);
			
			
			listSubstructureAction = new SubstructureFromListAction(getRule(),"Select from list");
			listSubstructureAction.setRule(getRule());
			actions.put("Select from list",listSubstructureAction);
			
            fileSubstructureAction = new SubstructuresFromFileAction(getRule(),"Select from file");
            fileSubstructureAction.setRule(getRule());
            actions.put("Select from file",fileSubstructureAction);
            
            
            
			substructureEditAction = new RuleSubstructuresEditAction(null,"Edit substructures");
			substructureEditAction.setRule(getRule());
			actions.put("Edit substructure",substructureEditAction);
            
            deleteSubstructureAction = new RuleDeleteSubstructureAction(null);
            deleteSubstructureAction.setRule(getRule());
            actions.put("Delete",deleteSubstructureAction);
			 
		} else {
			substructureEditAction = null;
			listSubstructureAction = null;
			newSubstructureAction = null;	
            fileSubstructureAction = null;
		}
		
		rulePanel = new RulePanel(rule);
		panel2D = new Panel2D();

		
		listPanel = new ListPanel("Substructures",
				new QueryAtomContainersModel(rule.getSubstructures(),rule),actions) {

			private static final long serialVersionUID = 5127883413200592898L;

			@Override
			public void setSelectedObject(Object selectedObject,int index) {
				super.setSelectedObject(selectedObject,index);
				if (selectedObject instanceof IAtomContainer) {
					panel2D.setAtomContainer((IAtomContainer)selectedObject,true);
					if (substructureEditAction != null)
						substructureEditAction.setAtomContainer((IAtomContainer)selectedObject,index);
                    if (deleteSubstructureAction !=null)
                        deleteSubstructureAction.setSelectedIndex(index);
				}
			};
		};
		listPanel.addListSelectionListener(listPanel);
		//JScrollPane scrollPane = new JScrollPane(listPanel);
		
        /*
        JSplitPane splitPanel = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                scrollPane, panel2D);
			*/
        JPanel splitPanel = new JPanel(new GridLayout(2,1));
        splitPanel.add(listPanel);
        splitPanel.add(panel2D);
		JTabbedPane pane = new JTabbedPane();
		pane.add("Rule",rulePanel);
		pane.add("Substructures",splitPanel);
		
		setLayout(new BorderLayout());
		add(pane,BorderLayout.CENTER);

	}
	/* (non-Javadoc)
	 * @see toxTree.ui.tree.rules.RulePanel#setRule(toxTree.core.IDecisionRule)
	 */
	public void setRule(IDecisionRule rule) {
		rulePanel.setRule(rule);
		if (rule instanceof IRuleSubstructures) {
			IRuleSubstructures ruleS = (IRuleSubstructures) rule;
			this.rule = ruleS;
			listPanel.setList(ruleS.getSubstructures());
			if (substructureEditAction != null)
				substructureEditAction.setRule(getRule());

		}
		
	}
	public IDecisionRule getRule() {
		return rule;
	}
	public IDecisionRule edit(Container owner, IDecisionRule rule) {
    	setEditable(true);
    	setRule(rule);
		JOptionPane pane = new JOptionPane(this, JOptionPane.QUESTION_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION);

		String title = "View rule ";
		if (rule.isEditable()) title = "Edit rule ";
		JDialog dialog = pane.createDialog(owner, title + rule.toString());
		dialog.setBounds(200,200,600,600);
		dialog.setVisible(true);
		if (pane.getValue() == null) return null;
		int value = ((Integer) pane.getValue()).intValue();
		if (value == 0) {
			String id = rulePanel.idField.getText();
			String name = rulePanel.nameField.getText();
			String expl = rulePanel.explanation.getText();
			rulePanel.rule.setID(id);						
			rulePanel.rule.setTitle(name);
			rulePanel.rule.setExplanation(expl);
			return getRule();
		} else
			return null;

	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
		rulePanel.setEditable(editable);
	}
	public Component getVisualCompoment() {
	    return this;
	}
}
