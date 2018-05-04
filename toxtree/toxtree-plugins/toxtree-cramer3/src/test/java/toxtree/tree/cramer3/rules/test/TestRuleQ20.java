package toxtree.tree.cramer3.rules.test;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ20;

public class TestRuleQ20 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ20();
	}

	@Override
	public void test() throws Exception {
		String[] smiles = new String[] { "C1CC(CCCCCCC(C)CCCC)CCC1" };
		test(smiles, false);
	}
	
	@Test
	public void testExcel_1323_00_8() throws Exception {
		
		String[] smiles = new String[] { "C=C1C(C2CC1CC2)(CCC=C(COC(=O)C)C)C" };
		test(smiles, true);
	}

	@Test
	public void testExcel_473_67_6() throws Exception {
		
		String[] smiles = new String[] { "C1(C)(C)C2C(O)C=C(C1C2)C" };
		test(smiles, true);
	}

	

}
