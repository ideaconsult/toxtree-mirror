package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleAromaticAzo extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleAromaticAzo() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "a-N=N");			
			id = "24";
			title = "aromatic azo";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}


