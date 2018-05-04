package toxtree.tree.cramer3.rules.test;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ11;

public class TestRuleQ11 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ11();
	}

	@Test
	public void testMultiple() throws Exception {
		//Cyclohexylmethyl pyrazine
		Object[][] answer = { { "n(ccnc1CC(CCCC2)C2)c1", new Boolean(false) } };
		ruleTest(answer);
	}
	
	@Test
	public void test() throws Exception {
		Object[][] answer = { 
				{"O=C1NC=CC(=O)N1", new Boolean(true)}
		};
		ruleTest(answer);
	}
}
