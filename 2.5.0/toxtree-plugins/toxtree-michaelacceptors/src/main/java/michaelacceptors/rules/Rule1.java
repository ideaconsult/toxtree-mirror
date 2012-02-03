package michaelacceptors.rules;

import toxTree.tree.rules.StructureAlertAmbit;
import ambit2.smarts.query.SMARTSException;

/**
 * TODO with StructureAlertAmbit [CH]#CC=O  doesn't match  COC(=O)C#C 
 * while C#CC=O  matches COC(=O)C#C 
 * @author nina
 *
 */
public class Rule1 extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule1() {
		super();		
		try {
			id = "1";
			setTitle("Ethynylene or acetylenic with a carbonyl");			
			addSubstructure(getTitle(), "C#CC=O");
			//addSubstructure(getTitle(), "[CH]#CC=O");
//			addSubstructure("2", "[CH]#CC=O");
			//addSubstructure("3", "C#C[CH]=O");
			setContainsAllSubstructures(false);

			/*
			 * Yes examples
C#CC(=O)OC
c1ccccc1C#CC=O
CCC#CC(=O)C
			 */
			examples[0] = ""; 
			examples[1] = "C#CC(=O)OC";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.error(x);
		}

	}

}
