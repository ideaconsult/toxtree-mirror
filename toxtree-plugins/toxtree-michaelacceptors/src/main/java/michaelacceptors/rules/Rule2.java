package michaelacceptors.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;

public class Rule2 extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule2() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C=CC=O");	
			super.initSingleSMARTS(super.smartsPatterns,"2", "[CH2]=[CH]C=O");
			super.initSingleSMARTS(super.smartsPatterns,"3", "C=[CH][CH]=O");
			id = "2";
			title = "Vinylene- and an internal carbonyl";
			
			examples[0] = "O=C=NC";
			examples[1] = "O=C1CN1";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
