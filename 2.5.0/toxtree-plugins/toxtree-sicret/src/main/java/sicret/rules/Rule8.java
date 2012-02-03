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
package sicret.rules;



/**
 * LipidSolubility <  0.4
 * @author Nina Jeliazkova
 *
 */
public class Rule8 extends RuleLipidSolubility {
	private static final long serialVersionUID = 0;
	public Rule8()
	{
		super();
		id = "10";
		title = "LipidSolubility <  0.4";
		propertyStaticValue = 0.4;
		condition = condition_lower;
		propertyName = LipidSolubility;

	}
    public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
        return null;
    };
}
