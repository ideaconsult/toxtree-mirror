package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleAldehyde extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleAldehyde() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[CX3H1](=O)[#6]");			
			id = "2";
			title = "Aldehyde";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}