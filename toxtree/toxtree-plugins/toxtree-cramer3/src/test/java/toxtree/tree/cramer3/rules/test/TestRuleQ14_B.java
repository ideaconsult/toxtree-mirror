package toxtree.tree.cramer3.rules.test;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ14_B;

public class TestRuleQ14_B extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ14_B();
	}
	/**
	 * Heteroaromatics without any substituents should go to Q29, i.e. answer no
	 * @throws Exception
	 */
	@Test
	public void testNosubstituents()
			throws Exception {
		test(new String[] { "c1ccc[nH]1" }, false);
	}
	@Test
	public void testMethylEthyl_yes()
			throws Exception {
		test(new String[] { "Cc1ccc(CO)[nH]1","CCCCc1ccc([nH]1)c2ccccc2" ,"CC(=O)c1ccc(C)[nH]1"}, true);
	}
	
	@Test
	public void testMethylEthyl_no()
			throws Exception {
		test(new String[] {
				"Cc1ccc[nH]1",
				"Cc1ccc([nH]1)c2ccccc2",
				"Cc1ccc(CC)[nH]1" }, false);
		
	}
	/**
	 * Yes according to #58 - but the structure is very similaro to the No example 
	 * @throws Exception
	 */
	@Test
	public void test_4208_57_5()
			throws Exception {
		test(new String[] { "C1=C(C(CCC)=O)OC=C1" }, true);
	}
	 
}
