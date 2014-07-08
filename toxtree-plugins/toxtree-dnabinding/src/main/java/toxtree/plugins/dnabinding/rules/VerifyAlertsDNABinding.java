package toxtree.plugins.dnabinding.rules;

import net.idea.modbcum.i.processors.IProcessor;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;

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
	public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
		return null;
	}
}
