package toxtree.tree.cramer3.rules;

import ambit2.smarts.query.SMARTSException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;

public class RuleQ27 extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8782068444656608886L;

	public RuleQ27() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this,examples);
		//A
		//super.addSubstructure( "alicyclic ring", "[C;R]!:[C;R](!:[C])!:[C;R]", false );
		super.addSubstructure( "alicyclic ring", "[C;R1]!:[C;R1]!:[C;R1]", false );
		super.addSubstructure( "alicyclic ring fused", "[#6;R][#6;R](!:[C;R])[#6;R](!:[C;R])[#6;R]", false );
		
		//B
		super.addSubstructure( "chain>5C", "[CR0]!@[CR0]!@[CR0]!@[CR0]!@[CR0]!@[CR0]", false );
		
		//C
		super.addSubstructure( "polyoxyethylene x≤4", "[OX2H1][CX4H2][CX4H2][OX2;$([OH]),$([O][CX4H2][CX4H2][OX2H1]),$([O][CX4H2][CX4H2][OX2][CX4H2][CX4H2][OX2H1]),$([O][CX4H2][CX4H2][OX2][CX4H2][CX4H2][OX2][CX4H2][CX4H2][OX2H1])]", false );
		
        super.setContainsAllSubstructures( false );
	}

}
