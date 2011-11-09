package toxtree.plugins.proteinbinding.test;

import toxTree.core.IDecisionRule;
import toxtree.plugins.proteinbinding.rules.SN2Rule;

public class SN2RuleTest extends TestProteinBindingRules {

	@Override
	public String getHitsFile() {

		return null;
	}

	@Override
	public String getResultsFolder() {

		return null;
	}

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SN2Rule();
	}

}
