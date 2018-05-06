package toxtree.plugin.kroes.test;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionInteractive;
import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.query.FunctionalGroups;
import toxTree.tree.DecisionNode;
import toxtree.plugins.kroes.Kroes1Tree;
import toxtree.plugins.kroes.rules.KroesRule8;
import ambit2.base.config.Preferences;

public class KroesRule8Test {
	KroesRule8 rule = new KroesRule8();
	@Test
	public void testYes() throws Exception {
		IAtomContainer mol = rule.getExampleMolecule(true);
		Assert.assertTrue(rule.verifyRule(mol));
	}
	@Test
	public void testNo() throws Exception {
		IAtomContainer mol = rule.getExampleMolecule(false);
		Assert.assertFalse(rule.verifyRule(mol));
	}
	
	@Test
	public void testClassI() throws Exception {
		Kroes1Tree tree = new Kroes1Tree();
		tree.setInteractive(false);
		IDecisionRuleList rules = tree.getRules();
		for (int i=0; i < rules.size();i++) {
			IDecisionRule rule = rules.get(i);
			if (rule instanceof DecisionNode) 
				rule = ((DecisionNode)rule).getRule();
			if (rule instanceof IDecisionInteractive) 
				((IDecisionInteractive)rule).setInteractive(false);
		}
		IDecisionResult r = tree.createDecisionResult();
		Preferences.setProperty(Preferences.STOP_AT_UNKNOWNATOMTYPES,"true");
		IAtomContainer mol = FunctionalGroups.createAtomContainer("CCCCCCCC");
		mol.setProperty("DailyIntake", 1);
		Assert.assertFalse(rule.verifyRule(mol));
		r.classify(mol);
		r.assignResult(mol);
		
	}	
}
