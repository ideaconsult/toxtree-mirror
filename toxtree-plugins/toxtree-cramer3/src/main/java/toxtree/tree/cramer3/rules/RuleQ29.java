package toxtree.tree.cramer3.rules;

import java.util.logging.Level;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.ReactionException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;
import toxtree.tree.Reactor;
import ambit2.core.data.MoleculeTools;
import ambit2.smarts.query.SMARTSException;

/**
 * Similar to Cramer rules Q33
 * {@link toxTree.tree.cramer.RuleSufficientSulphonateGroups} with addtions
 * 
 * @author nina
 * 
 */
public class RuleQ29 extends RuleSMARTSSubstructureHydrolysis {

	protected static transient String[] metals = { "Na", "K", "Ca", "Mg", "Al",
			"Zn" }; // ammonium NH3 - or ammonium ion ?
	/**
	 * 
	 */
	private static final long serialVersionUID = 2073608343639731210L;

	public RuleQ29() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
	}

	@Override
	public boolean isImplemented() {
		return true;
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {

		QueryAtomContainer sulphonate[] = new QueryAtomContainer[2];
		sulphonate[0] = FunctionalGroups.sulphonate(metals);
		sulphonate[1] = FunctionalGroups.sulphonate(metals, false);
		QueryAtomContainer sulphamate = FunctionalGroups.sulphamate(metals);
		boolean hasSulponate = FunctionalGroups.hasGroup(mol, sulphonate[0])
				|| FunctionalGroups.hasGroup(mol, sulphonate[1]);
		if (!hasSulponate) {
			logger.fine("NO sulphonate group found");
			if (FunctionalGroups.hasGroup(mol, sulphamate)) {
				logger.fine("Has at least one sulphamate group");
			} else {
				logger.fine("NO sulphamate group found");
				return false;
			}

		} else
			logger.fine("Has at least one sulphonate group");

		try {
			IAtomContainerSet residues = reactor.process(mol);
			if (residues != null) {
				MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
				if (mf == null)
					throw new DecisionMethodException(
							"Structure should be preprocessed!");
				mf.setHydrolysisProducts(residues);
				mf.setResidues(residues);

				logger.fine("Major structural components\t"
						+ residues.getAtomContainerCount());
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Original compound\tAtoms" + mol.getAtomCount());
					for (int i = 0; i < residues.getAtomContainerCount(); i++) {
						logger.fine("Component " + (i + 1) + "\tAtoms\t"
								+ residues.getAtomContainer(i).getAtomCount());
						logger.fine("\t" + residues.getAtomContainer(i).getID());
					}
				}
				for (int i = 0; i < residues.getAtomContainerCount(); i++) {
					IAtomContainer residue = residues.getAtomContainer(i);
					IMolecularFormula formula = MolecularFormulaManipulator
							.getMolecularFormula(residue);
					int s = MolecularFormulaManipulator
							.getElementCount(
									formula,
									MoleculeTools.newElement(
											formula.getBuilder(), "S"));
					if (s == 0) {
						logger.fine("No sulphonate or sulphamate group");
						return false;
					}

					if (!FunctionalGroups.hasGroup(residue, sulphonate[0])
							&& !FunctionalGroups.hasGroup(residue,
									sulphonate[1])) {
						logger.fine("No sulphonate group");
						if (!FunctionalGroups.hasGroup(residue, sulphamate)) {
							logger.fine("No sulphamate group");
							return false;
						}
					}
					int c = MolecularFormulaManipulator
							.getElementCount(
									formula,
									MoleculeTools.newElement(
											formula.getBuilder(), "C"));
					if (c > 20) {
						logger.fine("More than 20 C atoms per sulphonate or sulphamate group");
						return false;
					}
				}
				logger.fine("Sufficient number of sulphonate/sulphamate groups.");
				return true;

			} else {
				IMolecularFormula formula = MolecularFormulaManipulator
						.getMolecularFormula(mol);
				int c = MolecularFormulaManipulator.getElementCount(formula,
						MoleculeTools.newElement(formula.getBuilder(), "C"));
				return c <= 20;
			}
			// throw new DecisionMethodException(
			// "To check every residual with code. See https://bitbucket.org/vedina/cramer3/issue/2/task-2-implement-rules-by-smarts-where ");
		} catch (CloneNotSupportedException x) {
			throw new DecisionMethodException(x);
		} catch (ReactionException x) {
			throw new DecisionMethodException(x);
		}

	}

	@Override
	public String[][] getSMARTS() {
		return new String[][] {
				{ "sulfonate salt 1",
						"[SX4](=[OX1])(=[OX1])([#6])[O-1][Na+,K+,N+]" },
				{
						"sulfonate salt 2",
						"[SX4](=[OX1])(=[OX1])([#6])[O-1][Ca+2,Mg+2,Zn+2][O-1][SX4](=[OX1])(=[OX1])([#6])" },
				{
						"sulfonate salt 3",
						"[SX4](=[OX1])(=[OX1])([#6])[O-1][Al+3]([O-1][SX4](=[OX1])(=[OX1])([#6]))[O-1][SX4](=[OX1])(=[OX1])([#6])" },

				{ "sulfamate salt 1",
						"[SX4](=[OX1])(=[OX1])([NX3H2])[O-1][Na+,K+,N+]" },
				{
						"sulfamate salt 2",
						"[SX4](=[OX1])(=[OX1])([NX3H2])[O-1][Ca+2,Mg+2,Zn+2][O-1][SX4](=[OX1])(=[OX1])([NX3H2])" },
				{
						"sulfamate salt 3",
						"[SX4](=[OX1])(=[OX1])([NX3H2])[O-1][Al+3]([O-1][SX4](=[OX1])(=[OX1])([NX3H2]))[O-1][SX4](=[OX1])(=[OX1])([NX3H2])" },

				{ "X", "[$([NX3H2])][SX4](=[OX1])(=[OX1])([#6])[O-1]" },
				{ "Y", "[$([NX3H2])][SX4](=[OX1])(=[OX1])([NX3H2])[O-1]" } };
	}

	@Override
	protected String[][] getSmirks() {
		return new String[][] {
				{ "reaction metabolize R-N",
						"[#6][$([NX3H0]):1]([#6:2])[#6:3]>>[$([NX3H1]):1]([#6:2])[#6:3]" },
				{
						"reaction metabolize C=C",
						"[$([CX3]([#6:2])[#6:3]),$([CX3H:1][#6:3]):1]=[$([CX3]([#6])[#6]),$([CX3H][#6]):4]>>[$([CX4H2]([#6:2])[#6:3]),$([CX4H2][#6:3]):1].[H][O][$([CX3]([#6])[#6]),$([CX3H][#6]):4]" },
				{
						"reaction metabolize N+=",
						"[$([CX3]([#6])[#6]),$([CX3H][#6])]=[$([N+X3]([#6:2])[#6:3]),$([NH+X3][#6:3]):1]>>[H][$([NX3]([#6:2])[#6:3]),$([NHX3][#6:3]):1].[C]=[C][C](=[O])[C]=[C]" },

				{ "reaction metabolize N=N",
						"[#6:1][N:2]=[N:3][#6:4]>>[#6:1][NX3H2:2].[#6:4][NX3H2:3]" } };
	}


}
