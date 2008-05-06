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
import sicret.rules.RuleHalogenatedAlkanesAndAlkenes;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;

public class RuleHalogenatedAlkanesAndAlkenesTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(RuleHalogenatedAlkanesAndAlkenesTest.class);
	}

	/*
	 * Test method for 'toxTree.tree.rules.smarts.RuleSMARTSubstructure.verifyRule(IAtomContainer)'
	 */
	public void testVerifyRule() {
		RuleHalogenatedAlkanesAndAlkenes rule = new RuleHalogenatedAlkanesAndAlkenes();
		try {
			assertTrue(rule.verifyRule(rule.getExampleMolecule(true)));
			assertFalse(rule.verifyRule(rule.getExampleMolecule(false)));
			boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer("ClC(Cl)(Cl)C",true));
			
			assertTrue(result);
		} catch (DecisionMethodException x) {
			x.printStackTrace();
			fail();
		}
	}

}


