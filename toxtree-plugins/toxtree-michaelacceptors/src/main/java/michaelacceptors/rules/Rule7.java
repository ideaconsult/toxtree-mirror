package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertAmbit;
import toxTree.tree.rules.smarts.SMARTSException;

public class Rule7 extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule7() {
		super();		
		try {
			setTitle("Vinyl or vinylene with a S=O group");
			addSubstructure(getTitle(),"C=CS=O");			
			id = "7";
			
			
			examples[0] = "";
			examples[1] = "c1ccccc1S(=O)C=C";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
