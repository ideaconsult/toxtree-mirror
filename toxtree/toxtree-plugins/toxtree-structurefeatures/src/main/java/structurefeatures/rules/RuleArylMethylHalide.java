package structurefeatures.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;
public class RuleArylMethylHalide extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleArylMethylHalide() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "a-[$([CX4,CH,CH2,CH3][Cl]),$([#6][Cl])]");			
			id = "22";
			title = "aryl methyl halide";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}

