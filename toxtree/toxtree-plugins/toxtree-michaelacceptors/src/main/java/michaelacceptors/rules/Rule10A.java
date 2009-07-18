package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertCDK;
import toxTree.tree.rules.smarts.SMARTSException;

public class Rule10A extends StructureAlertCDK {
	private static final long serialVersionUID = 0;
	public Rule10A() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C#Cc1ccncc1");			
			id = "10A";
			title = "Para-ethynylene azaarene";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}