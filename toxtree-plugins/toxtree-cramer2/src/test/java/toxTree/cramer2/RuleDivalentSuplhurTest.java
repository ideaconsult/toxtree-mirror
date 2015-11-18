package toxTree.cramer2;

import toxTree.core.IDecisionRule;
import toxTree.cramer.AbstractRuleTest;
import cramer2.rules.RuleDivalentSulphur;

public class RuleDivalentSuplhurTest extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleDivalentSulphur();
	}

	@Override
	public void test() throws Exception {
	
	}

}
