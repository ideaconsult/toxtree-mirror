package mutant.test.rules;

import mutant.rules.SA26_gen;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;

public class SA26_genTest extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA26_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA26/sa4iss2.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA26";
	}
	public void testGroup() throws Exception {
		assertTrue(FunctionalGroups.hasGroup(ruleToTest.getExampleMolecule(true), FunctionalGroups.noxide_aromatic()));
	}
	public void testAromaticity_from_SMILES() throws Exception {
		assertTrue(verifyRule(ruleToTest,"O=[N+]([O-])C5=CC=C2C=CC3=C1C=CC=CC1=[N+]([O-])C=4C=CC5(=C2C3=4)"));
	}
}
