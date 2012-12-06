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

import toxTree.tree.rules.StructureAlertCDK;
import ambit2.smarts.query.SMARTSException;

public class SA23_gen extends StructureAlertCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3589707922783454840L;
	/**
	 * 
	 */

	public static String SA23_title = "Aliphatic N-nitro";
    public SA23_gen() {
        super();
        try {
        	setContainsAllSubstructures(false);
        	addSubstructure(SA23_title, "[C!r][NH1]N(=O)O");
        	addSubstructure(SA23_title+"1", "[C!r]N(A)N(=O)O");
     	
            setID("SA23_gen");
            setTitle(SA23_title);
            setExplanation(SA23_title + "<br>The possibility to have an aromatic substituent on the nitrogen should be excluded.");
            
            examples[0] = "CCN(C1=CC=CC=C1)N(=O)O";
            examples[1] = "CCCN(CC)N(=O)O";   
            editable = false;
        } catch (SMARTSException x) {
            logger.error(x);
        }
    }	
}


