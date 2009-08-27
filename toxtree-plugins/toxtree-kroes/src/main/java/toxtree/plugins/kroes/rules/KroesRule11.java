package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

/**
 * 
 * @author nina
 * Does estimated intake exceed 540 \u00B5g/day ?
 */
public class KroesRule11 extends AbstractRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6146027047205787207L;
	public KroesRule11() {
		setID("Q11");
		setTitle("Does estimated intake exceed 540 \u00B5g/day ?");
		setExplanation(getTitle());
	}
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		throw new DecisionMethodException("Not implemented");
	}

}
