package toxtree.plugin.kroes.test;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IMolecule;

import toxtree.plugins.kroes.rules.KroesRule10;

public class KroesRule10Test {

		KroesRule10 rule = new KroesRule10();
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
