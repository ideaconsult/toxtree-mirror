package toxtree.plugins.verhaar2.test.rules.class2;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;
import verhaar.rules.Rule25;

public class Rule25Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new Rule25();
	}

	@Override
	public void test() throws Exception {
		   Object[][] answer = {
	            	{"n1ccc(c2ccccc2)cc1",Boolean.FALSE},
	            	{"Nc1cc(C(F)(F)F)c(F)cc1",Boolean.FALSE},
	            	//{"Clc1nc(nc(n1)NC(C)C)NCC",Boolean.TRUE}  //atrazine has pyridine character (B.A.) - but N substituents are still not allowed!
		    };
		    
		    ruleTest(answer); 		
	}

}
