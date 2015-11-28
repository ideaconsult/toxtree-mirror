package toxtree.plugins.dnabinding.rules;

import ambit2.rendering.IAtomContainerHighlights;
import toxTree.tree.rules.RuleVerifyAlertsCounter;

public class VerifyAlertsDNABinding extends RuleVerifyAlertsCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6848759397201273916L;
	public VerifyAlertsDNABinding() {
		super();
		setID("6");
		setTitle("At least one alert for DNA binding?");
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
