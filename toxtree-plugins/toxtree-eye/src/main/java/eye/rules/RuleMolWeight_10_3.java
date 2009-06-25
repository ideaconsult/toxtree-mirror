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

import sicret.rules.RuleMolWeight;

/**
 * Molecular Weight > 370.0
 * @author Nina Jeliazkova
 *
 */
public class RuleMolWeight_10_3 extends RuleMolWeight {
	
	private static final long serialVersionUID = 0;
	public RuleMolWeight_10_3()
	{
		super();
		id = "10.3";
		title = "Molecular Weight > 370.0";
		propertyStaticValue = 370.0;
		condition = condition_higher;
		propertyName = MolWeight;
		examples[0] = "CN2C(=O)C\\N=C(\\c1ccccc1)c3cc(ccc23)Cl";
		examples[1] = "CCCCc2oc1ccccc1c2C(=O)c3cc(c(c(c3)I)OCCN(CC)CC)I";			
		setTitle(getPropertyName() + getCondition() + getProperty());
	}


}
