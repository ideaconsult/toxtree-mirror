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

import java.util.logging.Level;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import sicret.SicretRules;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;

/**
 * TODO Add SicretRulesTest description
 * 
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-4-30
 */
public class SicretRulesTest extends RulesTestCase {
	protected SmilesParser gen = null;

	/**
	 * 
	 */
	public SicretRulesTest() {
		super();
		try {
			rules = new SicretRules();
			((SicretRules) rules).setResiduesIDVisible(false);
		} catch (DecisionMethodException x) {
			fail();
		}
		gen = new SmilesParser(SilentChemObjectBuilder.getInstance());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * public void testVerifyRule2() { Molecule mol =
	 * MoleculeFactory.makeAlkane(6); try { verifyRule(mol,1); } catch
	 * (DecisionMethodException x) { x.printStackTrace(); assertTrue(false); } }
	 */

	/*
	 * Class under test for int classify(IAtomContainer)
	 */
	@Test
	public void testClassifyMolecule() throws Exception {
		IAtomContainer mol = FunctionalGroups.createAtomContainer(
				"Nc1ccc3c(c1)Cc2cc(ccc23)Br", true);
		/*
		 * mol.setProperty(RuleMeltingPoint.MeltingPoint,"100");
		 * mol.setProperty(RuleLogP.LogKow,"100");
		 * mol.setProperty(RuleLipidSolubility.LipidSolubility,"100");
		 * mol.setProperty("SurfaceTension","100");
		 * mol.setProperty("VapourPressure","100");
		 * mol.setProperty("AqueousSolubility","100");
		 */
		// assign properties
		rules.setParameters(mol);
		classify(mol, rules, rules.getNumberOfClasses());

	}

	protected IAtomContainer getMolecule(String smiles) throws Exception {
		IAtomContainer mol = gen.parseSmiles(smiles);
		MolAnalyser.analyse(mol);
		return mol;
	}

	@Test
	public void testSicret() throws Exception {
		SicretRules rulesNew = (SicretRules) objectRoundTrip(rules,
				"SicretRules");
		rulesNew.setResiduesIDVisible(false);
		rules = rulesNew;
		tryImplementedRules();
	}

	@Test
	public void testPrintSicret() throws Exception {
		logger.log(Level.INFO, new SicretRules().getRules().toString());
	}
}
