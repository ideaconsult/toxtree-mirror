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

*//**
 * Created on 2005-5-3

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree.rules
 * Filename: RuleHasNa_K_Ca30.java
 */
package cramer2.rules;


import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.tools.MFAnalyser;

import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.ReactionException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.query.SimpleReactions;
import toxTree.tree.AbstractRule;

/**
 * Rule 33 of the Cramer scheme (see {@link cramer2.CramerRulesExtendedExtended})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-3
 */
public class RuleSufficientSulphonateGroups extends AbstractRule {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2597352809512599833L;
    protected static transient String[] metals = {"Na","K","Ca"};
    protected static transient SimpleReactions metabolicReactions = null;
    /**
	 * Constructor
	 * 
	 */
	public RuleSufficientSulphonateGroups() {
		super();
		id = "33";
		title = "Has sufficient number of sulphonate or sulphamate groups";
		explanation.append("<html>");
		explanation.append("Does the substance bear on every major structural component at least one Na, K or Ca sulphonate or sulphamate for every <=20 carbon atoms,");
		explanation.append("without any free primary amines except those adjacent to the sulphonate or sulphamate.<p>");
		explanation.append("Na,K,Ca sulphonate and sulphamate salts have a strong tendency to decrease toxicity by promoting solubility and rapid excretion.");
		explanation.append("This is particularly noticeable, for example, with some of the food colourings. It is important that the substance bears sufficient sulphonate groups,");
		explanation.append("including one on each major structural fragments into which the original compound might be metabolized. This question serves to steer sulphonated compounds");
		explanation.append("except those with amines non-adjacent to the sulphonate into a presumptively less toxic classification than the compounds would occupy if unsulphonated.");
		explanation.append("</html>");
		examples[0] = "NC(=O)C1=CC=CC=C1";
		//examples[1] = "OC1=CC=C2C=C(C=CC2=C1N=NC3=CC=C(C=C3)S(=O)(=O)O[Na])S(=O)(=O)O[Na]";
		examples[1] = "OC1=CC=C2C=C(C=CC2=C1N=NC3=CC=C(C=C3)S(=O)(=O)[O-])S(=O)(=O)[O-].[Na+].[Na+]";
		editable = false;
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return true;
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  molecule) throws DecisionMethodException {
		logger.info(toString());
		Object o = molecule.getProperty(MolFlags.PARENT);
		IAtomContainer mol = molecule;
		if ((o != null) && (o instanceof IAtomContainer)) {
			logger.debug("Parent compound found, will continue analyzing the parent");
			mol = (IAtomContainer) o;
		}
		/*
		if (metabolicReactions == null) metabolicReactions = new SimpleReactions();
		try {
			SetOfAtomContainers results = metabolicReactions.canMetabolize(mol);
			if (results !=null) return true;
			else return false;
		} catch (ReactionException x) {
			throw new DecisionMethodException(x);
		}
		*/
		
		
		QueryAtomContainer sulphonate[] = new QueryAtomContainer[2];
		sulphonate[0] = FunctionalGroups.sulphonate(metals);
		sulphonate[1] = FunctionalGroups.sulphonate(metals,false);
		QueryAtomContainer sulphamate = FunctionalGroups.sulphamate(metals);
		boolean hasSulponate = FunctionalGroups.hasGroup(mol,sulphonate[0]) || 
							   FunctionalGroups.hasGroup(mol,sulphonate[1]);
		if (!hasSulponate) {
			logger.debug("NO sulphonate group found");
			if (FunctionalGroups.hasGroup(mol,sulphamate)) {
				logger.debug("Has at least one sulphamate group");
			} else {
				logger.debug("NO sulphamate group found");
				return false;
			}
			
		} else logger.debug("Has at least one sulphonate group");
		
		//else try to metabolize
		if (metabolicReactions == null) metabolicReactions = new SimpleReactions();
		try {
			IAtomContainerSet results = metabolicReactions.canMetabolize(mol,true);
			if (results == null) {
				MFAnalyser mfa = new MFAnalyser(mol);
				return mfa.getAtomCount("C") <= 20;
				//check for primary amines 
			}
			
			logger.info("Major structural components\t",results.getAtomContainerCount());
			if (logger.isDebugEnabled()) {
				logger.info("Original compound\tAtoms",mol.getAtomCount());
				for (int i=0; i < results.getAtomContainerCount(); i++) { 
					logger.debug("Component "+(i+1)+"\tAtoms\t",results.getAtomContainer(i).getAtomCount());
					logger.debug("\t",results.getAtomContainer(i).getID());
				}
			}
			for (int i=0; i < results.getAtomContainerCount(); i++) {
				IAtomContainer residue = results.getAtomContainer(i);
				MFAnalyser mfa = new MFAnalyser(residue);
				if (mfa.getAtomCount("S") == 0) {
					logger.debug("No sulphonate or sulphamate group");
					return false;
				}
				
				if (!FunctionalGroups.hasGroup(residue,sulphonate[0]) &&
						!FunctionalGroups.hasGroup(residue,sulphonate[1])
						) {
					logger.debug("No sulphonate group");
					if (!FunctionalGroups.hasGroup(residue,sulphamate)) {
						logger.debug("No sulphamate group");
						return false;
					}				
				}
				if (mfa.getAtomCount("C") > 20) {
					logger.debug("More than 20 C atoms per sulphonate or sulphamate group");
					return false;				
				}
			}
			logger.debug("Sufficient number of sulphonate/sulphamate groups.");
			return true;
		} catch (ReactionException x) {
			throw new DecisionMethodException(x);
		}
		
	}

}
