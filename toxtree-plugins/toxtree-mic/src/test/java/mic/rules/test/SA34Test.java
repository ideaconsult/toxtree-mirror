package mic.rules.test;

import mic.rules.SA34;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;

public class SA34Test extends AbstractRuleTest {
	
	
		public void test() throws Exception {
		Object[][] answer = {
				{"[S-]C(=S)N",Boolean.FALSE},
		};
		
		for (int i=0; i < 2;i++) {
			IAtomContainer a = FunctionalGroups.createAtomContainer(answer[i][0].toString());
			
			MolAnalyser.analyse(a);
			assertEquals((Boolean)answer[i][1],(Boolean)rule2test.verifyRule(a));
		}
	}

		@Override
		protected IDecisionRule createRule() {
			return new SA34();
		}
}
