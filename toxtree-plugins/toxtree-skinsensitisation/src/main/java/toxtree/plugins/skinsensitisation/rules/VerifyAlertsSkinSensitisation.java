package toxtree.plugins.skinsensitisation.rules;

import ambit2.rendering.IAtomContainerHighlights;
import toxTree.tree.rules.RuleVerifyAlertsCounter;

public class VerifyAlertsSkinSensitisation extends RuleVerifyAlertsCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6848759397201273916L;
	public VerifyAlertsSkinSensitisation() {
		super();
		setID("6");
		setTitle("At least one alert for skin sensitisation?");
	}
	@Override
	public boolean isImplemented() {
		return true;
	}
	@Override
	public IAtomContainerHighlights getSelector() {
		return null;
	}
}
