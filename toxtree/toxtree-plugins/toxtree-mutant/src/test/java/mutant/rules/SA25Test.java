package mutant.test.rules;

import mutant.rules.SA25;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA25Test extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA25();
	}
	@Override
	public String getHitsFile() {
		return "NA25/sa21iss2.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA25";
	}
	
}
