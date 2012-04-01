package toxTree.cramer2;

import toxTree.core.IDecisionRule;
import toxTree.cramer.AbstractRuleTest;
import cramer2.rules.RuleReadilyHydrolysedPO4;

public class RuleReadilyHydrolysedPO4Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new RuleReadilyHydrolysedPO4();
	}

	@Override
	public void test() throws Exception {

	}

}
