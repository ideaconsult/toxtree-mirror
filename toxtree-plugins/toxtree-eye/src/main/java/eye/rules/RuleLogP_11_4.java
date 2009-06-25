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

import sicret.rules.RuleLogP;

/**
 * LogKow < -2
 * @author Nina Jeliazkova
 *
 */
public class RuleLogP_11_4 extends RuleLogP {
	
	private static final long serialVersionUID = 0;
	public RuleLogP_11_4()
	{
		super(LogKow,"",condition_lower,-2);
		id = "11.4";
		
		examples[0] = "N1(c3c(Sc2c1cccc2)ccc(c3)SC)CCC4N(C)CCCC4";
		examples[1] = "C(C(N)CS)(=O)O";	
		
	}


}
