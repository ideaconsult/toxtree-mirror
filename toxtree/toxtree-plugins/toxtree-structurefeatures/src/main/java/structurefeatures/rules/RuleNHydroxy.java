package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleNHydroxy extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleNHydroxy() {
		super();		
		try{
			super.initSingleSMARTS(super.smartsPatterns,"1","a-NO");
			id = "32";
			title = "aromatic N-hydroxy amine";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
			
	} catch (SMARTSException x) {
		logger.error(x);
	}

	}

}