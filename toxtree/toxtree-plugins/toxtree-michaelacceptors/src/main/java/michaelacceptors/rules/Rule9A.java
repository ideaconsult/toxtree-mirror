package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule9A extends StructureAlertCDK {
	private static final long serialVersionUID = 0;
	public Rule9A() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C#Cc1ncccc1");			
			id = "9";
			title = "Ortho-ethynylene azaarene";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}