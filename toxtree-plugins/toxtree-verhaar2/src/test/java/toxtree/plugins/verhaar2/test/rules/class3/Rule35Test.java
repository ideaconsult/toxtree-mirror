package toxtree.plugins.verhaar2.test.rules.class3;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.rules.Rule35;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;

public class Rule35Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new Rule35();
	}
	/*
	{"amide	","C=,#CC(=O)N(C)C"},
	{"nitrile","C=,#CC#N"},
	{"nitro","C=,#C(N(=O)=O)"},
	{"nitro(charged)","C=,#C([N+](=O)[O-])"},
	{"sulphone","C=,#CS(=O)(=O)"},
	{"X","O=C1C=CC(=O)C=C1"},
	*/
	@Override
	public void test() throws Exception {
		   Object[][] answer = {
				   //carbonyl
	            	{"C=CC(=O)O",new Boolean(true)},
	            	{"C#CC(=O)O",new Boolean(true)},
	            	//ketone
	            	{"C=CC(=O)",new Boolean(true)},
	            	{"C#CC(=O)",new Boolean(true)},
	            	
	            	//amide
	            	{"C=CC(=O)N(C)C",new Boolean(true)},
	            	{"C#CC(=O)N(C)C",new Boolean(true)},
	            	
	            	//nitrile
	            	{"C=CC#N",new Boolean(true)},
	            	{"C#CC#N",new Boolean(true)},
	            	
	            	//nitro
	            	{"C#C(N(=O)=O)",new Boolean(true)},
	            	{"C=C(N(=O)=O)",new Boolean(true)},	      
	            	
	            	//nitro
	            	{"C=C([N+](=O)[O-])",new Boolean(true)},
	            	{"C#C([N+](=O)[O-])",new Boolean(true)},
	            	
	            	//sulphone
	            	{"C=CS(=O)(=O)",new Boolean(true)},
	            	{"C#CS(=O)(=O)",new Boolean(true)},	     
	            	
	            	//
	            	{"O=C1C=CC(=O)C=C1",new Boolean(true)},
  	            	
		    };
		    
		    ruleTest(answer); 		

	}

}
