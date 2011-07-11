package toxtree.plugins.verhaar2.rules;

import toxTree.tree.rules.RuleVerifyAlertsCounter;

public class RuleVerifyClass1Alerts extends RuleVerifyAlertsCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3321777668352576351L;
	public RuleVerifyClass1Alerts() {
		super();
		setID("Any alert of Class 1?");
		setTitle("At least one Class 1 alert fired?");
	}
}
