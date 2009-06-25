package michaelacceptors;

import junit.framework.TestCase;
import michaelacceptors.rules.Rule1;
import michaelacceptors.rules.Rule2;
import michaelacceptors.rules.Rule3;
import michaelacceptors.rules.Rule4A;
import michaelacceptors.rules.Rule5;
import michaelacceptors.rules.Rule6;
import michaelacceptors.rules.Rule7;
import michaelacceptors.rules.Rule8;
import michaelacceptors.rules.Rule9B;
import michaelacceptors.rules.Rule10A;
import michaelacceptors.rules.Rule11;
import michaelacceptors.rules.Rule12B;
import michaelacceptors.rules.Rule13A;
import michaelacceptors.rules.Rule14;
import toxTree.query.FunctionalGroups;
import toxTree.tree.rules.RuleAnySubstructure;

public  class TestMichaelAcceptorRules extends TestCase {
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestMichaelAcceptorRules.class);	
	}
	//Rule1 test
	public static String[] smiles_asertFalse = {"CCC(=O)OCC","C1CCC(CC1)[N+](=O)[O-]",
		"C1=CC=NC=C1","C1CCC=CC1","C=CC1=CC=CC=C1","C#CC1=CC=CC=C1","C=CCCCC#N","C=CCS(=O)(=O)C1=CC=CC=C1",
		"C#CCOC(=O)C1=CC=CC=C1"};
	
	
	
	public static void testRule1() {
		try {	
			
			Rule1 rule =  new Rule1();
			String smile ="COC(=O)C#C";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			smile ="C1=CC=C(C=C1)C#CC=O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			smile = "CCC#CC(=O)C";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			assertFalse(RuleSmiles_asertFalse(rule));
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//Rule2 test
	public static void testRule2() {
		try {
			
			Rule2 rule =  new Rule2();
			String smile ="COC(=O)C=C";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			smile ="C1CC=CC(=O)C1";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//2,3
			smile ="CC(=C)C(=O)OCCO";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//2,4A
			smile ="CCOC(=O)C=CC(=O)OCC";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//2,11
			smile ="CCC=CC(=O)O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//2,12B
			smile ="C1=CC=C2C(=C1)C=CC(=O)C2=O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//2,4A,13A
			smile ="C1=CC(=O)C=CC1=O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			//2,14
			smile ="C=CC=O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);
			
			assertFalse(RuleSmiles_asertFalse(rule));
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//Rule3 test
	public static void testRule3() {
		try {
			
			Rule3 rule =  new Rule3();
			//2,3
			String smile ="CC(=C)C(=O)OCCO";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,false));
			assertTrue(result);	
			assertFalse(RuleSmiles_asertFalse(rule));
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//Rule4A test
	public static void testRule4A() {
		try {
			
			Rule4A rule =  new Rule4A();
			
			String smile ="CCOC(=O)C=CC(=O)OCC";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);	
			
			smile ="C1=CC(=O)C=CC1=O";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);	
			
			assertFalse(RuleSmiles_asertFalse(rule));
						
			
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//Rule5 test
	public static void testRule5() {
		try {
			
			Rule5 rule =  new Rule5();			
			String smile ="C1CCC(=CC1)[N+](=O)[O-]";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);	
			assertFalse(RuleSmiles_asertFalse(rule));
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//Rule6 test
	public static void testRule6() {
		try {
			
			Rule6 rule =  new Rule6();			
			String smile ="CC1=CC=C(C=C1)S(=O)(=O)C#C";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);		
			assertFalse(RuleSmiles_asertFalse(rule));
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//Rule7 test
	public static void testRule7() {
		try {
			
			Rule7 rule =  new Rule7();			
			String smile ="C=CS(=O)C1=CC=CC=C1";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);
			smile ="C=CS(=O)(=O)OC1=CC=CC=C1";
			result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);	
			assertFalse(RuleSmiles_asertFalse(rule));
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//Rule8 test
	public static void testRule8() {
		try {
			
			Rule8 rule =  new Rule8();			
			String smile ="C=C(CCC#N)C#N";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);	
			assertFalse(RuleSmiles_asertFalse(rule));
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//Rule9B test
	public static void testRule9B() {
		try {
			
			Rule9B rule =  new Rule9B();			
			String smile ="C=CC1=CC=CC=N1";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);	
			assertFalse(RuleSmiles_asertFalse(rule));
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	
	//Rule10A test
	public static void testRule10A() {
		try {
			
			Rule10A rule =  new Rule10A();			
			String smile ="C#CC1=CC=NC=C1";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);	
			assertFalse(RuleSmiles_asertFalse(rule));
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//Rule11 test
	public static void testRule11() {
		try {
			
			Rule11 rule =  new Rule11();			
			String smile ="CCC=CC(=O)O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);	
			assertFalse(RuleSmiles_asertFalse(rule));
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//Rule12B test
	public static void testRule12B() {
		try {
			
			Rule12B rule =  new Rule12B();			
			String smile ="C1=CC=C2C(=C1)C=CC(=O)C2=O";			
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);
			assertFalse(RuleSmiles_asertFalse((RuleAnySubstructure)rule));
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//Rule13A test
	public static void testRule13A() {
		try {
			
			Rule13A rule =  new Rule13A();			
			String smile ="C1=CC(=O)C=CC1=O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);			
			assertFalse(RuleSmiles_asertFalse(rule));			
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//Rule14 test
	public static void testRule14() {
		try {
			
			Rule14 rule =  new Rule14();			
			String smile ="C=CC=O";
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
			assertTrue(result);	
			assertFalse(RuleSmiles_asertFalse(rule));
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	//RuleSmiles_asertFalse
	public static boolean RuleSmiles_asertFalse(toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK rule) {
		boolean result = false;
		try {
			 String smile = "";
			 
			 for (int i=0;i< smiles_asertFalse.length;i++){
				 smile = smiles_asertFalse[i];
				 result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
				 
			 }
			 return result;
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
			return true;
		}
		
	}
	public static boolean RuleSmiles_asertFalse(toxTree.tree.rules.RuleAnySubstructure rule) {
		boolean result = false;
		try {
			 String smile = "";
			 
			 for (int i=0;i< smiles_asertFalse.length;i++){
				 smile = smiles_asertFalse[i];
				 result = rule.verifyRule(FunctionalGroups.createAtomContainer(smile,true));
				 
			 }
			 return result;
						
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
			return true;
		}
		
	}
	
    
}
