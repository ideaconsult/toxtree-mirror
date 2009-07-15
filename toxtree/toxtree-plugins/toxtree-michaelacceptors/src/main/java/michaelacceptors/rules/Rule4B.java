package michaelacceptors.rules;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule4B extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule4B() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C=C(C)[CH]=O");			
			id = "4B";
			title = "alpha-C-atom substituted with a second carbonyl";
			
			examples[0] = "O=C=NC";
			examples[1] = "O=C1CN1";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}