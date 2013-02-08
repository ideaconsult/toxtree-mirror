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

import toxTree.tree.rules.StructureAlertCDK;
import ambit2.smarts.query.SMARTSException;

public class SA22_gen extends StructureAlertCDK {
	
	                 
	/**
	 * 
	 */
	private static final long serialVersionUID = -4104398484154885424L;
	public static String SA22_title = "Azide and triazene groups";
	public static String SA22_azide = "Azide";
	public static String SA22_triazene = "Triazene";
	
	public static String SA22_azide_smarts = "[N]=[N]-[N]";
	public static String SA22_triazene_smarts = "[N]=[N]=[N]";
	//public static String SA22_azide_triazene_smarts = "[N]=[N]-,=[N]";
	
    public SA22_gen() {
        super();
        try {
        	setContainsAllSubstructures(false);
        	addSubstructure(SA22_azide, SA22_azide_smarts);
            addSubstructure(SA22_triazene,SA22_triazene_smarts);
      
      
                    	
            setID("SA22_Ames");
            setTitle(SA22_title);
            setExplanation(SA22_title);
            
            examples[0] = "N=N";
            examples[1] = "[N-]=[N+]=[N-]";   
            editable = false;
        } catch (SMARTSException x) {
        	logger.log(Level.SEVERE,x.getMessage(),x);
        }
    }	                 
}


