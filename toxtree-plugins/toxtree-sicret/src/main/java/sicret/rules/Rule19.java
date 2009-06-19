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
 * MoleculWeight > 370.0
 * @author Nina Jeliazkova
 *
 */
public class Rule19 extends RuleMolWeight {
	
	private static final long serialVersionUID = 0;
	public Rule19()
	{
		super();
		id = "22";
		title = "Molecular Weight  > 370.0";
		propertyStaticValue = 370.0;
		condition = condition_higher;
		propertyName = MolWeight;
		examples[0]="O=C(N)CCCCCCCCCCCC=CCCCCCCCC";
		propertyExamples[0] = 337.591993;
		examples[1] = "O=C(OCCCCCCC(C)C)C1CCCCC1C(=O)OCCCCCCC(C)C";
		propertyExamples[1] =  424.665991;			

	}


}
