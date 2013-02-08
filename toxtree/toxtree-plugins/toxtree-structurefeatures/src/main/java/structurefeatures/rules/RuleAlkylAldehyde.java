package structurefeatures.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleAlkylAldehyde extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleAlkylAldehyde() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[CX4,CH,CH2,CH3][$([CX3H1](=O)[#6])]");			
			id = "28";
			title = "alkyl aldehyde";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}
