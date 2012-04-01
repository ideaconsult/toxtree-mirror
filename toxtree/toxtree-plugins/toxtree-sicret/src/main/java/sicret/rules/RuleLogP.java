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
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;
import org.openscience.cdk.qsar.result.DoubleResult;
import org.openscience.cdk.qsar.result.IDescriptorResult;

import toxTree.exceptions.DecisionMethodException;
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
		setTitle(getPropertyName() + getCondition() + getProperty());	
		examples[0]="CC1(C(CC=C1C)C=CC(C)(C)C(O)C)C";
		examples[1]="[Na]O[Si](=O)O[Na]";
	}
	
	@Override
	public String inputProperty(IAtomContainer mol) throws DecisionMethodException  {
		
		XLogPDescriptor descriptor = new XLogPDescriptor();
		try {descriptor.setParameters(new Object[] {Boolean.TRUE});} catch (Exception x) {}
		logger.info("Calculating "+ descriptor.getClass().getName());
		try {
			DescriptorValue value = descriptor.calculate(mol);
			if (value.getException()!=null) {
				return super.inputProperty(mol);
			} else {
				IDescriptorResult result = value.getValue();
				mol.setProperty(getPropertyName(), ((DoubleResult)result).doubleValue());
				return mol.getProperty(getPropertyName()).toString();
			}
			
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
		
	}
	/*
    @Override
    public JComponent optionsPanel(IAtomContainer atomContainer) {
        return null;
    }
    */
    @Override
    public String getImplementationDetails() {
    	XLogPDescriptor descriptor = new XLogPDescriptor();
    	try {descriptor.setParameters(new Object[] {Boolean.TRUE});} catch (Exception x) {}
    	StringBuffer b = new StringBuffer();
    	b.append("This rule automatically creates descriptor by using "+descriptor.getClass().getName());
    	b.append("\n");
    	b.append(descriptor.getSpecification().toString());
    	b.append("\n");
    	b.append(super.getImplementationDetails());
    	
    	return b.toString();
    }
}
