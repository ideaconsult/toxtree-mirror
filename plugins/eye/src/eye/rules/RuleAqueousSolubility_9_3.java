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

import sicret.rules.RuleAqueousSolubility;

/**
 * AqueousSolubility <  0.0001
 * @author Nina Jeliazkova
 *
 */
public class RuleAqueousSolubility_9_3 extends RuleAqueousSolubility {
	
	private static final long serialVersionUID = 0;

	public RuleAqueousSolubility_9_3()
	{
		super(AqueousSolubility,"g/l",condition_lower,0.1);
		id = "9.3";
		/*
		title = "AqueousSolubility <  0.0001";
		propertyStaticValue = 0.0001;
		condition = condition_lower;
		propertyName = AqueousSolubility;
		*/
		examples[0] = "C23(C1NC1CN2\\C4=C(/C3COC(=O)N)C(\\C(=C(/C4=O)C)OC)=O)OC";
		examples[1] = "C1(NC(=O)CC(N1)=O)=O";		

	}

	public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
	    return null;
    };
}
