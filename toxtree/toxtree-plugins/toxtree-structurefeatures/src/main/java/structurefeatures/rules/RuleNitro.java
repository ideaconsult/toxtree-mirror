package structurefeatures.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleNitro extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleNitro() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[$([NX3](=O)=O),$([NX3+](=O)[O-])][!#8]");			
			id = "13";
			title = "Nitro";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}