package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;
public class RuleEther extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleEther() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[OD2]([#6])[#6]");			
			id = "8";
			title = "Ether";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}