package michaelacceptors.rules;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule12A extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule12A() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "O=[c,CR]1[c,CR]=[c,CR][c,CR](=O)cc1");			
			id = "12A";
			title = "Ortho-quinone";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}