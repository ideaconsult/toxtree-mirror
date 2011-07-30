package mutant.test.rules;

import mutant.rules.SA24;
import mutant.test.TestMutantRules;

import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolAnalyser;

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
	
	/**
	 * https://sourceforge.net/tracker/?func=detail&aid=3138570&group_id=152702&atid=785126
	 */
	public void test_bug3138570() throws Exception {
		SmilesParser p = new SmilesParser(NoNotificationChemObjectBuilder.getInstance());
		IMolecule m = p.parseSmiles("C1CC=CO1");

		try {
			/*
			HydrogenAdder ha = new HydrogenAdder();
			ha.addExplicitHydrogensToSatisfyValency(m);
			*/
			MolAnalyser.analyse(m);
			for (int i=0; i < m.getAtomCount();i++)
				/*
            	https://sourceforge.net/tracker/?func=detail&aid=3020065&group_id=20024&atid=120024
                System.out.println(m.getAtom(i).getSymbol() + '\t'+ m.getAtom(i).getHydrogenCount());
            	*/
				System.out.println(m.getAtom(i).getSymbol() + '\t'+ m.getAtom(i).getImplicitHydrogenCount());
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
		assertEquals(true,ruleToTest.verifyRule(m));
		
	}
	
}
