package toxTree.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.tree.rules.RuleVerifyProperty;

public class PropertyInput implements PropertyChangeListener {
	public void propertyChange(PropertyChangeEvent evt) {
		Object o = evt.getSource();
		if (o instanceof RuleVerifyProperty) {
			
			RuleVerifyProperty rule = (RuleVerifyProperty)o;
			o = evt.getNewValue();
			if (o instanceof IAtomContainer) {
				IAtomContainer mol = (IAtomContainer)o;
				PropertyEditor p = new PropertyEditor(mol,new OptionsPanel(rule.toString(),mol, rule));
				if (JOptionPane.showConfirmDialog(null,p,String.format("Rule %s.%s",rule.getID(),rule.getCaption()),
			                JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			        rule.setInteractive(!p.isSilent());
			        try {
			        	mol.setProperty(rule.getPropertyName(), new Double(p.getPropertyValue()));
			        } catch (Exception x) {
			        	mol.setProperty(rule.getPropertyName(), p.getPropertyValue());
			        }
			    }			
			}
		}
		
	}

}
