package toxtree.tree.cramer3.rules.test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ26;

public class TestRuleQ26 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ26();
	}

}
