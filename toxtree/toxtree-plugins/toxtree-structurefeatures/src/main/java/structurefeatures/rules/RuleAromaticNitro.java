package structurefeatures.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleAromaticNitro extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleAromaticNitro() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "(a-[N+](=O)[O-].a-[N+](=O)[O-])");			
			id = "23";
			title = "aromatic nitro";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}

