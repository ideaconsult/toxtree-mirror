package toxtree.tree.cramer3.rules;

import toxTree.core.IImplementationDetails;
import toxTree.tree.cramer.RuleManyAromaticRings28;
import toxtree.tree.BundleRuleResource;

/**
 * Identical to Cramer rules Q28 {@link toxTree.tree.cramer.RuleManyAromaticRings28}
 * @author nina
 *
 */
public class RuleQ24 extends RuleManyAromaticRings28 implements IImplementationDetails {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3633543942406976277L;

	public RuleQ24() {
		super();
		BundleRuleResource.retrieveStrings(this,examples);
	}

	@Override
	public String getImplementationDetails() {

		return "toxTree.tree.cramer.RuleManyAromaticRings28";
	}
	

}
