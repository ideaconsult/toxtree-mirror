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

import toxTree.tree.rules.RuleVerifyProperty;

/**
 * SurfaceTension >  62.0.
 * Expects property to be read from IMolecule.getProperty({@link #SurfaceTension}).
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleSurfaceTension extends RuleVerifyProperty {
	public static String SurfaceTension="Surface Tension";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8012546663800600074L;
	public RuleSurfaceTension(String propertyName, String units, String condition, double value) {
		super(propertyName,units,condition,value);
		id = "7";
	}	

	public RuleSurfaceTension()
	{
		this(SurfaceTension,"mN/m",condition_higher,62);
		id = "7";

	}
	

}

