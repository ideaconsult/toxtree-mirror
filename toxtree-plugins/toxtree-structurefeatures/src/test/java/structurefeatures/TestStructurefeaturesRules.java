package structurefeatures;


import junit.framework.TestCase;
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



public class TestStructurefeaturesRules extends TestCase {
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestStructurefeaturesRules.class);	
	}
	public static void testRuleAlkylPhosphonate() {
		try {	
			
			RuleAlkylPhosphonate rule =  new RuleAlkylPhosphonate();
			//Dimethyl methylphosphonate
			String smile ="P(=O)(CO)(O)O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleAromaticAzo() {
		try {	
			
			RuleAromaticAzo rule =  new RuleAromaticAzo();
			//Azobenzene
			String smile ="N(=N/c1ccccc1)\\c2ccccc2";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleAromaticNitro() {
		try {	
			
			RuleAromaticNitro rule =  new RuleAromaticNitro();
			//2,4,6-Trinitrophenol
			String smile ="O=[N+]([O-])c1cc(cc([N+]([O-])=O)c1O)[N+]([O-])=O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			//Trotyl
			smile ="CC1=C(C=C(C=C1[N+](=O)[O-])[N+](=O)[O-])[N+](=O)[O-]";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleNitrosamine() {
		try {	
			
			RuleNitrosamine rule =  new RuleNitrosamine();
			//4-(methylnitrosamino)- 1-(3-pyridyl)-1-butanone (abbreviated NNK)
			String smile ="CN(CCCC(=O)C1=CN=CC=C1)N=O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//3-(1-Nitrosopyrrolidin-2-yl)pyridine N-Nitrosonornicotine (NNN)
			smile ="O=NN1CCCC1c2cccnc2";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleUrethaneDerivatives() {
		try {	
			
			RuleUrethaneDerivatives rule =  new RuleUrethaneDerivatives();
			//carbofuran
			String smile ="CC1(CC2=C(O1)C(=CC=C2)OC(=O)NC)C";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//fenobucarb
			smile ="CCC(C)C1=CC=CC=C1OC(=O)NC";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRulePropiolactone() {
		try {	
			
			RulePropiolactone rule =  new RulePropiolactone();
			//PIVALOLACTONE
			String smile ="CC1(COC1=O)C";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleprimaryAlkylHalide() {
		try {	
			
			RuleprimaryAlkylHalide rule =  new RuleprimaryAlkylHalide();
			//chloroethane
			String smile ="CCCl";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleArylMethylHalide() {
		try {	
			
			RuleArylMethylHalide rule =  new RuleArylMethylHalide();
			//benzyl chloride
			String smile ="ClCc1ccccc1";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleNitrogenMustard() {
		try {	
			
			RuleNitrogenMustard rule =  new RuleNitrogenMustard();
			//2-chloro-N-(2-chloroethyl)-N-methyl-ethanamine
			String smile ="CN(CCCl)CCCl";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//2-chloro-N,N-bis(2-chloroethyl)ethanamine
			smile ="ClCCN(CCCl)CCCl";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleAmine() {
		try {	
			
			RuleAmine rule =  new RuleAmine();
			//aniline
			String smile ="C1=CC=C(C=C1)N";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//Anisidine
			smile ="COc1c(N)cccc1";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleNOxide() {
		try {	
			
			RuleNOxide rule =  new RuleNOxide();
			//pyridine-N-oxide
			String smile ="O=n1ccccc1";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleNMethylol() {
		try {	
			
			RuleNMethylol rule =  new RuleNMethylol();
			//N-Methylolpyrrolidone
			String smile ="C1CC(=O)N(C1)CO";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//N-methylol acrylamide polymer
			smile ="CCCCOC(=O)C=C.CC(=O)OC=C.C=CC(=O)NCO.C=CC(=O)O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleAlkylAldehyde() {
		try {	
			
			RuleAlkylAldehyde rule =  new RuleAlkylAldehyde();
			//Pentanal
			String smile ="CCCCC=O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleAziridine() {
		try {	
			
			RuleAziridine rule =  new RuleAziridine();
			//aziridine
			String smile ="C1CN1";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleEpoxide() {
		try {	
			
			RuleEpoxide rule =  new RuleEpoxide();
			//Ethylene oxide
			String smile ="C1CO1";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//Glycidol
			smile ="OCC1CO1";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleAlkylHydrazine() {
		try {	
			
			RuleAlkylHydrazine rule =  new RuleAlkylHydrazine();
			//1,2-Dimethylhydrazine
			String smile ="CNNC";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//1,2-Diformylhydrazine
			smile ="O=CNNC=O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//METHYLHYDRAZINE
			smile ="CNN";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleNChloramine() {
		try {	
			
			RuleNChloramine rule =  new RuleNChloramine();
			//
			String smile ="ClN(Cl)S(=O)(=O)c1ccc(cc1)C";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//
			smile ="Clc1ccc(cc1)S(=O)(=O)N(Cl)Cl";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//
			smile ="ClNS(=O)(=O)c1ccc(cc1)C";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//
			smile ="ClN(Cl)S(=O)(=O)c1c(Cl)cccc1(Cl)";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleNHydroxy() {
		try {	
			
			RuleNHydroxy rule =  new RuleNHydroxy();
			//N-Hydroxy-aminofluorene
			String smile ="C1C2=CC=CC=C2C3=C1C=C(C=C3)NO";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleNitroso() {
		try {	
			
			RuleNitroso rule =  new RuleNitroso();
			//2-Methyl-2-nitrosopropane
			String smile ="CC(C)(C)N=O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleNitro() {
		try {	
			
			RuleNitro rule =  new RuleNitro();
			//nitromethane
			String smile ="C[N+]([O-])=O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleHeterocycle() {
		try {	
			
			RuleHeterocycle rule =  new RuleHeterocycle();
			//1,4-Benzoquinone
			String smile ="C1=CC(=O)C=CC1=O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//Benzimidazole
			smile ="c1cccc2ncnc12";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//Imidazole
			smile ="n1c[nH]cc1";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//Pyrazole
			smile ="C1=CC=NN1";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//butane-2-thione
			smile ="CCC(=S)C";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//2-Methylthio-3-methylpyrazine
			smile ="CC1=NC=CN=C1SC";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//QUINOLINE
			smile ="C1=CC=C2C(=C1)C=CC=N2";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleIminomethyl() {
		try {	
			
			RuleIminomethyl rule =  new RuleIminomethyl();
			//1-methylpyrrole
			String smile ="CN1C=CC=C1";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleKetone() {
		try {	
			
			RuleKetone rule =  new RuleKetone();
			//ACETOPHENONE
			String smile ="CC(=O)C1=CC=CC=C1";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleHalide() {
		try {	
			
			RuleHalide rule =  new RuleHalide();
			//Bromoethane
			String smile ="CCBr";			
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			//2-Bromopropane
			smile ="CC(C)Br";			
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			//2-Bromo-2-methylpropane 
			smile ="CC(C)(C)Br";			
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			//chlorobenzene 
			smile ="C1=CC=C(C=C1)Cl";			
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			//ALLYL CHLORIDE 
			smile ="C=CCCl";			
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleFuran() {
		try {	
			
			RuleFuran rule =  new RuleFuran();
			//2,5-Dimethylfuran
			String smile ="Cc1ccc(C)o1";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleEther() {
		try {	
			
			RuleEther rule =  new RuleEther();
			//Diethyl ether
			String smile ="CCOCC";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleBenzene() {
		try {	
			
			RuleBenzene rule =  new RuleBenzene();
			//naphthalene
			String smile ="C1=CC=C2C=CC=CC2=C1";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//Methylaniline
			smile ="CNC1=CC=CC=C1";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//N-phenylformamide
			smile ="C1=CC=C(C=C1)NC=O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//1-methyl
			smile ="CC1=CC=CC=C1";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//1-heteroamino
			//benzenamine
			smile ="C1CC(=NC1)NC2=CC=CC=C2";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleAromaticAldehyde() {
		try {	
			
			RuleAromaticAldehyde rule =  new RuleAromaticAldehyde();
			//Benzaldehyde
			String smile ="c1ccccc1C=O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleAlcohol() {
		try {	
			
			RuleAlcohol rule =  new RuleAlcohol();
			//Ethanol
			String smile ="CCO";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRuleNucleosides() {
		try {	
			
			RuleNucleosides rule =  new RuleNucleosides();
			/*//Pyrimidine
			String smile ="C1=CN=CN=C1";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			//Cytosine
			smile ="C1=C(NC(=O)N=C1)N";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);*/
			//adenosine
			String smile ="C1=NC2=C(C(=N1)N)N=CN2C3C(C(C(O3)CO)O)O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public static void testRulePhosphoricGroups() {
		try {	
			
			RulePhosphoricGroups rule =  new RulePhosphoricGroups();
			//Methylphosphonic acid
			String smile ="CP(=O)(O)O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			//Benzenephosphonic acid
			smile ="C1=CC=C(C=C1)P(=O)(O)O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			//Phenylphosphate
			smile ="C1=CC=C(C=C1)OP(=O)(O)O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			//Monomethyl phosphate
			smile ="COP(=O)(O)O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			//Benzenephosphinic acid
			smile ="C1=CC=C(C=C1)P(O)O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			//Benzenephosphinic acid
			smile ="CP(O)O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	

}
