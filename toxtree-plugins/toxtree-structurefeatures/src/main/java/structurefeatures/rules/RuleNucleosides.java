package structurefeatures.rules;


import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
public class RuleNucleosides extends RuleSMARTSSubstructureAmbit {
	private static final long serialVersionUID = 0;
	public RuleNucleosides() {
		super();		
		try {
			//pyrrol and pyrimidine
			super.initSingleSMARTS(super.smartsPatterns,"1", "[$([nX3](:*):*),$([nX2](:*):*),$([#7X2]=*),$([NX3](=*)=*),$([#7X3+](-*)=*),$([#7X3+H]=*)]");
			//deoxyribose deoxyribose 			
			super.initSingleSMARTS(super.smartsPatterns,"2", "[$(OCC1OCC(O)C1O)]");
			
			id = "36";
			title = "Heterocycle";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}
