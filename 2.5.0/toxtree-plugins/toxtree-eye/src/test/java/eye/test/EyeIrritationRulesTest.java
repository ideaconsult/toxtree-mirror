package eye.test;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.tree.AbstractTree;
import eye.EyeIrritationRules;

public class EyeIrritationRulesTest {
	@Test
	public void testRulesWithSelector() throws Exception {
		EyeIrritationRules rules = new EyeIrritationRules();
	    int na = ((AbstractTree)rules).testRulesWithSelector();
		 
	    Assert.assertEquals(0,na);
	}
}
