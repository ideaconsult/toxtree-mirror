package toxtree.tree.cramer3.rules.test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ9_B;

public class TestRuleQ9_B extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ9_B();
	}

}
