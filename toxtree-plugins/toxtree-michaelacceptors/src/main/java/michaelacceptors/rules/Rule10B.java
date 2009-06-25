package michaelacceptors.rules;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule10B extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule10B() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C=[CH]c1ccncc1");			
			id = "12";
			title = "Para-olefinic azaarine";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
