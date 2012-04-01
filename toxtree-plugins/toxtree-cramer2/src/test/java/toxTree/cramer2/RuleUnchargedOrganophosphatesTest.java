package toxTree.cramer2;

import toxTree.core.IDecisionRule;
import toxTree.cramer.AbstractRuleTest;
import cramer2.rules.RuleUnchargedOrganophosphates;

public class RuleUnchargedOrganophosphatesTest extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new RuleUnchargedOrganophosphates();
	}

	@Override
	public void test() throws Exception {

	}

}
