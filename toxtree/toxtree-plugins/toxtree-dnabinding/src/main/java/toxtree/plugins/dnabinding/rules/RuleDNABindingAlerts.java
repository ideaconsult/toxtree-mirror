package toxtree.plugins.dnabinding.rules;

import toxTree.tree.rules.RuleInitAlertCounter;

public class RuleDNABindingAlerts extends RuleInitAlertCounter {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5388334940521878939L;

	public RuleDNABindingAlerts() {
		super();
		setID("DNA binding alerts");
		setTitle("Verify structural alerts for DNA binding");
		setExplanation("");
		
	}
	@Override
	public boolean isImplemented() {
		return true;
	}

}
