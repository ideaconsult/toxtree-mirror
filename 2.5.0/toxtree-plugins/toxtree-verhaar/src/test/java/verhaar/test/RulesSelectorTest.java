package verhaar.test;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.tree.AbstractTree;
import verhaar.VerhaarScheme;

public class RulesSelectorTest {
	
	@Test
	public void testRulesWithSelector() throws Exception {
		 VerhaarScheme rules= new VerhaarScheme();
		    int na = ((AbstractTree)rules).testRulesWithSelector();
			 
		    Assert.assertEquals(0,na);
	}
}

