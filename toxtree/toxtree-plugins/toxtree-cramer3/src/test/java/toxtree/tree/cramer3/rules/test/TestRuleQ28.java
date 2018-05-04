package toxtree.tree.cramer3.rules.test;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ28;

public class TestRuleQ28 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ28();
	}

	@Test
	public void testMultiple() throws Exception {
		Object[][] answer = { { "c1ccccc1CCc1ccccc1CCc1ccccc1", new Boolean(true) },
				{ "c1ccccc1", new Boolean(false) }, };
		ruleTest(answer);
	}
}
