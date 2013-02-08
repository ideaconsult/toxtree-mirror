package structurefeatures.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleNMethylol extends RuleSMARTSSubstructureAmbit {
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
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}




