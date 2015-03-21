package toxtree.plugins.smartcyp.rules;

import net.idea.modbcum.i.exceptions.AmbitException;
import net.idea.modbcum.i.processors.IProcessor;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IPseudoAtom;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;
import org.openscience.cdk.renderer.selection.SingleSelection;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.RuleResult;
import toxTree.ui.tree.categories.CategoriesRenderer;
import ambit2.core.data.MoleculeTools;
import ambit2.core.helper.CDKHueckelAromaticityDetector;
import ambit2.core.processors.structure.HydrogenAdderProcessor;
import ambit2.rendering.CompoundImageTools;
import ambit2.rendering.IAtomContainerHighlights;
import dk.smartcyp.core.MoleculeKU;
import dk.smartcyp.core.MoleculeKU.SMARTCYP_PROPERTY;
import dk.smartcyp.core.SMARTSData;
import dk.smartcyp.core.SMARTSnEnergiesTable;

public class SMARTCYPRuleRank1 extends MetaboliteGenerator {
	protected static final CategoriesRenderer categoriesRenderer = new CategoriesRenderer(
			4, 240);
	/**
	 * 
	 */
	private static final long serialVersionUID = -8348454708993409448L;

	protected transient CDKHydrogenAdder adder = CDKHydrogenAdder
			.getInstance(SilentChemObjectBuilder.getInstance());

	public SMARTCYPRuleRank1() {
		this(1);
	}

	public SMARTCYPRuleRank1(int rank) {
		super();
		setRank(rank);
		setID(String.format("%d", rank));
		setTitle(bundle.getString("title"));
		setExplanation(String.format("Rank%d", rank));
		setEditable(false);
		setExamples(new String[] { "O", "C1C=CNC=C1" });
	}

	/**
	 * returns true if at least one atom has a score assigned
	 */
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		try {

			IAtomContainer newmol = mol;

			boolean calculated = false;
			for (IAtom atom : mol.atoms())
				if (atom instanceof IPseudoAtom)
					throw new DecisionMethodException(String.format(
							"IPseudoAtoms are not supported! %s",
							atom.getSymbol()));
				else if (SMARTCYP_PROPERTY.Accessibility.getNumber(atom) != null) {
					calculated = true;
					break;
				} else {
					// System.out.print(atom.getProperties());
				}
			if (!calculated) {
				AtomContainerManipulator.removeHydrogens(mol);
				newmol = calculate(mol);
			}
			MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
			IAtomContainerSet set = SilentChemObjectBuilder.getInstance()
					.newInstance(IAtomContainerSet.class);
			set.addAtomContainer(newmol);
			mf.setResidues(set);

			for (IAtom atom : newmol.atoms()) {
				Number atom_rank = SMARTCYP_PROPERTY.Ranking.getNumber(atom);
				SMARTSData data = SMARTCYP_PROPERTY.Energy.getData(atom);
				if ((atom_rank != null) && hasRank(atom_rank.intValue())
						&& (data != null))
					return true;
			}
			return false;
		} catch (DecisionMethodException x) {
			throw x;
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}

	protected boolean hasRank(int atom_rank) {
		return atom_rank == getRank();
	}

	public MoleculeKU calculate(IAtomContainer iAtomContainerTmp)
			throws DecisionMethodException {
		try {

			IAtomContainer iAtomContainer = AtomContainerManipulator
					.removeHydrogens(iAtomContainerTmp);
			AtomContainerManipulator
					.percieveAtomTypesAndConfigureAtoms(iAtomContainer);
			if (adder == null)
				adder = CDKHydrogenAdder.getInstance(SilentChemObjectBuilder
						.getInstance());
			CDKHueckelAromaticityDetector.detectAromaticity(iAtomContainer);
			adder.addImplicitHydrogens(iAtomContainer);

			MoleculeKU moleculeKU = new MoleculeKU(iAtomContainer,
					SMARTSnEnergiesTable.getSMARTSnEnergiesTable());
			// MoleculeKU moleculeKU = new MoleculeKU(iAtomContainerTmp,
			// SMARTSnEnergiesTable.getSMARTSnEnergiesTable());
			logger.finer("************** Matching SMARTS to assign Energies **************");
			moleculeKU.assignAtomEnergies(SMARTSnEnergiesTable
					.getSMARTSnEnergiesTable());

			logger.finer("************** Calculating Accessabilities and Atom Scores**************");
			moleculeKU.calculateAtomAccessabilities();

			logger.finer("************** Identifying, sorting and ranking C, N, P and S atoms **************");
			moleculeKU.sortAtoms();
			moleculeKU.rankAtoms();

			/*
			 * for (IAtom matchingAtom: moleculeKU.atoms()) if
			 * (SMARTCYP_PROPERTY.Energy.getData(matchingAtom)!=null) if
			 * (SMARTCYP_PROPERTY.Ranking.getNumber(matchingAtom).intValue()==1)
			 * System
			 * .out.println(String.format("[%s] Rank %d >>%s [%s]",matchingAtom
			 * .getID(), SMARTCYP_PROPERTY.Ranking.getNumber(matchingAtom),
			 * SMARTCYP_PROPERTY.Energy.getData(matchingAtom).getEnergy(),
			 * SMARTCYP_PROPERTY.Energy.getData(matchingAtom).getReaction()));
			 */
			return moleculeKU;
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}

	@Override
	public IAtomContainerHighlights getSelector() {
		return new IAtomContainerHighlights() {
			/**
		     * 
		     */
			private static final long serialVersionUID = -2848526389291041910L;

			public IChemObjectSelection process(IAtomContainer mol)
					throws AmbitException {
				try {
					IAtomContainer selected = MoleculeTools
							.newAtomContainer(SilentChemObjectBuilder
									.getInstance());
					verifyRule(mol, selected);
					return new SingleSelection<IAtomContainer>(selected);
				} catch (DecisionMethodException x) {
					throw new AmbitException(x);
				}
			}

			public boolean isEnabled() {
				return true;
			}

			public long getID() {
				return 0;
			}

			public void setEnabled(boolean arg0) {
			}

			@Override
			public void open() throws Exception {
			}

			@Override
			public void close() throws Exception {
			}

		};
	}

	public boolean verifyRule(
			org.openscience.cdk.interfaces.IAtomContainer mol,
			IAtomContainer selected) throws DecisionMethodException {
		try {
			IAtomContainer newmol = mol;
			for (IAtom atom : mol.atoms())
				if (SMARTCYP_PROPERTY.Accessibility.getNumber(atom) == null) {
					AtomContainerManipulator.removeHydrogens(mol);
					newmol = calculate(mol);
					break;
				}
			final double[] size = new double[] { 1.66, 1.33, 1.0, 0.5 };
			for (int i = 0; i < newmol.getAtomCount(); i++) {
				IAtom atom = newmol.getAtom(i);
				Number atom_rank = SMARTCYP_PROPERTY.Ranking.getNumber(atom);
				if ((atom_rank != null) && hasRank(atom_rank.intValue())) {
					if (atom_rank.intValue() < 4) {
						atom.setProperty(CompoundImageTools.SELECTED_ATOM_SIZE,
								size[getRank() - 1]);
						atom.setProperty(
								CompoundImageTools.SELECTED_ATOM_COLOR,
								categoriesRenderer.getShowColor(getRank() - 1));
						selected.addAtom(atom);
					}
				}
			}
			return selected.getAtomCount() > 0;
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}

	@Override
	public boolean isImplemented() {
		return true;
	}

	@Override
	public IAtomContainerSet getProducts(IAtomContainer reactant,
			RuleResult ruleResult) throws Exception {
		MoleculeKU mol = (reactant instanceof MoleculeKU) ? (MoleculeKU) reactant
				: calculate(reactant);
		// This is a workaround. CDK smarts matching needs implicit hydrogens,
		// while ambit smarts/smirks matching needs explicit hydrogens...
		HydrogenAdderProcessor.convertImplicitToExplicitHydrogens(mol);
		return super.getProducts(mol, ruleResult);
	}

	@Override
	public String getHelp(RuleResult ruleresult) {
		return super.getHelp(ruleresult);
	}
}
