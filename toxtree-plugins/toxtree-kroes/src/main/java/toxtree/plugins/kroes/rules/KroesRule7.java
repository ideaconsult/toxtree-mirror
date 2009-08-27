package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

/**
 *Does estimated intake exceed TTC of 18\u00B5g/day ?
 * @author nina
 *
 */
public class KroesRule7 extends AbstractRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5853268905275906969L;
	public KroesRule7() {
		setID("Q7");
		setTitle("Does estimated intake exceed TTC of 18\u00B5g/day ?");
		setExplanation(getTitle());
	}
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		throw new DecisionMethodException("Not implemented");
	}
}
