package toxtree.plugins.verhaar2.rules;

import toxTree.tree.rules.RuleVerifyAlertsCounter;

public class RuleVerifyClass3Alerts extends RuleVerifyAlertsCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 649391526513875024L;

	public RuleVerifyClass3Alerts() {
		super();
		setID("Any alert of Class 3?");
		setTitle("At least one Class 3 alert fired?");
	}
}
