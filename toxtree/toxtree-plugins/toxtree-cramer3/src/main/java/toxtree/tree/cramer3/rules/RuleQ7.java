package toxtree.tree.cramer3.rules;

import toxTree.core.IImplementationDetails;
import toxTree.tree.rules.RuleHeterocyclic;
import toxtree.tree.BundleRuleResource;

/**
 * Identical to Cramer rules Q7 {@link RuleHeterocyclic}
 * 
 * @author nina
 *
 */
public class RuleQ7 extends RuleHeterocyclic implements IImplementationDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 509908336476064151L;

	public RuleQ7() {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		/*
		 * super.addSubstructure( "heterocyclic", "[#5,#7,#8,#16;R]", false );
		 * super.setContainsAllSubstructures( true );
		 */
	}

	@Override
	public String getImplementationDetails() {

		return "toxTree.tree.rules.RuleHeterocyclic;";
	}

}
