package toxtree.tree.cramer3.rules.test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ22;

public class TestRuleQ22 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ22();
	}

}
