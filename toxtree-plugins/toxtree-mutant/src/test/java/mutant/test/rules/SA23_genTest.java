package mutant.test.rules;

import mutant.rules.SA23_gen;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA23_genTest extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA23_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA23/sa25iss2_myhits.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA23";
	}
}
