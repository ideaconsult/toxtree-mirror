package toxtree.plugins.verhaar2.rules.helper;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.DefaultAlertCounter;
import toxTree.tree.rules.IAlertCounter;
import toxTree.tree.rules.RuleElements;

public class RuleElementsCounter extends RuleElements implements IAlertCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 673556779228542499L;
	protected IAlertCounter alertsCounter;
	
	public RuleElementsCounter() {
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
