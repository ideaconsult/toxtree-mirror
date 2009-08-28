package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertAmbit;
import toxTree.tree.rules.smarts.SMARTSException;

public class Rule11 extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule11() {
		super();		
		try {
			setTitle("Vinylene carboxylic acid");
			addSubstructure(getTitle(), "C=[CH]C(=O)[OX2H1]");
			///addSubstructure(getTitle(), "C=[CH][CX3](=O)[OX2H1]"  );
			id = "11";
			
			examples[0] = "";
			examples[1] = "CCC=CC(=O)O";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
