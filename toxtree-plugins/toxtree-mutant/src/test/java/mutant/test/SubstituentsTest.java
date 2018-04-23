/*
Copyright Ideaconsult Ltd. (C) 2005-2007  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
 */

package mutant.test;

import java.io.InputStream;
import java.io.StringReader;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;
import mutant.descriptors.AromaticAmineSubstituentsDescriptor;
import mutant.descriptors.SubstituentExtractor;

import org.junit.Test;
import org.openscience.cdk.Bond;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.fingerprint.Fingerprinter;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.io.MDLV2000Reader;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.data.MoleculesFile;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import ambit2.core.data.MoleculeTools;

public class SubstituentsTest {
	protected static Logger logger = Logger.getLogger(SubstituentsTest.class
			.getName());

	@Test
	public void testFingerprint() throws Exception {
		// IAtomContainer a =
		// (IAtomContainer)FunctionalGroups.createAtomContainer("[*][N+](=O)[O-]",
		// false);
		IAtomContainer a = getfromSDF();
		for (int i = 0; i < a.getAtomCount(); i++)
			logger.log(Level.FINER, a.getAtom(i).getFormalCharge().toString());
		SmilesGenerator g = SmilesGenerator.unique();
		String smiles = g.create(a);
		logger.log(Level.FINE, smiles);
		Fingerprinter fp = new Fingerprinter(1024);
		BitSet bs = fp.getBitFingerprint(a).asBitSet();
		MolAnalyser.analyse(a);
		smiles = g.create(a);
		logger.log(Level.FINE, smiles);
		BitSet bs1 = fp.getBitFingerprint(a).asBitSet();
		Assert.assertEquals(bs, bs1);
	}

	public IAtomContainer getfromSDF() throws Exception {
		StringBuffer n = new StringBuffer();
		n.append("\n");
		n.append("  CDK    2/26/08,15:51\n");
		n.append("\n");
		n.append("  4  3  0  0  0  0  0  0  0  0999 V2000\n");
		n.append("    0.0000    0.0000    0.0000 R   0  0  0  0  0  0  0  0  0  0  0  0\n");
		n.append("    0.0000    1.5000    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n");
		n.append("   -1.2990    2.2500    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n");
		n.append("    1.2990    2.2500    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n");
		n.append("  1  2  1  0  0  0  0\n");
		n.append("  2  3  2  0  0  0  0\n");
		n.append("  2  4  1  0  0  0  0\n");
		n.append("M  CHG  1   2   1\n");
		n.append("M  CHG  1   4  -1\n");
		n.append("M  END\n");

		MDLV2000Reader reader = new MDLV2000Reader(new StringReader(
				n.toString()));
		IAtomContainer mol = MoleculeTools.newMolecule(SilentChemObjectBuilder
				.getInstance());
		reader.read(mol);
		AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(mol);
		reader.close();
		return mol;
	}

	public void xtest() {
		try {

			// LookupFile lookup = new
			// LookupFile("/src/mutant/descriptors/substituents.sdf");
			// LookupFile lookup = new LookupFile("substituents.sdf");
			MoleculesFile lookup = new MoleculesFile("substituents.sdf",
					SilentChemObjectBuilder.getInstance(), null);
			// lookup.setUseCache(false);
			// lookup.setCheckAromaticity(false);

			long now;
			IAtomContainer a;

			/*
			 * a = FunctionalGroups.createAtomContainer("c", true); now =
			 * System.currentTimeMillis(); assertTrue(lookup.find(a));
			 * System.out.println(System.currentTimeMillis()-now);
			 */
			a = FunctionalGroups
					.createAtomContainer(
							"[*]C(C1=C([H])C([H])=C([H])C([H])=C1([H]))(C2=C([H])C([H])=C([H])C([H])=C2([H]))C3=C([H])C([H])=C([H])C([H])=C3([H])",
							true);
			MolAnalyser.analyse(a);
			now = System.currentTimeMillis();
			Assert.assertTrue(lookup.find((IAtomContainer) a) > -1);
			logger.log(Level.FINE,
					Long.toString(System.currentTimeMillis() - now));

			a = FunctionalGroups.createAtomContainer("[*]C1CCC1", true);
			MolAnalyser.analyse(a);
			now = System.currentTimeMillis();
			Assert.assertTrue(lookup.find((IAtomContainer) a) > -1);
			logger.log(Level.FINE,
					Long.toString(System.currentTimeMillis() - now));

			a = FunctionalGroups.createAtomContainer("[*]P(=O)(F)F", true);
			MolAnalyser.analyse(a);
			now = System.currentTimeMillis();
			Assert.assertTrue(lookup.find((IAtomContainer) a) > -1);
			logger.log(Level.FINE,
					Long.toString(System.currentTimeMillis() - now));

			a = FunctionalGroups.createAtomContainer(
					"[*]C=1C=CC2=CC=CC=C2(C=1)", true);
			MolAnalyser.analyse(a);
			now = System.currentTimeMillis();
			Assert.assertTrue(lookup.find((IAtomContainer) a) > -1);
			logger.log(Level.FINE,
					Long.toString(System.currentTimeMillis() - now));

			a = MoleculeTools.newAtomContainer(SilentChemObjectBuilder
					.getInstance());
			IAtom a1 = MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.OXYGEN);
			IAtom a2 = MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), "R");
			IAtom a3 = MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.HYDROGEN);
			a.addAtom(a1);
			a.addAtom(a2);
			a.addAtom(a3);
			a.addBond(new Bond(a1, a2, CDKConstants.BONDORDER_SINGLE));
			a.addBond(new Bond(a1, a3, CDKConstants.BONDORDER_SINGLE));
			MolAnalyser.analyse(a);
			// a = FunctionalGroups.createAtomContainer("[*]O", true);
			// HueckelAromaticityDetector.detectAromaticity(a);
			now = System.currentTimeMillis();
			int index = lookup.find((IAtomContainer) a);
			if (index > -1) {
				IAtomContainer mol = lookup.getAtomContainer(index);
				Assert.assertNotNull(mol);
				logger.log(Level.FINE,
						Long.toString(System.currentTimeMillis() - now));
				Assert.assertEquals(0.28,
						Double.parseDouble(mol.getProperty("MR").toString()));
			} else
				Assert.fail("not found");

		} catch (Exception x) {
			logger.log(Level.SEVERE, x.getMessage(), x);
			Assert.fail(x.getMessage());
		}
	}

	@Test
	public void testAll() throws Exception {
		// LookupFile lookup = new
		// LookupFile("plugins/mutant/src/mutant/descriptors/substituents.sdf");
		MoleculesFile lookup = new MoleculesFile("substituents.sdf",
				SilentChemObjectBuilder.getInstance(), null);
		/*
		 * LookupFile lookup = new LookupFile("substituents.sdf");
		 * lookup.setUseCache(false); lookup.setCheckAromaticity(true);
		 */
		SmilesGenerator g = SmilesGenerator.unique();
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"substituents.sdf");
		IteratingSDFReader reader = new IteratingSDFReader(in,
				SilentChemObjectBuilder.getInstance());
		int record = 0;
		int found_records = 0;
		while (reader.hasNext()) {
			IAtomContainer o = reader.next();
			AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(o);

			long now = System.currentTimeMillis();

			if (lookup.find(o) > -1) {
				logger.log(Level.FINE,
						Long.toString(System.currentTimeMillis() - now));
				found_records++;
			} else {
				StringBuilder b = new StringBuilder();
				b.append(record);
				b.append('\t');
				Object val = ((IAtomContainer) o).getProperty("#");
				b.append(val==null?"":val.toString());
				b.append('\t');
				val = ((IAtomContainer) o).getProperty("SMILES");
				b.append(val==null?"":val.toString());
				b.append('\t');
				val = ((IAtomContainer) o).getProperty("Group");
				b.append(val==null?"":val.toString());
				b.append('\t');
				b.append("B5STM\t");
				val = ((IAtomContainer) o).getProperty("B5STM");
				b.append(val==null?"":val.toString());
				b.append("B1STM\t");
				val = ((IAtomContainer) o).getProperty("B1STM");
				b.append(val==null?"":val.toString());
				b.append("MR\t");

				val = ((IAtomContainer) o).getProperty("MR");
				b.append(val==null?"":val.toString());
				b.append("\nFingerprint\t");
				b.append(lookup.getFingerprint((IAtomContainer) o));
				b.append("\nSMILES generated ");
				b.append(g.create(o));
				b.append("\nFingerprint in file\t");
				b.append(lookup.getProperty(record,
						MoleculesFile.propertyFingerprint));

				b.append('\n');
				b.append("not found");

				Assert.fail(b.toString());
			}

			record++;

		}
		reader.close();
		Assert.assertEquals(291, record);
		Assert.assertEquals(record, found_records);
	}

	/*
	 * public void test1() { try { QueryAtomContainer q = aromaticAmine();
	 * //P(=O)(F)F IAtomContainer a =
	 * FunctionalGroups.createAtomContainer("c1(CC)c(O)c(Cl)c(P(=O)(F)F)c(N)c(S)1"
	 * , true); //IAtomContainer a =
	 * FunctionalGroups.createAtomContainer("c1ccc(P(=O)(F)F)c(N)c1", true);
	 * HueckelAromaticityDetector.detectAromaticity(a); if (markAtomsInRing(a,
	 * q)) { IAtom anchor = null; for (int i=0; i < a.getAtomCount();i++) {
	 * IAtom atom = a.getAtom(i); System.out.println(atom.getSymbol() + "\t" +
	 * atom.getProperty(FunctionalGroups.RING_NUMBERING)); // if
	 * ("O".equals(atom.getSymbol()))
	 * assertEquals(3,atom.getProperty(FunctionalGroups.RING_NUMBERING)); if
	 * ("Cl".equals(atom.getSymbol()))
	 * assertEquals(2,atom.getProperty(FunctionalGroups.RING_NUMBERING)); if
	 * ("P".equals(atom.getSymbol()))
	 * assertEquals(1,atom.getProperty(FunctionalGroups.RING_NUMBERING)); if
	 * ("S".equals(atom.getSymbol()))
	 * assertEquals(1,atom.getProperty(FunctionalGroups.RING_NUMBERING)); if
	 * ("C".equals(atom.getSymbol()) &&
	 * (atom.getProperty(FunctionalGroups.RING_NUMBERING) != null) &&
	 * atom.getProperty(FunctionalGroups.RING_NUMBERING).equals(1)) { anchor =
	 * atom; }
	 * 
	 * } assertNotNull(anchor); SSSRFinder ssrf = new SSSRFinder(a); IRingSet rs
	 * = ssrf.findSSSR(); rs = rs.getRings(anchor); for (int r=0; r <
	 * rs.getAtomContainerCount();r++) { IAtomContainer mc =
	 * cloneDiscardRingAtomAndBonds(a,(IRing)rs.getAtomContainer(r));
	 * IAtomContainerSet s = ConnectivityChecker.partitionIntoMolecules(mc);
	 * 
	 * int substituents = enumerateSubstituents(s); assertTrue(substituents>0);
	 * System.out.println("\tsubstituents\t"+substituents); } } else
	 * fail("Aromatic amine not found"); } catch (Exception x) {
	 * x.printStackTrace(); fail(x.getMessage()); } }
	 */
	@Test
	public void testSmiles() throws Exception {
		MoleculesFile lookup = new MoleculesFile("substituents.sdf",
				SilentChemObjectBuilder.getInstance(), null);
		SmilesGenerator g = SmilesGenerator.unique();
		int r = 0;
		for (int i = 0; i < lookup.getAtomContainerCount(); i++) {
			IAtomContainer a = lookup.getAtomContainer(i);
			logger.log(Level.FINE,a.getProperties().toString());
			Object mySmiles = a.getProperty("SMILES");
			if (mySmiles == null)
				continue;
			String newSmiles = g.create(a);
			if (!newSmiles.equals(mySmiles.toString())) {
				r++;
				StringBuilder b = new StringBuilder();
				//b.append(r);
				//b.append("SMILES in file\t");
				//b.append("\t");
				Object val = a.getProperty("#");
				b.append(val==null?"":val.toString());
				b.append("\n");
				b.append(mySmiles);

				b.append("\n");
				b.append(newSmiles);
				logger.log(Level.INFO,b.toString());
			}
		}
		Assert.assertEquals(0, r);
	}

	protected int enumerateSubstituents(IAtomContainerSet s) throws Exception {
		int substituents = 0;

		// LookupFile lookup = new LookupFile("substituents.sdf");
		// lookup.setUseCache(true);
		// lookup.setCheckAromaticity(false);
		MoleculesFile lookup = new MoleculesFile("substituents.sdf",
				SilentChemObjectBuilder.getInstance(), null);
		SmilesGenerator g = SmilesGenerator.unique();
		for (int k = 0; k < s.getAtomContainerCount(); k++) {
			System.out.println("Substituent\t" + k);
			IAtomContainer m = s.getAtomContainer(k);
			String smiles = g.create((IAtomContainer) m);
			System.out.println(smiles);
			for (int a = 0; a < m.getAtomCount(); a++) {
				System.out.print(m.getAtom(a).getSymbol());
				System.out.print('\t');
			}
			System.out.print('\n');
			if (m != null) {
				if ((m.getAtomCount() == 1)
						&& (m.getAtom(0).getSymbol().equals("H")))
					continue;

				Object place = null;
				int[] p = new int[m.getAtomCount()];
				for (int j = 0; j < m.getAtomCount(); j++) {
					p[j] = -1;
					place = m.getAtom(j).getProperty(
							FunctionalGroups.RING_NUMBERING);

					if (place != null) {
						System.out.println("Ring substituent at place\t"
								+ place + "\tatom id\t" + m.getAtom(j).getID()
								+ "\t" + m.getAtom(j).getSymbol() + "\tsize\t"
								+ m.getAtomCount());
						p[j] = ((Integer) place).intValue();
						/*
						 * 
						 * Ring substituent at place 2 C size 8 Ring substituent
						 * at place 3 O size 3 Ring substituent at place 2 Cl
						 * size 2 Ring substituent at place 1 P size 5 Ring
						 * substituent at place 1 S size 3
						 */

						int index = lookup.indexOf("SMILES", smiles);
						// assertTrue(index > -1);
						if (index > -1)
							System.out.println(lookup.getAtomContainer(index)
									.getProperties());
						// assertTrue(lookup.find(m));
						substituents++;
					}
				}
				System.out.print("place ");
				for (int j = 0; j < p.length; j++) {
					System.out.print(p[j]);
					System.out.print('\t');
				}
				System.out.println();

			}
		}
		return substituents;
	}

	@Test
	public void testAromaticAmine() throws Exception {
		QueryAtomContainer q = AromaticAmineSubstituentsDescriptor
				.aromaticAmine(FunctionalGroups.RING_NUMBERING);
		IAtomContainer mol = FunctionalGroups.createAtomContainer(
				"c1ccc(N)cc1", true);
		MolAnalyser.analyse(mol);
		Assert.assertTrue(FunctionalGroups.hasGroup(mol, q));
	}

	@Test
	public void testSubstituentExtractor1() throws Exception {
		substituentExtractor("c2ccc(N)cc2");
	}

	@Test
	public void testSubstituentExtractor2() throws Exception {
		substituentExtractor("N(=Nc1ccc(cc1)N(C)C)c2ccccc2");
	}

	@Test
	public void testSubstituentExtractor() throws Exception {
		substituentExtractor("c1(CC)c(O)c(Cl)c(P(=O)(F)F)c(NC)c(S)1");
	}

	@Test
	public void testSubstituentExtractorFusedRings() throws Exception {
		substituentExtractor("Nc1cccc2c4cccc3cccc(c12)c34");
	}

	@Test
	public void testSubstituentExtractorAnthracene() throws Exception {
		substituentExtractor("Nc1ccc2cc3ccccc3(cc2(c1))");
		// substituentExtractor("NC=2C=CC=C1N=CC=SC1=2");
		// substituentExtractor("NC=2C=CC=C1C=CC=CC1=2");
		// "NC=1C=CC=C2C=1C(=C(C(F)=C2I)Br)Cl");
		// "NC1=CC=CC2=CC=CC=C12");
		// "NC=1C=CC2=CC=CC=C2(C=1)");
	}

	/**
	 * TODO it looks for FunctionalGroups.RING_NUMBERING, while atoms are marked
	 * with FunctionalGroups.RING_NUMBERING_number
	 * 
	 * @param smiles
	 * @throws Exception
	 */
	public void substituentExtractor(String smiles) throws Exception {
		SubstituentExtractor extractor = new SubstituentExtractor(
				AromaticAmineSubstituentsDescriptor
						.aromaticAmine(FunctionalGroups.RING_NUMBERING));
		IAtomContainer a = FunctionalGroups.createAtomContainer(smiles, false);
		Assert.assertNotNull(a);
		MolAnalyser.analyse(a);
		Map<String, IAtomContainerSet> set = extractor.extractSubstituents(a);
		Assert.assertEquals(1, set.size());
		Iterator<IAtomContainerSet> c = set.values().iterator();
		while (c.hasNext())
			Assert.assertEquals(6, enumerateSubstituents(c.next()));

	}

	public void testtodo() {
		Assert.fail("this record was removed from substituents.sdf, since it does not pass isomorph test - verify why");
		/*
		 * CDK 2/26/08,16:56
		 * 
		 * 14 15 0 0 0 0 0 0 0 0999 V2000 1.2135 -0.8817 0.0000 Xx 0 0 0 0 0 0 0
		 * 0 0 0 0 0 0.0000 0.0000 0.0000 N 0 0 0 0 0 0 0 0 0 0 0 0 0.0000
		 * 1.5000 0.0000 N 0 0 0 0 0 0 0 0 0 0 0 0 -1.4266 -0.4635 0.0000 N 0 0
		 * 0 0 0 0 0 0 0 0 0 0 -1.4266 1.9635 0.0000 C 0 0 0 0 0 0 0 0 0 0 0 0
		 * -2.3083 0.7500 0.0000 C 0 0 0 0 0 0 0 0 0 0 0 0 -2.0367 3.3338 0.0000
		 * C 0 0 0 0 0 0 0 0 0 0 0 0 -3.8000 0.9068 0.0000 C 0 0 0 0 0 0 0 0 0 0
		 * 0 0 -3.5285 3.4906 0.0000 C 0 0 0 0 0 0 0 0 0 0 0 0 -1.1550 4.5474
		 * 0.0000 H 0 0 0 0 0 0 0 0 0 0 0 0 -4.4102 2.2771 0.0000 C 0 0 0 0 0 0
		 * 0 0 0 0 0 0 -4.6817 -0.3067 0.0000 H 0 0 0 0 0 0 0 0 0 0 0 0 -4.1386
		 * 4.8610 0.0000 H 0 0 0 0 0 0 0 0 0 0 0 0 -5.9019 2.4339 0.0000 H 0 0 0
		 * 0 0 0 0 0 0 0 0 0 1 2 1 0 0 0 0 2 3 2 0 0 0 0 2 4 2 0 0 0 0 5 3 1 0 0
		 * 0 0 4 6 1 0 0 0 0 6 5 1 0 0 0 0 7 5 2 0 0 0 0 6 8 2 0 0 0 0 9 7 1 0 0
		 * 0 0 7 10 1 0 0 0 0 8 11 1 0 0 0 0 8 12 1 0 0 0 0 11 9 2 0 0 0 0 9 13
		 * 1 0 0 0 0 11 14 1 0 0 0 0 M END
		 * 
		 * > <SMILES> []N=1=NC2=C([H])C([H])=C([H])C([H])=C2(N=1)
		 * 
		 * > <Position>
		 * 
		 * 
		 * > <LSTM>
		 * 
		 * 
		 * > <Group> ""-2-Benzotriazolyl""
		 * 
		 * > <MR> 3.33
		 * 
		 * > <B5STM>
		 * 
		 * 
		 * > <B1STM>
		 * 
		 * 
		 * > <#> 161
		 * 
		 * $$$$
		 */
	}

}
