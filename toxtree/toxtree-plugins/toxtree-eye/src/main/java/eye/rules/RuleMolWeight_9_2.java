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
package eye.rules;

import javax.swing.JComponent;

import org.openscience.cdk.interfaces.IAtomContainer;

import sicret.rules.RuleMolWeight;

/**
 * MoleculWeight > 290.0
 * @author Nina Jeliazkova
 *
 */
public class RuleMolWeight_9_2 extends RuleMolWeight {
	
	private static final long serialVersionUID = 0;
	public RuleMolWeight_9_2()
	{
		super();
		id = "9.2";
		title = "Molecular Weight  > 290.0";
		propertyStaticValue = 290.0;
		condition = condition_higher;
		propertyName = MolWeight;
		examples[0] = "C\\1=C\\NC(=O)NC/1=O";
		examples[1] = "CO\\C1=C\\C=C3/C(=C\\C1=O)C(CCc2cc(c(c(c23)OC)OC)OC)NC(C)=O";		
		setTitle(getPropertyName() + getCondition() + getProperty());
	}

	@Override
	public JComponent optionsPanel(IAtomContainer atomContainer) {
	    return null;
	}

}
