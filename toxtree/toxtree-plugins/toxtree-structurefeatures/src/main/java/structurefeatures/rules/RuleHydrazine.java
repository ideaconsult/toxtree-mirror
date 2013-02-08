package structurefeatures.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleHydrazine extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleHydrazine() {
		super();		
		try {
			//Hydrazine H2NNH2
			super.initSingleSMARTS(super.smartsPatterns,"1", "[NX3][NX3]");			
			id = "11";
			title = "Hydrazine";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}