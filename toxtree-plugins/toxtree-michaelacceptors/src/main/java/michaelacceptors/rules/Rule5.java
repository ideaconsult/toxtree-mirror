package michaelacceptors.rules;

import toxTree.tree.rules.StructureAlertCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule5 extends StructureAlertCDK {
	private static final long serialVersionUID = 0;
	public Rule5() {
		super();		
		try {
			//super.initSingleSMARTS(super.smartsPatterns,"1", "C=CN(=O)=O");	
			super.initSingleSMARTS(super.smartsPatterns,"1", "[$([NX3](=O)=O),$([NX3+](=O)[O-])][!#8]");
			id = "5";
			title = "Olefinic nitro";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
