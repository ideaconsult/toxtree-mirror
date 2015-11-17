package structurefeatures;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;

import structurefeatures.rules.RuleAlcohol;
import structurefeatures.rules.RuleAlkylAldehyde;
import structurefeatures.rules.RuleAlkylHydrazine;
import structurefeatures.rules.RuleAlkylPhosphonate;
import structurefeatures.rules.RuleAmine;
import structurefeatures.rules.RuleAromaticAldehyde;
import structurefeatures.rules.RuleAromaticAzo;
import structurefeatures.rules.RuleAromaticNitro;
import structurefeatures.rules.RuleArylMethylHalide;
import structurefeatures.rules.RuleAziridine;
import structurefeatures.rules.RuleBenzene;
import structurefeatures.rules.RuleEpoxide;
import structurefeatures.rules.RuleEther;
import structurefeatures.rules.RuleFuran;
import structurefeatures.rules.RuleHalide;
import structurefeatures.rules.RuleHeterocycle;
import structurefeatures.rules.RuleIminomethyl;
import structurefeatures.rules.RuleKetone;
import structurefeatures.rules.RuleNChloramine;
import structurefeatures.rules.RuleNHydroxy;
import structurefeatures.rules.RuleNMethylol;
import structurefeatures.rules.RuleNOxide;
import structurefeatures.rules.RuleNitro;
import structurefeatures.rules.RuleNitrogenMustard;
import structurefeatures.rules.RuleNitrosamine;
import structurefeatures.rules.RuleNitroso;
import structurefeatures.rules.RuleNucleosides;
import structurefeatures.rules.RulePhosphoricGroups;
import structurefeatures.rules.RulePropiolactone;
import structurefeatures.rules.RuleUrethaneDerivatives;
import structurefeatures.rules.RuleprimaryAlkylHalide;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;

public class TestStructurefeaturesRules {

	@Test
	public void testRuleAlkylPhosphonate() throws Exception {
		RuleAlkylPhosphonate rule = new RuleAlkylPhosphonate();
		// Dimethyl methylphosphonate
		String smiles = "P(=O)(CO)(O)O";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleAromaticAzo() throws Exception {
		RuleAromaticAzo rule = new RuleAromaticAzo();
		// Azobenzene
		String smiles = "N(=N/c1ccccc1)\\c2ccccc2";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleAromaticNitro() throws Exception {
		RuleAromaticNitro rule = new RuleAromaticNitro();
		// 2,4,6-Trinitrophenol
		String smiles = "O=[N+]([O-])c1cc(cc([N+]([O-])=O)c1O)[N+]([O-])=O";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);
		// Trotyl
		smiles = "CC1=C(C=C(C=C1[N+](=O)[O-])[N+](=O)[O-])[N+](=O)[O-]";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleNitrosamine() throws Exception {
		RuleNitrosamine rule = new RuleNitrosamine();
		// 4-(methylnitrosamino)- 1-(3-pyridyl)-1-butanone (abbreviated NNK)
		String smiles = "CN(CCCC(=O)C1=CN=CC=C1)N=O";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// 3-(1-Nitrosopyrrolidin-2-yl)pyridine N-Nitrosonornicotine (NNN)
		smiles = "O=NN1CCCC1c2cccnc2";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleUrethaneDerivatives() throws Exception {
		RuleUrethaneDerivatives rule = new RuleUrethaneDerivatives();
		// carbofuran
		String smiles = "CC1(CC2=C(O1)C(=CC=C2)OC(=O)NC)C";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// fenobucarb
		smiles = "CCC(C)C1=CC=CC=C1OC(=O)NC";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRulePropiolactone() throws Exception {
		RulePropiolactone rule = new RulePropiolactone();
		// PIVALOLACTONE
		String smiles = "CC1(COC1=O)C";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleprimaryAlkylHalide() throws Exception {
		RuleprimaryAlkylHalide rule = new RuleprimaryAlkylHalide();
		// chloroethane
		String smiles = "CCCl";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleArylMethylHalide() throws Exception {

		RuleArylMethylHalide rule = new RuleArylMethylHalide();
		// benzyl chloride
		String smiles = "ClCc1ccccc1";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleNitrogenMustard() throws Exception {

		RuleNitrogenMustard rule = new RuleNitrogenMustard();
		// 2-chloro-N-(2-chloroethyl)-N-methyl-ethanamine
		String smiles = "CN(CCCl)CCCl";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// 2-chloro-N,N-bis(2-chloroethyl)ethanamine
		smiles = "ClCCN(CCCl)CCCl";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleAmine() throws Exception {

		RuleAmine rule = new RuleAmine();
		// aniline
		String smiles = "C1=CC=C(C=C1)N";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// Anisidine
		smiles = "COc1c(N)cccc1";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleNOxide() throws Exception {

		RuleNOxide rule = new RuleNOxide();
		// pyridine-N-oxide
		String smiles = "[O-][N+]1=CC=CC=C1";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleNMethylol() throws Exception {

		RuleNMethylol rule = new RuleNMethylol();
		// N-Methylolpyrrolidone
		String smiles = "C1CC(=O)N(C1)CO";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// N-methylol acrylamide polymer
		smiles = "CCCCOC(=O)C=C.CC(=O)OC=C.C=CC(=O)NCO.C=CC(=O)O";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleAlkylAldehyde() throws Exception {

		RuleAlkylAldehyde rule = new RuleAlkylAldehyde();
		// Pentanal
		String smiles = "CCCCC=O";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleAziridine() throws Exception {
		RuleAziridine rule = new RuleAziridine();
		// aziridine
		String smiles = "C1CN1";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleEpoxide() throws Exception {

		RuleEpoxide rule = new RuleEpoxide();
		// Ethylene oxide
		String smiles = "C1CO1";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// Glycidol
		smiles = "OCC1CO1";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleAlkylHydrazine() throws Exception {

		RuleAlkylHydrazine rule = new RuleAlkylHydrazine();
		// 1,2-Dimethylhydrazine
		String smiles = "CNNC";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// 1,2-Diformylhydrazine
		smiles = "O=CNNC=O";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// METHYLHYDRAZINE
		smiles = "CNN";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleNChloramine() throws Exception {

		RuleNChloramine rule = new RuleNChloramine();
		//
		String smiles = "ClN(Cl)S(=O)(=O)c1ccc(cc1)C";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		//
		smiles = "Clc1ccc(cc1)S(=O)(=O)N(Cl)Cl";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		//
		smiles = "ClNS(=O)(=O)c1ccc(cc1)C";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		//
		smiles = "ClN(Cl)S(=O)(=O)c1c(Cl)cccc1(Cl)";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleNHydroxy() throws Exception {

		RuleNHydroxy rule = new RuleNHydroxy();
		// N-Hydroxy-aminofluorene
		String smiles = "C1C2=CC=CC=C2C3=C1C=C(C=C3)NO";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleNitroso() throws Exception {

		RuleNitroso rule = new RuleNitroso();
		// 2-Methyl-2-nitrosopropane
		String smiles = "CC(C)(C)N=O";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleNitro() throws Exception {
		RuleNitro rule = new RuleNitro();
		// nitromethane
		String smiles = "C[N+]([O-])=O";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleHeterocycle() throws Exception {

		RuleHeterocycle rule = new RuleHeterocycle();
		// 1,4-Benzoquinone
		String smiles = "C1=CC(=O)C=CC1=O";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// Benzimidazole
		smiles = "C2=C1N=C[NH]C1=CC=C2";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// Imidazole
		smiles = "n1c[nH]cc1";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// Pyrazole
		smiles = "C1=CC=NN1";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// butane-2-thione
		smiles = "CCC(=S)C";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// 2-Methylthio-3-methylpyrazine
		smiles = "CC1=NC=CN=C1SC";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// QUINOLINE
		smiles = "C1=CC=C2C(=C1)C=CC=N2";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleIminomethyl() throws Exception {
		RuleIminomethyl rule = new RuleIminomethyl();
		// 1-methylpyrrole
		String smiles = "CN1C=CC=C1";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleKetone() throws Exception {
		RuleKetone rule = new RuleKetone();
		// ACETOPHENONE
		String smiles = "CC(=O)C1=CC=CC=C1";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleHalide() throws Exception {

		RuleHalide rule = new RuleHalide();
		// Bromoethane
		String smiles = "CCBr";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);
		// 2-Bromopropane
		smiles = "CC(C)Br";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);
		// 2-Bromo-2-methylpropane
		smiles = "CC(C)(C)Br";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);
		// chlorobenzene
		smiles = "C1=CC=C(C=C1)Cl";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);
		// ALLYL CHLORIDE
		smiles = "C=CCCl";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleFuran() throws Exception {
		RuleFuran rule = new RuleFuran();
		// 2,5-Dimethylfuran
		String smiles = "Cc1ccc(C)o1";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleEther() throws Exception {
		RuleEther rule = new RuleEther();
		// Diethyl ether
		String smiles = "CCOCC";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleBenzene() throws Exception {

		RuleBenzene rule = new RuleBenzene();
		// naphthalene
		String smiles = "C1=CC=C2C=CC=CC2=C1";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// Methylaniline
		smiles = "CNC1=CC=CC=C1";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// N-phenylformamide
		smiles = "C1=CC=C(C=C1)NC=O";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// 1-methyl
		smiles = "CC1=CC=CC=C1";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

		// 1-heteroamino
		// benzenamine
		smiles = "C1CC(=NC1)NC2=CC=CC=C2";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleAromaticAldehyde() throws Exception {
		RuleAromaticAldehyde rule = new RuleAromaticAldehyde();
		// Benzaldehyde
		String smiles = "c1ccccc1C=O";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRuleAlcohol() throws Exception {
		RuleAlcohol rule = new RuleAlcohol();
		// Ethanol
		String smiles = "CCO";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);
	}

	@Test
	public void testRuleNucleosides() throws Exception {
		RuleNucleosides rule = new RuleNucleosides();
		/*
		 * //Pyrimidine String smiles ="C1=CN=CN=C1"; boolean result =
		 * rule.verifyRule (FunctionalGroups.createAtomContainer(smiles,false));
		 * Assert.assertTrue(result); //Cytosine smiles ="C1=C(NC(=O)N=C1)N";
		 * result =
		 * rule.verifyRule(FunctionalGroups.createAtomContainer(smiles,false ));
		 * Assert.assertTrue(result);
		 */
		// adenosine
		String smiles = "C1=NC2=C(C(=N1)N)N=CN2C3C(C(C(O3)CO)O)O";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	@Test
	public void testRulePhosphoricGroups() throws Exception {
		RulePhosphoricGroups rule = new RulePhosphoricGroups();
		// Methylphosphonic acid
		String smiles = "CP(=O)(O)O";
		boolean result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);
		// Benzenephosphonic acid
		smiles = "C1=CC=C(C=C1)P(=O)(O)O";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);
		// Phenylphosphate
		smiles = "C1=CC=C(C=C1)OP(=O)(O)O";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);
		// Monomethyl phosphate
		smiles = "COP(=O)(O)O";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);
		// Benzenephosphinic acid
		smiles = "C1=CC=C(C=C1)P(O)O";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);
		// Benzenephosphinic acid
		smiles = "CP(O)O";
		result = rule.verifyRule(createAtomContainer(smiles));
		Assert.assertTrue(result);

	}

	protected IAtomContainer createAtomContainer(String smiles) throws Exception {
		IAtomContainer ac = FunctionalGroups.createAtomContainer(smiles,false);
		MolAnalyser.analyse(ac);
		return ac;
	}

}
