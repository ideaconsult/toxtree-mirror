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
 * Molecular Weight >  620
 * @author Nina Jeliazkova
 *
 */
public class RuleMolWeight_11_1 extends RuleMolWeight {
	
	private static final long serialVersionUID = 0;
	public RuleMolWeight_11_1()
	{
		super();
		id = "11.1";
		title = "Molecular Weight >  620.0";
		propertyStaticValue = 620.0;
		condition = condition_higher;
		propertyName = MolWeight;
		examples[0] = "CN(C)CCCN2c1ccccc1Sc3ccccc23";
		examples[1] = "S(c3c(\\C(=C1/C=C\\C(=[N+](/CCCC)CCCC)\\C=C1)c2ccc(cc2)N(CCCC)CCCC)ccc(S(O)(=O)=O)c3)([O-])(=O)=O";	
		setTitle(getPropertyName() + getCondition() + getProperty());
	}

}
