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

import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.RuleAnySubstructure;

/**
 * TODO add description
 * @author Vedina
 * <b>Modified</b> 2005-8-19
 */
public class RuleAnySubstructureTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(RuleAnySubstructureTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for RuleAnySubstructureTest.
	 * @param arg0
	 */
	public RuleAnySubstructureTest(String arg0) {
		super(arg0);
	}
	public void testRuleAnySubstructure() {
		RuleAnySubstructure rule = new RuleAnySubstructure();
		rule.addSubstructure(MoleculeFactory.makeAlkane(3));
		IAtomContainer mol = MoleculeFactory.makeAlkane(20);
		
		try {
			assertTrue(rule.verifyRule(mol));
		} catch (DecisionMethodException x) {
			fail();
		}
		mol = MoleculeFactory.makeBenzene();
		try {
			assertFalse(rule.verifyRule(mol));
		} catch (DecisionMethodException x) {
			fail();
		}
		
		SmilesParser sp = new SmilesParser(SilentChemObjectBuilder.getInstance());
		try {
			mol = sp.parseSmiles("CCCCCc1ccccc1");
		} catch (InvalidSmilesException x) {
			fail();
		}
		try {
			assertTrue(rule.verifyRule(mol));
			rule.addSubstructure(MoleculeFactory.makeBenzene());
			assertTrue(rule.verifyRule(mol));
		} catch (DecisionMethodException x) {
			fail();
		}		
	}
	
}
