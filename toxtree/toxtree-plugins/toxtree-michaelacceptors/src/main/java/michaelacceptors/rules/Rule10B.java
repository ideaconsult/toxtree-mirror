package michaelacceptors.rules;
import toxTree.tree.rules.StructureAlertAmbit;
import toxTree.tree.rules.smarts.SMARTSException;

public class Rule10B extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule10B() {
		super();		
		try {
						
			id = "10B";
			setTitle("Para-vinyl azaarene");
			addSubstructure(getTitle(),"C=[CH]c1ccncc1");
			examples[0] = "";
			examples[1] = "C=[CH]c1ccncc1";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
