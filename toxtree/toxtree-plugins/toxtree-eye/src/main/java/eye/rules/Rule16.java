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


import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;

/**
 * 
 * Halogen benzenes with substituents containing carboxylic acid groups
 * <br>
 * SMARTS pattern  [CX3](=[OX1])[NX3H][CX3](=[OX1]).
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule16 extends  RuleSMARTSubstructureCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6619307428513761997L;
	public static final String MSG16 = "Halogen benzenes with substituents containing carboxylic acid groups";
	public Rule16() {
		//TODO fix sterically hindered condition (example NO fails)
		super();		
		try {
			setContainsAllSubstructures(false);
			
			addSubstructure("c1([F,Cl,Br])(c([#1,F,Cl,Br])[cH]c([OX2][CH]([C])[CX3](=O)[OX2H1])[cH][cH]1)");
					//"c1([F,Cl,Br])(c([H,F,Cl,Br])cc([OX2][CH]([AR0])[CX3](=O)[OX2H1])cc1)");
			addSubstructure("c1([F,Cl,Br])([cH]c([#1,F,Cl,Br])c([OX2][CH]([C])[CX3](=O)[OX2H1])[cH][cH]1)");
					//"c1([F,Cl,Br])(cc([H,F,Cl,Br])c([OX2][CH]([AR0])[CX3](=O)[OX2H1])cc1)");
			addSubstructure("c1([F,Cl,Br])([cH][cH]c([OX2][CH]([C])[CX3](=O)[OX2H1])c([#1,F,Cl,Br])[cH]1)");
					//"c1([F,Cl,Br])(ccc([OX2][CH]([AR0])[CX3](=O)[OX2H1])c([H,F,Cl,Br])c1)");
			addSubstructure("c1([F,Cl,Br])([cH][cH]c([OX2][CH]([C])[CX3](=O)[OX2H1])[cH]c1([#1,F,Cl,Br]))");
					//"c1([F,Cl,Br])(ccc([OX2][CH]([AR0])[CX3](=O)[OX2H1])cc1([H,F,Cl,Br]))");
			setID("16");
			setTitle(MSG16);
			
			editable = false;
			examples[0] = "c1(ccc(cc1)I)OC(C(=O)O)CC";
			examples[1] = "c1(ccc(cc1)Br)OC(C(=O)O)CC";

		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
}

