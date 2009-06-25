package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleIminomethyl extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleIminomethyl() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[nr5]:[cX3H]");			
			id = "14";
			title = "Iminomethyl";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
