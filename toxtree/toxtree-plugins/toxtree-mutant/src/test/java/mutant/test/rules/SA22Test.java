package mutant.test.rules;

import mutant.rules.SA22;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA22Test extends TestMutantRules {
	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA22();
	}
	@Override
	public String getHitsFile() {
		return "NA22/sa23iss2.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA22";
	}
}
