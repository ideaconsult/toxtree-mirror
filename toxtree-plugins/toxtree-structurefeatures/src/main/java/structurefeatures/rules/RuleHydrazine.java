package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleHydrazine extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleHydrazine() {
		super();		
		try {
			//Hydrazine H2NNH2
			super.initSingleSMARTS(super.smartsPatterns,"1", "[NX3][NX3]");			
			id = "11";
			title = "Hydrazine";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}