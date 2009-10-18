package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;
public class RuleNMethylol extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleNMethylol() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "NCO");			
			id = "26";
			title = "N-methylol";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}




