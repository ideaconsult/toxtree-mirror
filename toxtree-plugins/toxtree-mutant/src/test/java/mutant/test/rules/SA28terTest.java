package mutant.test.rules;

import mutant.rules.SA28ter_gen;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA28terTest extends TestMutantRules {
	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA28ter_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA28ter/sa27_l_iss2.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA28ter";
	}
	
	public void testSO3H() {
		try {
			//no SO3 group
			assertTrue(
					verifyRule(ruleToTest, "CC(=O)NC1=CC=C(C=C1)N=NC2=CC(C)=CC=C2(O)")
					);
			//SO3 on the amino ring
			assertFalse(
					verifyRule(ruleToTest, "CC(=O)NC=2C=CC(N=NC1=CC(C)=CC=C1(O))=C(C=2)S(=O)(=O)[O-]")
					);			
			//SO3H on the other ring
			assertTrue(
					verifyRule(ruleToTest, "CC(=O)NC1=CC=C(C=C1)C=CC=2C(O)=CC=C(C)C=2S(=O)(=O)[O-]")
					);			 

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
//
