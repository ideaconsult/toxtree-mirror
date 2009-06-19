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

public class SA17 extends StructureAlertCDK {
	//protected SA16 sa16;
    /**
     * 
     */
    private static final long serialVersionUID = 397119362022175631L;
    protected static String sa17_title = "Thiocarbonyl (Nongenotoxic carcinogens)";
    public SA17() {
        super();
      //  sa16 = new SA16();
        try {
        	setContainsAllSubstructures(true);
            //addSubstructure(sa17_title, "[#7X3][#6](=[SX1])[!O;!S]");
        	
        	addSubstructure(sa17_title, "[#7X3][#6](=[SX1])[!$([O,S][CX4])!$([OH,SH])!$([O-,S-])]");
            
            //suggested by Cecilia Bossa - Rule SA17 wrong hits 3  [iss2_556, iss2_741, iss2_878]
            //addSubstructure(sa17_title, "[#7X3][#6](=[SX1])[!$([O,S][CX4])]");
            
           
            setID("SA17");
            setTitle(sa17_title);
            StringBuffer e = new StringBuffer();
            e.append(sa17_title);
            e.append("<br>Substances that contain groups S=C=SH, S=C=[S-], S=C-OH, S=C-[O-] should be excluded.");
            e.append("<br>Exclude substances fired by <a href=\"#SA16\">SA16</a>.");
            setExplanation(e.toString());
            
            examples[0] = "S=C(N)O";
            examples[1] = "C1=CN(C(N1)=S)C";   
        } catch (SMARTSException x) {
            logger.error(x);
        }
    }
    /*
    @Override
    public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
    	if (super.verifyRule(mol)) {
    		return !(sa16.verifyRule(mol));
    	} else return false;
    }
    */
}


