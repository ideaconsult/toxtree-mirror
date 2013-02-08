package structurefeatures.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleAzo extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleAzo() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "N=N");			
			id = "4";
			title = "Azo";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}