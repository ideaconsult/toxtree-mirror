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
 * 
 * LogKow > 3.6
 *
 */
public class RuleLogP_11_5  extends RuleLogP {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4467205718576125531L;

	public RuleLogP_11_5()
	{
		super(LogKow,"",condition_higher,3.6);
		id = "11.5";
		examples[0] = "C12(C(NC(=S)NC1=O)=O)CCCCC2";
		examples[1] = "N1(c3c(Sc2c1cccc2)ccc(c3)SC)CCC4N(C)CCCC4";	
		
	}


}
