package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

/**
 * 
 * @author nina
 * Is the compound in Cramer structural class II?
 */
public class KroesRule10 extends AbstractRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8716961767047489196L;
	public KroesRule10() {
		setID("Q10");
		setTitle("Is the compound in Cramer structural class II?");
		setExplanation(getTitle());
	}
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		throw new DecisionMethodException("Not implemented");
	}
}
