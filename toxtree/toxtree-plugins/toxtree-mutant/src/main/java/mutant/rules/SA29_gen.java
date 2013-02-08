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

import java.util.logging.Level;

import toxTree.tree.rules.StructureAlertCDK;
import ambit2.smarts.query.SMARTSException;

public class SA29_gen extends StructureAlertCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5061212731819103939L;
	public static String SA29 = "Aromatic diazo";
	public static String SA29_smarts="a[N]=[N]a";
	//edited to work with cdk smarts
	public static String SA29_noSO3H="[$(a:a(S(=[OX1])(=[OX1])([O-,OX2H1]))),$(a:a:a(S(=[OX1])(=[OX1])([O-,OX2H1]))),$(a:a:a:a(S(=[OX1])(=[OX1])([O-,OX2H1]))),$(a:a:a:a:a(S(=[OX1])(=[OX1])([O-,OX2H1])))][N]=[N][$(a:a(S(=[OX1])(=[OX1])([O-,OX2H1]))),$(a:a:a(S(=[OX1])(=[OX1])([O-,OX2H1]))),$(a:a:a:a(S(=[OX1])(=[OX1])([O-,OX2H1]))),$(a:a:a:a:a(S(=[OX1])(=[OX1])([O-,OX2H1])))]";
	//works with joelib, but not with cdk smarts...
	//public static String SA29_noSO3H="[$(a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a:a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a:a:a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1])][N]=[N][$(a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a:a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a:a:a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1])]";
	
	
    public SA29_gen() {
        super();
        try {
        	setContainsAllSubstructures(true);
        	addSubstructure(SA29,SA29_smarts);
        	addSubstructure("aromatic diazo with sulfonic group on both  rings",SA29_noSO3H,true);

     	
            setID("SA29_gen");
            setTitle(SA29);
            
            StringBuffer e = new StringBuffer();
            e.append("<html>");
            e.append(SA29);
            e.append("<ul>");
            e.append("<li>");
            e.append("If a sulfonic acid group (-SO3H) is present on each of the rings that contain the diazo group, the substance should be not classified.");
            
            e.append("</ul>");
            e.append("</html>");
            
            setExplanation(e.toString());
            
            examples[0] = "O=C(Nc4=cc=c(c5=cc(=c(N=Nc2=cc=c(N=Nc1=cc=c(c=c1)S(=O)(=O)O)c3=cc=c(c=c23)S(=O)(=O)O)c(O)=c45)S(=O)(=O)O)S(=O)(=O)O)C";
            examples[1] = "N(=Nc1=cc=cc=c1)c2=cc=cc=c2";   
            editable = false;
        } catch (SMARTSException x) {
        	logger.log(Level.SEVERE,x.getMessage(),x);
        }
    }	
  
}


