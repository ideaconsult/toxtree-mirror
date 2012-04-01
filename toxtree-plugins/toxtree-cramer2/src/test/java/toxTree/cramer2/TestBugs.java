package toxTree.cramer2;


import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import toxTree.core.IDecisionResult;
import toxTree.cramer.bugs.AbstractTreeTest;
import cramer2.CramerRulesWithExtensions;

/**
 * https://sourceforge.net/tracker/?func=detail&aid=3445043&group_id=152702&atid=785126
 * @author nina
 *
 */
public class TestBugs extends AbstractTreeTest {

	@BeforeClass
	public static void setup() throws Exception {
		cr = new CramerRulesWithExtensions();
		((CramerRulesWithExtensions)cr).setResiduesIDVisible(false);

	}
	@Test
	public void test3488950() throws Exception {

		IDecisionResult result = classify("Oc1ccc(O)cc1");
		Assert.assertEquals(3,result.getCategory().getID());
		
		result = classify("Cc1cccc(C)c1O");
		Assert.assertEquals(3,result.getCategory().getID());
		
		 result = classify("Cc1cc(O)ccc1C");
		Assert.assertEquals(3,result.getCategory().getID());

	}
	
	@Test
	public void test3089699() throws Exception {

		IDecisionResult result = classify("CCCCCCP");
		Assert.assertEquals(3,result.getCategory().getID());
		Assert.assertEquals("1N,2N,3Y,4Y,40Y",cr.explainRules(result,false).toString());
		
		result = classify("CCCCOP(OCC)(OCCC)=O");
		Assert.assertEquals(3,result.getCategory().getID());
		Assert.assertEquals("1N,2N,3Y,4Y,40Y",cr.explainRules(result,false).toString());
		
	}	
	
	
}
