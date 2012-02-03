package toxtree.plugins.verhaar2.test.rules.class3;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;
import verhaar.rules.Rule38;

public class Rule38Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new Rule38();
	}

	@Override
	public void test() throws Exception {

		   Object[][] answer = {
	            	{"C(=O)OC(=O)",Boolean.TRUE},
	            	{"C1COC(=O)OC1",Boolean.TRUE},
	            	
	            	{"C(=O)Cl",Boolean.TRUE},
	            	{"NC(=O)Cl",Boolean.TRUE},
	            	{"C=C=O",Boolean.TRUE},
	            	{"CC(=O)",Boolean.TRUE},
	            	{"C=O",Boolean.TRUE},
	            	{"N=C=O",Boolean.TRUE},
	            	{"SC#N",Boolean.TRUE},
	            	{"CSSC",Boolean.TRUE},
	            	{"CS(=O)(=O)OC",Boolean.TRUE},
	            	
	            	{"OS(=O)(=O)OC",Boolean.TRUE},
	            	{"C1CS(=O)(=O)O1",Boolean.TRUE},
	            	{"C1OS(=O)(=O)O1",Boolean.TRUE},
	            	{"OCCl",Boolean.TRUE},
	            	{"OCCCl",Boolean.TRUE},
	            	{"NCCI",Boolean.TRUE},
	            	{"SCCBr",Boolean.TRUE},
	            	{"BrCC1OCCCC1",Boolean.FALSE},

	            	{"C(Cl)(Cl)(Cl)CO",Boolean.FALSE},

	            	

	            	

		    };
		    
		    ruleTest(answer); 
	}

}
