package structurefeatures.rules;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class RuleAlcohol extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public RuleAlcohol() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[#6][OX2H]");			
			id = "1";
			title = "Hydroxyl in Alcohol";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
