package toxtree.plugins.verhaar2.test.rules.class2;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.rules.Rule23;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;

public class Rule23Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new Rule23();
	}

	@Override
	public void test() throws Exception {

	    Object[][] answer = {
	    		//mononitroaromatics  with subst.
            	{"O=[N+]([O-])c1cccc(Cl)c1",Boolean.TRUE},
            	{"O=[N+]([O-])c1cccc(CCC)c1",Boolean.TRUE},
            	{"O=[N+]([O-])c1ccc(Cl)c(Cl)c1",Boolean.TRUE},
            	{"O=[N+]([O-])c1c(CCC)ccc(CCC)c1",Boolean.TRUE},
            	//
            	{"O=[N+]([O-])c1ccccc1",Boolean.FALSE},
            	{"O=[N+]([O-])c1cc(Cl)c(Cl)c(Cl)c1",Boolean.FALSE},

            	{"Fc1ccc(N(=O)=O)cc1",Boolean.FALSE},

            	

	    };
	    
	    ruleTest(answer); 		
	}

}
