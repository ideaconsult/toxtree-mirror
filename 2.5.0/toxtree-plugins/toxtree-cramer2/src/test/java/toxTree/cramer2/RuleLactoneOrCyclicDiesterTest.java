package toxTree.cramer2;

import toxTree.core.IDecisionRule;
import cramer2.rules.RuleLactoneOrCyclicDiester;


public class RuleLactoneOrCyclicDiesterTest extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new RuleLactoneOrCyclicDiester();
	}

	@Override
	public void test() throws Exception {
	    Object[][] answer = {
	            {"O=C1CCCCC(=O)OCCC(C)CCO1",new Boolean(true)}, 
	            };
	    ruleTest(answer);
		
	}


}
