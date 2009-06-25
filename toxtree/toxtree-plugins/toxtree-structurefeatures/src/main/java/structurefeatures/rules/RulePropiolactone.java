package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RulePropiolactone extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RulePropiolactone() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C1(=O)OCC1");			
			id = "17";
			title = "Propiolactone";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
