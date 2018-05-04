package toxtree.tree.cramer3.rules.test;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import toxtree.tree.cramer3.rules.RuleQ18_JM;

public class TestRuleQ18_JM extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ18_JM();
	}

	@Test
	public void test18L_positive() throws Exception {
		test(new String[] { "O=CC(=CCC)C", "O=CC(=CCC(C)C)C(C)C",
				"CCC(C=O)=CCCCC" }, true);
	}

	@Test
	public void test18L_negative() throws Exception {
		test(new String[] { "COCCC(C)(C)S", "SC(C)C(C)SC(C)C(O)C",
				"O=CCC(S)CCC", "O=C(O)CCS", "O=C(OCC(CCCC)CC)CCS",
				"OC(=O)C=CCC" }, false);
	}

	@Test
	public void test18Lalphabeta_positive() throws Exception {
		test(new String[] { "C1(=O)C=C(C)C(=CC=CC)C(C)(C)C1",
				"OC1(/C=C/C(C)O)C(C)=CC(=O)CC1(C)C" }, true);
	}

	@Test
	public void testPolysulfide() throws Exception {
		test(new String[] { "CCCSSCC" }, true);
	}

	@Test
	public void test18J() throws Exception {
		test(new String[] { "O=CC(CCCCCCCCC)C" }, false);
	}

	public void test18_notsure() throws Exception {
		//C(CCC1C(=O)OC)CC1 hydrolyse, one of products give yes ? why?
		test(new String[] { "C(CCC1C(=O)O)CC1", "CO" }, true);
	}

}
