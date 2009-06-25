package michaelacceptors.rules;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule9B extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule9B() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C=[CH]c1ncccc1");			
			id = "10";
			title = "Ortho-olefinic azaarine";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}