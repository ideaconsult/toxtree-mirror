package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

/**
 * Is the compound in Cramer structural class III?
 * @author nina
 *
 */
public class KroesRule8 extends AbstractRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9074211771162398484L;
	public KroesRule8() {
		setID("Q8");
		setTitle("Is the compound in Cramer structural class III?");
		setExplanation(getTitle());
	}
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		throw new DecisionMethodException("Not implemented");
	}
}
