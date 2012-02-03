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


import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;

/**
 * 
 * Diphenyl iodonium salts<br>
 * @author Nina Jeliazkova nina@acad.bg

 */

public class Rule19 extends  RuleSMARTSubstructureCDK{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4615399415007083583L;
	private static final String MSG19 = "Diphenyl iodonium salts";
	public Rule19() {
		super();		
		try {
			addSubstructure("[*]c1[cH][cH][c]([cH][cH]1)[I+]c2[cH][cH]c([cH][cH]2)[C]");
					//"c1c(C)ccc(c1)[I+]c2ccc(*)cc2");
			

					//"c1c(C)ccc(c1)[I+]c2ccc(*)cc2");.0
			
			setID("19");
			setTitle(MSG19);
			editable = false;
			examples[0] = "c1(ccc(cc1)CC=C)[Cl+]c2ccc(cc2)Cl";
			examples[1] = "c1(ccc(cc1)CC=C)[I+]c2ccc(cc2)Cl";			
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}
