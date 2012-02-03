/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package toxTree.test.tree.rules;

import junit.framework.TestCase;

import org.openscience.cdk.Molecule;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.logging.TTLogger;
import toxTree.query.MolAnalyser;
import toxTree.tree.rules.RuleAllAllowedElements;
import toxTree.tree.rules.RuleElements;

public class RuleAllAllowedElementsTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(RuleAllAllowedElementsTest.class);
	}
	@Override
	protected void setUp() throws Exception {
		
		super.setUp();
		TTLogger.configureLog4j(true);
	}

	/*
	 * Test method for 'toxTree.tree.rules.RuleAllAllowedElements.verifyRule(IAtomContainer)'
	 */
	public void testVerifyRule() {
		RuleAllAllowedElements rule = new RuleAllAllowedElements();
		rule.addElement("C");
		rule.addElement("N");

		
		Molecule m1= MoleculeFactory.makeAlkane(3);  //C only

		try {
			Molecule m2= MoleculeFactory.makePyrrole(); //C & N
			
			//no hydrogens
			rule.setComparisonMode(RuleElements.modeAllSpecifiedElements);
			assertTrue(rule.verifyRule(m2));
			assertFalse(rule.verifyRule(m1));
			
			rule.addElement("H");
			//with hydrogens
			MolAnalyser.analyse(m1);
			MolAnalyser.analyse(m2);

			
			assertTrue(rule.verifyRule(m2));
			assertFalse(rule.verifyRule(m1));

			rule.setComparisonMode(RuleElements.modeAnySpecifiedElements);
			assertTrue(rule.verifyRule(m2));
			assertTrue(rule.verifyRule(m1)); // doesn't have N
			
			rule.setComparisonMode(RuleElements.modeOnlySpecifiedElements);
			assertTrue(rule.verifyRule(m2));
			assertTrue(rule.verifyRule(m1)); // doesn't have N
			
			RuleAllAllowedElements rule1 = new RuleAllAllowedElements();
			rule1.addElement("C");
			rule1.setComparisonMode(RuleElements.modeOnlySpecifiedElements);
			assertTrue(rule1.verifyRule(m1));
			assertFalse(rule1.verifyRule(m2));
			
			rule1.addElement("Cl");
			assertTrue(rule1.verifyRule(m1));
			assertFalse(rule1.verifyRule(m2));			
			
			rule1.setComparisonMode(RuleElements.modeAnySpecifiedElements);
			assertTrue(rule1.verifyRule(m1));
			assertTrue(rule1.verifyRule(m2));
			
			rule1.setComparisonMode(RuleElements.modeAllSpecifiedElements);
			assertFalse(rule1.verifyRule(m1));
			assertFalse(rule1.verifyRule(m2));
			
		} catch (Exception x) {
			x.printStackTrace();
			fail();
		}
		
	}

}


