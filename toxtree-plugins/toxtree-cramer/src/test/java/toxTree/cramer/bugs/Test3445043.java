package toxTree.cramer.bugs;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.core.IDecisionResult;

/**
 * https://sourceforge.net/tracker/?func=detail&aid=3445043&group_id=152702&atid=785126
 * @author nina
 *
 */
public class Test3445043 extends AbstractTreeTest {

	@Test
	public void test() throws Exception {
		IDecisionResult result = classify("CC1=CC(N=C2C([N]3)=O)=C(C=C1C)N(CC(C(C(COP([O-])(O)=O)[O])[O])[O])C2=NC3=O.OCC[NH2+]CCO");
		Assert.assertEquals(3,result.getCategory().getID());
		String explanation = cr.explainRules(result,false).toString();
		Assert.assertEquals("1N,2Y",explanation);
	}
}
