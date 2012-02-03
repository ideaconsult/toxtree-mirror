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
 * Molecular Weight > 280.0
 * @author Nina Jeliazkova
 *
 */
public class Rule30  extends RuleMolWeight {
	
	private static final long serialVersionUID = 0;
	public Rule30()
	{
		super();
		id = "35";
		title = "Molecular Weight > 280.0";
		propertyStaticValue = 280.0;
		condition = condition_higher;
		propertyName = MolWeight;

		examples[0]= "S(SCCC)CCC";
		propertyExamples[0] = 150.305997;
		examples[1] = "OS(=O)(=O)c1cc2NC4(Nc2c(c1)S(O)(=O)=O)C=3\\C=C/C4=C\\C=3";
		propertyExamples[1] =   674.567985;		
		
	}


}
