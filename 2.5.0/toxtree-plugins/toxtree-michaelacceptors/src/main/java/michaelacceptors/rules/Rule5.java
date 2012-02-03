package michaelacceptors.rules;

import toxTree.tree.rules.StructureAlertAmbit;
import ambit2.smarts.query.SMARTSException;

public class Rule5 extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule5() {
		super();		
		try {
			//super.initSingleSMARTS(super.smartsPatterns,"1", "C=CN(=O)=O");	
			
			id = "5";
			setTitle("Olefinic nitro");
			//addSubstructure(getTitle(), "[$([NX3](=O)=O),$([NX3+](=O)[O-])][!#8]");
			addSubstructure(getTitle(), "[#6]=,:[#6][$([NX3](=O)=O),$([NX3+](=O)[O-])]");
			//"C=CN(=O)=O"
			examples[0] = "";
			examples[1] = "C1CCCC=C1[N+](=O)[O-]";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
