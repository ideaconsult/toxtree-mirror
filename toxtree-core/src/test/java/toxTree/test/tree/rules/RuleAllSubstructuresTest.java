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

import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.LoggingTool;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.RuleAllSubstructures;

/**
 * TODO add description
 * @author Vedina
 * <b>Modified</b> 2005-8-19
 */
public class RuleAllSubstructuresTest extends TestCase {
	public static LoggingTool logger = new LoggingTool(RuleAllSubstructures.class);
	public static void main(String[] args) {
		junit.textui.TestRunner.run(RuleAllSubstructuresTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		LoggingTool.configureLog4j();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for RuleAllSubstructuresTest.
	 * @param arg0
	 */
	public RuleAllSubstructuresTest(String arg0) {
		super(arg0);
	}
	public void testRule() {
		RuleAllSubstructures rule = new RuleAllSubstructures();
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
			rule.addSubstructure(MoleculeFactory.makeBenzene());
			assertFalse(rule.verifyRule(mol));
		} catch (DecisionMethodException x) {
			fail();
		}
		
		SmilesParser sp = new SmilesParser(SilentChemObjectBuilder.getInstance());
		try {
			mol = sp.parseSmiles("c1c(CCCCCC)cccc1");
			
			logger.debug("Molecule is aromatic\t");
			AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(mol);

			logger.debug(Boolean.toString(CDKHueckelAromaticityDetector.detectAromaticity(mol)));
		} catch (InvalidSmilesException x) {
			fail();
		} catch (CDKException x) {
			fail();
		}
		
		try {
			RuleAllSubstructures rule1 = new RuleAllSubstructures();
			rule1.addSubstructure(MoleculeFactory.makeAlkane(3));
			IAtomContainer q = MoleculeFactory.makeBenzene();
			logger.debug("\nSubstructure is aromatic\t");
			AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(q);

			logger.debug(Boolean.toString(CDKHueckelAromaticityDetector.detectAromaticity(q)));
			rule1.addSubstructure(q);
			assertTrue(rule1.verifyRule(mol));
		} catch (DecisionMethodException x) {
			fail();
		} catch (CDKException x) {
			fail();
		}		
	}

}
