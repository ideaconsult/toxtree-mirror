package mutant.test.rules;

import mutant.rules.SA31b_nogen;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA31b_nogenTest extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA31b_nogen();
	}
	@Override
	public String getHitsFile() {
		return "NA31b/HaloPAH.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA31b";
	}
	 
	public void test_new() throws Exception {
		assertFalse(verifyRule(ruleToTest,"CC1C=3C=C(F)C=CC=3(C=2C=CC(F)=CC1=2)"));
	}
	public void testAromaticity_from_smiles() throws Exception{
		String[] smiles = {
		 "CCOC(=O)C(O)(C1=CC=C(C=C1)Cl)C=2C=CC(=CC=2)Cl",
		"C1=CC(=CC=C1C(C2=CC=C(C=C2)Cl)=C(Cl)Cl)Cl",
		"O(C=1C(=C(C(=C(C=1Br)Br)Br)Br)Br)C2=C(C(=C(C(=C2Br)Br)Br)Br)Br",
		"OC(C=1C=CC(=CC=1)Cl)(C2=CC=C(C=C2)Cl)C(Cl)(Cl)Cl",
		"C=1C=C(C=CC=1C(C=2C=CC(=CC=2)Cl)C(Cl)Cl)Cl",
		"C=1C=C(C=CC=1C(C=2C=CC(=CC=2)Cl)C(Cl)(Cl)Cl)Cl",
		"O=S(=O)(C=1C=CC(=CC=1)Cl)C2=CC=C(C=C2)Cl",
		};
		for (int i=0; i < smiles.length; i++)
			assertTrue(verifyRule(ruleToTest,smiles[i]));
	}
}
