package michaelacceptors.rules;
import java.util.logging.Level;

import toxTree.tree.rules.StructureAlertAmbit;
import ambit2.smarts.query.SMARTSException;

public class Rule10A extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule10A() {
		super();		
		try {
			setTitle("Para-ethynylene azaarene");
			addSubstructure(getTitle(), "C#Cc1ccncc1");			
			id = "10A";
			
			
			examples[0] = "";
			examples[1] = "c1cnccc1C#C";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}