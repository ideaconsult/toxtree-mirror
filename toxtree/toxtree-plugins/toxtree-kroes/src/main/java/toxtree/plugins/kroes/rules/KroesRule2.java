package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

/**
 * Are there structural alerts that raise concern for potential genotoxicity?
 * @author nina
 *
 */
public class KroesRule2 extends AbstractRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7061116252242308444L;
	public KroesRule2() {
		setID("Q2");
		setTitle("Are there structural alerts that raise concern for potential genotoxicity?");
		setExplanation(getTitle());
	}
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		throw new DecisionMethodException("Not implemented");
	}

}
