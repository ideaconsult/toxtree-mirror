package michaelacceptors.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;

public class Rule3 extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule3() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[c,C,O,N,S,P][CH]=C(C)C=O");
			super.initSingleSMARTS(super.smartsPatterns,"2", "[CH2]=C(C)C=O");
			super.initSingleSMARTS(super.smartsPatterns,"3", "C=C(C)[CH]=O");
			id = "3";
			title = "alpha-C atom alkyl-substituted with a carbonyl";
			
			examples[0] = "O=C=NC";
			examples[1] = "O=C1CN1";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
