package toxtree.tree.cramer3.rules.test;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ4A;

public class TestRuleQ4A  extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ4A();
	}

	@Test
	public void testMultiple() throws Exception {
		Object[][] answer = { { "BrC1=C(Br)C=C(OC2=C(Br)C(Br)=C(Br)C(Br)=C2Br)C(Br)=C1", new Boolean(false) }
				};
		ruleTest(answer);
	}
	
}