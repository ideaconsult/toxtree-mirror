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
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.StructureAlertCDK;
import ambit2.core.data.MoleculeTools;
import ambit2.smarts.query.SMARTSException;

/**
 * Aromatic mono- and dialkylamine (with exceptions).
 * TODO SO3H subrule should be implemented
 * @author Nina Jeliazkova
 *
 */
public class SA28bis_gen extends StructureAlertCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3084959606481108406L;
	public static String SA28bis_title = "Aromatic mono- and dialkylamine";
      
    public static String[][] amines = {
            {SA28bis_title,"[NX3;v3]([#1,CH3])([CH3])"},
            {SA28bis_title,"[NX3;v3]([#1,CH3])([CH2][CH3])"},
            {SA28bis_title,"[NX3;v3]([CH2][CH3])([CH2][CH3])"},
           
    };  
    /*
    public static Object[][] exclusion_rules = {
            {"Ortho-disubstitution","a(a[A;!#1])(a[A;!#1])","[H]C=1C([H])=C(C)C(=C(C)C=1([H]))N([H])OC=O","",new Boolean(false)},
            {"Carboxylic acid substituent at ortho position","aa[CX3](=O)[OX2H1]","O=C(O)C1=CC=CC=C1(N)","",new Boolean(false)},
            {"-SO3H on the same ring","aa[SX4](=[OX1])(=[OX1])([O-])","NC=1C=CC=CC=1S(=O)(=O)[O-]","",new Boolean(false)},
            {"-SO3H on the same ring","aaa[SX4](=[OX1])(=[OX1])([O-])","NC=1C=CC=C(C=1)S(=O)(=O)[O-]","",new Boolean(false)},
            {"-SO3H on the same ring","aaaa[SX4](=[OX1])(=[OX1])([O-])","O=S(=O)([O-])C1=CC=C(N)C=C1","",new Boolean(false)},
            {"-SO3H on the same ring","aaaaa[SX4](=[OX1])(=[OX1])([O-])","","",new Boolean(false)},
            {"-SO3H on the same ring","aaaaaa[SX4](=[OX1])(=[OX1])([O-])","","",new Boolean(false)},
    };	
	*/
    public SA28bis_gen() throws SMARTSException  {
        super();
        	setContainsAllSubstructures(false);
            StringBuffer b = new StringBuffer();
            b.append("[a");
            for (int i=0; i < SA28_gen.exclusion_rules.length;i++) {
                b.append(";!$(");
                b.append(SA28_gen.exclusion_rules[i][1]);
                b.append(")");
            }
            b.append("]!@[");
            for (int i=0; i < amines.length;i++) {
                if (i>0) b.append(',');
                b.append("$(");
                b.append(amines[i][1]);
                b.append(")");
            } 
            b.append("]");
            addSubstructure(SA28bis_title,b.toString());             

            setID("SA28bis_Ames");
            setTitle(SA28bis_title);
            
            StringBuffer e = new StringBuffer();
            e.append("<html>");
            e.append("Mono- or di- methyl or ethyl aromatic amines, are included.However:");
            e.append("<ul>");
            e.append("<li>");
            e.append("Aromatic amino groups with ortho-disubstitution or with a carboxylic acid substituent in ortho position should be excluded.");
            
            e.append("<li>");
            e.append("If a sulfonic acid group (-SO3H) is present on the ring that contains also the amino group, the substance should be excluded from the alert.");
            
            e.append("<li>");
            e.append("see also the two examples of exceptions for the nitro alert (alert 27))");
            
            e.append("</ul>");
            e.append("</html>");
            
            setExplanation(e.toString());
            
            examples[0] = "CNC(=O)OC=1C=C(C)C(=C(C)C=1)N(C)C";  //ortho disubstituted
            examples[1] = "CN(C)C=1C=CC=CC=1";   
            editable = false;
    }	
	protected boolean isAPossibleHit(IAtomContainer mol, IAtomContainer processedObject) throws DecisionMethodException  {
		IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(mol);
		return 
		MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"N"));
	}        
}
