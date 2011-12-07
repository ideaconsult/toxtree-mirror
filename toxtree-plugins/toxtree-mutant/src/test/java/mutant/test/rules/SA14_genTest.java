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

import mutant.rules.SA14_gen;
import mutant.test.TestMutantRules;
import toxTree.core.IDecisionRule;

public class SA14_genTest extends TestMutantRules {
	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA14_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA14/sa24_31_l_iss2.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA14";
	}
	/**
	 * Overlap with SA22_gen
	 *
	 */
	public void testSA22_separate1() {
		try {
			assertTrue(verifyRule(ruleToTest,"N=NCCCC=N=[N+]=[N-]"));
		} catch (Exception x) {
			fail(x.getMessage());
		}
	}		
	public void testSA22_separate2() {
		try {
			assertTrue(verifyRule(ruleToTest,"N=NCCCC=N=NN"));
		} catch (Exception x) {
			fail(x.getMessage());
		}
	}		
	public void testSA22_separate3() {
		try {
			assertTrue(verifyRule(ruleToTest,"N=NCCC=[N+]=[N-]"));
		} catch (Exception x) {
			fail(x.getMessage());
		}
	}			
	/**
	 * Overlap with SA22_gen
	 *
	 */
	public void testSA22_embedding1() {
		try {
			assertFalse(verifyRule(ruleToTest,"C=N=[N+]=[N-]"));
			
		} catch (Exception x) {
			fail(x.getMessage());
		}
	}
	public void testSA22_embedding2() {
		try {
			assertFalse(verifyRule(ruleToTest,"C=N=NN"));
		} catch (Exception x) {
			fail(x.getMessage());
		}
	}	
	/**
	 * Cannot percieve atom type for the 3th atom: N
	 * TODO Verify
	 */
	public void testSA22_embedding3() throws Exception {
	    assertFalse(verifyRule(ruleToTest,"C=[N+]=[N-]=N"));

	}		
}


