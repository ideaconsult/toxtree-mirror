package toxTree.cramer2;

import toxTree.core.IDecisionRule;
import toxTree.cramer.AbstractRuleTest;

public class RuleFreeABUnsaturatedHeteroTest extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new cramer2.rules.RuleFreeABUnsaturatedHetero();
	}

	@Override
	public void test() throws Exception {

	}

}
