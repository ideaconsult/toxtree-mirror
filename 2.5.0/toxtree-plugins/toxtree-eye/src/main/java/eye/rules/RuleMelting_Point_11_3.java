/*
Copyright Ideaconsult Ltd.(C) 2006-2011
Contact: jeliazkova.nina@gmail.com

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
 * MeltingPoint > 200.0
 * @author Nina Jeliazkova
 *
 */
public class RuleMelting_Point_11_3 extends RuleMeltingPoint {
	
	private static final long serialVersionUID = 0;
	public RuleMelting_Point_11_3()
	{
		super(MeltingPoint,"\u2103",condition_higher,50.0);
		id = "11.3";
		setTitle(getPropertyName() + getCondition() + getProperty());
		examples[0] = "C(=S)=N/CC=C";
		examples[1] = "N1C(N/C(=C\\C1=O)CCC)=S";	
		

	}
    public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
        return null;
    };
}
