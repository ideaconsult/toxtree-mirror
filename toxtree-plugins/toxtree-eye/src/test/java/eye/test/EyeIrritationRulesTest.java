package eye.test;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import eye.EyeIrritationRules;

public class EyeIrritationRulesTest {
	@Test
	public void testRulesWithSelector() throws Exception {
		EyeIrritationRules rules = new EyeIrritationRules();
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
