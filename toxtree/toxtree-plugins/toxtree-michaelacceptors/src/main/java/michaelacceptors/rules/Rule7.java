package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule7 extends StructureAlertCDK {
	private static final long serialVersionUID = 0;
	public Rule7() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C=CS=O");			
			id = "7";
			title = "Vinyl or vinylene with a sulfonyl";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
