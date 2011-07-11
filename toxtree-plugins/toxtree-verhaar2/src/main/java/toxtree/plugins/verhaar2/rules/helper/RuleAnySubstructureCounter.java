package toxtree.plugins.verhaar2.rules.helper;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.DefaultAlertCounter;
import toxTree.tree.rules.IAlertCounter;
import toxTree.tree.rules.RuleAnySubstructure;

public class RuleAnySubstructureCounter extends RuleAnySubstructure implements IAlertCounter  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 180517824565997711L;

	protected IAlertCounter alertsCounter;
	
	public RuleAnySubstructureCounter() {
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
