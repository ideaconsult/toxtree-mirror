package toxTree.cramer;

import toxTree.core.IDecisionRule;
import toxTree.tree.cramer.RuleLactoneOrCyclicDiester;


public class RuleLactoneOrCyclicDiesterTest extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
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
