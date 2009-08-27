package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

/**
 * Does estimated intake exceed 1800 \u00B5g/day ?
 * @author nina
 *
 */
public class KroesRule12 extends RuleVerifyIntake {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1262200903658375242L;
	public KroesRule12() {
		setID("Q12");
		setTitle("Does estimated intake exceed 1800 \u00B5g/day ?");
		setExplanation(getTitle());
		propertyStaticValue = 1800;
	}

}
