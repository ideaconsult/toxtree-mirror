package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleNitrogenMustard extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleNitrogenMustard() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "N(CCCl)CCCl");			
			id = "21";
			title = "nitrogen mustard";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
