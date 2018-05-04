package toxtree.tree.cramer3.rules;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.SMARTSException;

public class RuleQ19 extends RuleSMARTSSubstructureAmbit {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1654949865628751459L;

	public RuleQ19() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this,examples);
		super.addSubstructure( "aromatic", "a", false );
        super.setContainsAllSubstructures( true );
	}

}
