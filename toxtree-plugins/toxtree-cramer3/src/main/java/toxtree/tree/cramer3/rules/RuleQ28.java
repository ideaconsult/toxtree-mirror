package toxtree.tree.cramer3.rules;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.SMARTSException;

public class RuleQ28 extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5306667312045783704L;

	public RuleQ28() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		// code count the aromatic rings

		super.addSubstructure("fused", "[a;R2][a;R2]", false);
		super.setContainsAllSubstructures(false);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return this.verifyRule(mol, null);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		if (super.verifyRule(mol, selected))
			return true;
		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		if (mf == null)
			throw new DecisionMethodException("Not preprocessed");
		IRingSet rings = mf.getRingset();
		if (rings == null || rings.getAtomContainerCount() == 0)
			return false;
		int a = 0;
		for (int r = 0; r < rings.getAtomContainerCount(); r++) {
			if (rings.getAtomContainer(r).getFlag(CDKConstants.ISAROMATIC))
				a++;
		}
		return a > 2;
	}
}
