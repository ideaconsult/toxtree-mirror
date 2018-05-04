package toxtree.tree.cramer3.rules;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.cramer.RuleManyAromaticRings14;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.SMARTSException;

/**
 * Similar to Cramer rules Q14 {@link RuleManyAromaticRings14}
 * 
 * @author nina
 *
 */
public class RuleQ14_A extends RuleRingsSMARTSSubstituents {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6371498019860995444L;
	private static final String tag = RuleQ14_A.class.getName();
	public RuleQ14_A() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		return processRings(mol, selected);
	}
	@Override
	protected boolean process(int allowedSubstituents, int allsubstituents) {
		return allowedSubstituents > 1;
	}
	@Override
	protected boolean analyze(IRing r) {
		// only heteroaromatic rings
		if (!r.getFlag(CDKConstants.ISAROMATIC))
			return false;
		for (IAtom atom : r.atoms())
			if (!"C".equals(atom.getSymbol()) && (atom.getProperty(tag)==null)) {
				atom.setProperty(tag, true);
				return true;
			}

		return false;
	}
	@Override
	public boolean substituentIsAllowed(IAtomContainer a, int[] place)
			throws DecisionMethodException {
		/*
		for (IAtom atom : a.atoms())
			System.out.print(atom.getSymbol() + "\t");
		System.out.println();
		*/
		for (IAtom atom : a.atoms())
			if (atom.getFlag(CDKConstants.ISAROMATIC))
				return true;
		return false;
	}
	@Override
	public boolean isImplemented() {
		return true;
	}
}
