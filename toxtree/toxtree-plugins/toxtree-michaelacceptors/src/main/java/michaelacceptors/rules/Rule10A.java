package michaelacceptors.rules;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule10A extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule10A() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C#Cc1ccncc1");			
			id = "11";
			title = "Para-olefinic azaarine";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}