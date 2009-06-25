package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleAromaticNitro extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleAromaticNitro() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "(a-[N+](=O)[O-].a-[N+](=O)[O-])");			
			id = "23";
			title = "aromatic nitro";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}

