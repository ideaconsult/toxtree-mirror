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
 * MoleculWeight > 370
 * @author Nina Jeliazkova
 *
 */
public class RuleMolweight_12_1 extends RuleMolWeight {
	
	private static final long serialVersionUID = 0;
	public RuleMolweight_12_1()
	{
		super();
		
		title = "Molecular Weight > 370.0";
		propertyStaticValue = 370.0;
		
		condition = condition_higher;
		propertyName = MolWeight;
		setID("12.1");
		examples[0] = "c1(COC(=O)Cl)ccccc1";
		examples[1] = "Oc1c(cc(c(c1Cc1c(c(cc(c1Cl)Cl)Cl)O)Cl)Cl)Cl";	
		setTitle(getPropertyName() + getCondition() + getProperty());

	}


}
