package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertAmbit;
import ambit2.smarts.query.SMARTSException;

public class Rule4A extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule4A() {
		super();		
		try {
			//super.initSingleSMARTS(super.smartsPatterns,"1", "C(=O)=CC(=O)");
			setTitle("\u03B1-C-atom substituted with a second carbonyl");
			addSubstructure(getTitle(), "[CX3](=[OX1])[$(C=C),$([CX3](=[OX1]))]");
			//addSubstructure(getTitle(), "O=CC=C=O");
			id = "4A";
			
			
			examples[0] = "";
			examples[1] = "CCOC(=O)C=CC(=O)OCC";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
