package mutant.test.rules;

import mutant.rules.SA21;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA21Test extends TestMutantRules {
	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA21();
	}
	@Override
	public String getHitsFile() {
		return "NA21/SA17iss2.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA21";
	}
}
