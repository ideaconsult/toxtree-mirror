package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleNitroso extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleNitroso() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[NX2]=[OX1]");			
			id = "15";
			title = "Nitroso";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
