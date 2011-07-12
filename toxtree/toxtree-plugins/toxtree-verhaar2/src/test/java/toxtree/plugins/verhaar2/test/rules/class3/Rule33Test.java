package toxtree.plugins.verhaar2.test.rules.class3;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.rules.Rule33;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;

public class Rule33Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new Rule33();
	}

	@Override
	public void test() throws Exception {

	    Object[][] answer = {
            	{"C=CC(C)Cl",new Boolean(true)},
            	{"C=CC(C)Br",new Boolean(true)},
            	{"C=CC(C)I",new Boolean(true)},
            	{"C=CC(C)C#N",new Boolean(true)},
            	{"C=CC(C)O",new Boolean(true)},
            	{"C=CC(C)CC(O)=O",new Boolean(true)},
            	{"C=CC(C)CC=O",new Boolean(true)},
            	
            	{"C#CC(C)Cl",new Boolean(true)},
            	{"C#CC(C)Br",new Boolean(true)},
            	{"C#CC(C)I",new Boolean(true)},
            	{"C#CC(C)C#N",new Boolean(true)},
            	{"C#CC(C)O",new Boolean(true)},
            	{"C#CC(C)CC(O)=O",new Boolean(true)},
            	{"C#CC(C)CC=O",new Boolean(true)},            	
	    };
	    
	    ruleTest(answer); 		
	}

}
