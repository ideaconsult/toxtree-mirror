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
package toxTree.tree.cramer;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.query.QueryAtomContainers;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;

/**
 * 
 * Rule 32 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * 
 * @author Nina Jeliazkova <b>Modified</b> 2005-10-18
 */
public class Rule32 extends RuleOnlyAllowedSubstructures {
	transient QueryAtomContainer polyoxyethylene5;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2629244201826413885L;

	public Rule32() throws Exception {
		super();
		id = "32";
		title = "Contains only the functional groups listed in Q30 or Q31 and those listed below.";
		explanation.append("<html>");
		explanation
				.append("Does the substance contain only the <i>functional groups</i> (E) listed in Q30, or their derivatives listed in Q31, but with any or all of the following:<UL>");
		explanation
				.append("<LI>(a)a single fused non-aromatic carbocyclic ring");
		explanation
				.append("<LI>(b)<i>aliphatic</i> (A) substituent chains longer than 5 carbon atoms, or");
		explanation
				.append("<LI>(c)a polyoxyethylene [(-OCH2CH2-)x, with x <= 4] chain either on the aromatic ring or on an aliphatic side chain?");
		explanation.append("</UL>");
		explanation
				.append("<P>Part (a) is intended to allow simple derivatives of tetralin into class II while putting polyciclic compounds such as steroids ultimately into class III except those that may be normal food components.");

		explanation
				.append("<p>Part (b) allows compounds with permitted functional groups but longer side chains into class II instead of sending them eventually into class III.");
		explanation
				.append("<p>Part (c) puts short-chain polyoxyethylene derivatives of aryl compounds into class II rather than class III.");
		explanation.append("</html>");
		examples[0] = "CSC1=CC=CC=C1";
		examples[1] = "CC(C)CCC(=O)C1=CC=CC=C1";

		polyoxyethylene5 = FunctionalGroups.polyoxyethylene(5);

		editable = false;

	}

	@Override
	protected QueryAtomContainers initQuery() throws Exception {
		QueryAtomContainers q = super.initQuery();
		setQuery(q);
		addSubstructure(FunctionalGroups.hydroxy_ring());
		addSubstructure(FunctionalGroups.methoxy_ring());
		addSubstructure(FunctionalGroups.alcohol(false));
		addSubstructure(FunctionalGroups.ketone());
		addSubstructure(FunctionalGroups.aldehyde());
		addSubstructure(FunctionalGroups.carboxylicAcid());
		addSubstructure(FunctionalGroups.ester());
		addSubstructure(FunctionalGroups.acyclic_acetal());
		addSubstructure(FunctionalGroups.polyoxyethylene(1));
		return q;
	}

	@Override
	public boolean isImplemented() {
		return true;
	}

	/**
	 * 
	 * @param mol
	 * @param rings
	 * @return
	 */
	protected boolean singleFusedNonAromaticCarbocyclicRing(IAtomContainer mol,
			IRingSet rings) {
		final String c = "C";
		if (FunctionalGroups.singleFusedRing(mol, rings)) {
			for (int i = 0; i < rings.getAtomContainerCount(); i++) {
				IRing ring = (IRing) rings.getAtomContainer(i);
				if (ring.getFlag(CDKConstants.ISAROMATIC)) {
					logger.finer("Aromatic ring found");
					return false;
				}
				for (int j = 0; j < ring.getAtomCount(); j++)
					if (!ring.getAtom(j).getSymbol().equals(c)) {
						logger.finer("Heteroring found\t"
								+ ring.getAtom(j).getSymbol());
						return false;
					}
			}
			//
			return true;
		} else
			return false;
	}

	/**
	 * @see toxTree.core.IDecisionRule#verifyRule(IAtomContainer)
	 */
	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		logger.finer(toString());
		if (super.verifyRule(mol)) {
			logger.finer(toString());
			MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
			if (mf == null)
				throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
			IRingSet rings = mf.getRingset();
			if (singleFusedNonAromaticCarbocyclicRing(mol, rings)) {
				logger.finer("Single Fused NonAromatic CarbocyclicRing\tYES");
				return true;
			}
			if (FunctionalGroups.hasGroup(mol, polyoxyethylene5)) {
				logger.finer("Polyoxyethylene (-OCH2CH2-)x, x>4");
				return false;
			} else
				return true;
		} else
			return false;
	}

}
