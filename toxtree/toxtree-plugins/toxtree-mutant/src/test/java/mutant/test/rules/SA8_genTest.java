/*
Copyright Ideaconsult Ltd. (C) 2005-2007  

Contact: nina@acad.bg

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

package mutant.test.rules;

import mutant.rules.SA8_gen;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA8_genTest extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA8_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA8/NA8_newhits.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA8";
	}
	/**
	 * Overlap with SA5_gen
	 *
	 */
	public void testSA5_embedding() throws Exception {
			assertFalse(verifyRule(ruleToTest,"CCC1=CC=C(C=C1)CN(CCCl)CCCl"));
	}		
	/**
	 * Overlap with SA5_gen
	 *
	 */
	public void testSA5_separate() throws Exception {
			assertTrue(verifyRule(ruleToTest,"ClCCC1=CC=C(C=C1)CN(CCCl)CCCl"));
	}	
	/**
	 * Overlap with SA1_gen
	 *
	 */
	public void testSA1_embedding() throws Exception {
			assertFalse(verifyRule(ruleToTest,"[H]C(=O)Cl"));
	}
	/**
	 * Overlap with SA1_gen
	 *
	 */
	public void testSA1_separate() throws Exception {
			assertTrue(verifyRule(ruleToTest,"[H]C(=O)CCCl"));
	}		
	
	/**
	 * Overlap with SA2_gen
	 *
	 */
	public void testSA2_embedding_phosphonic() throws Exception {
			assertFalse(verifyRule(ruleToTest,"O=P(OCCCl)(OCCCl)OCCCl"));
	}

	/**
	 * Overlap with SA2_gen
	 *
	 */
	public void testSA2_embedding_sulphonic() throws Exception {
			assertFalse(verifyRule(ruleToTest,"[O-]S(=O)(=O)OCCCl"));
	}	

	/**
	 * Overlap with SA4_gen
	 *
	 */
	public void testSA4_embedding() throws Exception {
			assertFalse(verifyRule(ruleToTest,"CCCCC(=O)Cl"));
	}		
	/**
	 * Overlap with SA4_gen
	 *
	 */
	public void testSA4_separate() throws Exception {
			assertTrue(verifyRule(ruleToTest,"[H]C(CCCl)=C(C)Cl"));
	}	
	/**
	 * Overlap with SA20_nogen
	 *
	 */
	public void testSA20_separate() throws Exception {
			assertTrue(verifyRule(ruleToTest,"C1C(CCCCCCCCCl)C(C(CC1Br)I)Cl"));
	}		
	/**
	 * Overlap with SA20_nogen
	 *
	 */
	public void testSA20_embedding()  throws Exception{
			assertFalse(verifyRule(ruleToTest,"C1CC(C(CC1Br)I)Cl"));
	}	
	
	
}


