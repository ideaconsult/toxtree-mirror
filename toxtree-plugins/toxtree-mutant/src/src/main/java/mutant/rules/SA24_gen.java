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

import toxTree.tree.rules.StructureAlertCDK;
import ambit2.smarts.query.SMARTSException;

public class SA24_gen extends StructureAlertCDK {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5624687406911731732L;
	public static String SA24_title = "\u03B1,\u03B2 unsaturated alkoxy";
    public SA24_gen() {
        super();
        try {
        	setContainsAllSubstructures(false);
        	addSubstructure(SA24_title, "[!$([#6](=O)[!O]),#1][C!H0;!R]([!$([#6](=O)[!O]),#1])!@;=[C!H0;!R]O[#6]");
        	
     	
            setID("SA24_gen");
            setTitle(SA24_title);
            setExplanation(SA24_title + "<br>An aromatic substituent on the oxygen is also allowed.<br>If a partial overlap with SA10_gen is found, this alert should not fire.");
            
            examples[0] = "COC=CC(C)=O"; //partial overlap with SA10_gen
//"C1CCC(OC)=CC1";
            examples[1] = "C=COC=1C=CC(=CC=1)N(=O)O";   
            editable = false;
        } catch (SMARTSException x) {
            logger.error(x);
        }
    }		
}


