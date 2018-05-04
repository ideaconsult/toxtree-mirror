package toxtree.tree.cramer3.rules.test;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ9_D;

public class TestRuleQ9_D extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ9_D();
	}

	@Test
	public void testLactone4()
			throws Exception {
		test(new String[] { "O=C1OC(CS)C1C" }, true);
	}
	
	@Test
	public void testLactone4Substituted()
			throws Exception {
		test(new String[] { "O=C1OC(CCC)C1CCCc1ccccc1" }, true);
	}
}
