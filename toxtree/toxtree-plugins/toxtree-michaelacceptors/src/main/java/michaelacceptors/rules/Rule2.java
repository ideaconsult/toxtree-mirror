package michaelacceptors.rules;

import toxTree.tree.rules.StructureAlertAmbit;
import toxTree.tree.rules.smarts.SMARTSException;

public class Rule2 extends StructureAlertAmbit{
	private static final long serialVersionUID = 0;
	public Rule2() {
		super();		
		try {
			setTitle("Vinyl or vinylene with a carbonyl");
			addSubstructure(getTitle(), "C=CC=O");	
			//addSubstructure("2", "[CH2]=[CH]C=O");
			//addSubstructure("3", "C=[CH][CH]=O");
			setContainsAllSubstructures(false);
			id = "2";
			
			/*
Yes examples
C=CC(=O)OC
C1CCC=CC1=O
OCCOC(=O)C(=C)C
CCOC(=O)C=CC(=O)OCC

			 */
			examples[0] = "O=C=NC"; // COC(=O)C=C
			examples[1] = "CCOC(=O)C=CC(=O)OCC";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
