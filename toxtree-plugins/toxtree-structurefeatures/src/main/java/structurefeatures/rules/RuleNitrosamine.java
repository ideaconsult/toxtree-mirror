package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleNitrosamine extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleNitrosamine() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[$([NX3]N=O)]");			
			id = "19";
			title = "Nitrosamine";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}


