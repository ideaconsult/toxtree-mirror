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

import java.util.List;
import java.util.logging.Level;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.rules.StructureAlertCDK;
import ambit2.core.data.MoleculeTools;
import ambit2.smarts.query.SMARTSException;

public class SA10_gen extends StructureAlertCDK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 610255293661685341L;
	protected static String AB_UNSATURATED_CARBONYLS = "\u03B1,\u03B2 unsaturated carbonyls";
	protected static String AB_UNSATURATED_CARBONYLS_SMARTS = "[!a,#1;!$(C1(=O)C=CC(=O)C=C1)][#6]([!a,#1;!$(C1(=O)C=CC(=O)C=C1)])!:;=[#6][#6](=O)[!O;!$([#6]1:,=[#6][#6](=O)[#6]:,=[#6][#6](=O)1)]";
    //protected static String AB_UNSATURATED_CARBONYLS_SMARTS = "[!a,#1;!$(C1(=O)C=CC(=O)C=C1)][#6]([!a,#1;!$(C1(=O)C=CC(=O)C=C1)])!:;=[#6][#6](=O)[!O;!$(C1=CC(=O)C=CC(=O)1)]";
	protected QueryAtomContainer query = FunctionalGroups.ab_unsaturated_carbonyl();
	public SA10_gen() throws SMARTSException {
			setContainsAllSubstructures(true);
			addSubstructure(AB_UNSATURATED_CARBONYLS, AB_UNSATURATED_CARBONYLS_SMARTS); //now aldehydes are included
			
			setID("SA10_gen");
			setTitle(AB_UNSATURATED_CARBONYLS);
			StringBuffer b = new StringBuffer();
			b.append(AB_UNSATURATED_CARBONYLS);
			
			b.append(". Exclude :");
			b.append("<ul>");
			b.append("<li>");
			b.append("\u03B1,\u03B2 unsaturated carboxylic acid and carboxylate");
			b.append("<li>");
			b.append("Quinones");
			b.append("<li>");
			b.append("Acyclic (linear) chemicals with the \u03B2 carbon with substituents with C >= 6, or aromatic ring");
			b.append("</ul>");
			setExplanation(b.toString());
			
			
			examples[0] = "[H]OC(=O)C=C";
			examples[1] = "CCCC(=O)C=C";	

	}
	
	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		return ((!CH6SubstituentAtBetaCarbon(mol)) && super.verifyRule(mol));
	}
	
	public IMoleculeSet detachSubstituentAtBetaCarbon(IAtomContainer c) {
		List map = FunctionalGroups.getBondMap(c,query,false);
		FunctionalGroups.markMaps(c,query,map);
		if (map == null) return null;
		return FunctionalGroups.detachGroup(c,query);
	}
	public boolean CH6SubstituentAtBetaCarbon(IAtomContainer c) {
		try {
			IAtomContainer cc = (IAtomContainer) c.clone();
			IMoleculeSet sc = detachSubstituentAtBetaCarbon(cc);
			if (sc != null) {
				for (int i=0;i<sc.getAtomContainerCount();i++) {
					IAtomContainer a = sc.getAtomContainer(i);
					int ringAtoms = 0;
					int aromaticAtoms = 0; 
					for (int j=0; j < a.getAtomCount();j++) {
						if (a.getAtom(j).getFlag(CDKConstants.ISINRING)) ringAtoms++;
						if (a.getAtom(j).getFlag(CDKConstants.ISAROMATIC)) aromaticAtoms++;
					}
					if (ringAtoms > 0) continue; //ring but not aromatic

					if (FunctionalGroups.hasGroupMarked(sc.getAtomContainer(i),query.getID())) 
						continue; 
					else {
						IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(sc.getAtomContainer(i));
						int catoms = MolecularFormulaManipulator.getElementCount(formula,MoleculeTools.newElement(formula.getBuilder(),"C"));
						if (catoms >=6) {
							logger.fine("Substituent at beta carbon with >=6 C atoms\t"+catoms);
							return true;
						}
					}	
				}
			}	
			return false;
		} catch (Exception x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
			return false;
		}
	}
}
