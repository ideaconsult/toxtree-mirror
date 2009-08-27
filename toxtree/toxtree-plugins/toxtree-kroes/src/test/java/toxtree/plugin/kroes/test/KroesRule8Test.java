package toxtree.plugin.kroes.test;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;

import ambit2.base.config.Preferences;

import toxTree.core.IDecisionResult;
import toxTree.query.FunctionalGroups;
import toxtree.plugins.kroes.Kroes1Tree;
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
	
	@Test
	public void testClassI() throws Exception {
		Kroes1Tree tree = new Kroes1Tree();
		IDecisionResult r = tree.createDecisionResult();
		Preferences.setProperty(Preferences.STOP_AT_UNKNOWNATOMTYPES,"true");
		IAtomContainer mol = FunctionalGroups.createAtomContainer("CCCCCCCC");
		Assert.assertFalse(rule.verifyRule(mol));
		r.classify(mol);
		r.assignResult(mol);
		
	}	
}
