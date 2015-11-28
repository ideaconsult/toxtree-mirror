package mutant.test.rules;

import mutant.rules.SA28_gen;
import mutant.test.TestMutantRules;

import org.junit.Test;

import toxTree.core.IDecisionRule;
import toxTree.tree.rules.smarts.AbstractRuleSmartSubstructure;

public class SA28_genTest extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA28_gen();
	}

	@Override
	public String getHitsFile() {
		return "NA28/sa18_l_iss2_updated.sdf";
	}

	@Override
	public String getResultsFolder() {
		return "NA28";
	}

	@Test
	public void testNnotinring() throws Exception {
		assertFalse(verifyRule(ruleToTest, "ON2CCC=1C=CC=CC=12"));

	}

	@Test
	public void testSO3H() throws Exception {

		assertTrue(applySmarts(SA28_gen.amine_and_SO3H,
				"[H]C(=C([H])C=1C([H])=C([H])C(=C([H])C=1S(=O)(=O)[O-])N([H])[H])C=2C([H])=C([H])C(=C([H])C=2S(=O)(=O)[O-])N([H])[H]"));
		assertFalse( // different rings
		applySmarts(SA28_gen.amine_and_SO3H, "O=S(=O)([O-])C2=CC=CC=C2(CC=1C=CC=C(N)C=1)"));
		assertTrue(applySmarts(SA28_gen.amine_and_SO3H, "O=S(=O)([O-])C1=CC=C(N)C=C1"));
		assertTrue(applySmarts(SA28_gen.amine_and_SO3H, "O=S(=O)([O-])C1=CC=CC2=CC(N)=CC=C12"));
		assertTrue(applySmarts(SA28_gen.amine_and_SO3H, "O=S(=O)([O-])C=1C=CC2=CC(N)=CC=C2(C=1)"));

	}

	@Test
	public void testOrthoSubstitution_ignoreAromatic() throws Exception {

		assertTrue(verifyRule(ruleToTest, "CC=1C=CC=2C=CC=CC=2(C=1(N))"));

	}

	@Test
	public void test669() throws Exception {
		assertFalse(verifyRule(ruleToTest,
				"[H]C(=C([H])C=1C([H])=C([H])C(=C([H])C=1S(=O)(=O)[O-])N([H])[H])C=2C([H])=C([H])C(=C([H])C=2S(=O)(=O)[O-])N([H])[H]"));
	}

	@Test
	public void test_ortho_carboxylicacid() throws Exception {
		exclusionRuleTest(SA28_gen.index_ortho_carboxylicacid);
	}

	@Test
	public void test_ortho_disubstituted() throws Exception {
		exclusionRuleTest(SA28_gen.index_ortho_disubstitution);
	}

	@Test
	public void test_SO3H_rule() throws Exception {
		exclusionRuleTest(SA28_gen.index_so3h_1);
		exclusionRuleTest(SA28_gen.index_so3h_2);
		exclusionRuleTest(SA28_gen.index_so3h_3);
		// exclusionRuleTest(SA28_gen.index_so3h_4);
	}

	public void exclusionRuleTest(int index) throws Exception {
		assertTrue(verifySMARTS((AbstractRuleSmartSubstructure) ruleToTest,
				SA28_gen.exclusion_rules[index][1].toString(), SA28_gen.exclusion_rules[index][2].toString()) > 0);
		assertEquals(((Boolean) SA28_gen.exclusion_rules[index][4]).booleanValue(),
				verifyRule(ruleToTest, SA28_gen.exclusion_rules[index][2].toString()));
	}
}
