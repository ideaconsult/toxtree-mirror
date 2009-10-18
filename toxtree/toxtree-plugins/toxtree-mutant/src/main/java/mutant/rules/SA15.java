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

import toxTree.tree.rules.StructureAlert;
import ambit2.smarts.query.SMARTSException;

public class SA15 extends StructureAlert {
    /**
     * 
     */
    private static final long serialVersionUID = -3437130658406764865L;
    protected static String sa15_title = "Isocyanate and isothiocyanate groups"; 
    public SA15() {
        super();
        try {
            addSubstructure(sa15_title, "[NX2]=C=[O,S]");

            setID("SA15");
            setTitle(sa15_title);
            setExplanation(sa15_title);
            
            examples[0] = "C=CCN=C";
            examples[1] = "C=CCN=C=S";   
        } catch (SMARTSException x) {
            logger.error(x);
        }
    }
}


