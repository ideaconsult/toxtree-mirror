/*
Copyright (C) 2005-2006  

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
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.isomorphism.matchers.SymbolQueryAtom;
import org.openscience.cdk.isomorphism.matchers.smarts.AromaticAtom;
import org.openscience.cdk.silent.AtomContainerSet;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.core.data.MoleculeTools;
import ambit2.smarts.query.SMARTSException;

/*
 * 	ar-N=CH2
 *  ar-N=C=O
 * "c1c(N=C)cccc1"  
 */
public class RuleDerivedAromaticAmines extends RuleSMARTSubstructureCDK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5890191226995213346L;
	protected transient QueryAtomContainer[] groups = new QueryAtomContainer[] {
			group1(), group2() };

	public RuleDerivedAromaticAmines() {

		try {
			setContainsAllSubstructures(false);
			addSubstructure("ar-N=CH2", "a[NX2;v3]=[CX3H2]");
			addSubstructure("ar-N=C=O", "a[NX2;v3]=[CX2H0]=O");
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE, x.getMessage(), x);
		}
		setID("ar-N=CH2");
		setTitle("Derived aromatic amines");
		setExplanation(getTitle() + " ar-N=CH2 and ar-N=C=O");
		examples[0] = "c1ccccc1";
		examples[1] = "c1c(N=C)cccc1";
	}

	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		if (super.verifyRule(mol)) {
			MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
			if (mf == null)
				throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);

			IAtomContainerSet origin = ConnectivityChecker
					.partitionIntoMolecules(mol);
			IAtomContainerSet sc = null;
			for (int g = 0; g < groups.length; g++) {
				sc = new AtomContainerSet();
				deriveAmine(groups[g], origin, sc);
				if ((sc != null) && (sc.getAtomContainerCount() > 0))
					origin = sc;
			}
			if (origin != null) {
				int r = 1;
				SmilesGenerator gen = SmilesGenerator.generic();
				for (int i = origin.getAtomContainerCount() - 1; i >= 0; i--) {
					if (origin.getAtomContainer(i).getAtomCount() <= 3)
						origin.removeAtomContainer(i);
					else {
						String s = gen.createSMILES((IAtomContainer) origin
								.getAtomContainer(i));
						origin.getAtomContainer(i).setID(
								"Derived_amine_" + Integer.toString(r) + " "
										+ s);
						r++;
					}
				}
				mf.setResidues(origin);
			}

			return true;
		} else
			return false;
	}

	protected void deriveAmine(QueryAtomContainer q, IAtomContainerSet origin,
			IAtomContainerSet results) {
		for (int j = 0; j < origin.getAtomContainerCount(); j++) {
			IAtomContainerSet sc = detachSubstituent(q,
					origin.getAtomContainer(j));
			if (sc != null)
				for (int i = sc.getAtomContainerCount() - 1; i >= 0; i--)
					if (sc.getAtomContainer(i).getAtomCount() > 3)
						results.addAtomContainer(sc.getAtomContainer(i));
		}
	}

	// aN=C=O
	public static QueryAtomContainer group1() {
		QueryAtomContainer query = new QueryAtomContainer(
				SilentChemObjectBuilder.getInstance());
		query.setID("aN=C=O");
		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom o = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN));
		SymbolQueryAtom n = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));
		AromaticAtom a = new AromaticAtom(query.getBuilder());
		query.addAtom(c);
		query.addAtom(o);
		query.addAtom(n);
		query.addAtom(a);
		query.addBond(new OrderQueryBond(c, o, CDKConstants.BONDORDER_DOUBLE,
				query.getBuilder()));
		query.addBond(new OrderQueryBond(c, n, CDKConstants.BONDORDER_DOUBLE,
				query.getBuilder()));
		query.addBond(new OrderQueryBond(n, a, CDKConstants.BONDORDER_SINGLE,
				query.getBuilder()));
		c.setProperty(FunctionalGroups.DONTMARK, query.getID());
		// to be split at C=N bond
		return query;
	}

	// aN=C=CH2
	public static QueryAtomContainer group2() {
		QueryAtomContainer query = new QueryAtomContainer(
				SilentChemObjectBuilder.getInstance());
		query.setID("aN=CH2");
		SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolQueryAtom n = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.NITROGEN));
		AromaticAtom a = new AromaticAtom(query.getBuilder());
		query.addAtom(c);
		query.addAtom(n);
		query.addAtom(a);
		query.addBond(new OrderQueryBond(c, n, CDKConstants.BONDORDER_DOUBLE,
				query.getBuilder()));
		query.addBond(new OrderQueryBond(n, a, CDKConstants.BONDORDER_SINGLE,
				query.getBuilder()));

		for (int i = 0; i < 2; i++) {
			SymbolQueryAtom h = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN));
			query.addBond(new OrderQueryBond(c, h,
					CDKConstants.BONDORDER_SINGLE, query.getBuilder()));
		}
		// to be split at C=N bond
		c.setProperty(FunctionalGroups.DONTMARK, query.getID());
		return query;
	}

	public IAtomContainerSet detachSubstituent(QueryAtomContainer q,
			IAtomContainer c) {
		List map = FunctionalGroups.getBondMap(c, q, false);
		FunctionalGroups.markMaps(c, q, map);
		if (map == null)
			return null;
		return FunctionalGroups.detachGroup(c, q);
	}
}
