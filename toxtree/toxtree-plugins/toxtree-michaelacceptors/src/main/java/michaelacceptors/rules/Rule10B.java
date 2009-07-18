package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertCDK;
import toxTree.tree.rules.smarts.SMARTSException;

public class Rule10B extends StructureAlertCDK {
	private static final long serialVersionUID = 0;
	public Rule10B() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C=[CH]c1ccncc1");			
			id = "10B";
			title = "Para-vinyl azaarene";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
