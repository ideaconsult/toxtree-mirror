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
 * AqueousSolubility <  0.1. Expects property to be read from IMolecule.getProperty({@link #AqueousSolubility}).
 * @author Nina Jeliazkova
 * @author Martin Martinov
 */
public class RuleAqueousSolubility extends RuleVerifyProperty {
	public static String AqueousSolubility="Water Solubility";
	private static final long serialVersionUID = 0;
	public RuleAqueousSolubility()
	{
		this(AqueousSolubility,"g/l",condition_lower,0.1);
		
	}
	public RuleAqueousSolubility(String propertyName, String units, String condition, double value) {
		super(propertyName,units,condition,value);
		setID("12");
		setTitle(getPropertyName() + getCondition() + getProperty());		
		
		examples[0]= "Oc(c(cc(c1)Cc(cc(c(O)c2C(C)(C)C)C(C)(C)C)c2)C(C)(C)C)c1C(C)(C)C";
		propertyExamples[0] = 0.000171;
		examples[1] = "Fc1ccc(cc1Cl)[N+]([O-])=O";
		propertyExamples[1] =   127.21151;			
	}

}
