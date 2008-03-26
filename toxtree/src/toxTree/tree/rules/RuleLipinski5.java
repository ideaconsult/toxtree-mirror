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

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.descriptors.molecular.RuleOfFiveDescriptor;
import org.openscience.cdk.qsar.result.IntegerResult;

import toxTree.exceptions.DRuleNotImplemented;
import toxTree.exceptions.DecisionMethodException;
/**
 * Returns true if all Lipinski Rule of Five are true
 * @author Nina Jeliazkova
 *
 */
public class RuleLipinski5 extends RuleDescriptor {
	protected Object[] params;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2049662136403540371L;

	public RuleLipinski5() {
		super();
		id = "R5";
		
		setExplanation("Returns <b>YES</b> is all of Lipinski five rules are fulfilled.");
		params = new Object[1];
		params[0] = new Boolean(true);
		setDescriptor(new RuleOfFiveDescriptor());
		setTitle("Lipinski Rule of 5");
	}

	/**
	 *Returns true if all Lipinski Rule of Five are true 
	 */
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (descriptor != null) {
			try {
				descriptor.setParameters(params);
				DescriptorValue value = ((RuleOfFiveDescriptor) descriptor).calculate(mol);
				IntegerResult r = (IntegerResult) value.getValue();
				return (r.intValue() == 0); //no failures
			} catch (CDKException x) {
				throw new DecisionMethodException(x);
			}
		} else throw new DRuleNotImplemented();
	}

}
