package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleFuran extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleFuran() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[$([or5])]");			
			id = "9";
			title = "Furan";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
