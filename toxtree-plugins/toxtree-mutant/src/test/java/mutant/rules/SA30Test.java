package mutant.test.rules;

import toxTree.core.IDecisionRule;
import mutant.rules.SA30;
import mutant.test.TestMutantRules;

public class SA30Test extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA30();
	}
	@Override
	public String getHitsFile() {
		return "NA30/onc_16.sdf";

	}
	@Override
	public String getResultsFolder() {
		return "NA30";
		
	}
	
}
