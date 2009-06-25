package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleNitro extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleNitro() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[$([NX3](=O)=O),$([NX3+](=O)[O-])][!#8]");			
			id = "13";
			title = "Nitro";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}