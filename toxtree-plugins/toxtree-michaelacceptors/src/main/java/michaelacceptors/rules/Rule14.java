package michaelacceptors.rules;
import java.util.logging.Level;

import toxTree.tree.rules.StructureAlertAmbit;
import ambit2.smarts.query.SMARTSException;

public class Rule14 extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule14() {
		super();		
		try {
					
			id = "14";
			setTitle("Acrolein");
			addSubstructure(getTitle(), "[CH2]=[CH][CH]=O");
			examples[0] = "";
			examples[1] = "C=CC=O";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}
