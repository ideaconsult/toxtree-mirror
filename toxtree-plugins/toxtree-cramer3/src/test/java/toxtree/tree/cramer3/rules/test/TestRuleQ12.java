package toxtree.tree.cramer3.rules.test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ12;

public class TestRuleQ12 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ12();
	}

}
