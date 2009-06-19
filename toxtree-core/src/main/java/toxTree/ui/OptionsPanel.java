/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/


package toxTree.ui;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.tree.rules.RuleVerifyProperty;

public class OptionsPanel extends JPanel implements FocusListener, ItemListener {
    protected JCheckBox dontAsk;
    protected JFormattedTextField propertyField;
    protected IAtomContainer atomContainer;
    protected final RuleVerifyProperty rule;
    /**
     * 
     */
    private static final long serialVersionUID = 8261591244903486566L;

    public OptionsPanel(String caption, IAtomContainer atomContainer, RuleVerifyProperty rule) {
        super();
        //setBorder(BorderFactory.createEtchedBorder());
        this.atomContainer = atomContainer;
        this.rule = rule;
        //setFloatable(false);
        JLabel propertyNameField = new JLabel("<html><b>"+rule.getPropertyName()+"</b></html>");
        propertyNameField.setToolTipText("Property name");
        add(propertyNameField);
        if (!"".equals(rule.getPropertyUnits())) {
            JLabel propertyUnitsField = new JLabel(","+rule.getPropertyUnits());
            add(propertyUnitsField);
        }
        //addSeparator();
        
        propertyField = new JFormattedTextField(NumberFormat.getNumberInstance());
        propertyField.addFocusListener(this);
        propertyField.setToolTipText(rule.getPropertyName() + " value");
        propertyField.setColumns(10);
        propertyField.setPreferredSize(new Dimension(300,18));
        propertyField.setMinimumSize(new Dimension(100,18));

        add(propertyField);
        
        dontAsk = new JCheckBox("Silent");
        dontAsk.addItemListener(this);
        dontAsk.setSelected(false);
        dontAsk.setToolTipText("Silently answer \"No\" if property value is not available"); //If checked, the dialog will not appear anymore for this rule, and the answer of the rule will be \"No\" if property value is not available.");
        add(dontAsk);
        setBorder(BorderFactory.createTitledBorder(caption));
        
    }
    public boolean isSilent() {
        return dontAsk.isSelected();
    }
    public String getPropertyValue() {
        return propertyField.getText();
    }
    public void itemStateChanged(ItemEvent e) {
        this.rule.setInteractive(!dontAsk.isSelected());
    } 
    
    public void focusGained(FocusEvent e) {
        // TODO Auto-generated method stub
        
    }
    public void focusLost(FocusEvent e) {
        if (atomContainer!=null)
        atomContainer.setProperty(rule.getPropertyName(), propertyField.getText());
    }    
}