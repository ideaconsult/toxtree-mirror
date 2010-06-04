package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertAmbit;
import ambit2.smarts.query.SMARTSException;

public class Rule8 extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule8() {
		super();		
		try {
			setTitle("Olefinic cyano");
			addSubstructure(getTitle(), "C=CC#N");			
			id = "8";
			
			
			examples[0] = "NCCCC(=C)CN";
			examples[1] = "C=CC#N";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}