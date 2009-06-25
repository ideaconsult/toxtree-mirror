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


/**
 * LipidSolubility < 0.01. Expects property to be read from IMolecule.getProperty({@link #LipidSolubility}).
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Mar 31, 2008
 */
public class RuleLipidSolubility extends sicret.rules.RuleLipidSolubility 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8538391835920402168L;

	public RuleLipidSolubility()
	{
		super();
		id = "4";
		propertyStaticValue = 0.01;
		condition = condition_lower;
		propertyName = LipidSolubility;
		setPropertyUnits("g/kg");
		setTitle(getPropertyName() + getCondition() + getProperty());
	}

}
