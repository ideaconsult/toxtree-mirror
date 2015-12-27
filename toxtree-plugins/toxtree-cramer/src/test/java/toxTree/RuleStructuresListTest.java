/*
Copyright Ideaconsult Ltd. (C) 2005-2009 

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
package toxTree;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.SDFWriter;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.io.Tools;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.tree.cramer.RuleCommonComponentOfFood;
import toxTree.tree.rules.ILookupFile;
import toxTree.tree.rules.InChILookupFile;
import toxTree.tree.rules.RuleStructuresList;
import ambit2.core.io.FileInputState;

/**
 * Test for {@link RuleStructuresList}
 * 
 * @author Nina Jeliazkova <b>Modified</b> 2009-6-17
 */
public class RuleStructuresListTest {
	public static Logger logger = Logger.getLogger(RuleStructuresListTest.class
			.getName());

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testRule() throws Exception {
		RuleStructuresList rule = new RuleStructuresList("bodymol.sdf");
		Assert.assertTrue(rule.isImplemented());
		// default file
		IAtomContainer c = FunctionalGroups
				.createAtomContainer("NC1=NC2=C(NC=N2)C(=O)N1");
		MolAnalyser.analyse(c);
		Assert.assertTrue(rule.verifyRule(c));
	}

	// CSCCCN=C=S
	@Test
	public void testCachedRule() throws Exception {
		RuleCommonComponentOfFood rule = new RuleCommonComponentOfFood(
				new File("foodmol.sdf"));
		Assert.assertTrue(rule.isImplemented());
		// default file
		IAtomContainer c = FunctionalGroups.createAtomContainer("O=C(C(=O)C)C");
		for (int i = 0; i < 2; i++) {
			MolAnalyser.analyse(c);
			boolean b = rule.verifyRule(c);
			Assert.assertTrue(b);
		}
	}

	// MDL Writer assumes properties are of type String
	@Test
	public void testMDLWriterWriteProperties() throws Exception {

		SDFWriter writer = new SDFWriter(new FileOutputStream(new File(
				"bodymol.test.sdf")));
		SmilesGenerator gen = SmilesGenerator.generic();

		// adenine NC1=C2N=CN=C2N=CN1
		// guanine NC1=NC2=C(NC=N2)C(=O)N1
		IAtomContainer c = FunctionalGroups
				.createAtomContainer("NC1=NC2=C(NC=N2)C(=O)N1");

		MolAnalyser.analyse(c);
		c.setProperty("SMILES", gen.create((IAtomContainer) c));
		writer.write(c);
		System.out.print(c.getProperty("NAME"));
		System.out.print("\t");
		System.out.print(c.getAtomCount());
		System.out.print("\t");
		System.out.println(c.getBondCount());

	}

	@Test
	public void testBug() throws Exception {

		SDFWriter writer = new SDFWriter(new FileOutputStream(new File(
				"bodymol.test.sdf")));
		SmilesGenerator gen = SmilesGenerator.generic();
		IAtomContainer c = FunctionalGroups
				.createAtomContainer("NC1=NC2=C(NC=N2)C(=O)N1");
		MolAnalyser.analyse(c);

		IIteratingChemObjectReader reader;

		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("bodymol.sdf");
		reader = new IteratingSDFReader(in,
				SilentChemObjectBuilder.getInstance());
		int r = 0;
		boolean ok = false;
		while (reader.hasNext()) {
			r++;
			Object o = reader.next();

			if (o instanceof IAtomContainer) {
				IAtomContainer m = (IAtomContainer) o;
				try {
					MolAnalyser.analyse(m);
					// hadder.addExplicitHydrogensToSatisfyValency(m);
					if (FunctionalGroups.isSubstance(m, c)) {
						ok = true;
						;
					} else {
						System.out.print(m.getProperty("NAME"));
						System.out.print("\t");
						System.out.print(m.getAtomCount());
						System.out.print("\t");
						System.out.println(m.getBondCount());

					}
					m.setProperty("SMILES", gen.create(m));
					writer.write(m);
				} catch (Exception x) {
					x.printStackTrace();
				}
			}
		}
		writer.close();
		reader.close();
		Assert.assertTrue(ok);

	}

	@Test
	public void testBodyMol() throws Exception {
		ILookupFile lookup = verifyFile("bodymol.sdf");
		Assert.assertEquals(403, lookup.size());
		System.out.println(lookup);
	}

	@Test
	public void testBodyMolInchi() throws Exception {
		ILookupFile lookup = new InChILookupFile(
				Tools.getFileFromResourceSilent("bodymol.inchi"));
		lookup = verifyFile(lookup, "bodymol.sdf");
		Assert.assertEquals(398, lookup.size());

	}

	@Test
	public void testFoodMol() throws Exception {
		ILookupFile lookup = verifyFile("foodmol.sdf");
		// System.out.println(lookup);
		Assert.assertEquals(104, lookup.size());
	}

	@Test
	public void testFoodMolInChi() throws Exception {
		ILookupFile lookup = new InChILookupFile(
				Tools.getFileFromResourceSilent("foodmol.inchi"));
		lookup = verifyFile(lookup, "foodmol.sdf");
		// System.out.println(lookup);
		Assert.assertEquals(104, lookup.size());
	}

	protected ILookupFile verifyFile(String filename) throws Exception {
		return verifyFile(null, filename);
	}

	protected ILookupFile verifyFile(ILookupFile lookupFile, String filename)
			throws Exception {
		URL url = getClass().getClassLoader().getResource(filename);
		ILookupFile lookup = lookupFile == null ? new InChILookupFile(new File(
				url.getFile())) : lookupFile;
		InputStream stream = null;
		IIteratingChemObjectReader<IAtomContainer> reader = null;
		try {
			stream = getClass().getClassLoader().getResourceAsStream(filename);
			reader = FileInputState.getReader(stream, filename);
			while (reader.hasNext()) {
				IAtomContainer ac = reader.next();
				AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(ac);
				MolAnalyser.analyse(ac);
				Object title = ac.getProperty(CDKConstants.TITLE);
				// if (title !=null) System.out.println(title);
				Assert.assertTrue(title == null ? "" : title.toString(),
						lookup.find(ac));
			}
		} catch (Exception x) {
			throw x;
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception x) {
			}
			try {
				if (stream != null)
					stream.close();
			} catch (Exception x) {
			}
		}
		return lookup;
	}

	/*
	 * protected void verifyFile(String filename) throws Exception { IChemFile m
	 * = null; InputStream in =
	 * getClass().getClassLoader().getResourceAsStream(filename); MDLReader
	 * reader = new MDLReader(in); m = (IChemFile)reader.read((IChemObject)new
	 * ChemFile()); reader.close();
	 * 
	 * List c = ChemFileManipulator.getAllAtomContainers(m);
	 * 
	 * Assert.assertNotNull(c); int okCount = 0; int allCount = 0; try {
	 * 
	 * for (int i=0;i < c.size();i++) { IAtomContainer a = (IAtomContainer
	 * )c.get(i); MolAnalyser.analyse(a); } for (int i=0;i < c.size();i++) { if
	 * (((IAtomContainer)c.get(i)).getAtomCount() == 0) break; allCount++;
	 * IAtomContainer a = (IAtomContainer ) ((IAtomContainer )
	 * c.get(i)).clone(); if (a.getProperty("NAME") != null)
	 * a.setID(a.getProperty("NAME").toString()); else if
	 * (a.getProperty("TITLE") != null)
	 * a.setID(a.getProperty("TITLE").toString());
	 * 
	 * MolAnalyser.analyse(a);
	 * 
	 * boolean ok = false;
	 * 
	 * for (int j=0;j < c.size();j++) { IAtomContainer a1 = (IAtomContainer
	 * )c.get(i); if (a.getAtomCount() != a1.getAtomCount()) {
	 * logger.debug("Atom count does not match at record\t"
	 * +Integer.toString(j),"\t", Integer.toString(a.getAtomCount()),"\t",
	 * Integer.toString(a1.getAtomCount())); continue; } if (a.getBondCount() !=
	 * a1.getBondCount()) {
	 * logger.debug("Bond count does not match at record\t"+
	 * Integer.toString(j),"\t", Integer.toString(a.getBondCount()),"\t",
	 * Integer.toString(a1.getBondCount())); continue; } try { if
	 * (UniversalIsomorphismTester.isIsomorph(a,a1)) { ok = true;
	 * logger.info("Found\t"
	 * +a.getProperty("NAME")+"\tatoms\t"+a.getAtomCount()+"\tbonds\t"
	 * +a.getBondCount());
	 * //System.out.println(FunctionalGroups.mapToString(a));
	 * //System.out.println(FunctionalGroups.mapToString(a1)); } else if
	 * (a.getProperty("NAME").equals(a1.getProperty("NAME"))) {
	 * logger.info("NOT Found\t"
	 * +a.getProperty("NAME")+"\tatoms\t"+a.getAtomCount
	 * ()+"\tbonds\t"+a.getBondCount());
	 * 
	 * //System.out.println(FunctionalGroups.mapToString(a)); //printBonds(a);
	 * //System.out.println(FunctionalGroups.mapToString(a1));
	 * 
	 * //printBonds(a1); } } catch (CDKException x) { //x.printStackTrace(); } }
	 * if (ok) okCount++; if (!ok) {
	 * 
	 * 
	 * 
	 * } }
	 * 
	 * } catch (Exception x) { //x.printStackTrace();
	 * 
	 * }
	 * 
	 * Assert.assertEquals(okCount,allCount);
	 * 
	 * }
	 */
	protected void printBonds(AtomContainer m) {
		for (int i = 0; i < m.getBondCount(); i++)
			if (m.getBond(i).getFlag(CDKConstants.ISAROMATIC))
				System.out.println("Aromatic bond\t" + i + "\t");
		System.out.println("\n");

	}
}
