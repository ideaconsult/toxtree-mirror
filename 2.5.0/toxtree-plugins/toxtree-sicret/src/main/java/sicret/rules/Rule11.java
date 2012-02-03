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
 * LogKow >  4.5
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule11 extends RuleLogP {
	
	private static final long serialVersionUID = 0;
	public Rule11()
	{
		super(LogKow,"",condition_higher,4.5);
		id = "13";
		examples[0]="C12CCC(C3(C1(CCC3)C(=O)OCC))C2";
		propertyExamples[0] = 3.132985201739;
		examples[1] = "CCCCCCCCCC[C@H]2C[C@H]\\1C[C@@H]2/C=C/1";
		propertyExamples[1] = 6.1431003262466;
	}

}
