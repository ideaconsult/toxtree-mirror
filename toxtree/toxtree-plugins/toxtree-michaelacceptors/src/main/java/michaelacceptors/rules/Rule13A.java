package michaelacceptors.rules;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule13A extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule13A() {
		super();		
		try {			
			super.initSingleSMARTS(super.smartsPatterns,"1", "C1=CC(=O)C=CC1=O");			
			
			id = "16";
			title = "Para quinone";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
