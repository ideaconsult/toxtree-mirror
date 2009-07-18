package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule14 extends StructureAlertCDK {
	private static final long serialVersionUID = 0;
	public Rule14() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[CH2]=[CH][CH]=O");			
			id = "18";
			title = "Acrolein";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
