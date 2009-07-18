package toxTree.tree.rules;


import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;

public class StructureAlertCDK extends RuleSMARTSubstructureCDK implements
		IAlertCounter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5283264004521118487L;
	IAlertCounter alertsCounter;
	public StructureAlertCDK() {
		super();
		alertsCounter = new DefaultAlertCounter();
	}
    public void incrementCounter(IAtomContainer mol) {
        alertsCounter.incrementCounter(mol);
    }
	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (super.verifyRule(mol)) {
			incrementCounter(mol);
			return true;	
		} else return false;
	}
	@Override
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append(alertsCounter.getImplementationDetails());
		b.append("Uses CDK SMARTS<br>");
		b.append(super.getImplementationDetails());
		
		return b.toString();
	}
}
