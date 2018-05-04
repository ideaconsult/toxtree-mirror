package toxtree.tree.cramer3.rules.test;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ27;

public class TestRuleQ27 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ27();
	}

	@Test
	public void test_122_99_6() throws Exception {
		String[] smiles = { "O(c(cccc1)c1)CCO" };
		test(smiles, false);
	}
}
