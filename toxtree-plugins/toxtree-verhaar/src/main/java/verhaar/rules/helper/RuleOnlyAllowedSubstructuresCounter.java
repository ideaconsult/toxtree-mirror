package verhaar.rules.helper;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.DefaultAlertCounter;
import toxTree.tree.rules.IAlertCounter;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;

public class RuleOnlyAllowedSubstructuresCounter extends RuleOnlyAllowedSubstructures  implements IAlertCounter{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3700379077322343876L;
	protected IAlertCounter alertsCounter;
	
	public RuleOnlyAllowedSubstructuresCounter() {
		super();
		alertsCounter = new DefaultAlertCounter();
	}
	@Override
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append(alertsCounter.getImplementationDetails());
		
		return b.toString();
	}



	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (super.verifyRule(mol)) {
			incrementCounter(mol);
			return true;	
		} else return false;
	}
	
	@Override
	public void incrementCounter(IAtomContainer mol) {
		alertsCounter.incrementCounter(mol);
		
	}
}
