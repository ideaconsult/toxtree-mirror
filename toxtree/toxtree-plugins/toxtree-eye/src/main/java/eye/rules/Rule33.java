/*
Copyright (C) 2005-2008  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package eye.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;

/**
 * 
 * @author nina
 *
 */
public class Rule33 extends RuleSMARTSSubstructureAmbit {
	/**
	 * 
	 */
	private static final long serialVersionUID = -437751597303663215L;
	public static final String MSG33 = "Aromatic ammonium salts";
	public static final String[] smarts = {

/*
		"[c;!$([*Cl,Br,I,#16])]1[c;!$([*Cl,Br,I,#16])][c;!$([*Cl,Br,I,#16])][c;!$([*Cl,Br,I,#16])][c;!$([*Cl,Br,I,#16])]c1[NH3+]",

		"[c;!$([*Cl,Br,I,#16])]1[c;!$([*Cl,Br,I,#16])][c;!$([*Cl,Br,I,#16])][c;!$([*Cl,Br,I,#16])][c;!$([*Cl,Br,I,#16])]c1[N+]([!F;!Cl;!Br;!I;!#16])([#1,C])([#1,C])",

		"[c;!$([*Cl,Br,I,#16])]1[c;!$([*Cl,Br,I,#16])][c;!$([*Cl,Br,I,#16])][c;!$([*Cl,Br,I,#16])][c;!$([*Cl,Br,I,#16])]c1C([!F,!Cl,!Br,!I,!#16])([!F,!Cl,!Br,!I,!#16])[N+]([!F;!Cl;!Br;!I;!#16])([#1,C])([#1,C])",

		"[c;!$([*Cl,Br,I,#16])]1[c;!$([*Cl,Br,I,#16])][c;!$([*Cl,Br,I,#16])][c;!$([*Cl,Br,I,#16])][c;!$([*Cl,Br,I,#16])]c1C([!F,!Cl,!Br,!I,!#16])([!F,!Cl,!Br,!I,!#16])C([!F,!Cl,!Br,!I,!#16])([!F,!Cl,!Br,!I,!#16])[N+]([!F;!Cl;!Br;!I;!#16])([#1,C])([#1,C])"
*/
        "c1c([#1,!F&!Cl&!Br&!I&!#16])c([#1,!F&!Cl&!Br&!I&!#16])c([#1,!F&!Cl&!Br&!I&!#16])c([#1,!F&!Cl&!Br&!I&!#16])c1[$([NH3+]),$([N+]([!F;!Cl;!Br;!I;!#16])([#1,C])([#1,C])),$(C([!F;!Cl;!Br;!I;!#16])([!F;!Cl;!Br;!I;!#16])[N+]([!F;!Cl;!Br;!I;!#16])([#1,C])([#1,C])),$(C([!F;!Cl;!Br;!I;!#16])([!F;!Cl;!Br;!I;!#16])C([!F;!Cl;!Br;!I;!#16])([!F;!Cl;!Br;!I;!#16])[N+]([!F;!Cl;!Br;!I;!#16])([#1,C])([#1,C]))]"		
		//"c1c([!$(Cl)])cccc1[$([NH3+])]"
	};
	/**
	 * doesn't work with CDK smarts parser for some reason... 
	 *
	 */
	public Rule33() {
		
		super();		
		try {
			for (String smart: smarts) 
				addSubstructure(smart);
			//addSubstructure("[c;$([H]),$([CH3])]1c(c[c;$([H]),$([CH3])]cc1)[NH3+]");
			setID("33");
			setTitle(MSG33);
			editable = false;
			examples[0] = "c1(cc(cc(c1)C)C(C)(C)C)C";
			//examples[0] = "c1cc(Cl)c(Br)cc1[N+]";
			//examples[0] = "c1(cc(cc(c1)Br)C(Cl)(Cl)[N+](C)(C)Br)Br";
			
			examples[1] = "c1(cc(cc(c1)C)[N+](C)(C)C)C";					
			//examples[1] = "c1ccccc1[N+]";
			
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
}


