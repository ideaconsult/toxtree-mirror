package michaelacceptors.rules;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule4A extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule4A() {
		super();		
		try {
			//super.initSingleSMARTS(super.smartsPatterns,"1", "C(=O)=CC(=O)");
			super.initSingleSMARTS(super.smartsPatterns,"1", "[CX3](=[OX1])[$(C=C),$([CX3](=[OX1]))]");
			id = "4";
			title = "";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
