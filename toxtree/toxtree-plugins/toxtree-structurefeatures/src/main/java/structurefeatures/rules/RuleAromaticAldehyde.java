package structurefeatures.rules;


import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleAromaticAldehyde extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleAromaticAldehyde() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "a-[$([CX3H1](=O)[#6])]");			
			id = "35";
			title = "aromatic aldehyde";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
