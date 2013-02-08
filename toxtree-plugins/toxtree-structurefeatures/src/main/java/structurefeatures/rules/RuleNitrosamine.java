package structurefeatures.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleNitrosamine extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleNitrosamine() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[$([NX3]N=O)]");			
			id = "19";
			title = "Nitrosamine";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}


