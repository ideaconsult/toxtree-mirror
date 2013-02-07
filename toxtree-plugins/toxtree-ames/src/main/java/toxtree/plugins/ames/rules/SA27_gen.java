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
 * Nitro aromatic (and more). 
 * [*]S(=O)(=O)[OH1] 
 * @author Nina Jeliazkova
 *
 */
public class SA27_gen extends StructureAlertCDK{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1315653781295236217L;
	public static String SA27_title = "Nitro aromatic";
    public String[][] nitro = {
            {"Nitro charged","[N+]([O-])=O"},
            {"Nitro uncharged","[N](=O)=O"}
    };
	public Object[][] exclusion_rules = {

            {"Ortho-disubstitution","a(a[A;!#1;!H])(a[A;!#1;!H])",new Boolean(false)},
            {"Carboxylic acid substituent at ortho position","aa[CX3](=O)[OX2H1]",new Boolean(false)},
            {"-SO3H on the same ring","aa[SX4](=[OX1])(=[OX1])([OX2H1])",new Boolean(false)},
            {"-SO3H on the same ring","aaa[SX4](=[OX1])(=[OX1])([OX2H1])",new Boolean(false)},
            {"-SO3H on the same ring","aaaa[SX4](=[OX1])(=[OX1])([OX2H1])",new Boolean(false)},
            {"-SO3H on the same ring","aaaaa[SX4](=[OX1])(=[OX1])([OX2H1])",new Boolean(false)},
            {"-SO3H on the same ring","aaaaaa[SX4](=[OX1])(=[OX1])([OX2H1])",new Boolean(false)}
    };
	
    public SA27_gen() throws SMARTSException {
        super();
        	setContainsAllSubstructures(true);
        	StringBuffer b = new StringBuffer();
            b.append("[a");
            for (int i=0; i < exclusion_rules.length;i++) {
            	b.append(";");
            	if (!((Boolean)exclusion_rules[i][2]).booleanValue())
            		b.append("!");            	
                b.append("$(");
                b.append(exclusion_rules[i][1]);
                b.append(")");
            }
            b.append("]([");
            for (int i=0; i < nitro.length;i++) {
                if (i>0) b.append(',');
                b.append("$(");
                b.append(nitro[i][1]);
                b.append(")");
            }                
            b.append("])");
            addSubstructure(SA27_title,b.toString());
            //addSubstructure(SA27_title, "[a;!$(a(a[A;!#1])(a[A;!#1]));!$(aa[CX3](=O)[OX2H1])]([$([N+]([O-])=O),$([N](=O)=O)])");

            //addSubstructure("-SO3H on the same ring","[$(a([N+]([O-])=O):a[SX4](=[OX1])(=[OX1])[OX2H1]),$(a([N+]([O-])=O):a:a[SX4](=[OX1])(=[OX1])[OX2H1]),$(a([N+]([O-])=O):a:a:a[SX4](=[OX1])(=[OX1])[OX2H1]),$(a([N+]([O-])=O):a:a:a:a[SX4](=[OX1])(=[OX1])[OX2H1])]",true);
            //C[$(aaO)]
        	//TODO this doesn't work with CDK smarts ...
            /*
        	addSubstructure(SA27_title, "[aX3H1,aX2H0]a(N(O)=O)[a;!$(a[$([CX3](=O)[OX2H1])])]");
        	addSubstructure("-SO3H on the same ring","[$(a(N(O)=O):a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a(N(O)=O):a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a(N(O)=O):a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a(N(O)=O):a:a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1])]",true);
            */
        	
        	
            setID("SA27_Ames");
            setTitle(SA27_title);
            
            StringBuffer e = new StringBuffer();
            e.append("<html>");
            e.append(SA27_title);
            e.append("<br>");
            e.append("However:");
            e.append("<ul>");
            e.append("<li>");
            e.append("Aromatic nitro groups with ortho-disubstitution or with a carboxylic acid substituent in ortho position should be excluded.");
            e.append("<li>");
            e.append("Please note that a molecule like this <b>CC1=CC=CC(=C1[N+](=O)[O-])[N+](=O)[O-]</b> should be included in the alert: one of the two nitro groups is ortho disubstituted, but the other one is ortho-monosubstituted.");
            e.append("<li>");
            e.append("Also the following molecule <b>CC2=CC=CC(CCC1=CC=CC(=C1)[N+](=O)[O-])=C2[N+](=O)[O-]</b> Should fire the alert (one nitro group is ortho disubstituted, but the other is not).");
            e.append("<li>");
            e.append("If a sulfonic acid group (-SO3H) is present on the ring that contains also the nitro group, the substance should be excluded."); 
            e.append("</ul>");
            e.append("</html>");
            
            setExplanation(e.toString());
            
            examples[0] = "CCC=1C=CC(C)=C(C)C=1[N+](=O)[O-]";
            examples[1] = "O=[N+]([O-])C=2C=3C=CC=CC=3(C=C1C=CC=CC1=2)";//"CC1=CC=CC(=C1[N+](=O)[O-])[N+](=O)[O-]";   
            editable = false;
    }

	protected boolean isAPossibleHit(IAtomContainer mol, IAtomContainer processedObject) throws DecisionMethodException  {
		IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(mol);
		return 
		MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"N")) &&
		MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"O"));
	}   	
}


