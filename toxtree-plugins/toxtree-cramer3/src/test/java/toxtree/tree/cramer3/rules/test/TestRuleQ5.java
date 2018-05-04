package toxtree.tree.cramer3.rules.test;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesGenerator;

import junit.framework.Assert;
import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxtree.tree.cramer3.rules.RuleQ5;

public class TestRuleQ5 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ5();
	}

	@Test
	public void testNeutraliser() throws Exception {
		RuleQ5 rule = (RuleQ5) rule2test;
		IAtomContainer mol = FunctionalGroups.createAtomContainer("c1ccc(cc1)CC(=O)C(=O)[O-].[Na+]");
		IAtomContainer newmol = rule.neutralise(mol);
		Assert.assertEquals("O=C(O)C(=O)CC=1C=CC=CC1", SmilesGenerator.unique().create(newmol));
	}
}
