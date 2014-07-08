package toxTree.tree;

import net.idea.modbcum.i.exceptions.AmbitException;
import net.idea.modbcum.i.processors.IProcessor;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;
import org.openscience.cdk.renderer.selection.SingleSelection;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.MolAnalyseException;
import toxTree.query.MolAnalyser;
import ambit2.core.data.MoleculeTools;

public abstract class AbstractRuleHilightHits extends AbstractRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6028214376570266393L;

	public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
		return new IProcessor<IAtomContainer, IChemObjectSelection>() {
			public IChemObjectSelection process(IAtomContainer mol)
					throws AmbitException {
				try {
					MolAnalyser.analyse(mol);
				} catch (MolAnalyseException x) {
					x.printStackTrace();
				}
				try {
					IAtomContainer selected = MoleculeTools
							.newAtomContainer(SilentChemObjectBuilder
									.getInstance());
					boolean ok = verifyRule(mol, selected);
					// selected =
					// AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(selected);
					if (selected.getAtomCount() == 0)
						return null;
					else
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

	public abstract boolean verifyRule(
			org.openscience.cdk.interfaces.IAtomContainer mol,
			IAtomContainer selected) throws DecisionMethodException;

}
