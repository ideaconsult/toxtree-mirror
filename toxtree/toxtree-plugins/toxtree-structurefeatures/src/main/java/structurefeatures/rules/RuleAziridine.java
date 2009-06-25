package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleAziridine extends RuleSMARTSubstructureCDK {
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

