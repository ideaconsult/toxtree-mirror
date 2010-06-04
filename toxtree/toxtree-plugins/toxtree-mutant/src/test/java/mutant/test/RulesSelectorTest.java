package mutant.test;

import junit.framework.Assert;
import mutant.BB_CarcMutRules;

import org.junit.Test;

import toxTree.tree.AbstractTree;

public class RulesSelectorTest {
	@Test
	public void testRulesWithSelector() throws Exception {
		BB_CarcMutRules rules = new BB_CarcMutRules();
	    int na = ((AbstractTree)rules).testRulesWithSelector();
		 
	    Assert.assertEquals(0,na);
	}
}

