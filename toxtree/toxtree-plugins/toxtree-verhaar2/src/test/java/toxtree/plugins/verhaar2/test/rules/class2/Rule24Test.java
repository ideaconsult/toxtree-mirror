package toxtree.plugins.verhaar2.test.rules.class2;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.rules.Rule24;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;

public class Rule24Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new Rule24();
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
