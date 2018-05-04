package toxtree.tree.cramer3.rules.test;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesGenerator;

import toxTree.core.IDecisionRule;
import toxTree.query.MolFlags;
import toxtree.tree.cramer3.rules.RuleQ9_C;

public class TestRuleQ9_C extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ9_C();
	}

	@Test
	public void testlactone() throws Exception {
		test(new String[] { "O=C(OC(CCC1C)C(C)C)C1" }, true);
	}

	@Override
	protected void verifyResult(String smiles, IAtomContainer mol,
			boolean result) throws Exception {
		if ("O=C(OC(CCC1C)C(C)C)C1".equals(smiles)) {
			SmilesGenerator g = SmilesGenerator.generic();
			MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);

			Assert.assertNotNull(mf.getHydrolysisProducts());
			Assert.assertNotNull(mf.getResidues());
			for (IAtomContainer a : mf.getHydrolysisProducts().atomContainers()) {
				System.out.println(g.create(a));
			}
		}
	}
}
