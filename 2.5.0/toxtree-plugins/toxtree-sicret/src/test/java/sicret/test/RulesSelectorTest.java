package sicret.test;

import junit.framework.Assert;

import org.junit.Test;

import sicret.SicretRules;
import toxTree.tree.AbstractTree;

public class RulesSelectorTest {
	@Test
	public void testRulesWithSelector() throws Exception {
		SicretRules rules = new SicretRules();
	    int na = ((AbstractTree)rules).testRulesWithSelector();
		 
	    Assert.assertEquals(0,na);
	}
}

