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
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import toxTree.tree.rules.RuleVerifyProperty;

public class RulePropertyEditor extends RuleEditor {
    protected JTextField propertyNameField ;
    protected JTextField propertyValueField ;
    protected JComboBox conditionBox;

	/**
	 * 
	 */
	private static final long serialVersionUID = -555524195056865333L;

	public RulePropertyEditor(RuleVerifyProperty object) {
		super(object);

	}

	public RulePropertyEditor() {
		this(null);
	}
	@Override
	protected void addWidgets(Object object) {
		super.addWidgets(object);
		add(explanationPane,BorderLayout.CENTER);
		
		JPanel p = new JPanel(new GridLayout(1,3));
		p.setBorder(BorderFactory.createTitledBorder("Property"));
		propertyNameField = new JTextField();
		propertyNameField.setToolTipText("Property name");
		propertyNameField.addFocusListener(this);
		propertyValueField = new JTextField();
		propertyValueField.setToolTipText("Property value");
		propertyValueField.addFocusListener(this);
		
		conditionBox = new JComboBox(new String[] {RuleVerifyProperty.condition_higher,RuleVerifyProperty.condition_equals,RuleVerifyProperty.condition_lower});
		conditionBox.addFocusListener(this);
		p.add(propertyNameField);
		p.add(conditionBox);
		p.add(propertyValueField);
		add(explanationPane,BorderLayout.CENTER);
		add(p,BorderLayout.SOUTH);
		
	}
	public void setPropertyName(String propertyName) {
		if (rule != null) ((RuleVerifyProperty) rule).setPropertyName(propertyName);
	}
	public void setPropertyValue(String propertyValue) throws Exception {
		if (rule != null) ((RuleVerifyProperty) rule).setProperty(Double.parseDouble(propertyValue));
	}
	public void setCondition(String condition) {
		if (rule != null) ((RuleVerifyProperty) rule).setCondition(condition);
	}

	@Override
	public void focusLost(FocusEvent e) {
		super.focusLost(e);
        if (e.getSource() == propertyNameField) {
            setPropertyName(propertyNameField.getText());
        } else if (e.getSource() ==propertyValueField) 
        	try {
        		setPropertyValue(propertyValueField.getText());
        	} catch (Exception x) {
        		
        	}
        else if (e.getSource() ==conditionBox)
            setCondition(conditionBox.getSelectedItem().toString());
	}
	public String getPropertyName() {
		if (rule != null) return ((RuleVerifyProperty) rule).getPropertyName();
		else return "";
	}
	public String getPropertyValue() {
		if (rule != null) return Double.toString(((RuleVerifyProperty) rule).getProperty());
		else return "";
	}
	public String getCondition() {
		if (rule != null) return ((RuleVerifyProperty) rule).getCondition();
		else return "";
	}		
	@Override
	public void update(Observable arg0, Object arg1) {
		super.update(arg0, arg1);
		propertyNameField.setText(getPropertyName());        
		propertyValueField.setText(getPropertyValue());
		conditionBox.setSelectedItem(getCondition());
        //explanation.setText(getExplanation());
         
	}
}


