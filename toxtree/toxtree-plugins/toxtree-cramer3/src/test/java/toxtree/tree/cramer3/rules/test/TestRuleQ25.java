package toxtree.tree.cramer3.rules.test;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ25;

public class TestRuleQ25 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ25();
	}

	@Test
	public void test() throws Exception {
		Object[][] answer = { 
				//{ "O=C(CCc(ccc(O)c1OC)c1)C", new Boolean(false) } 
				{"O=C(Oc(ccc(c1)C)c1)CC(C)C", new Boolean(false)}
		};
		ruleTest(answer);
	}
	
	
	@Test
	public void testMercaptans()
			throws Exception {
		test(new String[] { "Sc(c(ccc1)C)c1C" ,"SC(C)c1ccccc1"}, true);
	}
	@Test
	public void testMonosulfide()
			throws Exception {
		test(new String[] { "CCCC(SCc1ccccc1)SCc2ccccc2" }, false);
	}
	
	@Test
	public void testNeurtralq5()
			throws Exception {
		test(new String[] { "c1ccc(cc1)CC(=O)C(=O)[O]" }, false);
	}
	
	@Test
	public void testCarbonchain()
			throws Exception {
		test(new String[] { "O=C\\C(=C/C(C)CC)c1ccccc1","O=C\\C(=C/C(C)CC)c1ccccc1" }, true);
	}
	
			
	
}
