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
            	{"C=CC(C)Cl",Boolean.TRUE},
            	{"C=CC(C)Br",Boolean.TRUE},
            	{"C=CC(C)I",Boolean.TRUE},
            	{"C=CC(C)C#N",Boolean.TRUE},
            	{"C=CC(C)O",Boolean.TRUE},
            	{"C=CC(C)CC(O)=O",Boolean.TRUE},
            	{"C=CC(C)CC=O",Boolean.TRUE},
            	
            	{"C#CC(C)Cl",Boolean.TRUE},
            	{"C#CC(C)Br",Boolean.TRUE},
            	{"C#CC(C)I",Boolean.TRUE},
            	{"C#CC(C)C#N",Boolean.TRUE},
            	{"C#CC(C)O",Boolean.TRUE},
            	{"C#CC(C)CC(O)=O",Boolean.TRUE},
            	{"C#CC(C)CC=O",Boolean.TRUE},     
            	
            	{"C1CCCC(C#C)(O)C1",Boolean.FALSE},
            	 
	    };
	    
	    ruleTest(answer); 		
	}

}
