package toxtree.plugins.func.test;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.tree.AbstractTree;
import toxtree.plugins.func.FuncRules;

public class RulesSelectorTest {
	@Test
	public void testRulesWithSelector() throws Exception {
		FuncRules rules = new FuncRules();
	    int na = ((AbstractTree)rules).testRulesWithSelector();
		 
	    Assert.assertEquals(0,na);
	}
}

