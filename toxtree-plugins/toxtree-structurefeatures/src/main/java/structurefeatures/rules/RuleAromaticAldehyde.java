package structurefeatures.rules;


import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleAromaticAldehyde extends RuleSMARTSSubstructureAmbit {
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
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}
