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

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.IDescriptor;
import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.result.DoubleResult;
import org.openscience.cdk.qsar.result.IDescriptorResult;
import org.openscience.cdk.qsar.result.IntegerResult;

import toxTree.core.IDecisionRuleEditor;
import toxTree.core.IRuleRange;
import toxTree.exceptions.DecisionMethodException;

/**
 * 
 * Verifies if the descriptor is within a range
 * @author Nina Jelizekova
 * <b>Modified</b> 2005-10-18
 */
public class RuleDescriptorRange extends RuleDescriptor implements IRuleRange {
	protected double minValue, maxValue;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5767622970686754198L;

	public RuleDescriptorRange() {
		super();
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		try {
			if (descriptor != null)  {
			    if (!(descriptor instanceof IMolecularDescriptor))
			        throw new DecisionMethodException("Not a molecular descriptor "+descriptor.toString());
				DescriptorValue value = ((IMolecularDescriptor)descriptor).calculate(mol);
				if (value.getException()!= null) throw value.getException();
				IDescriptorResult result = value.getValue();
				if (result instanceof DoubleResult) {
					double d = ((DoubleResult) result).doubleValue();
					logger.info(descriptor.getSpecification().getImplementationTitle(),"\t",
							Double.toString(d));
					return (d>=minValue) && (d<=maxValue);
				} else if (result instanceof IntegerResult) {
					int i = ((IntegerResult) result).intValue();
					logger.info(descriptor.getSpecification().getImplementationTitle(),"\t",
							Integer.toString(i));
					return (i>=minValue) && (i<=maxValue);					
				}  else 
					throw new DecisionMethodException("UNSUPPORTED descriptor result " + result.getClass().getName());
			} else throw new DecisionMethodException("Descriptor not assigned!");
		} catch (DecisionMethodException x) {
			throw x;
	
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}
	public Object getProperty() {
		if (descriptor ==null) return "Descriptor not defined";
		else return descriptor.getClass().getName();
	}
	public void setProperty(Object value) {
		if (value instanceof IDescriptor)
			setDescriptor((IDescriptor)value);
	}
	/*
	@Override
	public IDecisionRuleEditor getEditor() {
		return new RuleRangeEditor(this);
	}
	*/
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RuleDescriptorRange)
			return super.equals(obj) && (getMaxValue()== ((RuleDescriptorRange)obj).getMaxValue()) &&
				(getMinValue()== ((RuleDescriptorRange)obj).getMinValue());
		else return false;
	}
	

}
