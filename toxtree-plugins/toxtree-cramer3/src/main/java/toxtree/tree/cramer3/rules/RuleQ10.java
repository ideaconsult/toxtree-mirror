package toxtree.tree.cramer3.rules;

import ambit2.smarts.query.SMARTSException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;

/**
 * Very similar to Cramer Rules Q10 {@link toxTree.tree.cramer.Rule3MemberedHeterocycle}
 * TODO Check if there are differences!
 * @author nina
 *
 */
public class RuleQ10 extends RuleSMARTSSubstructureAmbit {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3388789328508499765L;

	public RuleQ10() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this,examples);
		super.addSubstructure( "3MemberedHeterocycle", "[#6;R][#7,#8,#16;r3]", false );
        super.setContainsAllSubstructures( true );
	}
	
}
