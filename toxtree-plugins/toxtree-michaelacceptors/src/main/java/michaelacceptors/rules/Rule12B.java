package michaelacceptors.rules;

import toxTree.tree.rules.StructureAlertCDK;

public class Rule12B extends StructureAlertCDK {
	private static final long serialVersionUID = 0;
	public Rule12B() {
		super();		
			try {
				addSubstructure("O=C1C=CC=CC1(=O)");
				addSubstructure("C1=CC=C2C(=C1)C=CC(=O)C2=O");
			} catch (Exception x) {
				x.printStackTrace();
			}
			id = "12B";
			title = "Ortho-quinone";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
			setContainsAllSubstructures(false);

	}

}
