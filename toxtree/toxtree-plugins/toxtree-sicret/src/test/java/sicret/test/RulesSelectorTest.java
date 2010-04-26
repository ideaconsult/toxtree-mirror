package sicret.test;

import junit.framework.Assert;

import org.junit.Test;

import sicret.SicretRules;
import toxTree.core.IDecisionRule;

public class RulesSelectorTest {
	@Test
	public void testRulesWithSelector() throws Exception {
		SicretRules rules = new SicretRules();
	    int nr = rules.getNumberOfRules();
	    int na = 0;
	    for (int i = 0; i < nr; i++) {
	        IDecisionRule rule = rules.getRule(i);

	        if (rule.getSelector()==null){
	        	System.err.println(rule.toString());
	        	na++;
	        }
	    }
	    System.out.println(nr);
	    Assert.assertEquals(0,na);
	}
}

