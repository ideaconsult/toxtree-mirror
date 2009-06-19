/*
Copyright Ideaconsult Ltd.(C) 2006  
Contact: nina@acad.bg

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

import junit.framework.TestCase;
import sicret.rules.RuleHasOnlyC_H_O_N_Halogen;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;

public class RuleHasOnlyC_H_O_N_HalogenTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(RuleHasOnlyC_H_O_N_HalogenTest.class);
	}

	/*
	 * Test method for 'sicret.rules.RuleHasOnlyC_H_O_N_Halogen.verifyRule(IAtomContainer)'
	 */
	public void testVerifyRule() {
		RuleHasOnlyC_H_O_N_Halogen rule = new RuleHasOnlyC_H_O_N_Halogen();
		try {
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer("ClC(Cl)(Cl)C",false));
			assertFalse(result);
			assertFalse(rule.verifyRule(FunctionalGroups.createAtomContainer("ClC(Cl)(Cl)CC(=O)CCC(N)CP",false)));
			assertTrue(rule.verifyRule(rule.getExampleMolecule(true)));
			assertFalse(rule.verifyRule(rule.getExampleMolecule(false)));
			
		} catch (DecisionMethodException x) {
			x.printStackTrace();
			fail();
		}
	}

}


