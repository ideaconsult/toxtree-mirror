package mutant.test.rules;

import mutant.rules.SA23;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA23Test extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA23();
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
