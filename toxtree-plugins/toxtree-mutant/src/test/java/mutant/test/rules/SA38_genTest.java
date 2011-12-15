package mutant.test.rules;

import mutant.rules.SA38_gen;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA38_genTest extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA38_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA38/NA38hits.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA38";
	}
	
}
