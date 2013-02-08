package structurefeatures.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleNOxide extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleNOxide() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "a-[$([#7+][OX1-]),$([#7v5]=[OX1]);!$([#7](~[O])~[O]);!$([#7]=[#7])]");			
			id = "25";
			title = "N-Oxide";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}



