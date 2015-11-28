/*
Copyright Ideaconsult Ltd.(C) 2006 - 2015
Contact: jeliazkova.nina@gmail.com

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

 */

package sicret.test;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;

import sicret.rules.RuleAliphaticSaturatedAcidsAndHalogenatedAcids;
import sicret.rules.RuleC10_C20AliphaticAlcohols;
import sicret.rules.RuleEthyleneGlycolEthers;
import sicret.rules.RuleHalogenatedAlkanesAndAlkenes;
import sicret.rules.RulePhenols;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;

public class RuleHalogenatedAlkanesAndAlkenesTest {
	/*
	 * Test method for
	 * 'toxTree.tree.rules.smarts.RuleSMARTSubstructure.verifyRule(IAtomContainer)'
	 */
	@Test
	public void testVerifyRule() throws Exception {
		RuleHalogenatedAlkanesAndAlkenes rule = new RuleHalogenatedAlkanesAndAlkenes();
		Assert.assertTrue(rule.verifyRule(rule.getExampleMolecule(true)));
		Assert.assertFalse(rule.verifyRule(rule.getExampleMolecule(false)));
		IAtomContainer ac = FunctionalGroups.createAtomContainer("ClC(Cl)(Cl)C", true);
		MolAnalyser.analyse(ac);
		boolean result = rule.verifyRule(ac);

		Assert.assertTrue(result);
	}

	@Test
	public void testVerifyRulePhenols() throws Exception {
		RulePhenols rule = new RulePhenols();
		String smiles = " Oc1ccccc1";
		IAtomContainer ac = FunctionalGroups.createAtomContainer(smiles, false);
		MolAnalyser.analyse(ac);
		boolean result = rule.verifyRule(ac);
		Assert.assertTrue(result);
		smiles = "Oc1c(C)cccc1";
		ac = FunctionalGroups.createAtomContainer(smiles, false);
		MolAnalyser.analyse(ac);
		result = rule.verifyRule(ac);
		Assert.assertTrue(result);
		smiles = "Oc1ccc(cc1)C3(OC(=O)c2ccccc23)c4ccc(O)cc4";
		ac = FunctionalGroups.createAtomContainer(smiles, false);
		MolAnalyser.analyse(ac);
		result = rule.verifyRule(ac);
		Assert.assertTrue(result);
	}

	@Test
	public void testVerifyAliphaticSaturatedAcidsAndHalogenatedAcids() throws Exception {
		RuleAliphaticSaturatedAcidsAndHalogenatedAcids rule = new RuleAliphaticSaturatedAcidsAndHalogenatedAcids();

		// Decanoic acid
		String smile = "ClCCCCCCCCCCC(=O)O";
		boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// oleic acid
		smile = "CCCCCCCCC=CCCCCCCCC(=O)O";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertFalse(result);
		// Arachidonic Acid
		smile = "CCCCCC=CCC=CCC=CCC=CCCCC(=O)O";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, false));
		Assert.assertFalse(result);

	}

	@Test
	public void testVerifyRuleEthyleneGlycolEthers() throws Exception {
		RuleEthyleneGlycolEthers rule = new RuleEthyleneGlycolEthers();

		// Glucose
		String smile = "OCC1OC(O)C(O)C(O)C1(O)";
		boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertFalse(result);
		// Ethylene Glycol
		smile = "OCCO";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertFalse(result);
		// No free hydroxyl group
		smile = "COCCOC";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// Methyl glycol
		smile = "COCCO";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// 2-Ethoxyethanol
		smile = "CCOCCO";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// 2-Butoxyethanol
		smile = "CCCCOCCO";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// /////////////////////////////
		// 2-(phenoxy)ethanol
		smile = "C1=CC=C(C=C1)OCCO";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// 2-(2-phenylethoxy)ethanol
		smile = "C1=CC=C(C=C1)CCOCCO";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// B.Aleksiev
		// methyl 3-methoxypropanoate
		smile = "COCCC(=O)OC";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// propyl 4-hydroxybenzoate
		smile = "CCCOC(=O)C1=CC=C(C=C1)O";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// 1,1-dimethoxy-2-methylsulfonylethane
		smile = "COC(CS(=O)(=O)C)OC";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// (2R)-2-aminopropan-1-ol
		smile = "CC(CO)N";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// 2-(ethylamino)ethanol
		smile = "CCNCCO";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// 2-(propylamino)ethanol
		smile = "CCCNCCO";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);
		// 3-phenylpropyl carbamate
		smile = "C1=CC=C(C=C1)CCCOC(=O)N";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, true));
		Assert.assertTrue(result);

	}

	@Test
	public void testVerifyRuleC10_C20_Aliphatic_alcohols() throws Exception {
		RuleC10_C20AliphaticAlcohols rule = new RuleC10_C20AliphaticAlcohols();

		String smile = " Oc1ccc2ccccc2c1";
		boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, false));
		Assert.assertFalse(result);
		smile = "Oc1ccccc1";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, false));
		Assert.assertFalse(result);
		smile = "CCCCCCCCCCCO";
		result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile, false));
		Assert.assertTrue(result);

	}

}
