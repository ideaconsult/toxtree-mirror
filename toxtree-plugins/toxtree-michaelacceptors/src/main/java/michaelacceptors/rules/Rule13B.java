package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertAmbit;
import toxTree.tree.rules.smarts.SMARTSException;

public class Rule13B extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule13B() {
		super();		
		try {
			setTitle("Para- quinone");
			addSubstructure(getTitle(),"[#6]1=,:[#6][#6](=O)[#6]=,:[#6][#6]1=O");

			id="13B";
			
			
			examples[0] = "";
			examples[1] = "O=C1C=CC(=O)C=2C=CC=CC1=2";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
