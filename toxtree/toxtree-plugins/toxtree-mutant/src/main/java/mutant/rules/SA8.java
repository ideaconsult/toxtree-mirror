/*
Copyright Ideaconsult Ltd. (C) 2005-2007  

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

package mutant.rules;

import toxTree.tree.rules.smarts.SMARTSException;

public class SA8 extends StructureAlert {
	public static String SA8_title = "Aliphatic halogens";
	/**
	 * 
	 */
	private static final long serialVersionUID = -3069463173082446510L;

	/**
	 *
	 */
	public SA8() {
		try {
			setContainsAllSubstructures(true); //and all smarts
			
			StringBuffer b = new StringBuffer();
			b.append("[");
			b.append("$([CX4!H0;R0])");
			b.append(";");
			b.append("!$(C([#1,C])=[O,C])");
			b.append(";");
			b.append("!$([CX4H2]([F,Cl,Br,I])[CX4H2][N,S][CX4H2][CX4H2][F,Cl,Br,I])");
			StringBuffer C= new StringBuffer();

			for (int i=0; i < 6; i++) {
				C.append("C");
				b.append(";");
				b.append("!$(");
				b.append(C);
				b.append("OP(O)(=O)");
				b.append(")");
				
				b.append(";");
				b.append("!$(");
				b.append(C);
				b.append("OS(=O)(=O)");
				b.append(")");				
			}

			b.append("]");
			b.append("[Cl,Br,I]");
			addSubstructure(SA8_title,b.toString());
			//"C"
			//addSubstructure("aliphatic halogens","[$([CX4!H0;R0]);!$(C([#1,C])=[O,C]);!$([CX4H2]([F,Cl,Br,I])[CX4H2][N,S][CX4H2][CX4H2][F,Cl,Br,I])][Cl,Br,I]");
			//addSubstructure("aliphatic halogens","[$([CX4!H0;!R][Cl,Br,I])]");
			//addSubstructure("not crowded", "C([C])([C])C[C!H0][Cl,Br,I]",true);
			
//			addSubstructure(SA4.SA4_title,SA4.SA4_smarts,true);
//			addSubstructure(SA5.SA5_title,SA5.SA5_smarts,true);
			
//			addSubstructure("not crowded", "[$([!#1]([!#1])([!#1])C[CH2]([Cl,Br,I]))]",true);
			/*
			 * geminal di- or tri-halogens should not anymore be excluded. 
			addSubstructure("!aliphatic halogens & geminal dihalogen substituent", 
					"[!$([C!H0][Cl,Br,I])&$([C]([F,Cl,Br,I])[F,Cl,Br,I])]",true);
			*/
			/*									
			addSubstructure("!tertiary halogen", 
					"[$(C([!#1])([!#1])([!#1])[F,Cl,Br,I])]",true);
			
			*/

			
			setID("SA8");
			setTitle(SA8_title);
			b = new StringBuffer();
			b.append("This alert contains non tertiary aliphatic halogens.");
            /*            
			b.append("<ul>");

			b.append("<li>geminal di- or tri- halogen substituent");
			b.append("<li>aromatic halogen");
			b.append("<li>tertiary halogens");
			b.append("<li>primary aliphatic halocarbons adjoined to a sterically crowded atom");

			b.append("</ul>");
			b.append("should be excluded.<br/>");
            */            
			b.append("Substances fired by Alerts <a href=\"#SA2\">SA2</a>,<a href=\"#SA4\">SA4</a>,<a href=\"#SA5\">SA5</a> and <a href=\"#SA20\">NA20</a> should be also excluded.");
			setExplanation(b.toString());
			
			examples[0] = "C1=CC=C(C=C1)Cl";
			examples[1] = "CC(CCl)OC(C)CCl";	
			editable = false;
		} catch (SMARTSException x) {
			logger.error(x);
		}	
	}

}

