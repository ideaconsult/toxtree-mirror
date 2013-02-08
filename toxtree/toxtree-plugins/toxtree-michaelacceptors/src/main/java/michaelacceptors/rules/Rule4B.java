package michaelacceptors.rules;
import java.util.logging.Level;

import toxTree.tree.rules.StructureAlertAmbit;
import ambit2.smarts.query.SMARTSException;

public class Rule4B extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule4B() {
		super();		
		try {
						
			id = "4B";
			
			setTitle("\u03B1-C-atom substituted with a second carbonyl");
			//[H]
			addSubstructure(getTitle(), "[CH]=C(C(=O))C=O");
			//addSubstructure(getTitle(), "C=C(C)[CH]=O");
			examples[0] = "O=C=NC";
			examples[1] = "C=C(C)[CH]=O";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

}