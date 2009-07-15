package michaelacceptors.rules;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule11 extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule11() {
		super();		
		try {
			//super.initSingleSMARTS(super.smartsPatterns,"1", "C=[CH]C(=O)[OX1]");
			super.initSingleSMARTS(super.smartsPatterns,"1", "C=[CH][CX3](=O)[OX2H1]"  );
			id = "11";
			title = "Vinylene carboxylic acid";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
