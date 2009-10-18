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
 * Thiazoles and thiazolidines
 * @author Nina Jeliazkova nina@acad.bg
 */
public class Rule26 extends  RuleSMARTSubstructureCDK{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7046271360905421880L;
	private static final String MSG26="Thiazoles and thiazolidines";
	public Rule26() {
		super();		
		try {
			addSubstructure("n1c([#1,C])sc([*])c1([*])");
			addSubstructure("N1([#1,C])c([#1,C])SC([*])C1([*])");
			addSubstructure("c21[cH][cH][cH][cH]c1sc([#1,C])n2");
			addSubstructure("c21[cH][cH][cH][cH]c1SC([#1,C])N2([#1,C])");

//			addSubstructure("n1c([#1,C])sc([*])c1([*])");
			setID("26");
			setTitle(MSG26);
			editable = false;
			examples[0] = "C=1(C/C(=C\\C=1C)CO)C";
			examples[1] = "c1(sc(nc1C)CO)C";					
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}

