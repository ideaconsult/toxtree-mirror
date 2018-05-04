package toxtree.tree.cramer3.rules.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.core.IDecisionRule;
import toxTree.query.MolAnalyser;
import toxtree.tree.cramer3.rules.RuleQ19;

public class TestRuleQ19 extends AbstractRuleTest {

	
	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleQ19();
	}

	@Override
	public void test() throws Exception {
		IAtomContainer a = MoleculeFactory.makeBiphenyl();
		MolAnalyser.analyse(a);
		Assert.assertTrue(rule2test.verifyRule(a));
	}

	@Test
	public void testMultiple() throws Exception {
		Object[][] answer = { { "CCCCCCCCC", new Boolean(false) },
				{ "c1ccccc1O", new Boolean(true) }, };
		ruleTest(answer);
	}

}
