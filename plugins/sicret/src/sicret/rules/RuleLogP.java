/*
Copyright Ideaconsult Ltd.(C) 2006  
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
package sicret.rules;
import javax.swing.JComponent;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;
import org.openscience.cdk.qsar.result.DoubleResult;
import org.openscience.cdk.qsar.result.IDescriptorResult;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.rules.RuleVerifyProperty;

/**
 * LogP < 3.1.<br>
 * Expects property to be read from IMolecule.getProperty({@link #LogKow}).
 * If it is null, calculates LogP by {@link XLogPDescriptor}.
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
 
public class RuleLogP extends RuleVerifyProperty 
{
	protected static XLogPDescriptor descriptor=null;
	public static String LogKow="LogP";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7734658495811755955L;

	public RuleLogP()
	{
		this(LogKow,"",condition_lower,-3.1);
		
	}
	public RuleLogP(String propertyName, String units, String condition, double value) {
		super(propertyName,units,condition,value);
		id = "2";
	}
	public IMolecule getExampleMolecule(boolean ruleResult) throws DecisionMethodException {
		IMolecule m = DefaultChemObjectBuilder.getInstance().newMolecule();      
        if (ruleResult){
        	
        		m = (IMolecule)FunctionalGroups.createAtomContainer("[Na]O[Si](=O)O[Na]",false);
    			m.setProperty(LogKow, -3.91956926097417);
        }
    	else {
    		m = (IMolecule)FunctionalGroups.createAtomContainer("CC1(C(CC=C1C)C=CC(C)(C)C(O)C)C",false);
        		m.setProperty(LogKow, 3.61402048827646);
    	}      	
		return m;
	}
	
	
	@Override
	public String inputProperty(IAtomContainer mol) {
		
		if (descriptor ==null) descriptor = new XLogPDescriptor();
		logger.info("Calculating "+ descriptor.getClass().getName());
		try {
			DescriptorValue value = descriptor.calculate(mol);
			IDescriptorResult result = value.getValue();
			mol.setProperty(getPropertyName(), ((DoubleResult)result).doubleValue());
			return mol.getProperty(getPropertyName()).toString();
			
		} catch (CDKException x) {
			return super.inputProperty(mol);
		}
		
	}
    @Override
    public JComponent optionsPanel(IAtomContainer atomContainer) {
        return null;
    }
    @Override
    public String getImplementationDetails() {
    	StringBuffer b = new StringBuffer();
    	b.append("This rule automatically creates descriptor by using "+descriptor.getClass().getName());
    	b.append("\n");
    	b.append(descriptor.getSpecification().toString());
    	b.append("\n");
    	b.append(super.getImplementationDetails());
    	
    	return b.toString();
    }
}
