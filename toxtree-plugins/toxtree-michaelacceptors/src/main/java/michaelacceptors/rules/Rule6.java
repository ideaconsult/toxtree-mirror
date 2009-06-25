package michaelacceptors.rules;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule6 extends RuleSMARTSubstructureCDK {
	private static final long serialVersionUID = 0;
	public Rule6() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "C#CS(=O)=O");			
			id = "6";
			title = "Acetylenic- and sulfone- or sulfonate";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}