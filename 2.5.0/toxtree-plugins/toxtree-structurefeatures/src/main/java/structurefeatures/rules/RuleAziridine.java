package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleAziridine extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleAziridine() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C1[N]C1");			
			id = "29";
			title = "Aziridine";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}

