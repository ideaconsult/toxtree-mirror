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
package toxtree.test.plugins.smartcyp;

import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.FixBondOrdersTool;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.exceptions.DecisionResultException;
import toxTree.exceptions.MolAnalyseException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxtree.plugins.smartcyp.SMARTCYPPlugin;
import ambit2.core.io.FileInputState;

/**
 * TODO Add SMARTCypRulesTest description
 * 
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-4-30
 */
public class SMARTCypRulesTest extends RulesTestCase {
	protected SmilesParser gen = null;

	/**
	 * 
	 */
	public SMARTCypRulesTest() throws Exception {
		super();
		rules = new SMARTCYPPlugin();
		((SMARTCYPPlugin) rules).setResiduesIDVisible(false);
		gen = new SmilesParser(SilentChemObjectBuilder.getInstance());

	}

	/*
	 * Class under test for int classify(IMolecule)
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

	protected IAtomContainer getMolecule(String smiles) {
		try {
			IAtomContainer mol = gen.parseSmiles(smiles);
			MolAnalyser.analyse(mol);
			return mol;
		} catch (InvalidSmilesException x) {
			x.printStackTrace();
			return null;
		} catch (MolAnalyseException x) {
			x.printStackTrace();
			return null;
		}
	}

	@Test
	public void testSMARTCypSerialization() throws Exception {
		SMARTCYPPlugin rulesNew = (SMARTCYPPlugin) objectRoundTrip(rules,
				"SMARTCYPPlugin");
		rulesNew.setResiduesIDVisible(false);
		rules = rulesNew;
		tryImplementedRules();
	}

	@Test
	public void testPrintSmartCyp() throws Exception {
		System.out.println(new SMARTCYPPlugin().getRules());
	}

	/**
	 * https://sourceforge.net/tracker/?func=detail&aid=3389084&group_id=152702&
	 * atid=785126
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_3389084() throws Exception {
		rules = new SMARTCYPPlugin();
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"toxtree/test/plugins/smartcyp/bug3389084.sdf");
		IIteratingChemObjectReader reader = FileInputState
				.getReader(in, ".sdf");
		while (reader.hasNext()) {
			IAtomContainer mol = (IAtomContainer) reader.next();
			try {
				for (IAtom atom : mol.atoms())
					System.out.println(atom.getClass().getName());
				// EquivalentClassPartitioner partitioner = new
				// EquivalentClassPartitioner(mol);
				classify(mol, rules, rules.getCategories().size());
			} catch (DecisionResultException x) {
				Assert.assertEquals("PseudoAtoms are not supported! R", x
						.getCause().getMessage());
			}
		}
		reader.close();

	}

	protected void printAtom(IAtom atom) {
		System.out
				.println(String
						.format("AtomTypeName=%10s\tAtomicNum=%d\tBondOrderSum=%f\tHybridisation=%s\tImplicitH=%d\tIsAromatic=%s",
								atom.getAtomTypeName(), atom.getAtomicNumber(),
								atom.getBondOrderSum(),
								atom.getHybridization(),
								atom.getImplicitHydrogenCount(),
								atom.getFlag(CDKConstants.ISAROMATIC)));
	}

	/**
	 * http://sourceforge.net/tracker/?func=detail&aid=3426327&group_id=152702&
	 * atid=785126
	 * 
	 * @throws Exception
	 */

	public void test_3426327() throws Exception {
		// rules = new SMARTCYPPlugin();
		FixBondOrdersTool fbt = new FixBondOrdersTool();
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"toxtree/test/plugins/smartcyp/bad.sdf");
		IIteratingChemObjectReader reader = new IteratingSDFReader(in,
				SilentChemObjectBuilder.getInstance());
		SmilesGenerator g = new SmilesGenerator();
		g.setUseAromaticityFlag(false);
		try {
			while (reader.hasNext()) {
				IAtomContainer mol = (IAtomContainer) reader.next();
				int aromaticAtoms = 0;
				int doubleBonds = 0;
				for (IAtom atom : mol.atoms()) {
					if (atom.getFlag(CDKConstants.ISAROMATIC))
						aromaticAtoms++;

				}
				for (IBond bond : mol.bonds()) {
					switch (bond.getOrder()) {
					case DOUBLE: {
						doubleBonds++;
						break;
					}
					}
				}
				Assert.assertEquals(3, doubleBonds);
				Assert.assertEquals(9, aromaticAtoms);
				// kekulise
				// CDKHydrogenAdder hadder =
				// CDKHydrogenAdder.getInstance(SilentChemObjectBuilder.getInstance());

				AtomContainerManipulator
						.percieveAtomTypesAndConfigureUnsetProperties(mol);
				mol = fbt.kekuliseAromaticRings(mol);
				for (IAtom atom : mol.atoms()) {
					printAtom(atom);
				}
				aromaticAtoms = 0;
				doubleBonds = 0;
				for (IAtom atom : mol.atoms())
					if (atom.getFlag(CDKConstants.ISAROMATIC))
						aromaticAtoms++;
				for (IBond bond : mol.bonds()) {
					switch (bond.getOrder()) {
					case DOUBLE: {
						doubleBonds++;
						break;
					}
					}
				}

				System.out.println(g.createSMILES(mol));
				System.out.println(doubleBonds);
				Assert.assertTrue(doubleBonds > 6);

			}
		} finally {
			reader.close();
		}
	}

	@Test
	public void test_3426327_SMILES() throws Exception {
		rules = new SMARTCYPPlugin();
		FixBondOrdersTool fbt = new FixBondOrdersTool();

		String smiles = "c1nc(c2c(n1)n(cn2)[C@H]3[C@@H]([C@@H]([C@H](O3)CO[P@@](=O)(O)O[P@@](=O)(O)OP(=O)(O)O)O)O)N";
		SmilesParser parser = new SmilesParser(
				SilentChemObjectBuilder.getInstance());

		IAtomContainer mol = parser.parseSmiles(smiles);
		int aromaticAtoms = 0;
		int doubleBonds = 0;
		for (IAtom atom : mol.atoms()) {
			if (atom.getFlag(CDKConstants.ISAROMATIC))
				aromaticAtoms++;
			printAtom(atom);
		}
		for (IBond bond : mol.bonds()) {
			switch (bond.getOrder()) {
			case DOUBLE: {
				doubleBonds++;
				break;
			}
			}
		}
		Assert.assertEquals(3, doubleBonds);
		Assert.assertEquals(9, aromaticAtoms);
		// kekulise
		AtomContainerManipulator
				.percieveAtomTypesAndConfigureUnsetProperties(mol);
		mol = fbt.kekuliseAromaticRings(mol);
		aromaticAtoms = 0;
		doubleBonds = 0;
		for (IAtom atom : mol.atoms())
			if (atom.getFlag(CDKConstants.ISAROMATIC))
				aromaticAtoms++;
		for (IBond bond : mol.bonds()) {
			switch (bond.getOrder()) {
			case DOUBLE: {
				doubleBonds++;
				break;
			}
			}
		}

		System.out.println(doubleBonds);
		Assert.assertTrue(doubleBonds > 6);

	}
}
