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
 * LogKow >  3.8
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleLogP_10_1 extends RuleLogP {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7707624938651582581L;

	public RuleLogP_10_1()
	{
		super(LogKow,"",condition_higher,3.8);
		id = "10.1";
		examples[0] = "N\1(C(NC(/C(=C/1)F)=O)=O)C2OC(CO)C(C2)C";
		examples[1] = "[N+](c2cc(c(NC(c1c(ccc(c1)Cl)O)=O)cc2)Cl)([O-])=O";			
		

	}

}
