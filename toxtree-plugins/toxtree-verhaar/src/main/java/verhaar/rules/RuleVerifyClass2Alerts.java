package verhaar.rules;

import toxTree.tree.rules.RuleVerifyAlertsCounter;

public class RuleVerifyClass2Alerts extends RuleVerifyAlertsCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9032067488054886185L;
	public RuleVerifyClass2Alerts() {
		super();
		setID("Any alert of Class 2?");
		setTitle("At least one Class 2 alert fired?");
	}
}
