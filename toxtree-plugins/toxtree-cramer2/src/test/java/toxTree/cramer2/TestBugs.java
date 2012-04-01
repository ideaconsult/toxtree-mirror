package toxTree.cramer2;


import junit.framework.Assert;

import org.junit.Test;

import toxTree.core.IDecisionResult;
import toxTree.cramer.bugs.AbstractTreeTest;

/**
 * https://sourceforge.net/tracker/?func=detail&aid=3445043&group_id=152702&atid=785126
 * @author nina
 *
 */
public class TestBugs extends AbstractTreeTest {

	@Test
	public void test3488950() throws Exception {

		IDecisionResult result = classify("Oc1ccc(O)cc1");
		Assert.assertEquals(3,result.getCategory().getID());
		
		result = classify("Cc1cccc(C)c1O");
		Assert.assertEquals(3,result.getCategory().getID());
		
		 result = classify("Cc1cc(O)ccc1C");
		Assert.assertEquals(3,result.getCategory().getID());

	}
	
}
