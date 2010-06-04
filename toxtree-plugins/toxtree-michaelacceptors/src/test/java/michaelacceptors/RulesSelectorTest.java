package michaelacceptors;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.tree.AbstractTree;

public class RulesSelectorTest {
	@Test
	public void testRulesWithSelector() throws Exception {
		MichaelAcceptorRules rules = new MichaelAcceptorRules();
	    int na = ((AbstractTree)rules).testRulesWithSelector();
		 
	    Assert.assertEquals(0,na);
	}
}

