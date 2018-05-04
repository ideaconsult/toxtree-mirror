package toxtree.tree.cramer3.rules;

import ambit2.smarts.query.SMARTSException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;

public class RuleQ13 extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4864479154301899816L;

	public RuleQ13() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		super.addSubstructure( "thiophene derivative", "[s;$([sX2H0]),$([s+][OX1-])]1[c;$([cX3H1]),$([cX3H0]),$([c][C,N,O,Cl,F,Br,I])][c;$([cX3H1]),$([cX3H0]),$([c][C,N,O,Cl,F,Br,I])][c;$([cX3H1]),$([cX3H0]),$([c][C,N,O,Cl,F,Br,I])][c;$([cX3H1]),$([cX3H0]),$([c][C,N,O,Cl,F,Br,I])]1", false );
        super.setContainsAllSubstructures( true );
	}

}
