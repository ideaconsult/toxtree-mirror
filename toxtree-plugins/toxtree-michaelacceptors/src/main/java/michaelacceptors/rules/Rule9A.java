package michaelacceptors.rules;
import java.util.logging.Level;

import toxTree.tree.rules.StructureAlertAmbit;
import ambit2.smarts.query.SMARTSException;

public class Rule9A extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule9A() {
		super();		
		try {
			setTitle("Ortho-ethynylene azaarene");
			addSubstructure(getTitle(), "C#Cc1ncccc1");			
			id = "9A";
			examples[0] = "";
			examples[1] = "C#Cc1ncccc1";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}