package toxtree.plugins.verhaar2.test.rules.class3;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;
import verhaar.rules.Rule34;

public class Rule34Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new Rule34();
	}

	@Override
	public void test() throws Exception {
		   Object[][] answer = {
	            	{"C1OC1",new Boolean(true)},
	            	{"C1NC1",new Boolean(true)},
		    };
		    
		    ruleTest(answer); 		
	}

}
