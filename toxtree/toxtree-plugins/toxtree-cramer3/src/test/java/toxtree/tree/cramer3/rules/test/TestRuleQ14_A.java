package toxtree.tree.cramer3.rules.test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ14_A;

public class TestRuleQ14_A extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ14_A();
	}

}
