package michaelacceptors.rules;

import toxTree.tree.rules.StructureAlertAmbit;

public class Rule12B extends StructureAlertAmbit {
	private static final long serialVersionUID = 0;
	public Rule12B() {
		super();		
			try {
				addSubstructure("O=[#6]1[#6]=,:[#6][#6]=,:[#6][#6]1(=O)");
			//	addSubstructure("C1=CC=C2C(=C1)C=CC(=O)C2=O");
			} catch (Exception x) {
				x.printStackTrace();
			}
			id = "12B";
			title = "Ortho-quinone";
			
			examples[0] = "";
			examples[1] = "O=C2C=CC1=CC=CC=C1C2(=O)";	
			editable = false;		
			setContainsAllSubstructures(false);

	}

}
