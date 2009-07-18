package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertCDK;
import toxTree.tree.rules.smarts.SMARTSException;
public class Rule4A extends StructureAlertCDK {
	private static final long serialVersionUID = 0;
	public Rule4A() {
		super();		
		try {
			//super.initSingleSMARTS(super.smartsPatterns,"1", "C(=O)=CC(=O)");
			super.initSingleSMARTS(super.smartsPatterns,"1", "[CX3](=[OX1])[$(C=C),$([CX3](=[OX1]))]");
			id = "4A";
			title = "alpha-C-atom substituted with a second carbonyl";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
