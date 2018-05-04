package toxtree.tree.cramer3.rules;

import ambit2.smarts.query.SMARTSException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;

public class RuleQ9_B extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5095588195576163493L;

	/*9B. Is the substance fused to one or more than one aromatic or heteroaromatic ring with which the lactone is conjugated OR a 3-membered or 4-membered ring lactone?*/
	
	public RuleQ9_B() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this,examples);
		//super.addSubstructure( "conjugated_lactone", "[O;R1][C;R1](=[OX1])[c;!R1][c,n,o,s]", false );
		super.addSubstructure( "conjugated_lactone", "[O;R1][C;R1](=[OX1])[$([c;R2]),$([C;R2]=[C;R])]", false );
		super.addSubstructure( "3or4_membered_ring_lactone", "[C;$([#6;r3]),$([#6;r4])](=[OX1])[O;$([#6;r3]),$([#6;r4])]", false );
        super.setContainsAllSubstructures( false );
	}


}
