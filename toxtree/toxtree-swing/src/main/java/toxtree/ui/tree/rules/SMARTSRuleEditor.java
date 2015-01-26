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

package toxtree.ui.tree.rules;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleEditor;
import toxTree.tree.DecisionNode;
import toxTree.tree.rules.smarts.AbstractRuleSmartSubstructure;
import toxTree.tree.rules.smarts.RuleSMARTSubstructure;
import toxtree.ui.HashtableModel;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;

/**
 * Editor for {@link RuleSMARTSubstructure}.
 * @author Nina Jeliazkova
 *
 */
public class SMARTSRuleEditor extends RuleEditor implements
		IDecisionRuleEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8216887948144449098L;
	protected HashtableModel tableModel;
	protected JCheckBox allBox = null;
	protected JTable table;
	protected int selectedIndex = -1;

	public SMARTSRuleEditor(AbstractRuleSmartSubstructure object) {
		super(object);
		setRule((AbstractRuleSmartSubstructure)object);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("SMARTS",createSmartsPane());
		tabbedPane.addTab("Explanation",explanationPane);
		add(tabbedPane,BorderLayout.CENTER);
	}

	public Component createSmartsPane() {
		JPanel p = new JPanel(new BorderLayout());
		tableModel = new HashtableModel(((AbstractRuleSmartSubstructure)rule).getSmartsPatterns(),true) {
			/**
		     * 
		     */
		    private static final long serialVersionUID = -7486290393614266313L;
			@Override
			public Object getValueAt(int row, int col) {
				
				switch(col) {
				case 0: {
					Object o = super.getValueAt(row, 1);
					if (o instanceof ISmartsPattern)
						return ((ISmartsPattern)o).isNegate();
				}
				case 1: return super.getValueAt(row, 0);
				case 2: {
					Object o = super.getValueAt(row, 1);
					if (o instanceof ISmartsPattern)
						return ((ISmartsPattern)o).getSmarts();
					else 	
						return o.toString();
				}
				default: return "";
				}
				
			};
			@Override
			public int getColumnCount() {
				return 3;
			}
			@Override
			public Class getColumnClass(int columnIndex) {
				switch (columnIndex) {
				case 0: return Boolean.class;
				default: return super.getColumnClass(columnIndex);
				}
			}
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnIndex==0;
			}
			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				switch (columnIndex) {
				case 0: {
					Object o = super.getValueAt(rowIndex, 1);
					if (o instanceof ISmartsPattern)
						((ISmartsPattern)o).setNegate((Boolean)aValue);
				}
				default: {}
				}
			}
			@Override
			public String getColumnName(int arg0) {
				if (arg0 ==0) return "NOT";
				else return super.getColumnName(arg0-1);
			}
		};
		table = new JTable(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) return;
				else {
					selectedIndex = ((ListSelectionModel) e.getSource()).getMinSelectionIndex();
				}
			}
		});
		p.add(new JScrollPane(table),BorderLayout.CENTER);
		JPanel toolbar = new JPanel();
		JButton b = new JButton(new AbstractAction("Add SMARTS"){
			/**
		     * 
		     */
		    private static final long serialVersionUID = -8906706820110658576L;

			public void actionPerformed(ActionEvent arg0) {
				editSmarts();
			}
		});
		b.setToolTipText("Add SMARTS pattern");
		toolbar.add(b);
		
		b = new JButton(new AbstractAction("Edit") {
			/**
		     * 
		     */
		    private static final long serialVersionUID = 9045669465786568251L;

			public void actionPerformed(ActionEvent arg0) {
				if (selectedIndex == -1) return;
				editSmarts(table.getValueAt(selectedIndex, 1).toString(), table.getValueAt(selectedIndex, 2).toString());
			};
		});
		b.setToolTipText("Edit SMARTS pattern");
		toolbar.add(b);		
		
		b = new JButton(new AbstractAction("Delete") {
			/**
		     * 
		     */
		    private static final long serialVersionUID = 7647704546247842919L;

			public void actionPerformed(ActionEvent arg0) {
				deleteSmarts();
			};
		});
		b.setToolTipText("Delete SMARTS pattern");
		toolbar.add(b);		
		
		allBox = new JCheckBox("All");
		allBox.addItemListener(new ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					((AbstractRuleSmartSubstructure)rule).setContainsAllSubstructures(false);
					allBox.setText("Any");
				} else {
					allBox.setText("All");
					((AbstractRuleSmartSubstructure)rule).setContainsAllSubstructures(true);
				}
	
			};
		});
		allBox.setToolTipText("Returns true if the query structure contains all or any of the substructures defined by SMARTS.");
		allBox.setSelected(((AbstractRuleSmartSubstructure)rule).containsAllSubstructures());
		allBox.addFocusListener(this);
		
		toolbar.add(allBox);
		
		p.add(toolbar,BorderLayout.NORTH);
		return p;
	}
	protected void launchErrorDialog(Exception x) {
		JOptionPane.showMessageDialog(this, x.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
	
	}
	public void deleteSmarts() {
		if (selectedIndex == -1) return;
		
		if (JOptionPane.showConfirmDialog(this,
				table.getValueAt(selectedIndex,1) + "\n" + table.getValueAt(selectedIndex,2)  
				,"Delete SMARTS", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
			try {
				((AbstractRuleSmartSubstructure)rule).deleteSubstructure(table.getValueAt(selectedIndex,1).toString());
				tableModel.setTable(((AbstractRuleSmartSubstructure)rule).getSmartsPatterns());
			} catch (Exception x) {
				launchErrorDialog(x);
			}
	}
	public void editSmarts() {
		editSmarts("","");
	}
	public void editSmarts(String title, String smarts) {
		SmartsPanelEditor smartsEditor = new SmartsPanelEditor(title,smarts, (AbstractRuleSmartSubstructure)rule);
		if (JOptionPane.showConfirmDialog(this, smartsEditor,"Enter SMARTS",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			smarts = smartsEditor.getSMARTS();
			title = smartsEditor.getTitle();
			if (smarts != null)
				try {
					((AbstractRuleSmartSubstructure)rule).addSubstructure(title,smarts);
					tableModel.setTable(((AbstractRuleSmartSubstructure)rule).getSmartsPatterns());
				} catch (SMARTSException x) {
					launchErrorDialog(x);
				}
		}	
		smartsEditor = null;
	}
	
	public SMARTSRuleEditor() {
		this(null);

	}

	public IDecisionRule edit(Container owner, IDecisionRule rule) {
		if (rule instanceof DecisionNode)
			rule = ((DecisionNode)rule).getRule();
		if (!(rule instanceof AbstractRuleSmartSubstructure)) {
			return null;
		}
		setRule(rule);
		if (JOptionPane.showConfirmDialog(owner, this,"Rule", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE )
				== JOptionPane.OK_OPTION)
			return rule;
		else
			return null;
	}	

}


