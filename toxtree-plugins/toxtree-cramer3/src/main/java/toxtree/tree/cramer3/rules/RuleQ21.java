package toxtree.tree.cramer3.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.SMARTSException;

public class RuleQ21 extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1869334946181755606L;

	public RuleQ21() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		super.addSubstructure(
				"ring ketone with an alpha isopropylidene or isobutylidene group",
				"[#6;R][CX3;R](=O)[#6;R;$([CX3H0]=[CX3H0;!R]([C;!R])[C;!R]),$([CX3H0]=[CX3H1;!R][C;!R]([C;!R])[C;!R])]",
				false);
		super.setContainsAllSubstructures(true);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return this.verifyRule(mol, null);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		if (mf == null)
			throw new DecisionMethodException("Not preprocessed");
		if (mf.isOpenChain()) {
			return false;
		} else {
			return super.verifyRule(mol, selected);
		}
	}
}
