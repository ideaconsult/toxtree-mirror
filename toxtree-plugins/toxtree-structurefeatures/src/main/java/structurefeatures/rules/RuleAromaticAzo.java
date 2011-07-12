package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleAromaticAzo extends RuleSMARTSSubstructureAmbit {
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


