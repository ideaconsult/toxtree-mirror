package mutant.test.rules;

import mutant.rules.SA37_gen;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA37_genTest extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA37_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA37/NA37hits.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA37";
	}
	
}
