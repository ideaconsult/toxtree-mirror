package toxtree.plugins.kroes.rules;

import toxTree.tree.rules.RuleVerifyAlertsCounter;

/**
 * Are there structural alerts that raise concern for potential genotoxicity?
 * @author nina
 *
 */
public class KroesRule2 extends RuleVerifyAlertsCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7061116252242308444L;
	public KroesRule2() {
		setID("Q2");
		setTitle("Are there structural alerts that raise concern for potential genotoxicity?");
		setExplanation(getTitle());
	}

}
