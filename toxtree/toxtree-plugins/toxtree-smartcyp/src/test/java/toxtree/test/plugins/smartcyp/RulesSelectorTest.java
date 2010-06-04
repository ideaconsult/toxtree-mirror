package toxtree.test.plugins.smartcyp;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.tree.AbstractTree;
import toxtree.plugins.smartcyp.SMARTCYPPlugin;

public class RulesSelectorTest {
	
	@Test
	public void testRulesWithSelector() throws Exception {
		SMARTCYPPlugin rules = new SMARTCYPPlugin();
	    int na = ((AbstractTree)rules).testRulesWithSelector();
		 
	    Assert.assertEquals(0,na);
	}
}

