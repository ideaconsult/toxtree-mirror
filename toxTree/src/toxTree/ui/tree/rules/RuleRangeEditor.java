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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import toxTree.core.IRuleRange;

public class RuleRangeEditor extends RuleEditor {
    protected JTextField propertyNameField ;
    protected JTextField propertyMinValueField ;
    protected JTextField propertyMaxValueField ;
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -1332379568034013673L;

	public RuleRangeEditor(IRuleRange object) {
		super(object);
	}

	public RuleRangeEditor() {
		super();

	}

	public void setPropertyName(String propertyName) {
		if (rule != null) ((IRuleRange) rule).setProperty(propertyName);
	}

	public void setPropertyMinValue(String propertyValue) throws Exception {
		if (rule != null) ((IRuleRange) rule).setMinValue(Double.parseDouble(propertyValue));
	}
	public void setPropertyMaxValue(String propertyValue) throws Exception {
		if (rule != null) ((IRuleRange) rule).setMaxValue(Double.parseDouble(propertyValue));
	}		
	@Override
	protected void addWidgets(Object object) {
		super.addWidgets(object);
		add(explanationPane,BorderLayout.CENTER);
		
		JPanel p = new JPanel(new GridLayout(2,3));
		p.add(new JLabel("Property"));
		p.add(new JLabel("Min value"));
		p.add(new JLabel("Max value"));
		propertyNameField = new JTextField();
	    propertyNameField.addFocusListener(this);
	    p.add(propertyNameField);
	    propertyMinValueField = new JTextField();
	    propertyMinValueField.addFocusListener(this);
	    p.add(propertyMinValueField);
	    propertyMaxValueField = new JTextField();
	    propertyMaxValueField.addFocusListener(this);
	    p.add(propertyMaxValueField);
	    
	    add(p,BorderLayout.SOUTH);
	}
	@Override
	public void focusLost(FocusEvent e) {
		super.focusLost(e);
        if (e.getSource() == propertyNameField) {
            setPropertyName(propertyNameField.getText());
        } else if (e.getSource() ==propertyMinValueField) 
        	try {
        		setPropertyMinValue(propertyMinValueField.getText());
        	} catch (Exception x) {
        		
        	}
		else if (e.getSource() ==propertyMaxValueField) 
	    	try {
	    		setPropertyMaxValue(propertyMaxValueField.getText());
	    	} catch (Exception x) {
	    		
	    	}
	}
	public String getPropertyName() {
		if (rule != null) return ((IRuleRange) rule).getProperty().toString();
		else return "";
	}
	public String getPropertyMinValue() {
		if (rule != null) return Double.toString(((IRuleRange) rule).getMinValue());
		else return "";
	}
	public String getPropertyMaxValue() {
		if (rule != null) return Double.toString(((IRuleRange) rule).getMaxValue());
		else return "";
	}		
	@Override
	public void update(Observable arg0, Object arg1) {
		super.update(arg0, arg1);
		propertyNameField.setText(getPropertyName());        
		propertyMaxValueField.setText(getPropertyMaxValue());
		propertyMinValueField.setText(getPropertyMinValue());
        //explanation.setText(getExplanation());
         
	}

	public boolean isSetPropertyEditable() {
		return propertyNameField.isEditable();
	}

	public void setSetPropertyEditable(boolean setPropertyEditable) {
		propertyNameField.setEditable(setPropertyEditable);
	}	
}


