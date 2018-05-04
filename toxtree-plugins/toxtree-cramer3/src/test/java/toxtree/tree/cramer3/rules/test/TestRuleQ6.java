package toxtree.tree.cramer3.rules.test;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ6;

public class TestRuleQ6 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ6();
	}

	@Test
	public void testExcel_586_62_9() throws Exception {

		String[] smiles = new String[] { "C(=C(C)C)(CCC(=C1)C)C1" };
		test(smiles, true);
	}

	@Test
	public void testExcel_99_86_5() throws Exception {

		String[] smiles = new String[] { "C(=C(C)C)(CCC(=C1)C)C1" };
		test(smiles, true);
	}

	@Test
	public void testExcel_214220_85_6() throws Exception {

		String[] smiles = new String[] { "OCC1COC(\\C=C\\CCC)O1" };
		test(smiles, false);
	}

	@Test
	public void test_monocyclic_terpene() throws Exception {
		String[] smiles = new String[] { "C(=CCC(=C1)C)(C1)C(C)C",
				"C(=C(CCC=C(C)C)C)(CCC(=C1)C)C1" };
		test(smiles, true);
	}

	@Test
	public void test_bicyclic_terpene_1() throws Exception {
		test(new String[] { "C(=CCCC(C(C(C1(C)C)C2)C1)=C)(C2)C" }, true);
	}

	@Test
	public void test_bicyclic_terpene_2() throws Exception {
		test(new String[] { "C(C(C(CC1)C)(CC(C(=C)C)C2)C)(=C1)C2" }, true);
	}

	@Test
	public void test_bicyclic_terpene_3() throws Exception {
		test(new String[] { "C(C(CC1C2)C2)(C1(C)C)=C" }, true);
	}

	@Test
	public void test_bicyclic_terpene_4() throws Exception {
		test(new String[] { "C(C(CC1C2)C1(C)C)(=C2)C" }, true);
	}

}
