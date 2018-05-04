package toxtree.tree.cramer3.rules;

import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;

public abstract class RuleRingsSMARTSSubstituents extends
		RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4341758041049633453L;

	protected abstract boolean analyze(IRing r);

	public abstract boolean substituentIsAllowed(IAtomContainer a, int[] place)
			throws DecisionMethodException;

	public boolean processRings(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		int allowedSubstituents = 0;
		logger.fine(toString());
		IRingSet rs = hasRingsToProcess(mol);
		if (rs == null)
			return false;

		IRing r = null;

		// TODO sort rings with increasing size, otherwise fused rings may be
		// analysed first
		int substituents = 0;
		for (int i = 0; i < rs.getAtomContainerCount(); i++) {
			r = (IRing) rs.getAtomContainer(i);
			boolean analyzeRing = analyze(r); 
			if (!analyzeRing)
				continue;
			logger.finer("Ring\t" + (i + 1));

			// new atomcontainer with ring atoms/bonds deleted
			IAtomContainer mc = FunctionalGroups.cloneDiscardRingAtomAndBonds(
					mol, r);
			logger.finer("\tmol atoms\t" + mc.getAtomCount());

			IAtomContainerSet s = ConnectivityChecker
					.partitionIntoMolecules(mc);
			// System.out.println(s.getAtomContainerCount());
			
			for (int k = 0; k < s.getAtomContainerCount(); k++) {
				IAtomContainer m = s.getAtomContainer(k);
				if (m != null) {
					if ((m.getAtomCount() == 1)
							&& (m.getAtom(0).getSymbol().equals("H")))
						continue;
					logger.finer("Ring substituent\t" + (k + 1));
					substituents++;
					if (substituentIsAllowed(m, null))
						allowedSubstituents++;
				}
			}

		}

		logger.fine("Ring substituents\t" + (allowedSubstituents));
		return process(allowedSubstituents,substituents);
	}

	protected abstract boolean process(int allowedSubstituents, int allsubstituents);
	@Override
	public String getImplementationDetails() {
		return "Custom implementation using ring detection and SMARTS\n" + super.getImplementationDetails();
	}	
}
