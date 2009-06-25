package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleKetone extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleKetone() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[#6][CX3](=O)[#6]");			
			id = "12";
			title = "Ketone";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
