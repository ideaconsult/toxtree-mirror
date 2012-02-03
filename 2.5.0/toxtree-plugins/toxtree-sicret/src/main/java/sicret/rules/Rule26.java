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
 * SurfaceTension > 62.0
 * @author Nina Jeliazkova
 *
 */
public class Rule26  extends RuleSurfaceTension {
	
	private static final long serialVersionUID = 0;
	public Rule26()
	{
		super(SurfaceTension,"mN/m",condition_higher,62);
		id = "30";
		/*
		title = "SurfaceTension > 62.0";
		propertyStaticValue = 62.0;
		condition = condition_higher;
		propertyName = SurfaceTension;
		*/

	}
    public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
        return null;
    };

}
