package toxtree.tree.cramer3.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;

import ambit2.smarts.query.SMARTSException;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.ReactionException;
import toxTree.query.MolFlags;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.Reactor;

public abstract class RuleSMARTSSubstructureHydrolysis extends
		RuleSMARTSSubstructureAmbit implements IRuleSMARTS {
	protected final transient Reactor reactor;

	public RuleSMARTSSubstructureHydrolysis() throws SMARTSException {
		super();
		reactor = new Reactor(getSmirks());
	}

	public Reactor getReactor() {
		return reactor;
	}

	protected abstract String[][] getSmirks();

	/**
	 * 
	 */
	private static final long serialVersionUID = 8886947918496788577L;

	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol, null);
	}
	/**
	 * Allows to apply non-SMART based tests
	 * @param mol
	 * @param selected
	 * @return
	 * @throws DecisionMethodException
	 */
	protected boolean extraTest(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		return true;
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		if (super.verifyRule(mol, selected)) {
			if (extraTest(mol, selected)) {
				try {
					IAtomContainerSet residues = reactor.process(mol);
					if (residues != null
							&& (residues.getAtomContainerCount() > 0)) {
						
						//for (IAtomContainer m : residues.atomContainers()) try {
							//MolAnalyser.analyse(m);
						//} catch (Exception x) {}
						MolFlags mf = (MolFlags) mol
								.getProperty(MolFlags.MOLFLAGS);
						if (mf == null)
							throw new DecisionMethodException(
									"Structure should be preprocessed!");
						mf.setHydrolysisProducts(residues);
						mf.setResidues(residues);
					}
					return true;
				} catch (CloneNotSupportedException x) {
					throw new DecisionMethodException(x);
				} catch (ReactionException x) {
					throw new DecisionMethodException(x);
				}
			}
		}
		return false;
	}

	@Override
	public String getImplementationDetails() {
		return "Custom implementation using reaction SMIRKS and SMARTS\n"
				+ reactor.getImplementationDetails()
				+ super.getImplementationDetails();
	}

}
