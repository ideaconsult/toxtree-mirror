package structurefeatures.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleAmine extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleAmine() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "a-[NX3;H2,H1;!$(NC=O)]");			
			id = "3";
			title = "aromatic amine";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}