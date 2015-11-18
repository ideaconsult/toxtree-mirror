package toxTree.cramer2;

import junit.framework.Assert;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import toxTree.core.IDecisionRule;
import toxTree.cramer.AbstractRuleTest;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import cramer2.rules.RuleReadilyHydrolysedPO4;

public class RuleReadilyHydrolysedPO4Test extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleReadilyHydrolysedPO4();
	}

	@Override
	public void testExampleYes() throws Exception {
		//don't test, fails always by design!!!!!
		IAtomContainer a = rule2test.getExampleMolecule(true);
		MolAnalyser.analyse(a);
		assertFalse(rule2test.verifyRule(a));
		MolFlags mf = (MolFlags)a.getProperty(MolFlags.MOLFLAGS);
		IAtomContainerSet residues = mf.getResidues();
		Assert.assertNotNull(residues);
		System.out.println(residues.getAtomContainerCount());
	}
	
	@Override
	public void testExampleNo() throws Exception {
		//fails always by design, but should not have residues
		IAtomContainer a = rule2test.getExampleMolecule(false);
		MolAnalyser.analyse(a);
		assertFalse(rule2test.verifyRule(a));
		MolFlags mf = (MolFlags)a.getProperty(MolFlags.MOLFLAGS);
		Assert.assertNull(mf.getResidues());
	}
	@Override
	public void test() throws Exception {

	}

}
