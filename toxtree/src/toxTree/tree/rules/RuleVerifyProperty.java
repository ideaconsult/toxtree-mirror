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
package toxTree.tree.rules;

import java.text.NumberFormat;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;

import toxTree.core.IDecisionInteractive;
import toxTree.core.IDecisionRuleEditor;
import toxTree.core.IImplementationDetails;
import toxTree.exceptions.DRuleNotImplemented;
import toxTree.exceptions.DRulePropertyNotAvailable;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;
import toxTree.ui.OptionsPanel;
import toxTree.ui.PropertyEditor;
import toxTree.ui.tree.rules.RulePropertyEditor;

/**
 * 
 * Verifies if property is >, < or = to a {@link #getProperty()} value.
 * Property to be read as {@link IMolecule}.getProperty({@link #getPropertyName()}).
 * If there exist no such property of the molecule, a {@link #inputProperty(IAtomContainer)}
 * method is invoked, which typically waits for user input of the property value.
 * If the property is not assigned after {@link #inputProperty(IAtomContainer)} call, then {@link DRuleNotImplemented} is fired.
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleVerifyProperty extends AbstractRule implements IDecisionInteractive, IImplementationDetails { 

    /**
	 * 
	 */
	private static final long serialVersionUID = 2634387005293933539L;
	protected double propertyStaticValue;
    protected String propertyName = "property" ;
    protected String propertyUnits = "" ;
    protected boolean interactive = true;

    public static String condition_higher=">";
    public static String condition_lower="<";
    public static String condition_equals="=";
    protected String condition =condition_equals;
    protected static NumberFormat nf = NumberFormat.getInstance();
    
    public RuleVerifyProperty() {
    	super();
    	nf.setMaximumFractionDigits(4);
    }
    public RuleVerifyProperty(String propertyName, String units, String condition, double value) {
    	this();
    	setPropertyName(propertyName);
    	setPropertyUnits(units);
    	setCondition(condition);
    	setProperty(value);
    	setTitle(getCaption());
    }    
    public String getCaption() {
    	return getPropertyName() + "["+ getPropertyUnits() + "] " + getCondition() + " " + nf.format(getProperty());
    }
    public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException { 
        logger.info(toString());
        try {
        	Object value = mol.getProperty(this.propertyName);
 
        	if ((value == null) || ("".equals(value))){
                if (interactive) {
            		value = inputProperty(mol);
            		mol.setProperty(propertyName, value.toString());
                } else throw new DRuleNotImplemented(propertyName + " not assigned ");
        	}
        	//Double.valueOf returns Double, perhaps Double.parseDouble() could be used  
        	return this.compare(Double.valueOf(mol.getProperty(this.propertyName).toString()), 
        				this.propertyStaticValue);
        	
        } catch (NumberFormatException x) {
        	//just in case, the property might hold any value, or be empty
        	throw new DRulePropertyNotAvailable(propertyName,propertyName + " invalid value ",x);
        } catch (NullPointerException x) {
        	//or the getProperty might be null
        	throw new DRulePropertyNotAvailable(propertyName,propertyName + " not assigned ",x);
        }
    }
    public void setProperty(double propertyStaticValue ){        
        this.propertyStaticValue = propertyStaticValue;
    }
    public double getProperty() {
            return this.propertyStaticValue ;
    }
    public void setCondition(String condition ){        
        this.condition = condition;
    }
    public String getCondition() {
        return this.condition;
    }
    public boolean compare(double Param1,double Param2){ 
    	
        if(this.getCondition().equals(condition_higher))
            return Param1 > Param2;
        else if(this.getCondition().equals(condition_lower))
            return Param1 < Param2;
        else 
        	//better introduce parameter for equality
            return Param1 == Param2;
    }
    @Override
    public boolean isImplemented() {
    	return true;
    }
    
    @Override
    public String getExplanation() {
    	return getPropertyName() + " " + getCondition() + " " + getProperty();
    }
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	@Override
	public IMolecule getExampleMolecule(boolean ruleResult) throws DecisionMethodException {
		IMolecule m = DefaultChemObjectBuilder.getInstance().newMolecule();
        if(this.getCondition().equals(condition_higher))
        	if (ruleResult)
    			m.setProperty(getPropertyName(), getProperty()+1);
        	else
        		m.setProperty(getPropertyName(), getProperty()-1);
        else if(this.getCondition().equals(condition_lower))
        	if (ruleResult)
    			m.setProperty(getPropertyName(), getProperty()-1);
        	else
        		m.setProperty(getPropertyName(), getProperty()+1);
        else
        	if (ruleResult)
    			m.setProperty(getPropertyName(), getProperty());
        	else
        		m.setProperty(getPropertyName(), getProperty()+1);        	
		return m;
	}
	
	@Override
	public IDecisionRuleEditor getEditor() {
		return new RulePropertyEditor(this);
	}
	/**
	 * Called when mol.getProperty(getPropertyName()) is null.
	 * @return
	 */
    public String inputProperty(IAtomContainer mol) {
    	PropertyEditor p = new PropertyEditor(mol,new OptionsPanel(toString(),mol, this));
    	if (JOptionPane.showConfirmDialog(null,p,"Rule " + getID() + "." + getCaption(),
                    JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            setInteractive(!p.isSilent());
    		return p.getPropertyValue();
        } else return null;
    	//return JOptionPane.showInputDialog("Enter " + propertyName + "," + propertyUnits);
    }
	public String getPropertyUnits() {
		return propertyUnits;
	}
	public void setPropertyUnits(String propertyUnits) {
		this.propertyUnits = propertyUnits;
	}
    public boolean getInteractive() {
        return interactive;
    }
    public void setInteractive(boolean value) {
        this.interactive = value;
    }
    public JComponent optionsPanel(IAtomContainer atomContainer) {
        
        if (interactive && ((atomContainer==null) || (atomContainer.getProperty(this.propertyName) == null))) {
            return  new OptionsPanel(toString(),atomContainer, this);
        } else return null;
    }
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append("The value is expected to be assigned as a molecule property, available by IMolecule.getProperty(\"");
		b.append(propertyName);
		b.append("\".\n");
		if (!"".equals(propertyUnits)) {
			b.append("units: ");
			b.append(propertyUnits);
		}
		b.append("The value could be assigned by e.g. reading a .csv file with column \"");
		b.append(propertyName);
		b.append("\" or .sdf file with property <");
		b.append(propertyName);
		b.append(">\n");
		b.append("If value is not assigned, the user is prompted to enter the value interactively.");
		return b.toString();
	}
} 


