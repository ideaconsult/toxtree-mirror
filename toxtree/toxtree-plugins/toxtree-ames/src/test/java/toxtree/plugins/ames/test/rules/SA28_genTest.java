/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

Contact: jeliazkova.nina@gmail.com

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package toxtree.plugins.ames.test.rules;

import toxTree.core.IDecisionRule;
import toxTree.tree.rules.smarts.AbstractRuleSmartSubstructure;
import toxtree.plugins.ames.rules.SA28_gen;
import toxtree.plugins.ames.test.TestAmesMutagenicityRules;

public class SA28_genTest extends TestAmesMutagenicityRules {
	
	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA28_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA28/sa18_l_iss2_updated.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA28";
	}
	
	public void testNnotinring() throws Exception  {
			assertFalse(verifyRule(ruleToTest,"ON2CCC=1C=CC=CC=12"));

	}

	public void testSO3H() throws Exception {

			
			assertTrue(
			applySmarts(SA28_gen.amine_and_SO3H,
					"[H]C(=C([H])C=1C([H])=C([H])C(=C([H])C=1S(=O)(=O)[O-])N([H])[H])C=2C([H])=C([H])C(=C([H])C=2S(=O)(=O)[O-])N([H])[H]")
			);
			assertFalse( //different rings
					applySmarts(SA28_gen.amine_and_SO3H,
							"O=S(=O)([O-])C2=CC=CC=C2(CC=1C=CC=C(N)C=1)")
					);			
			assertTrue(applySmarts(SA28_gen.amine_and_SO3H,"O=S(=O)([O-])C1=CC=C(N)C=C1"));
			assertTrue(applySmarts(SA28_gen.amine_and_SO3H,"O=S(=O)([O-])C1=CC=CC2=CC(N)=CC=C12"));
			assertTrue(applySmarts(SA28_gen.amine_and_SO3H,"O=S(=O)([O-])C=1C=CC2=CC(N)=CC=C2(C=1)"));
			

		
	}
	
	public void testOrthoSubstitution_ignoreAromatic() throws Exception {
		
			assertTrue(verifyRule(ruleToTest, "CC=1C=CC=2C=CC=CC=2(C=1(N))")
			);

	}
	public void test669() throws Exception {
			assertFalse(
			verifyRule(ruleToTest, "[H]C(=C([H])C=1C([H])=C([H])C(=C([H])C=1S(=O)(=O)[O-])N([H])[H])C=2C([H])=C([H])C(=C([H])C=2S(=O)(=O)[O-])N([H])[H]")
			);
	}
    public void test_ortho_carboxylicacid() throws Exception {
        exclusionRuleTest(SA28_gen.index_ortho_carboxylicacid);
    }
    public void test_ortho_disubstituted() throws Exception {
        exclusionRuleTest(SA28_gen.index_ortho_disubstitution);
    }    
    public void test_SO3H_rule() throws Exception {
        exclusionRuleTest(SA28_gen.index_so3h_1);
        exclusionRuleTest(SA28_gen.index_so3h_2);
        exclusionRuleTest(SA28_gen.index_so3h_3);
        //exclusionRuleTest(SA28_gen.index_so3h_4);
    }       
    public void exclusionRuleTest(int index) throws Exception {
        assertTrue(verifySMARTS((AbstractRuleSmartSubstructure)ruleToTest,SA28_gen.exclusion_rules[index][1].toString(),SA28_gen.exclusion_rules[index][2].toString())>0);
        assertEquals(((Boolean)SA28_gen.exclusion_rules[index][4]).booleanValue()
                ,verifyRule(ruleToTest,SA28_gen.exclusion_rules[index][2].toString()));
    }        
}
