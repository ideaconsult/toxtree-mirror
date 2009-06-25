package michaelacceptors.rules;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule8 extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule8() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C=CC#N");			
			id = "8";
			title = "Slow reacting, olefinic cyano";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}