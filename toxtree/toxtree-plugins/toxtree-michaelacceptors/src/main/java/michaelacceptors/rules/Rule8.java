package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule8 extends StructureAlertCDK {
	private static final long serialVersionUID = 0;
	public Rule8() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C=CC#N");			
			id = "8";
			title = "Olefinic cyano";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}