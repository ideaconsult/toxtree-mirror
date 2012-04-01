package toxTree.cramer2;

import toxTree.core.IDecisionRule;
import toxTree.cramer.AbstractRuleTest;
import cramer2.rules.RuleReHydrolysedNoPO4;

public class RuleReHydrolysedNoPO4Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new RuleReHydrolysedNoPO4();
	}

	@Override
	public void test() throws Exception {

	}

}
