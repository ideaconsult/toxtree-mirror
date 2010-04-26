package michaelacceptors;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.core.IDecisionRule;

public class RulesSelectorTest {
	@Test
	public void testRulesWithSelector() throws Exception {
		MichaelAcceptorRules rules = new MichaelAcceptorRules();
	    int nr = rules.getNumberOfRules();
	    int na = 0;
	    for (int i = 0; i < nr; i++) {
	        IDecisionRule rule = rules.getRule(i);

	        if (rule.getSelector()==null){
	        	na++;
	        }
	    }
	    System.out.println(nr);
	    Assert.assertEquals(2,na);
	}
}

