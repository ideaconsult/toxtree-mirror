package toxtree.tree.cramer3.rules;

import java.util.logging.Level;

import org.openscience.cdk.graph.Cycles;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;

public class RuleQ22 extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -278158450767829118L;

	public RuleQ22() {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		if (mf == null)
			throw new DecisionMethodException("Not preprocessed");
		//by default MolAnalyser uses Cycles.all
		IRingSet rings = Cycles.sssr(mol).toRingSet();

		if (rings != null && (rings.getAtomContainerCount() < 4)) {
			int c = 0;
			for (int r = 0; r < rings.getAtomContainerCount(); r++) {
				for (IAtom a : rings.getAtomContainer(r).atoms()) {
					if ("C".equals(a.getSymbol())) {
						if (a.getProperty("C") == null) {
							a.setProperty("C", "C");
							c++;
						}
						;
					}
					if (c > 12)
						return false;
				}
				if (c > 12)
					return false;
			}
			return c <= 12;

		} else {
			if (rings != null)
				logger.log(Level.FINE, String.format("Rings\t%s", rings.getAtomContainerCount() ));
			return false;
		}
	}

	@Override
	public String getImplementationDetails() {

		return getClass().getName();
	}

	@Override
	public boolean isImplemented() {
		return true;
	}
}
