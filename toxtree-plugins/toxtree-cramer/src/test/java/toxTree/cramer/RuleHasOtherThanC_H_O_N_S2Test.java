package toxTree.cramer;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import toxTree.tree.cramer.RuleHasOtherThanC_H_O_N_S2;

/**
 * Test for {@link RuleHasOtherThanC_H_O_N_S2}
 * @author nina
 *
 */
public class RuleHasOtherThanC_H_O_N_S2Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {

		return new RuleHasOtherThanC_H_O_N_S2();
	}

	@Override
	public void test() throws Exception {
		Object[][] answer = {
				{"O=C(C1=C(SN=N2)C2=CC=C1)SC",false},
				{"O=C(O)N[C@H](C(N[C@@H](C1=NC2=CC=CC=C2S1)C)=O)C(C)C",false},
				{"C1(C2=CSC=N2)=NC3=CC=CC=C3N1",false}
		};
		
		for (int i=0; i < answer.length;i++) {
			IAtomContainer a = FunctionalGroups.createAtomContainer(answer[i][0].toString());
			
				MolAnalyser.analyse(a);
				Boolean result = rule2test.verifyRule(a);
				Object mf = a.getProperty(MolFlags.MOLFLAGS);
				assertNotNull(mf);
				assertEquals(((Boolean)answer[i][1]),result);
		}
		
	}

}
