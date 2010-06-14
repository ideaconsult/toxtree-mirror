package toxtree.plugins.skinsensitisation.test;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.tree.AbstractTree;
import toxtree.plugins.skinsensitisation.SkinSensitisationPlugin;

public class RulesSelectorTest {
	
	@Test
	public void testRulesWithSelector() throws Exception {
		SkinSensitisationPlugin rules = new SkinSensitisationPlugin();
	    int na = ((AbstractTree)rules).testRulesWithSelector();
		 
	    Assert.assertEquals(1,na);
	}
}

