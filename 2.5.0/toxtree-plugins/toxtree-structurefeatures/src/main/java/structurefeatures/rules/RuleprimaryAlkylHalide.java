package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;


public class RuleprimaryAlkylHalide extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleprimaryAlkylHalide() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[CX4,CH,CH2,CH3][#6][F,Cl,Br,I]");			
			id = "20";
			title = "primary alkyl halide";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}


