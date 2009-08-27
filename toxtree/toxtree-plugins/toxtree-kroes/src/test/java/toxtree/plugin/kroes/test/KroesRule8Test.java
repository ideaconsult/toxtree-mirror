package toxtree.plugin.kroes.test;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IMolecule;

import toxtree.plugins.kroes.rules.KroesRule8;

public class KroesRule8Test {
	KroesRule8 rule = new KroesRule8();
	@Test
	public void testYes() throws Exception {
		IMolecule mol = rule.getExampleMolecule(true);
		Assert.assertTrue(rule.verifyRule(mol));
	}
	@Test
	public void testNo() throws Exception {
		IMolecule mol = rule.getExampleMolecule(false);
		Assert.assertFalse(rule.verifyRule(mol));
	}
	
}
