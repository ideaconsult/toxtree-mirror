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

public class SA31c_nogen extends StructureAlert {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1445930404886318331L;
	public static String SA31c_title = "Halogenated dibenzodioxins  (Nongenotoxic carcinogens)";
	
	
    public SA31c_nogen() {
        super();
        try {
        	setContainsAllSubstructures(false);
        	
        	addSubstructure(SA31c_title,"c1ccc2Oc3cc(ccc3(Oc2(c1)))[Cl,Br,F,I]");
        	
            setID("SA31c_nogen");
            setTitle(SA31c_title);

            StringBuffer e = new StringBuffer();
            e.append("<html>");
            e.append(SA31c_title);
           
            e.append("<br>");
            e.append("<ul>");
            e.append("<li>");
            e.append("Only the chemicals with at least one halogen in one of the four lateral positions should fire");
            e.append("</ul>");
            e.append("</html>");
            
            setExplanation(e.toString());
            
            examples[0] = "C=1C=CC=2OC=3C=CC=CC=3(OC=2(C=1))";
            examples[1] = "C=1C=C2OC=3C=C(C=CC=3(OC2(=CC=1Cl)))Cl";   
            editable = false;
        } catch (SMARTSException x) {
            logger.error(x);
        }
    }
}
