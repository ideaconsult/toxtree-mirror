package mutant.test.rules;

import mutant.rules.SA39_gen_and_nogen;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA39_gen_and_nogenTest extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA39_gen_and_nogen();
	}
	@Override
	public String getHitsFile() {
		return "NA39/NA39hits.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA39";
	}
	
}
