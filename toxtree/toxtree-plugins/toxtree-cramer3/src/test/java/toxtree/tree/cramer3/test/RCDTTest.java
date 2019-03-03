/*
Copyright Ideaconsult Ltd. (C) 2005-2019 

Contact: support@idewaconsult.net

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
package toxtree.tree.cramer3.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.core.IDecisionRule;
import toxTree.cramer.RulesTestCase;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolAnalyser;
import toxtree.tree.cramer3.RevisedCramerDecisionTree;

/**
 * TODO Add CramerRulesExtendedTest description
 * 
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-4-30
 */
public class RCDTTest extends RulesTestCase {
	protected SmilesParser gen = null;

	/**
	 * 
	 */
	public RCDTTest() throws Exception {
		super();
		try {
			rules = new RevisedCramerDecisionTree();
			// ((RevisedCramerDecisionTree)rules).setResiduesIDVisible(false);
		} catch (DecisionMethodException x) {
			throw x;
		}
		gen = new SmilesParser(SilentChemObjectBuilder.getInstance());

	}

	@Test
	public void testVerifyRule2() throws Exception {
		IAtomContainer mol = MoleculeFactory.makeAlkane(6);
		verifyRule(mol, 2);

	}

	/*
	 * Class under test for int classify(Molecule)
	 */
	@Test
	public void testClassifyMolecule() throws Exception {
		IAtomContainer mol = MoleculeFactory.makeAlkane(6);
		classify(mol, rules, 3);

	}

	protected IAtomContainer getMolecule(String smiles) throws Exception {
		IAtomContainer mol = gen.parseSmiles(smiles);
		MolAnalyser.analyse(mol);
		return mol;
	}

	@Test
	public void testCramer() throws Exception {
		RevisedCramerDecisionTree rulesNew = (RevisedCramerDecisionTree) objectRoundTrip(rules, "RCDT");
		// rulesNew.setResiduesIDVisible(false);
		rules = rulesNew;
		tryImplementedRules();
	}

	@Test
	public void testPrintCramer() throws Exception {
		System.out.println(new RevisedCramerDecisionTree().getRules());
	}
	@Test
	public void testSerializable() throws Exception {
		int nr = rules.getNumberOfRules();

		File f = File.createTempFile("rule", "test");
		for (int i = 0; i < nr; i++) {
			IDecisionRule rule = rules.getRule(i);
			
			try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
				os.writeObject(rule);
			} catch (Exception x) {
				logger.severe(rule.toString());
				throw x;
			}
		}
		
	}
}
