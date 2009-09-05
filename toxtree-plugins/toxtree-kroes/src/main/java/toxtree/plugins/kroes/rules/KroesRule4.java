package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

/**
 * Does estimated intake exceed TTC of 0.15 \u00B5g/day ?
 * @author nina
 *
 */
public class KroesRule4 extends RuleVerifyIntake {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3075417665758907375L;
	public KroesRule4() {
		super();
		setID("Q4");
		setTitle("Does estimated intake exceed TTC of 0.15 \u00B5g/day ?");
		setExplanation(getTitle());
		propertyStaticValue =0.15;
	}


}
