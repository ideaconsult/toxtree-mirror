package toxtree.tree.cramer3.rules;

import ambit2.smarts.query.SMARTSException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;

/**
 * Identical to Cramer rules Q8 {@link toxTree.tree.cramer.RuleLactoneOrCyclicDiester}
 * @author nina
 *
 */
public class RuleQ9_A extends RuleSMARTSSubstructureAmbit {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3032619226793652566L;

	public RuleQ9_A() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this,examples);
		super.addSubstructure( "cyclic diester", "[C;R;$(C[OX2H0;R][#6;R])](=[OX1])[C;R;$(C[OX2H0;R][#6;R])](=[OX1])", false );
		super.addSubstructure( "lactone", "[C;R](=[OX1])[O;R]", false );
        super.setContainsAllSubstructures( false );
	}

}
