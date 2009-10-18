package structurefeatures.rules;


import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;
public class RuleNucleosides extends RuleSMARTSubstructureCDK {
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
			logger.error(x);
		}

	}

}
