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
import toxTree.tree.rules.StructureAlertAmbit;
import ambit2.core.data.MoleculeTools;
import ambit2.smarts.query.SMARTSException;

/**
<pre> 
[a;!$(a(a[A;!#1])(a[A;!#1]));!$(aa[CX3](=O)[OX2H1]);!$(aa[SX4](=[OX1])(=[OX1])([O-]));!$(aaa[SX4](=[OX1])(=[OX1])([O-]));!$(aaaa[SX4](=[OX1])(=[OX1])([O-]));!$(aaaaa[SX4](=[OX1])(=[OX1])([O-]));!$(aaaaaa[SX4](=[OX1])(=[OX1])([O-]));!$(aN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I]));!$(aaN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I]));!$(aaaN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I]));!$(aaaaN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I]));!$(aaaaaN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I]))]!@[$([NX3;v3]([#1,CX4,CX3])([#1,CX4,CX3])),$([NX3;v3]([OX2H])([#1,CX4,CX3])),$([NX3;v3]([#1,CX4])OC=O)]
</pre>
 * @author nina
 *
 */
public class SA28_gen extends StructureAlertAmbit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8180236810801295676L;
	public static String SA28_title = "Primary aromatic amine, hydroxyl amine and its derived esters (with restrictions)";
	public static String amine_and_SO3H =  "[$(a([NX3;v3]):a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a([NX3;v3]):a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a([NX3;v3]):a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a([NX3;v3]):a:a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a([NX3;v3]):a:a:a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1])]";
    public static int index_ortho_disubstitution = 0;
    public static int index_ortho_carboxylicacid = 1;
    public static int index_so3h_1 = 2;
    public static int index_so3h_2 = 3;
    public static int index_so3h_3 = 4;
    public static int index_so3h_4 = 5;
    
    public static String[][] amines = {
            //{"Primary amine","[NX3;v3]([#1,CX4,CX3])([#1,CX4,CX3])"},
            {"Primary amine","[NX3;v3]([#1])([#1])"},
            {"Hydroxyl amine","[NX3;v3]([OX2H])([#1,CX4,CX3])"},
            {"Hydroxyl amine ester","[NX3;v3]([#1,CX4])OC=O"}
                       
    };	
    public static Object[][] exclusion_rules = {
            {"Ortho-disubstitution","a(a[A;!#1])(a[A;!#1])","[H]C=1C([H])=C(C)C(=C(C)C=1([H]))N([H])OC=O","",new Boolean(false)},
            {"Carboxylic acid substituent at ortho position","aa[CX3](=O)[OX2H1]","O=C(O)C1=CC=CC=C1(N)","",new Boolean(false)},
            {"-SO3H on the same ring","aa[SX4](=[OX1])(=[OX1])([O])","NC=1C=CC=CC=1S(=O)(=O)[O-]","",new Boolean(false)},
            {"-SO3H on the same ring","aaa[SX4](=[OX1])(=[OX1])([O])","NC=1C=CC=C(C=1)S(=O)(=O)[O-]","",new Boolean(false)},
            {"-SO3H on the same ring","aaaa[SX4](=[OX1])(=[OX1])([O])","O=S(=O)([O-])C1=CC=C(N)C=C1","",new Boolean(false)},
            {"-SO3H on the same ring","aaaaa[SX4](=[OX1])(=[OX1])([O])","","",new Boolean(false)},
            {"-SO3H on the same ring","aaaaaa[SX4](=[OX1])(=[OX1])([O])","","",new Boolean(false)},

            /*
            {"diazo","aN=[N]a","","",new Boolean(false)},
            {"diazo","aaN=[N]a","","",new Boolean(false)},
            {"diazo","aaaN=[N]a","","",new Boolean(false)},
            {"diazo","aaaaN=[N]a","","",new Boolean(false)},
            {"diazo","aaaaaN=[N]a","","",new Boolean(false)},

            {"N mustards","aN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I])","","",new Boolean(false)},
            {"N mustards","aaN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I])","","",new Boolean(false)},
            {"N mustards","aaaN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I])","","",new Boolean(false)},
            {"N mustards","aaaaN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I])","","",new Boolean(false)},
            {"N mustards","aaaaaN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I])","","",new Boolean(false)},
            */            
    };
        
    public SA28_gen() throws SMARTSException {
        super();
        	setContainsAllSubstructures(false);
            StringBuffer b = new StringBuffer();
            b.append("[a");
            for (int i=0; i < exclusion_rules.length;i++) {
                b.append(";!$(");
                b.append(exclusion_rules[i][1]);
                b.append(")");
            }
            b.append("]!@[");
            for (int i=0; i < amines.length;i++) {
                if (i>0) b.append(',');
                b.append("$(");
                b.append(amines[i][1]);
                b.append(")");
            } 
            //b.append(";!$(NC(=[O,S]))");
            b.append("]");
            //[a;!$(a(a[A;!#1])(a[A;!#1]));!$(aa[CX3](=O)[OX2H1]);!$(aa[SX4](=[OX1])(=[OX1])([O-]));!$(aaa[SX4](=[OX1])(=[OX1])([O-]));!$(aaaa[SX4](=[OX1])(=[OX1])([O-]));!$(aaaaa[SX4](=[OX1])(=[OX1])([O-]));!$(aaaaaa[SX4](=[OX1])(=[OX1])([O-]));!$(aN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I]));!$(aaN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I]));!$(aaaN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I]));!$(aaaaN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I]));!$(aaaaaN(CC[Cl,Br,F,I])(CC[Cl,Br,F,I]))]!@[$([NX3;v3]([#1,CX4,CX3])([#1,CX4,CX3])),$([NX3;v3]([OX2H])([#1,CX4,CX3])),$([NX3;v3]([#1,CX4])OC=O)]
            addSubstructure(SA28_title,b.toString());            
        	/*
        	//This defines for hydroxyl amine and its derived esters, only secondary compounds. Instead other substitution on the N should be possible
        	addSubstructure("Hydroxyl amine", "[aX3H1,aX2H0]a([NX3;H1][OX2H1])[a;!$(a[$([CX3](=O)[OX2H1])])]");
        	addSubstructure("Hydroxyl amine ester", "[aX3H1,aX2H0]a([NX3;H1][OX2][CX3H1]=O)[a;!$(a[$([CX3](=O)[OX2H1])])]");
*/
            /*
          	addSubstructure("Hydroxyl amine", "[aX3H1,aX2H0]a([NX3;v3;!R][OX2H1+0])[a;!$(a[$([CX3](=O)[OX2H1])])]");
        	addSubstructure("Hydroxyl amine ester", "[aX3H1,aX2H0]a([NX3;v3;!R][OX2][CX3H1]=O)[a;!$(a[$([CX3](=O)[OX2H1])])]");

        	addSubstructure("Primary amine", "[$([aX3H1]),$([aX2H0]),$([a;R2])]a([NX3H2;!R])[a;!$(a[$([CX3](=O)[OX2H1])])]");

        	

   	
        	final_and_patch = createSmartsPattern(amine_and_SO3H,true);
            */
            addSubstructure("new 1", "aN=C=O");
            addSubstructure("new 2", "aN=[CH2]");
            
            setID("SA28_gen");
            setTitle(SA28_title);
            
            StringBuffer e = new StringBuffer();
            e.append("<html>");
            e.append(SA28_title);
            e.append("<br>");
            e.append("However:");
            e.append("<ul>");
            e.append("<li>");
            e.append("Aromatic amino groups with ortho disubstitutions or with a carboxylic acid substituent in ortho position are excluded.");
            
            e.append("<li>");
            e.append("If a sulfonic acid group (-SO3H) is present on the ring that contains also the amino group, the substance should be excluded from the alert.");
            
            e.append("<li>");
            e.append("see also the two examples of exceptions for the nitro alert (alert 27))");
            
            e.append("<li>");
            e.append("The following structures should also be included: <b>O=C=NC1=CC=CC=C1</b> and <b>C([H])([H])=NC1=CC=CC=C1</b>");
            
            e.append("<li>");
            e.append("The possibility that the Nitrogen atom of hydroxyl amine is part of a cycle, should be excluded.");

            e.append("</ul>");
            e.append("</html>");
            
            setExplanation(e.toString());
            
            examples[0] = "O=S(=O)([O-])C1=CC=C(N)C=C1";
            examples[1] = "O=S(=O)([O-])C2=CC=CC=C2(CC=1C=CC=C(N)C=1)";   
            editable = false;
    }
    
	protected boolean isAPossibleHit(IAtomContainer mol, IAtomContainer processedObject) throws DecisionMethodException  {
		IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(mol);
		return 
		MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"N"));
	}   
}


