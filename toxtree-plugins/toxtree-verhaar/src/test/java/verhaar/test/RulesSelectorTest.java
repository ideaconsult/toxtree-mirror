package verhaar.test;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import verhaar.VerhaarScheme;

public class RulesSelectorTest {
	
	@Test
	public void testRulesWithSelector() throws Exception {
		 VerhaarScheme rules= new VerhaarScheme();
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

