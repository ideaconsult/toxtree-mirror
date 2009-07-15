package michaelacceptors.rules;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule13B extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule13B() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "O=[c,CR]1[c,CR](=O)[c,CR][c,CR]cc1");id = "17";
			id="13B";
			title = "Para- quinone";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
