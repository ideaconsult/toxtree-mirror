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
*//**
 * <b>Filename</b> RulePanel.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-3
 * <b>Project</b> toxTree
 */
package toxtree.ui.tree.rules;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.openscience.cdk.interfaces.IMolecule;

import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleEditor;
import toxTree.exceptions.DecisionMethodException;
import ambit2.ui.Panel2D;

/**
 * 
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-3
 */
public class RulePanel extends JPanel implements Observer , IDecisionRuleEditor,FocusListener {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4523210818884113893L;
    protected IDecisionRule rule = null;
    protected JTextField idField ;
    protected JTextField nameField ;
    protected JEditorPane explanation;
    protected JRadioButton yesExample, noExample;
    protected RuleExampleEditAction moleculeEditAction=null;
    protected Panel2D panel2D;
    protected TitledBorder panel2DBorder;
    protected JButton editMolButton;
    

    
    protected boolean editable = false;
    Color bgColor = new Color(250,250,250);
    /**
     * 
     */
    public RulePanel(IDecisionRule rule) {
        super();
        this.rule = rule;
        if (rule instanceof Observable) 
            ((Observable) rule).addObserver(this);
        setLayout(new BorderLayout());
        addWidgets();    
        if (rule != null)
        	setEditable(rule.isEditable());
    }
    protected void addWidgets() {
    	JPanel pp = new JPanel();
    	pp.setLayout(new BorderLayout());
    	pp.setBackground(bgColor);
    	
        idField = new JTextField();
        idField.addFocusListener(this);
        idField.setPreferredSize(new Dimension(64,24));
        idField.setEditable(editable);
        idField.setBorder(BorderFactory.createTitledBorder("Rule ID"));
        idField.setBackground(bgColor);
        pp.add(idField,BorderLayout.WEST);
        
        nameField = new JTextField();
        nameField.addFocusListener(this);
        nameField.setEditable(editable);
        nameField.setBorder(BorderFactory.createTitledBorder("Rule title"));
        nameField.setBackground(bgColor);
        pp.add(nameField,BorderLayout.CENTER);
        
        add(pp,BorderLayout.NORTH);
        explanation = new JEditorPane("text/html","");
        explanation.addFocusListener(this);
        explanation.setBackground(bgColor);
        explanation.setEditable(editable);
        explanation.setAutoscrolls(true);
        //explanation.setLineWrap(true);
        //explanation.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(explanation,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        explanation.setPreferredSize(new Dimension(200,200));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Rule explanation"));        
        scrollPane.setBackground(bgColor);
        
        yesExample = new JRadioButton("Yes branch");
        yesExample.setSelected(true);
        yesExample.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = -6836338894902329072L;
            public void actionPerformed(ActionEvent e) {
                updateExampleView();
            }
        });
        yesExample.setBackground(bgColor);
        noExample = new JRadioButton("No branch");
        noExample.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 8240029605727079547L;
            public void actionPerformed(ActionEvent e) {
                updateExampleView();
            }
        });        
        noExample.setBackground(bgColor);
        
    	moleculeEditAction = new RuleExampleEditAction(getRule(),false,"Edit example");
    	moleculeEditAction.setParentComponent(this);
        JPanel p = new JPanel();
        p.setBackground(bgColor);
        p.setBorder(BorderFactory.createTitledBorder("There are example molecules for each rule outcome. Select which one to display."));
        p.setLayout(new BoxLayout(p,BoxLayout.LINE_AXIS));
        p.add(yesExample); p.add(noExample);
        ButtonGroup bg = new ButtonGroup();
        bg.add(yesExample);
        bg.add(noExample);
        
        editMolButton = new JButton(moleculeEditAction);
        editMolButton.setVisible(editable);
        p.add(editMolButton);
        add(p,BorderLayout.SOUTH);
        
        panel2D = new Panel2D();
        panel2DBorder = BorderFactory.createTitledBorder("Example compound");
        panel2D.setBorder(panel2DBorder);
//      Create a split pane with the two scroll panes in it.
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                scrollPane, panel2D);
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerLocation(250);
        splitPane.setBackground(bgColor);
        //Provide minimum sizes for the two components in the split pane
        Dimension minimumSize = new Dimension(100, 50);
        scrollPane.setMinimumSize(minimumSize);
        panel2D.setMinimumSize(minimumSize);
        
        add(splitPane,BorderLayout.CENTER);        
        
		setPreferredSize(new Dimension(200,200));                
    }
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(Observable o, Object arg) {
        if (rule == null) return;
        moleculeEditAction.setRule(rule);
        idField.setText(rule.getID());        
        nameField.setText(rule.getTitle());
        explanation.setText(rule.getExplanation());
	
        updateExampleView();
    }
    protected void updateExampleView() {
    	IMolecule mol = null;
    	try {
            moleculeEditAction.setAnswer(yesExample.isSelected());
    		mol = rule.getExampleMolecule(yesExample.isSelected());
    		if (yesExample.isSelected())
    		    panel2DBorder.setTitle("Example with answer 'YES'");
    		else
    		    panel2DBorder.setTitle("Example with answer 'NO'");    		    
    		panel2D.setAtomContainer(mol,true);
    		//moleculeEditAction.setAnswer(yesExample.isSelected());    		
    		
    	} catch (DecisionMethodException x) {
    		panel2D.setAtomContainer(null,false);
    		panel2DBorder.setTitle("Example not available");
    	}        
    }
    

    public IDecisionRule getRule() {
        return rule;
    }
    public void setRule(IDecisionRule rule) {
    	
    	if (this.rule != null) {
    		((Observable) this.rule).deleteObserver(this);
    	}
    	
        this.rule = rule;
        ((Observable) this.rule).addObserver(this);
        update((Observable) rule,null);
    }
    
    public IDecisionRule edit(Container owner, IDecisionRule rule) {
    	setEditable(true);
    	setRule(rule);
		JOptionPane pane = new JOptionPane(this, JOptionPane.QUESTION_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION);

		JDialog dialog = pane.createDialog(owner, "Edit rule "+ rule.toString());
		dialog.setBounds(200,100,600,600);
		dialog.setVisible(true);
		if (pane.getValue() == null) return null;
		int value = ((Integer) pane.getValue()).intValue();
		if (value == 0) {
			String id = idField.getText();
			String name = nameField.getText();
			String expl = explanation.getText();
			rule.setID(id);						
			rule.setTitle(name);
			rule.setExplanation(expl);
			return getRule();
		} else
			return null;    	
    }
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
		idField.setEditable(editable);
		nameField.setEditable(editable);
		explanation.setEditable(editable);
		editMolButton.setVisible(editable);
	}
    public void focusLost(FocusEvent e) {
        if (rule==null) return;
        if (e.getSource() ==nameField) {
            rule.setTitle(nameField.getText());
        } else if (e.getSource() ==idField) {
                rule.setID(idField.getText());
        } else if (e.getSource() ==explanation) 
            rule.setExplanation(explanation.getText());
        moleculeEditAction.setAnswer(yesExample.isSelected());
    }
    public void focusGained(FocusEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    public Component getVisualCompoment() {
        return this;
    }
}

