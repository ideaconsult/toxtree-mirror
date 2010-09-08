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
 * MeltingPoint < 200. Expects property to be read from IMolecule.getProperty({@link #MeltingPoint}).
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */

public class RuleMeltingPoint extends RuleVerifyProperty {
	private static final long serialVersionUID = 0;
	public static String MeltingPoint = "Melting Point";
	public RuleMeltingPoint(String propertyName, String units, String condition, double value) {
		super(propertyName,units,condition,value);
		id = "1";
	}	
	
	public RuleMeltingPoint()
	{
		this(MeltingPoint,"\u0260C",condition_higher,200.0);
		id = "1";
		examples[0] = "CC1(C(CC=C1C)C=CC(C)(C)C(O)C)C";
		examples[1] = "CCOC(=O)N1CCN(CC1)c2ccc(cc2)OCC4COC(Cn3cncc3)(O4)c5ccc(Cl)cc5Cl";

		
		examples[0]= "CC1(C(CC=C1C)C=CC(C)(C)C(O)C)C";
		propertyExamples[0] = 63.639511;
		examples[1] = "CCOC(=O)N1CCN(CC1)c2ccc(cc2)OCC4COC(Cn3cncc3)(O4)c5ccc(Cl)cc5Cl";
		propertyExamples[1] =    290.264832;		

	}


}
