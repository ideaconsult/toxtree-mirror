package toxtree.tree.cramer3.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.SMARTSException;

public class RuleQ9_C extends RuleLactone {
	private static final long serialVersionUID = 6336001392204412897L;

	public RuleQ9_C() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);
		super.setContainsAllSubstructures(false);
	}

	@Override
	protected String[][] getSmirks() {
		return new String[][] { {
				"hydrolysis of lactones",
				"[#6:1][CX3:2](=[OX1:3])[OX2H0:4][#6:5]>>[#6:1][CX3:2](=[OX1:3])[OH].[#6:5][OX2H0:4]",
				"O=C1CCC(C)(C=C)O1" },

		};
	}

	
	@Override
	protected boolean extraTest(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {

		IRingSet rings = tagRings(mol);
		// one ring, can't be fused
		if (rings!=null && rings.getAtomContainerCount() == 1)
			return true;
		try {
			return !isFused(mol, tag_ring,null);
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}

	}

}
