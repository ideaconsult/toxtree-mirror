package structurefeatures.rules;


import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleAlkylHydrazine extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleAlkylHydrazine() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "CNN");
			super.initSingleSMARTS(super.smartsPatterns,"2", "NNC");
			id = "30";
			title = "Alkyl Aziridine";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
