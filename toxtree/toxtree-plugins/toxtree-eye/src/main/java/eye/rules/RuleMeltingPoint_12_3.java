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

import sicret.rules.RuleMeltingPoint;

/**
 * MeltingPoint > 65.0
 * @author Nina Jeliazkova
 *
 */
public class RuleMeltingPoint_12_3  extends RuleMeltingPoint {
	
	private static final long serialVersionUID = 0;
	public RuleMeltingPoint_12_3()
	{
		super(MeltingPoint,"\u0260C",condition_higher,65.0);
		id = "12.3";
		setTitle(getPropertyName() + getCondition() + getProperty());
		/*
		title = "MeltingPoint > 120.0";
		propertyStaticValue = 120.0;
		condition = condition_higher;
		propertyName = MeltingPoint;
		*/
		examples[0] = "C(=O)(c1ccccc1)CCl";
		examples[1] = "C41(C(C3C(C(C1)O)(C\\2(\\C(=C/C(/C=C/2)=O)CC3)C)F)CC(C4(C(=O)CO)O)C)C";		
		

	}


    public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
        return null;
    };
}
