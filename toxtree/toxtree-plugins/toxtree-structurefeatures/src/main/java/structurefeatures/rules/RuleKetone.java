package structurefeatures.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleKetone extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleKetone() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[#6][CX3](=O)[#6]");			
			id = "12";
			title = "Ketone";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}
