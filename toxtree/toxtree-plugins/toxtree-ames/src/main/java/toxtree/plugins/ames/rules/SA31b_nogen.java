/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

Contact: jeliazkova.nina@gmail.com

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

package toxtree.plugins.ames.rules;

import toxTree.tree.rules.StructureAlert;
import ambit2.smarts.query.SMARTSException;

public class SA31b_nogen extends StructureAlert {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8299877660995850022L;
	public static String SA31b_title = "Halogenated PAH (naphthalenes, biphenyls, diphenyls)  (Nongenotoxic carcinogens)";
	
	
    public SA31b_nogen() {
        super();
        try {
        	setContainsAllSubstructures(false);
        	
        	addSubstructure("naphtalenes","[Cl,Br,F,I]c1ccc2ccccc2(c1)");
        	//addSubstructure("biphenyls1","c1cc(ccc1c2ccc(cc2)[Cl,Br,F,I])[Cl,Br,F,I]");
        	addSubstructure("biphenyls1","[Cl,Br,F,I]c1ccc(cc1)!@c2ccc(cc2)[Cl,Br,F,I]");
        	

        	/*
        	addSubstructure("biphenyls2","c1cc(ccc1c2c([Cl,Br,F,I])cc(cc2))[Cl,Br,F,I]");
        	addSubstructure("biphenyls3","c1cc(ccc1c2cc([Cl,Br,F,I])c(cc2))[Cl,Br,F,I]");
        	addSubstructure("biphenyls4","c1c([Cl,Br,F,I])c(ccc1c2cc([Cl,Br,F,I])c(cc2))");
        	addSubstructure("biphenyls5","c([Cl,Br,F,I])1cc(ccc1c2cc([Cl,Br,F,I])c(cc2))");
        	*/
        	addSubstructure("diphenyls","c1cc(ccc1[!R]c2ccc(cc2)[Cl,Br,F,I])[Cl,Br,F,I]");
        	

    	
            setID("SA31b_nogen");
            setTitle(SA31b_title);

            StringBuffer e = new StringBuffer();
            e.append("<html>");
            e.append(SA31b_title);
     
            e.append("<br>");
            e.append("<ul>");
            e.append("<li>");
            e.append("Naphthalenes: should fire with any halogenation pattern");
            
            e.append("<li>");
            e.append("Diphenyls and biphenyls: should fire only if both 4 and 4\u0027 positions are occupied by halogens");
            
            e.append("</ul>");
            e.append("</html>");
            
            setExplanation(e.toString());
            
            examples[0] = "CC1C=3C=C(F)C=CC=3(C2=CC=C(F)C=C12)";
//"NC2=CC=C(CC1=CC=C(N)C(=C1)Cl)C=C2Cl";
            examples[1] = "C=1C=C(C=CC=1C2=CC=C(C=C2)Cl)Cl";   
            editable = false;
        } catch (SMARTSException x) {
            logger.error(x);
        }
    }
}
