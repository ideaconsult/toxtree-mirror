package michaelacceptors.rules;
import java.util.logging.Level;

import toxTree.tree.rules.StructureAlertAmbit;
import ambit2.smarts.query.SMARTSException;

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
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}
