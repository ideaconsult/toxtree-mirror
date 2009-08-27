package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

/**
 * Does estimated intake exceed TTC of 0.15 \u00B5g/day ?
 * @author nina
 *
 */
public class KroesRule4 extends AbstractRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3075417665758907375L;
	public KroesRule4() {
		setID("Q4");
		setTitle("Does estimated intake exceed TTC of 0.15 \u00B5g/day ?");
		setExplanation(getTitle());
	}
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		throw new DecisionMethodException("Not implemented");
	}

}
