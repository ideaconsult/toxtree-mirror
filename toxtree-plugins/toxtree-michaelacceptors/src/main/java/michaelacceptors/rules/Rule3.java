package michaelacceptors.rules;

import toxTree.tree.rules.StructureAlertAmbit;
import toxTree.tree.rules.smarts.SMARTSException;

public class Rule3 extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule3() {
		super();		
		try {
			/*
			addSubstructure("1", "[c,C,O,N,S,P][CH]=C(C)C=O");
			addSubstructure("2", "[CH2]=C(C)C=O");
			addSubstructure("3", "C=C(C)[CH]=O");
			*/
			setTitle("\u03B1-C atom alkyl-substituted with a carbonyl");
			addSubstructure(getTitle(),"[CH2]=C(C)C=O");
			id = "3";
			
			
			examples[0] = "O=C=NC";
			examples[1] = "OCCOC(=O)C(=C)C";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
