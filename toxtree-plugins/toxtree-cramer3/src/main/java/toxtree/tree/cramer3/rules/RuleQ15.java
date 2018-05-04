package toxtree.tree.cramer3.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.cramer.RuleIsOpenChain;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.SMARTSException;

/**
 * Identical to Cramer Rule Q19 {@link RuleIsOpenChain}
 * @author nina
 *
 */
public class RuleQ15 extends RuleSMARTSSubstructureAmbit implements IRuleSMARTS { 
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4143603875842869910L;

	public RuleQ15() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this,examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);
		super.setContainsAllSubstructures(true);		

	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		IRingSet rings = hasRingsToProcess(mol);
		return (rings==null) || (rings.getAtomContainerCount()==0);
	}
	@Override
	public String[][] getSMARTS() {

		return new String[][] { { "acyclic", "[R0]" }};
		
	}
	
}
