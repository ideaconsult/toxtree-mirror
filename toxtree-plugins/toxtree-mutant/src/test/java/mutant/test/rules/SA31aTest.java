package mutant.test.rules;

import mutant.rules.SA31a_nogen;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;
import toxTree.tree.rules.smarts.AbstractRuleSmartSubstructure;

public class SA31aTest extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA31a_nogen();
	}
	@Override
	public String getHitsFile() {
		return "NA31a/HaloBenzRefined_1.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA31a";
	}

	public void test_diphenyl1_separate() throws Exception {
		assertTrue(verifyRule(ruleToTest,"c1c(Cl)c(ccc1CCc2ccc(cc2))CCCCCc1ccc(Cl)cc1"));
	}	

	public void test_diphenyl2_separate() throws Exception  {
		assertTrue(verifyRule(ruleToTest,"c1c(Cl)c(ccc1Cc2ccc(cc2))CCCCCc1c(Cl)cccc1"));
	}		
	
	public void test_biphenyl() throws Exception  {
		assertFalse(verifyRule(ruleToTest,"c1c(Cl)c(ccc1c2ccc(cc2))"));
	}
	public void test_biphenyl_separate() throws Exception  {
		assertTrue(verifyRule(ruleToTest,"c1c(Cl)c(ccc1c2ccc(cc2))CCCc1cc(Cl)ccc1"));
	}		

    public void test_tobefired() throws Exception  {
        assertTrue(verifyRule(ruleToTest,"C=1C=CC=2NC(=CC=2(C=1))C=3C=CC=C(C=3)Cl"));
    }       
     
    public void exclusionRuleHalTest(int index) throws Exception {
        assertTrue(verifySMARTS((AbstractRuleSmartSubstructure)ruleToTest,SA31a_nogen.exclusion_rules_Hal[index][1],SA31a_nogen.exclusion_rules_Hal[index][2])>0);
        assertFalse(verifyRule(ruleToTest,SA31a_nogen.exclusion_rules_Hal[index][2]));
    }
    public void exclusionRuleTest(int index) throws Exception {
        assertTrue(verifySMARTS((AbstractRuleSmartSubstructure)ruleToTest,SA31a_nogen.exclusion_rules[index][1].toString(),SA31a_nogen.exclusion_rules[index][2].toString())>0);
        assertEquals(((Boolean)SA31a_nogen.exclusion_rules[index][4]).booleanValue()
                ,verifyRule(ruleToTest,SA31a_nogen.exclusion_rules[index][2].toString()));
    }    
    public void test_o_m_halogen() throws Exception {
        exclusionRuleHalTest(0);
        exclusionRuleHalTest(1);
       
    }
    public void test_nitroaromatic() throws Exception {
        exclusionRuleTest(SA31a_nogen.index_nitro_aromatic);

    }
    public void test_primaryaromaticamine() throws Exception {
        exclusionRuleTest(SA31a_nogen.index_primary_aromatic_amine);
    }    
    public void test_hydroxylamine() throws Exception {
        exclusionRuleTest(SA31a_nogen.index_hydroxyl_amine);
    }    
    
    public void test_hydroxylamineester() throws Exception {
        exclusionRuleTest(SA31a_nogen.index_hydroxyl_amineester);
    }        
    public void test_mono_and_dialkylamine() throws Exception {
        exclusionRuleTest(SA31a_nogen.index_monodialkylamine1);
        exclusionRuleTest(SA31a_nogen.index_monodialkylamine2);
        exclusionRuleTest(SA31a_nogen.index_monodialkylamine3);
    }     
    public void test_N_acyl_amine() throws Exception {
        exclusionRuleTest(SA31a_nogen.index_nacymamine);
    }     
    public void test_aromatic_diazo() throws Exception {
        exclusionRuleTest(SA31a_nogen.index_diazo);
    }
    public void test_biphenyl_rules() throws Exception {
        exclusionRuleTest(SA31a_nogen.index_biphenyl);
    }           
    public void test_diphenyl_rules() throws Exception {
        exclusionRuleTest(SA31a_nogen.index_diphenyl1);
        exclusionRuleTest(SA31a_nogen.index_diphenyl2);
    }
    public void test_fused() throws Exception  {
        //exclusionRuleTest(SA31a_nogen.index_singlering);
        assertFalse(verifyRule(ruleToTest,"C=1C=CC2=C(C=1)C=CC=C2I"));
    }

    public void testR() throws Exception {
        int[] hits = match("[R1]1[R1][R1][R1][R1][R1]1[Cl,Br,I,F]","c1ccccc1Cl");
        assertEquals(2,hits[0]);
        assertEquals(1,hits[1]);
        
        hits = match("[c;$([R1])]1[c;$([R1])][c;$([R1])][c;$([R1])][c;$([R1])][c;$([R1])]1","C=1C=CC=2C=CC=CC=2(C=1)");
        assertEquals(0,hits[0]);
        assertEquals(0,hits[1]);
    }
    public void test_3hydroxylgroups() throws Exception  {
        assertFalse(verifyRule(ruleToTest,"OC=1C=C(O)C(=CC=1(O))Cl"));
    }
    public void test_bug1() throws Exception  {
        assertTrue(verifyRule(ruleToTest,"C1=CC=C(C=C1)CCCC2=CC=CC(=C2)Cl"));    	
        assertTrue(verifyRule(ruleToTest,"C1=CC=C(C=C1)CCC2=CC=C(C=C2)Cl"));
    }    


    
}

