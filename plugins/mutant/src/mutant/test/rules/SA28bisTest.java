package mutant.test.rules;

import toxTree.core.IDecisionRule;
import mutant.rules.SA28bis;
import mutant.test.TestMutantRules;

public class SA28bisTest extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA28bis();
	}
	@Override
	public String getHitsFile() {
		return "NA28bis/sa5_l_iss2.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA28bis";
	}
	
	public void testSO3H() {
		try {
			
			assertTrue(
					verifyRule(ruleToTest, "CN(C)C1=CC=C(C=C1)N=NC2=CC=CC=C2")
					);
			assertFalse(
					verifyRule(ruleToTest, "CN(C)C=1C=CC(=CC=1S(=O)(=O)[O-])N=NC2=CC=CC=C2")
					);			
			 

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
