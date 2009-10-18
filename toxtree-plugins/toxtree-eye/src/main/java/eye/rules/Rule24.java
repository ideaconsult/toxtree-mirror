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
 * @author Nina Jeliazkova nina@acad.bg
 */
public class Rule24 extends  RuleSMARTSubstructureCDK{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5694629047963940604L;
	private static final String MSG24="Aromatic ammonium salts";
	private static String[] smarts = {
        /*
        "c([F,Cl,Br,I,#16])c[N+]c([F,Cl,Br,I,#16])",
        "c([F,Cl,Br,I,#16])c[N+]cc([F,Cl,Br,I,#16])",
        "c([F,Cl,Br,I,#16])c[N+]ccc([F,Cl,Br,I,#16])",
        "c([F,Cl,Br,I,#16])c([F,Cl,Br,I,#16])c[N+]",
        "c([F,Cl,Br,I,#16])c([F,Cl,Br,I,#16])cc[N+]",
        "c([F,Cl,Br,I,#16])cc([F,Cl,Br,I,#16])cc[N+]"
        */
        "c([F,Cl,Br,I,#16])c[$([NH3+]),$([N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])),$(C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])),$(C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C]))]c([F,Cl,Br,I,#16])",
        "c([F,Cl,Br,I,#16])c[$([NH3+]),$([N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])),$(C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])),$(C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C]))]cc([F,Cl,Br,I,#16])",
        "c([F,Cl,Br,I,#16])c[$([NH3+]),$([N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])),$(C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])),$(C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C]))]ccc([F,Cl,Br,I,#16])",
        "c([F,Cl,Br,I,#16])c([F,Cl,Br,I,#16])c[$([NH3+]),$([N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])),$(C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])),$(C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C]))]",
        "c([F,Cl,Br,I,#16])c([F,Cl,Br,I,#16])cc[$([NH3+]),$([N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])),$(C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])),$(C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C]))]",
        "c([F,Cl,Br,I,#16])cc([F,Cl,Br,I,#16])cc[$([NH3+]),$([N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])),$(C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])),$(C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C]))]"        
        /*
		"c1([F,Cl,Br,I,#16])c([F,Cl,Br,I,#16])[cH][cH][cH]c1[NH3+]",

		"c1([F,Cl,Br,I,#16])[cH]c([F,Cl,Br,I,#16])[cH][cH]c1[NH3+]",

		"c1([F,Cl,Br,I,#16])[cH][cH]c([F,Cl,Br,I,#16])[cH]c1[NH3+]",

		"c1([F,Cl,Br,I,#16])[cH][cH][cH]c([F,Cl,Br,I,#16])c1[NH3+]",

		"[cH]1c([F,Cl,Br,I,#16])c([F,Cl,Br,I,#16])[cH][cH]c1[NH3+]",

		"[cH]1c([F,Cl,Br,I,#16])[cH]c([F,Cl,Br,I,#16])[cH]c1[NH3+]",


		"c1([F,Cl,Br,I,#16])c([F,Cl,Br,I,#16])[cH][cH][cH]c1[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"c1([F,Cl,Br,I,#16])[cH]c([F,Cl,Br,I,#16])[cH][cH]c1[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"c1([F,Cl,Br,I,#16])[cH][cH]c([F,Cl,Br,I,#16])[cH]c1[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"c1([F,Cl,Br,I,#16])[cH][cH][cH]c([F,Cl,Br,I,#16])c1[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"[cH]1c([F,Cl,Br,I,#16])c([F,Cl,Br,I,#16])[cH][cH]c1[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"[cH]1c([F,Cl,Br,I,#16])[cH]c([F,Cl,Br,I,#16])[cH]c1[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",


		"c1([F,Cl,Br,I,#16])c([F,Cl,Br,I,#16])[cH][cH][cH]c1C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"c1([F,Cl,Br,I,#16])[cH]c([F,Cl,Br,I,#16])[cH][cH]c1C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"c1([F,Cl,Br,I,#16])[cH][cH]c([F,Cl,Br,I,#16])[cH]c1C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"c1([F,Cl,Br,I,#16])[cH][cH][cH]c([F,Cl,Br,I,#16])c1C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"[cH]1c([F,Cl,Br,I,#16])c([F,Cl,Br,I,#16])[cH][cH]c1C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		
		"[cH]1c([F,Cl,Br,I,#16])[cH]c([F,Cl,Br,I,#16])[cH]c1C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		
		"c1([F,Cl,Br,I,#16])c([F,Cl,Br,I,#16])[cH][cH][cH]c1C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"c1([F,Cl,Br,I,#16])[cH]c([F,Cl,Br,I,#16])[cH][cH]c1C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"c1([F,Cl,Br,I,#16])[cH][cH]c([F,Cl,Br,I,#16])[cH]c1C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"c1([F,Cl,Br,I,#16])[cH][cH][cH]c([F,Cl,Br,I,#16])c1C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		"[cH]1c([F,Cl,Br,I,#16])c([F,Cl,Br,I,#16])[cH][cH]c1C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",

		
		"[cH]1c([F,Cl,Br,I,#16])[cH]c([F,Cl,Br,I,#16])[cH]c1C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])C([F,Cl,Br,I,#16])([F,Cl,Br,I,#16])[N+]([F,Cl,Br,I,#16])([#1,C])([#1,C])",
		*/
	};
	public Rule24() {
		super();		
		try {
			setContainsAllSubstructures(false);
			for (String smart : smarts) {
				addSubstructure(smart);
			}
/**			
			addSubstructure("[c;$([H]),$([CH3])]1c(c[c;$([H]),$([CH3])]cc1)[NH3+]");
			addSubstructure("[c;$([H]),$([CH3])]1c([c;$([H]),$([CH3])]ccc1)[NH3+]");
			addSubstructure("[c;$([H]),$([CH3])]1c(cc[c;$([H]),$([CH3])]c1)[NH3+]");
**/			
			setID("24");
			setTitle(MSG24);
			editable = false;
			examples[0] = "c1(cc(cc(c1)Br)C(C)(C)Br)Br";
			examples[1] = "c1(cc(cc(c1)Br)C(Cl)(Cl)[N+](C)(C)Br)Br";			
			
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}	
}
