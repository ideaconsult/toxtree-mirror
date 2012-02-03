/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package cramer2.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.ReactionException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.query.SimpleReactions;

/**
 * Rule 31 of the Cramer scheme (see {@link cramer2.CramerRulesExtendedExtended})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-3
 */
public class RuleAcyclicAcetalEsterOfQ30 extends RuleRingComplexSubstituents30 {
	protected QueryAtomContainer acetal;
	protected QueryAtomContainer ester;
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 5048900098470975756L;;
    /**
	 * Constructor
	 * 
	 */
	public RuleAcyclicAcetalEsterOfQ30() {
		super();
		//this checks only for acyclic acetal and ester, 
		//assumes that molecule already had successfully passed through Q30 
		acetal = FunctionalGroups.acyclic_acetal();
		ester = FunctionalGroups.ester();
		id = "31";
		title = "Is the substance an acyclic acetal or ester of substances defined in Q30?";
		explanation = new StringBuffer();
		explanation.append("<html>");
		explanation.append("Is the substance an acyclic acetal, -ketal or -ester of any of the above substances (see Q30)?");
		explanation.append("(If YES, assume hydrolysis and treat the non-aromatic residues by Q19 and the aromatic residue by Q18.)<p>");
		explanation.append("This question is simply designed to see whether the substance would fit within the definition of Q30 if it were not an acetal, a ketal or an ester.");
		explanation.append("In other words, would the substance carry only the groups listed in Q30.");
		explanation.append("</html>");
		examples[0] = "NC1=CC=CC=C1";
		examples[1] = "COC(OC)C1=CC=CC=C1";
		editable = false;
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleAnySubstructure#verifyRule(org.openscience.cdk.Molecule)
	 */
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {

		if (!super.verifyRule(mol,selected)) {
			if (FunctionalGroups.hasGroup(mol,acetal) || FunctionalGroups.hasGroup(mol,ester)) { 
			
				SimpleReactions sr = new SimpleReactions();
				try {
					IAtomContainerSet residues = sr.isReadilyHydrolised(mol);
					if (residues != null) {
					    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
					    if (mf == null) throw new DecisionMethodException("Structure should be preprocessed!");
					    mf.setHydrolysisProducts(residues);
					    mf.setResidues(residues);
					}
					return true;
				} catch (ReactionException x) {
					throw new DecisionMethodException(x);
				}
			} else return false;	
			//hydrolysis
		} else return false;
	}
}
