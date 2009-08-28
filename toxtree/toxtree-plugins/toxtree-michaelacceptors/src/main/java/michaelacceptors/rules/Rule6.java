package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertAmbit;
import toxTree.tree.rules.smarts.SMARTSException;

public class Rule6 extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule6() {
		super();		
		try {
			setTitle("Ethylnylene or acetylenic with a S=O group");
			addSubstructure(getTitle(), "C#CS(=O)");			
			id = "6";
			
			
			examples[0] = "";
			examples[1] = "c1cc(C)ccc1S(=O)(=O)C#C";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}