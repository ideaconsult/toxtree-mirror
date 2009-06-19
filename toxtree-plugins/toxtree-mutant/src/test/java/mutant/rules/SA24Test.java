package mutant.test.rules;

import mutant.rules.SA24;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA24Test extends TestMutantRules {
	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA24();
	}
	@Override
	public String getHitsFile() {
		return "NA24/sa26iss2_linear.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA24";
	}
	
}
