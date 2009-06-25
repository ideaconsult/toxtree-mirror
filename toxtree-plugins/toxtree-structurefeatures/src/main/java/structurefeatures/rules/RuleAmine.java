package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleAmine extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleAmine() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "a-[NX3;H2,H1;!$(NC=O)]");			
			id = "3";
			title = "aromatic amine";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}