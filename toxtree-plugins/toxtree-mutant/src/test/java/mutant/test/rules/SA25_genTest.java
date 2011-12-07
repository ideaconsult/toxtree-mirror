package mutant.test.rules;

import mutant.rules.SA25_gen;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA25_genTest extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA25_gen();
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
