package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleEpoxide extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleEpoxide() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C1[O]C1");			
			id = "7";
			title = "Epoxide";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
