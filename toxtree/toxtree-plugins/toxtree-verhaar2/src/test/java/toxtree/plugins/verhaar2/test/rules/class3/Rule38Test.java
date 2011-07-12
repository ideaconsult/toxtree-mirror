package toxtree.plugins.verhaar2.test.rules.class3;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.rules.Rule38;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;

public class Rule38Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new Rule38();
	}

	@Override
	public void test() throws Exception {

		   Object[][] answer = {
	            	{"C(=O)OC(=O)",new Boolean(true)},
	            	{"C1COC(=O)OC1",new Boolean(true)},
	            	
	            	{"C(=O)Cl",new Boolean(true)},
	            	{"NC(=O)Cl",new Boolean(true)},
	            	{"C=C=O",new Boolean(true)},
	            	{"CC(=O)",new Boolean(true)},
	            	{"C=O",new Boolean(true)},
	            	{"N=C=O",new Boolean(true)},
	            	{"SC#N",new Boolean(true)},
	            	{"CSSC",new Boolean(true)},
	            	{"CS(=O)(=O)OC",new Boolean(true)},
	            	
	            	{"OS(=O)(=O)OC",new Boolean(true)},
	            	{"C1CS(=O)(=O)O1",new Boolean(true)},
	            	{"C1OS(=O)(=O)O1",new Boolean(true)},
	            	{"OCCl",new Boolean(true)},
	            	{"OCCCl",new Boolean(true)},
	            	{"NCCI",new Boolean(true)},
	            	{"SCCBr",new Boolean(true)},
		    };
		    
		    ruleTest(answer); 
	}

}
