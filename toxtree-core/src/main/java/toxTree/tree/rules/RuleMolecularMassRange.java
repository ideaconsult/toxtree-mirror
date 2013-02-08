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

import org.openscience.cdk.qsar.descriptors.molecular.WeightDescriptor;

/**
 * Molecular mass in [{@link #getMinValue()},{@link #getMaxValue()}]
 * TODO add description
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleMolecularMassRange extends RuleDescriptorRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 301601043868779452L;

	public RuleMolecularMassRange() {
		super();
		setDescriptor(new WeightDescriptor());
		setMaxValue(600.0);
		setMinValue(0.0);
	}

	
	@Override
	public boolean isImplemented() {
		return true;
	}
	/*
	@Override
	public IDecisionRuleEditor getEditor() {
		RuleRangeEditor e = new RuleRangeEditor(this);
		e.setSetPropertyEditable(false);
		return e;
	}
	*/
	public Object getProperty() {
		return "Molecular weight";
	}
	public void setProperty(Object value) {
	
	}
}
