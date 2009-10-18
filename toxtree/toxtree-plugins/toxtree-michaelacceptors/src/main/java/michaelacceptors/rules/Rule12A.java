package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertAmbit;
import ambit2.smarts.query.SMARTSException;

public class Rule12A extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule12A() {
		super();		
		try {
			setTitle("Ortho-quinone");
			addSubstructure(getTitle(),"C1=,:CC=,:CC(=O)C1=O");			
			id = "12A";
			
			
			examples[0] = "";
			examples[1] = "O=C1C=CC=CC1=O";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}