package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleEpoxide extends RuleSMARTSubstructureCDK {
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
