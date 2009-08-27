package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

/**
 * Is the chemical an aflatoxin-like, azoxy-, or N-nitroso compound
 * @author nina
 *
 */
public class KroesRule3 extends AbstractRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6122710066223986724L;
	public KroesRule3() {
		setID("Q3");
		setTitle("Is the chemical an aflatoxin-like, azoxy-, or N-nitroso compound");
		setExplanation(getTitle());
	}
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		throw new DecisionMethodException("Not implemente");
	}

}
