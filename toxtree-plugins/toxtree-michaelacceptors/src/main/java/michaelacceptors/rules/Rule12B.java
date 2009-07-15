package michaelacceptors.rules;
import toxTree.query.FunctionalGroups;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;
import toxTree.tree.rules.RuleAnySubstructure;
public class Rule12B extends RuleAnySubstructure {
	private static final long serialVersionUID = 0;
	public Rule12B() {
		super();		
		
			addSubstructure(FunctionalGroups.createAtomContainer("O=C1C=CC=CC1(=O)",false));
			addSubstructure(FunctionalGroups.createAtomContainer("C1=CC=C2C(=C1)C=CC(=O)C2=O",false));
			id = "12B";
			title = "Ortho-quinone";
			
			examples[0] = "";
			examples[1] = "";	
			editable = false;		
			

	}

}
