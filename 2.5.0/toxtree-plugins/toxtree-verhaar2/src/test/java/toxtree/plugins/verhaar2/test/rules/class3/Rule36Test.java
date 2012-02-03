package toxtree.plugins.verhaar2.test.rules.class3;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;
import verhaar.rules.Rule36;

public class Rule36Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new Rule36();
	}

	@Override
	public void test() throws Exception {
		   Object[][] answer = {
	            	{"CNNC",new Boolean(true)},
	            	{"CN=NC",new Boolean(true)},
	            	{"NN#N",new Boolean(true)},
		    };
		    
		    ruleTest(answer); 		
	}

}
