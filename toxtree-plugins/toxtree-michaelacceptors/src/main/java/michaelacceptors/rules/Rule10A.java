package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertAmbit;
import toxTree.tree.rules.smarts.SMARTSException;

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
			logger.error(x);
		}

	}

}