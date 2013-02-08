package structurefeatures.rules;
import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleAlcohol extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleAlcohol() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[#6][OX2H]");			
			id = "1";
			title = "Hydroxyl in Alcohol";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}
