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

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.smiles.SmilesGenerator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.query.QueryAtomContainers;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;

/**
 * Rule 4 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * 
 * @author Nina Jeliazkova <b>Modified</b> 2005-8-23
 */
public class RuleHasOnlySaltSulphonateSulphate extends
		RuleOnlyAllowedSubstructures {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3868442005056778784L;

	protected static transient SmilesGenerator sg = null;

	protected static ArrayList<String> elements = null;

	private String[] Me;
	private String[] Me1;

	protected static transient QueryAtomContainer sulphonate = null;
	protected static transient QueryAtomContainer sulphate = null;
	protected static transient QueryAtomContainer aminoSulphate = null;
	protected static transient QueryAtomContainer salt = null;
	protected static transient QueryAtomContainer salt1 = null;
	protected static transient QueryAtomContainer salt2 = null;
	protected static transient QueryAtomContainer hClAmine = null;

	protected String[] initMetals() {
		return new String[] { "Na", "K", "Ca" };
	}

	protected String[] initMetals1() {
		return new String[] { "Na", "K", "Ca", "Mg", "N" };
	}

	@Override
	protected QueryAtomContainers initQuery() throws Exception {

		query = super.initQuery();

		if (Me == null)
			Me = initMetals();
		if (Me1 == null)
			Me1 = initMetals1();
		sulphonate = FunctionalGroups.sulphonate(Me, false);
		sulphate = FunctionalGroups.sulphate(null);
		// (a)
		addSubstructure(FunctionalGroups.saltOfCarboxylicAcid(Me1));
		addSubstructure(FunctionalGroups.saltOfCarboxylicAcid1(Me1));
		addSubstructure(FunctionalGroups.saltOfCarboxylicAcid2(Me1));

		// (b)
		addSubstructure(FunctionalGroups.sulphateOfAmine(0)); // any
		for (int i = 1; i <= 3; i++) {
			// addSubstructure(FunctionalGroups.sulphateOfAmine(i));
			addSubstructure(FunctionalGroups.hydrochlorideOfAmine(i));
		}
		addSubstructure(FunctionalGroups.hydrochlorideOfAmine3());
		// (c)
		addSubstructure(FunctionalGroups.sulphonate(Me));
		addSubstructure(sulphonate);
		addSubstructure(FunctionalGroups.sulphate(Me));
		addSubstructure(FunctionalGroups.sulphamate(Me));
		addSubstructure(FunctionalGroups.sulphamate(null));
		return query;
	}

	/**
	 * 
	 */
	public RuleHasOnlySaltSulphonateSulphate() throws Exception {
		super();
		editable = false;

		if (elements == null) {
			elements = new ArrayList<String>();
			elements.add("C");
			elements.add("H");
			elements.add("O");
			elements.add("N");
		}
		for (int i = 0; i < elements.size(); i++)
			ids.add(elements.get(i));
		ids.add("S2");

		id = "4";
		title = "Elements not listed in Q3 occurs only as a Na,K,Ca,Mg,N salt, sulphamate, sulphonate, sulphate, hydrochloride ...";
		explanation
				.append("<html>Do all elements not listed in Q3 occur only as <UL>");
		explanation
				.append("<LI>(a) a Na,K,Ca,Mg or N salt of a carboxylic acid, or");
		explanation
				.append("<LI>(b) a sulphate or hydrochloride of an amine, or");
		explanation
				.append("<LI>(c) a Na,K, or Ca sulphonate, sulphamate or sulphate?");
		explanation.append("</UL>");
		explanation
				.append("If the answer is yes, treat as free acid, amine, unsulphonated or unsulphated compound, except for the purposes of Q24 and Q33.");
		explanation
				.append("<p>This is intended to let through, for further consideration, certain acid,amine, sulphonate and sulphate salts. Sulphamate salts are treated as such because they are not readily hydrolised.");
		explanation.append("</html>");

		examples[0] = "C(Cl)(Cl)Cl";
		examples[1] = "[Na+].[O-]S(=O)(=O)NC1CCCCC1";
		// examples[1] = "[Na]OS(=O)(=O)NC1CCCCC1";
	}

	/**
	 * Calls the inherited
	 * {@link toxTree.tree.rules.RuleOnlyAllowedSubstructures#verifyRule(IAtomContainer)}
	 * Removes sulphonate and sulphate groups if any by
	 * {@link FunctionalGroups#detachGroup(IAtomContainer, QueryAtomContainer)}
	 * and sets molflag property of a molecule to mf.setResidue(residue); This
	 * makes subsequent rules to work with the residue rather than with the
	 * original molecule If a rule needs the original structure instead, it can
	 * get it by getProperty(MolFlags.PARENT) See for example Q24 and Q33 TODO
	 * treat hydrochloride of amine as free amine
	 * 
	 */
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol, null);
	};

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {

		FunctionalGroups.mark(mol, elements);
		// take care of divalent S
		for (int i = 0; i < mol.getAtomCount(); i++) {
			IAtom a = mol.getAtom(i);
			double order = 0;
			if (a.getSymbol().equals("S")) {
				List<IBond> bonds = mol.getConnectedBondsList(a);

				for (int b = 0; b < bonds.size(); b++) {
					IBond.Order o = bonds.get(b).getOrder();
					if (bonds.get(b).getFlag(CDKConstants.ISAROMATIC))
						order += 1.5;
					else if (o.equals(IBond.Order.SINGLE))
						order += 1.0;
					else if (o.equals(IBond.Order.DOUBLE))
						order += 2.0;
					else if (o.equals(IBond.Order.TRIPLE))
						order += 3.0;
					else if (o.equals(IBond.Order.QUADRUPLE))
						order += 4.0;
				}
				/*
				 * https://sourceforge.net/tracker/?func=detail&aid=3020065&group_id
				 * =20024&atid=120024 order = order + a.getHydrogenCount();
				 */
				Integer hc = a.getImplicitHydrogenCount(); // in case it is not
															// initialized
				order = order + (hc == null ? 0 : hc.intValue());
				if ((order - 2) < 0.1)
					a.setProperty("S2", new Integer(i));
				else {
					logger.finer("Found S valency " + Double.toString(order));
				}
			}
		}
		if (super.verifyRule(mol, selected)) {
			IAtomContainer residue = null;
			try {
				residue = (IAtomContainer) mol.clone();
			} catch (CloneNotSupportedException x) {
				throw new DecisionMethodException(x);
			}
			IAtomContainerSet residues = null;

			Object detached = null;

			if (FunctionalGroups.hasGroupMarked(mol,
					FunctionalGroups.SULPHONATE, selected)) {
				detached = FunctionalGroups.SULPHONATE;
				FunctionalGroups.clearMark(residue, detached);
				if (sulphonate == null)
					sulphonate = FunctionalGroups.sulphonate(Me, false);
				residues = FunctionalGroups.detachGroup(residue, sulphonate);
				residues.setID("Unsulphonated ");
			} else if (FunctionalGroups.hasGroupMarked(mol,
					FunctionalGroups.SULPHATE, selected)) {
				detached = FunctionalGroups.SULPHATE;
				FunctionalGroups.clearMark(residue, detached);
				if (sulphate == null)
					sulphate = FunctionalGroups.sulphate(null);
				residues = FunctionalGroups.detachGroup(residue, sulphate);
				residues.setID("Unsulphated ");
			} else if (FunctionalGroups.hasGroupMarked(mol,
					FunctionalGroups.SULPHATE_OF_AMINE, selected)) {
				detached = FunctionalGroups.SULPHATE_OF_AMINE;
				FunctionalGroups.clearMark(residue, detached);
				if (aminoSulphate == null)
					aminoSulphate = FunctionalGroups.sulphateOfAmineBreakable();
				residues = FunctionalGroups.detachGroup(residue, aminoSulphate);
				residues.setID("Amine ");
			} else if (FunctionalGroups.hasGroupMarked(mol,
					FunctionalGroups.CARBOXYLIC_ACID_SALT, selected)) {

				detached = FunctionalGroups.CARBOXYLIC_ACID_SALT;
				FunctionalGroups.clearMark(residue, detached);
				if (salt == null)
					salt = FunctionalGroups.saltOfCarboxylicAcidBreakable(Me1);
				residues = FunctionalGroups.detachGroup(residue, salt);
				if (residues == null) {
					if (salt1 == null)
						salt1 = FunctionalGroups
								.saltOfCarboxylicAcidBreakable1(Me1);
					residues = FunctionalGroups.detachGroup(residue, salt1);
					if (residues != null)
						residues.setID("Acid ");
					else {
						if (salt2 == null)
							salt2 = FunctionalGroups
									.saltOfCarboxylicAcidBreakable2(Me1);
						residues = FunctionalGroups.detachGroup(residue, salt2);
						if (residues != null)
							residues.setID("Acid ");
					}
				} else
					residues.setID("Acid ");

			} else if (FunctionalGroups.hasGroupMarked(mol,
					FunctionalGroups.HYDROCHLORIDE_OF_AMINE, selected)) {

				detached = FunctionalGroups.HYDROCHLORIDE_OF_AMINE;
				FunctionalGroups.clearMark(residue, detached);
				if (hClAmine == null)
					hClAmine = FunctionalGroups.hydrochlorideOfAmineBreakable();
				residues = FunctionalGroups.detachGroup(residue, hClAmine);
				residues.setID("Amine ");

			}
			if (residues != null) {
				MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
				if (mf == null)
					throw new DecisionMethodException(
							ERR_STRUCTURENOTPREPROCESSED);
				/*
				 * this will make subsequent rules to work with the residue
				 * rather than with the original molecule If a rule needs the
				 * original structure instead, it can get it by
				 * getProperty(MolFlags.PARENT)
				 */
				// residue.setProperty(MolFlags.PARENT,mol);
				// mf.addResidue(residue);
				mf.setResidues(null); // clear residues if any
				for (int i = 0; i < residues.getAtomContainerCount(); i++) {
					IAtomContainer a = residues.getAtomContainer(i);
					if (FunctionalGroups.hasGroupMarked(a, detached.toString(),
							selected))
						if (detached
								.equals(FunctionalGroups.CARBOXYLIC_ACID_SALT)
								|| detached
										.equals(FunctionalGroups.HYDROCHLORIDE_OF_AMINE)) {

						} else {
							logger.fine("Skipping residue\t"
									+ FunctionalGroups.mapToString(a));
							continue;
						}
					else if (detached
							.equals(FunctionalGroups.CARBOXYLIC_ACID_SALT)
							|| detached
									.equals(FunctionalGroups.HYDROCHLORIDE_OF_AMINE)) {
						logger.fine("Skipping residue\t"
								+ FunctionalGroups.mapToString(a));
						continue;
					}

					if (sg == null)
						sg = SmilesGenerator.generic();
					if (!residueIDHidden) {
						a.setID(residues.getID() + sg.createSMILES(a));
						/*
						 * if set to smth different than mol.getId() will affect
						 * path representation (not the decision!) which makes
						 * it difficult to automatically compare with the path
						 * from the paper :)
						 */

					} else
						a.setID(mol.getID());
					logger.finer("Subsequent rules will proceed on\t"
							+ residues.getID() + " part of the compound.");

					FunctionalGroups.clearMarks(a);
					a.setProperty(MolFlags.PARENT, mol); // very important for
															// the rules to be
															// able to get the
															// original
															// structure if
															// needed
					mf.addResidue(a);

				}
			}
			return true;
		} else
			return false;
	}

}
