/*
Copyright Ideaconsult Ltd. (C) 2005-2011 

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

package mutant.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.core.data.MoleculeTools;

public class RuleAromaticAmineNoSulfonicGroup extends RuleSMARTSSubstructureAmbit {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3246531093544049211L;
	protected static String aromatic_amine="Aromatic amine with exclusion rules";
	public RuleAromaticAmineNoSulfonicGroup() throws Exception {

			setContainsAllSubstructures(true);
        	//addSubstructure(aromatic_amine,"a[NX3;v3]");
        	
            StringBuffer b = new StringBuffer();
            StringBuffer a = new StringBuffer();
            a.append("c");
            
            b.append("[");
            b.append("c");
            
            for (int i=0; i < 6; i++) {
                a.append(":c");

     
                b.append(";");
                b.append("!$(");
                b.append(a.toString());
                b.append("[SX4](=[OX1])(=[OX1])([O-,OX2H1])");
                b.append(")");
    
				
                //not heteroaromatic
                if (i<5) {
	                b.append(";");
	                b.append("$(");
	                b.append(a.toString());
	                b.append(")");
                }

            }
            b.append("]");
            b.append("!@[");
            b.append("N;X3;v3;R0");

    
            String[][] exclusion_rules = {
                    {"Aromatic N-methylols","N[CX4H2][OX2H1]"},
                    {"Aromatic N mustards","N(CC[Cl,Br,F,I])(CC[Cl,Br,F,I])"},
                    {"Aromatic Hydrazines","NN"},
                    {"Aromatic aryl N-nitroso groups","N([#1,C])N=O"},
                    {"Aromatic azide and triazene groups","NN=N"},
                    {"Aromatic Hydroxylamines","N[OX2H1]"},
                  
            };

            for (int i=0; i < exclusion_rules.length;i++) {
                b.append(";");
                b.append("!$(");                
                b.append(exclusion_rules[i][1]);
                b.append(")");                
            }
               
       
            
            b.append("]");            
            //System.out.println(b.toString());
            addSubstructure(aromatic_amine,b.toString());
            /*
[c;!$(cc[SX4](=[OX1])(=[OX1])([O-,OX2H1]));!$(cc[n,o,s,p]);!$(ccc[SX4](=[OX1])(=[OX1])([O-,OX2H1]));!$(ccc[n,o,s,p]);!$(cccc[SX4](=[OX1])(=[OX1])([O-,OX2H1]));!$(cccc[n,o,s,p]);!$(ccccc[SX4](=[OX1])(=[OX1])([O-,OX2H1]));!$(ccccc[n,o,s,p]);!$(cccccc[SX4](=[OX1])(=[OX1])([O-,OX2H1]));!$(cccccc[n,o,s,p]);!$(ccccccc[SX4](=[OX1])(=[OX1])([O-,OX2H1]));!$(ccccccc[n,o,s,p])][NX3v3;!$(N[CX4H2][OX2H1]);!$(N(CC[Cl,Br,F,I])(CC[Cl,Br,F,I]));!$(NN);!$(N([#1,C])N=O);!$(NN=N);!$(N[OX2H1])]
            */
			setID("QSAR6,8 applicable?");
			setTitle("Aromatic amine without sulfonic group on the same ring");
			StringBuffer e = new StringBuffer();
			e.append(aromatic_amine);
			e.append("<br>Exclude:");
			e.append("<ul>");
			e.append("<li>");
			e.append("Aromatic amine with sulphonic group on the ring");
			e.append("<li>");
			e.append("Heterocyclic aromatic amines");
		
            for (int i=0; i < exclusion_rules.length;i++) {
    			e.append("<li>");
    			e.append(exclusion_rules[i][0]);
            }
            e.append("The possibility that the Nitrogen atom of aromatic amine is part of a cycle");
			e.append("</ul>");
/**
 * For QSAR6 calculation of mutagenicity of aromatic amines, molecules that contain also NA_27 (aromatic nitro), should be excluded. In this case NA_27 is lower ranking with respect of the aromatic amine. 
 */
			setExplanation(e.toString());
			examples[0] = "NC1CCCCC1";
			examples[1] = "CNc1ccc(cc1(C))N=Nc2ccccc2";	

	}
	
	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {

		return super.verifyRule(mol);
	}
	@Override
	protected boolean isAPossibleHit(IAtomContainer mol, IAtomContainer processedObject) throws DecisionMethodException  {
		IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(mol);
		return MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"N"));
	}

}


