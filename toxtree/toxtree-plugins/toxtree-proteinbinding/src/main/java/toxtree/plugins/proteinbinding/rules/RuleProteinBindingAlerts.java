package toxtree.plugins.proteinbinding.rules;

import toxTree.tree.rules.RuleInitAlertCounter;

public class RuleProteinBindingAlerts extends RuleInitAlertCounter {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5388334940521878939L;

	public RuleProteinBindingAlerts() {
		super();
		setID("Protein binding alerts");
		setTitle("Verify structural alerts for protein binding");
		setExplanation("");
		
	}
	@Override
	public boolean isImplemented() {
		return true;
	}

}
