package toxtree.tree.cramer3.rules;

import toxTree.core.IImplementationDetails;
import toxTree.tree.cramer.RuleRingsWithSubstituents;
import toxtree.tree.BundleRuleResource;

/**
 * Identical to Cramer rules Q27 {@link RuleRingsWithSubstituents}
 * @author nina
 *
 */
public class RuleQ23 extends RuleRingsWithSubstituents implements IImplementationDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3773448229447591483L;

	public RuleQ23() {
		super();
		BundleRuleResource.retrieveStrings(this,examples);
	}

	@Override
	public String getImplementationDetails() {

		return "toxTree.tree.cramer.RuleRingsWithSubstituents";
	}
	
}
