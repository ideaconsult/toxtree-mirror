package toxtree.plugins.skinsensitisation.rules;

import toxTree.tree.rules.RuleVerifyAlertsCounter;

public class VerifyAlertsSkinSensitisation extends RuleVerifyAlertsCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6848759397201273916L;
	public VerifyAlertsSkinSensitisation() {
		super();
		setID("Skin sensitisation alert?");
		setTitle("At least one alert for skin sensitisation?");
	}
}
