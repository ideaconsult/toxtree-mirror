package toxtree.plugins.verhaar2.test.rules.class3;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.rules.Rule37;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;

public class Rule37Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new Rule37();
	}

	@Override
	public void test() throws Exception {
		   Object[][] answer = {
	            	{"OCC#N",new Boolean(true)},
	            	{"OC(C)C#N",new Boolean(true)},
	            	{"C#CC#N",new Boolean(true)},
	            	{"CC#CC#N",new Boolean(true)},
		    };
		    
		    ruleTest(answer); 		

	}

}
