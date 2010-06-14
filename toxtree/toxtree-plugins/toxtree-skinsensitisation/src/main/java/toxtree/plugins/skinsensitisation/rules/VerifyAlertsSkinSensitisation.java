package toxtree.plugins.skinsensitisation.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.jchempaint.renderer.selection.IChemObjectSelection;

import ambit2.base.interfaces.IProcessor;
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
	public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
		return null;
	}
}
