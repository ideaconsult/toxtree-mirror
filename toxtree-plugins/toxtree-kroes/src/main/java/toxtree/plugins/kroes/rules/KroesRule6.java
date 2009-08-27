package toxtree.plugins.kroes.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import cramer2.rules.RuleUnchargedOrganophosphates;

/**
 * Is the compound an organophosphate?
 * @author nina
 *
 */
public class KroesRule6 extends RuleUnchargedOrganophosphates {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1776897873563751556L;
	public KroesRule6() {
		setID("Q6");
		setTitle("Is the compound an organophosphate?");
		setExplanation(getTitle());
	}
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		throw new DecisionMethodException("Not implemented");
	}
}
