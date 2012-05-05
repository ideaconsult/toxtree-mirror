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
package toxTree.test.tree;

import junit.framework.TestCase;

import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.DecisionNode;
import toxTree.tree.demo.SubstructureTree;
import toxTree.tree.rules.RuleAnySubstructure;

public class SubstructureTreeTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(SubstructureTreeTest.class);
	}

	public SubstructureTreeTest(String arg0) {
		super(arg0);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'toxTree.tree.demo.SubstructureTree.SubstructureTree()'
	 */
	public void testSubstructureTree() {
		try {
			SubstructureTree tree = new SubstructureTree();
			
			IDecisionRule topRule = tree.getTopRule();
			assertNotNull(topRule);
			assertTrue(topRule instanceof DecisionNode);
			DecisionNode node = (DecisionNode)topRule;
			assertTrue(node.getRule() instanceof RuleAnySubstructure);
			
			assertEquals(1,((RuleAnySubstructure) node.getRule()).getSubstructuresCount());
			
			IAtomContainer fragment = ((RuleAnySubstructure) node.getRule()).getSubstructure(0);
			IAtomContainer benzene = MoleculeFactory.makeBenzene();
			AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(benzene);
			CDKHueckelAromaticityDetector.detectAromaticity(benzene);
			
			//IAtomContainer hexane = MoleculeFactory.makeAlkane(6);
			assertTrue(UniversalIsomorphismTester.isIsomorph(fragment,benzene));
			
			IDecisionResult result = tree.createDecisionResult();
			//IAtomContainer mol = FunctionalGroups.createAtomContainer("c1ccccc1", true);
			result.classify(benzene);
			assertEquals(node.getCategory(true),result.getCategory());
			
			IAtomContainer mol = MoleculeFactory.makeAlkane(1);
			result.classify(mol);
			assertEquals(node.getCategory(false),result.getCategory());
			
		} catch (CDKException x) {
			fail();
			
		} catch (DecisionResultException x) {
			
			fail();
		} catch (DecisionMethodException x) {
			fail();
		}
	}

}


