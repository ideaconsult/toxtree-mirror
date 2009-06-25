package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleUrethaneDerivatives extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleUrethaneDerivatives() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[NX3,NX4+][CX3](=[OX1])[OX2,OX1-]");			
			id = "18";
			title = "RuleUrethaneDerivatives(Carbamate)";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}

