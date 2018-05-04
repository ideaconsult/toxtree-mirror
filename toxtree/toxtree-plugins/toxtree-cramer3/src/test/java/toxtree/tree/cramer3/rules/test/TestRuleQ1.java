package toxtree.tree.cramer3.rules.test;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.ReactionException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import toxtree.tree.Reactor;
import toxtree.tree.cramer3.rules.RuleQ1;

public class TestRuleQ1 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ1();
	}
	
	@Override
	public void test() throws Exception {
		
		RuleQ1 rule = new RuleQ1();
		Reactor reactor = rule.getReactor();
		IAtomContainer mol = FunctionalGroups.createAtomContainer("c1ccccc1CCC(OCCCC)OCCCC",true);
		MolAnalyser.analyse(mol);
		
		try {
			IAtomContainerSet residues = reactor.process(mol);
			Assert.assertNotNull(residues);
			if (residues != null) {
				MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
				if (mf == null)
					throw new DecisionMethodException(
							"Structure should be preprocessed!");
				mf.setHydrolysisProducts(residues);
				mf.setResidues(residues);
			}
			Assert.assertEquals(3, residues.getAtomContainerCount());
		} catch (ReactionException x) {
			throw new DecisionMethodException(x);
		}
	}
	
	@Test
	public void testMultiple() throws Exception {
		Object[][] answer = { { "C(#N)SSSC#N", new Boolean(true) },
				 { "O=C1OCCC1", new Boolean(false) }
				};
		ruleTest(answer);
	}
	
	/**
	 * https://bitbucket.org/vedina/cramer3/issue/11
	 * @throws Exception
	 */
	@Test
	public void testEster() throws Exception {
		
		RuleQ1 rule = new RuleQ1();
		Reactor reactor = rule.getReactor();
		IAtomContainer mol = FunctionalGroups.createAtomContainer("CCCCCC(OCC=C)=O",true);
		MolAnalyser.analyse(mol);
		
		try {
			IAtomContainerSet residues = reactor.process(mol);
			Assert.assertNotNull(residues);

		} catch (ReactionException x) {
			throw new DecisionMethodException(x);
		}
	}
	
	
	
	
}
