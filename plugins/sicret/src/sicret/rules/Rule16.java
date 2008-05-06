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
 * LogKow > 5.5
 * @author Nina Jeliazkova
 *
 */
public class Rule16 extends RuleLogP {
	
	private static final long serialVersionUID = 0;
	public Rule16()
	{
		super(LogKow,"",condition_higher,5.5);
		id = "18";
		examples[0]="Oc(c(cc(c1)Cc(cc(c(O)c2C(C)(C)C)C(C)(C)C)c2)C(C)(C)C)c1C(C)(C)C";
		propertyExamples[0] = 6.37044824156464;
		examples[1] = "Cc1cc(cc(C)c1N)N(=O)=O";
		propertyExamples[1] = 2.03426733210718;		
		 		
	}
}
