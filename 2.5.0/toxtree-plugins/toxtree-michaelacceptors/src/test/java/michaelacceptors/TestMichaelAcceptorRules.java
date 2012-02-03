package michaelacceptors;

import junit.framework.Assert;
import michaelacceptors.rules.Rule1;
import michaelacceptors.rules.Rule10A;
import michaelacceptors.rules.Rule10B;
import michaelacceptors.rules.Rule11;
import michaelacceptors.rules.Rule12A;
import michaelacceptors.rules.Rule12B;
import michaelacceptors.rules.Rule13A;
import michaelacceptors.rules.Rule13B;
import michaelacceptors.rules.Rule14;
import michaelacceptors.rules.Rule2;
import michaelacceptors.rules.Rule3;
import michaelacceptors.rules.Rule4A;
import michaelacceptors.rules.Rule4B;
import michaelacceptors.rules.Rule5;
import michaelacceptors.rules.Rule6;
import michaelacceptors.rules.Rule7;
import michaelacceptors.rules.Rule8;
import michaelacceptors.rules.Rule9A;
import michaelacceptors.rules.Rule9B;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;

public  class TestMichaelAcceptorRules  {
	
	//Rule1 test
	public static String[] smiles_asertFalse = {"CCC(=O)OCC","C1CCC(CC1)[N+](=O)[O-]",
		"C1=CC=NC=C1","C1CCC=CC1","C=CC1=CC=CC=C1","C#CC1=CC=CC=C1","C=CCCCC#N","C=CCS(=O)(=O)C1=CC=CC=C1",
		"C#CCOC(=O)C1=CC=CC=C1"};
	
	protected static boolean verifyRule(IDecisionRule rule, String smiles) throws Exception {
		IAtomContainer mol = FunctionalGroups.createAtomContainer(smiles,false);
		MolAnalyser.analyse(mol);
		return rule.verifyRule(mol);
	}
	@Test
	public void testRule1() throws Exception {
			
			Rule1 rule =  new Rule1();
			String smile ="COC(=O)C#C";

			Assert.assertTrue(verifyRule(rule, smile));
			smile ="C1=CC=C(C=C1)C#CC=O";
			Assert.assertTrue(verifyRule(rule, smile));
			smile = "CCC#CC(=O)C";
			Assert.assertTrue(verifyRule(rule, smile));
			Assert.assertFalse(RuleSmiles_asertFalse(rule));

	}
	//Rule2 test
	@Test
	public void testRule2() throws Exception {
			Rule2 rule =  new Rule2();
			String smile ="COC(=O)C=C";
			Assert.assertTrue(verifyRule(rule, smile));
			
			smile ="C1CC=CC(=O)C1";
			Assert.assertTrue(verifyRule(rule, smile));
			
			//2,3
			smile ="CC(=C)C(=O)OCCO";
			Assert.assertTrue(verifyRule(rule, smile));
			
			//2,4A
			smile ="CCOC(=O)C=CC(=O)OCC";
			Assert.assertTrue(verifyRule(rule, smile));
			
			//2,11
			smile ="CCC=CC(=O)O";
			Assert.assertTrue(verifyRule(rule, smile));
			
			//2,12B
			smile ="C1=CC=C2C(=C1)C=CC(=O)C2=O";
			Assert.assertTrue(verifyRule(rule, smile));
			
			//2,4A,13A
			smile ="C1=CC(=O)C=CC1=O";
			Assert.assertTrue(verifyRule(rule, smile));
			
			//2,14
			smile ="C=CC=O";
			Assert.assertTrue(verifyRule(rule, smile));
			
			Assert.assertFalse(RuleSmiles_asertFalse(rule));
			
						

	}
	//Rule3 test
	@Test
	public  void testRule3() throws Exception {

			Rule3 rule =  new Rule3();
			//2,3
			String smile ="CC(=C)C(=O)OCCO";
			Assert.assertTrue(verifyRule(rule, smile));
			Assert.assertFalse(RuleSmiles_asertFalse(rule));

	}
	//Rule4A test
	@Test
	public  void testRule4A() throws Exception {

			Rule4A rule =  new Rule4A();
			
			String smile ="CCOC(=O)C=CC(=O)OCC";
			Assert.assertTrue(verifyRule(rule, smile));
			
			smile ="C1=CC(=O)C=CC1=O";
			Assert.assertTrue(verifyRule(rule, smile));
			
			Assert.assertFalse(RuleSmiles_asertFalse(rule));

	}
	//Rule4B test
	
	public  void testRule4B() throws Exception {

			Rule4B rule =  new Rule4B();
			
			String smile ="CCOC(=O)C=CC(=O)OCC";
			Assert.assertTrue(verifyRule(rule, smile));
			
			smile ="C1=CC(=O)C=CC1=O";
			Assert.assertTrue(verifyRule(rule, smile));
			
			Assert.assertFalse(RuleSmiles_asertFalse(rule));

	}	
	//Rule5 test
	@Test
	public  void testRule5() throws Exception {

			Rule5 rule =  new Rule5();			
			String smile ="C1CCC(=CC1)[N+](=O)[O-]";
			Assert.assertTrue(verifyRule(rule, smile));
			Assert.assertFalse(RuleSmiles_asertFalse(rule));

	}
	//Rule6 test
	@Test
	public  void testRule6() throws Exception{
			Rule6 rule =  new Rule6();			
			String smile ="CC1=CC=C(C=C1)S(=O)(=O)C#C";
			Assert.assertTrue(verifyRule(rule, smile));
			Assert.assertFalse(RuleSmiles_asertFalse(rule));

	}
	//Rule7 test
	@Test
	public  void testRule7() throws Exception{

			Rule7 rule =  new Rule7();			
			String smile ="C=CS(=O)C1=CC=CC=C1";
			Assert.assertTrue(verifyRule(rule, smile));
			smile ="C=CS(=O)(=O)OC1=CC=CC=C1";
			Assert.assertTrue(verifyRule(rule, smile));
			Assert.assertFalse(RuleSmiles_asertFalse(rule));

	}
	//Rule8 test
	@Test
	public  void testRule8() throws Exception {

			Rule8 rule =  new Rule8();			
			String smile = "N#CCCC(=C)C#N" ;//"C=C(CCC#N)C#N";
			Assert.assertTrue(verifyRule(rule, smile));
			Assert.assertFalse(RuleSmiles_asertFalse(rule));

	}
	//Rule9A test
	@Test
	public  void testRule9A() throws Exception {
			Rule9A rule =  new Rule9A();			
			String smile ="C#Cc1ncccc1";
			Assert.assertTrue(verifyRule(rule, smile));
			Assert.assertFalse(RuleSmiles_asertFalse(rule));

	}	
	//Rule9B test
	@Test
	public  void testRule9B() throws Exception {
			Rule9B rule =  new Rule9B();		
			String smile;
			/*
			smile ="C=CC1=CC=CC=N1";
			Assert.assertTrue(verifyRule(rule, smile));
			
			smile ="c1cccnc1C=C";
			Assert.assertTrue(verifyRule(rule, smile));			
			*/
			smile ="C=Cc1ccccn1";
			Assert.assertTrue(verifyRule(rule, smile));			


			Assert.assertFalse(RuleSmiles_asertFalse(rule));

	}
	
	//Rule10A test
	@Test
	public  void testRule10A() throws Exception {

			Rule10A rule =  new Rule10A();			
			String smile ="C#CC1=CC=NC=C1";
			Assert.assertTrue(verifyRule(rule, smile));
			smile ="c1cnccc1C#C";
			Assert.assertTrue(verifyRule(rule, smile));
			
			Assert.assertFalse(RuleSmiles_asertFalse(rule));
	}
	
	@Test
	public  void testRule10B() throws Exception {
			Rule10B rule =  new Rule10B();			
			Assert.assertTrue(verifyRule(rule, "C=[CH]c1ccncc1"));
			Assert.assertFalse(RuleSmiles_asertFalse(rule));
	}	
	//Rule11 test
	@Test
	public  void testRule11() throws Exception {
			Rule11 rule =  new Rule11();			
			String smile ="CCC=CC(=O)O";
			Assert.assertTrue(verifyRule(rule, smile));
			Assert.assertFalse(RuleSmiles_asertFalse(rule));

	}
	//Rule12A test
	@Test
	public  void testRule12A() throws Exception {
			Rule12A rule =  new Rule12A();			
			String smile ="O=C1C=CC=CC1=O";			
			Assert.assertTrue(verifyRule(rule, smile));
			Assert.assertFalse(RuleSmiles_asertFalse(rule));
						
	}	
	//Rule12B test
	@Test
	public  void testRule12B() throws Exception {
			Rule12B rule =  new Rule12B();			
			String smile ="C1=CC=C2C(=C1)C=CC(=O)C2=O";			
			Assert.assertTrue(verifyRule(rule, smile));
			Assert.assertFalse(RuleSmiles_asertFalse(rule));
						
	}
	//Rule13A test
	@Test
	public  void testRule13A() throws Exception {
			Rule13A rule =  new Rule13A();			
			String smile ="C1=CC(=O)C=CC1=O";
			Assert.assertTrue(verifyRule(rule, smile));		
			Assert.assertFalse(RuleSmiles_asertFalse(rule));			

	}
	//Rule13B test
	@Test
	public  void testRule13B() throws Exception {
			Rule13B rule =  new Rule13B();			
			String smile ="O=C1C=CC(=O)C=2C=CC=CC1=2";
			Assert.assertTrue(verifyRule(rule, smile));		
			Assert.assertFalse(RuleSmiles_asertFalse(rule));			

	}	
	//Rule14 test
	@Test
	public void testRule14() throws Exception {
			Rule14 rule =  new Rule14();			
			String smile ="C=CC=O";
			Assert.assertTrue(verifyRule(rule, smile));
			Assert.assertFalse(RuleSmiles_asertFalse(rule));

	}
	//RuleSmiles_asertFalse
	public static boolean RuleSmiles_asertFalse(IDecisionRule rule) throws Exception {
		boolean result = false;
		
			 String smile = "";
			 
			 for (int i=0;i< smiles_asertFalse.length;i++){
				 smile = smiles_asertFalse[i];
				 result = verifyRule(rule,smile);
				 if (result) System.out.println(smile);
				 
			 }
			 return result;

		
	}

	
    
}
