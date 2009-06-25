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
 * 
 * AqueousSolubility < 0.001
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleAqueousSolubility_10_5 extends RuleAqueousSolubility {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9207094589145106619L;

	public RuleAqueousSolubility_10_5()
	{
		super(AqueousSolubility,"g/l",condition_lower,0.004);
		id = "10.5";
		examples[0] = "[N+](c2cc(c(NC(c1c(ccc(c1)Cl)O)=O)cc2)Cl)([O-])=O";
		examples[1] = "c1(c(cc(cc1I)CC(C(=O)O)N)I)Oc2cc(c(c(c2)I)O)I";	

	}

    public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
        return null;
    };
}
