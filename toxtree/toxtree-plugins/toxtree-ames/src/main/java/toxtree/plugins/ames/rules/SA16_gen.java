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

import java.util.logging.Level;

import toxTree.tree.rules.StructureAlert;
import ambit2.smarts.query.SMARTSException;

public class SA16_gen extends StructureAlert {
    /**
     * 
     */
    private static final long serialVersionUID = 2255928320356164303L;
    public static String sa16_title = "Alkyl carbamate and thiocarbamate";
    public static String sa16_smarts = "[NX3]([CX4,#1])([CX4,#1])C(=[O,S])[O,S][CX4]";
    public SA16_gen() {
        super();
        try {
            //addSubstructure("1", "[NX3H2]C(=[O,S])[O,S]C");
            //addSubstructure("2", "[NX3H](C)C(=[O,S])[O,S]C");
            addSubstructure("3", sa16_smarts);
            setID("SA16_Ames");
            setTitle(sa16_title);
            setExplanation(sa16_title);
            
            examples[0] = "[H]N([H])C(=O)Oc1ccccc1";
            examples[1] = "[H]N([H])C(=O)OCCCC";   
        } catch (SMARTSException x) {
        	logger.log(Level.SEVERE,x.getMessage(),x);
        }
    }
}


