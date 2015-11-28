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

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.LoggingTool;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.tree.rules.RuleAllSubstructures;
import ambit2.core.helper.CDKHueckelAromaticityDetector;


public class RuleAllSubstructuresTest {

	@BeforeClass
	public static void setUp() throws Exception {
		LoggingTool.configureLog4j();
	}

	public void testRule() throws Exception {
		RuleAllSubstructures rule = new RuleAllSubstructures();
		rule.addSubstructure(MoleculeFactory.makeAlkane(3));
		IAtomContainer mol = MoleculeFactory.makeAlkane(20);

		Assert.assertTrue(rule.verifyRule(mol));

		mol = MoleculeFactory.makeBenzene();

		Assert.assertFalse(rule.verifyRule(mol));
		rule.addSubstructure(MoleculeFactory.makeBenzene());
		Assert.assertFalse(rule.verifyRule(mol));

		SmilesParser sp = new SmilesParser(
				SilentChemObjectBuilder.getInstance());
		mol = sp.parseSmiles("c1c(CCCCCC)cccc1");

		AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(mol);

		CDKHueckelAromaticityDetector.detectAromaticity(mol);

		RuleAllSubstructures rule1 = new RuleAllSubstructures();
		rule1.addSubstructure(MoleculeFactory.makeAlkane(3));
		IAtomContainer q = MoleculeFactory.makeBenzene();
		AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(q);

		CDKHueckelAromaticityDetector.detectAromaticity(q);
		rule1.addSubstructure(q);
		Assert.assertTrue(rule1.verifyRule(mol));

	}
}
