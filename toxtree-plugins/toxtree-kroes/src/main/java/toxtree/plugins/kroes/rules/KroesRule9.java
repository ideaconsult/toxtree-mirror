package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

/**
 * 
 * @author nina
 *
 */
public class KroesRule9 extends AbstractRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9202526263556621253L;
	public KroesRule9() {
		setID("Q9");
		setTitle("Does estimated intake exceed 90 \u00B5g/day ?");
		setExplanation(getTitle());
	}
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		throw new DecisionMethodException("Not implemented");
	}

}
