package toxtree.tree.cramer3.rules;

import ambit2.smarts.query.SMARTSException;
import toxTree.tree.cramer.RuleHeteroaromatic;
import toxtree.tree.BundleRuleResource;

/**
 * Identical to Cramer rules Q12 {@link toxTree.tree.cramer.RuleHeteroaromatic}
 * @author nina
 *
 */
public class RuleQ12 extends RuleHeteroaromatic {


	/**
	 * 
	 */
	private static final long serialVersionUID = -496061857340122792L;

	public RuleQ12() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this,examples);
		super.addSubstructure( "heteroaromatic", "[a;!c;R]", false );
        super.setContainsAllSubstructures( true );
	}

}
