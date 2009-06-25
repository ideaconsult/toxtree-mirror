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
 * LogP > 4.5
 * @author Nina Jeliazkova
 *
 */
public class RuleLogP_12_4 extends RuleLogP {
	private static final long serialVersionUID = 0;
	public RuleLogP_12_4()
	{
		super(LogKow,"",condition_higher,4.5);
		id = "12.4";
		examples[0] = "C41(C(C3C(C(C1)O)(C\\2(\\C(=C/C(/C=C/2)=O)CC3)C)F)CC(C4(C(=O)CO)O)C)C";
		examples[1] = "c1(c(cc(cc1I)CC(=O)O)I)Oc2cc(I)c(cc2)O";		

	}

}