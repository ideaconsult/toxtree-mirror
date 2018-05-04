package toxtree.tree.cramer3.rules.test;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ4;

public class TestRuleQ4 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ4();
	}

	/**
	 * #58, but likely should be false..
	 * @throws Exception
	 */
	@Test
	public void test_67_68_5() throws Exception {
		String[] smiles = { "O=S(C)C" };
		test(smiles, true);
	}
	
}
