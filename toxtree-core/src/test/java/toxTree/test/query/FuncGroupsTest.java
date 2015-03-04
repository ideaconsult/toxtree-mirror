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

 *//**
 * <b>Filename</b> FuncGroupsTest.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>cellbox</b> 2005-8-8
 * <b>Project</b> toxTree
 */
package toxTree.test.query;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openscience.cdk.Bond;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.io.CDKSourceCodeWriter;
import org.openscience.cdk.io.IChemObjectWriter;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainerCreator;
import org.openscience.cdk.isomorphism.matchers.SymbolAndChargeQueryAtom;
import org.openscience.cdk.isomorphism.matchers.SymbolQueryAtom;
import org.openscience.cdk.isomorphism.matchers.smarts.AnyOrderQueryBond;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.exceptions.MolAnalyseException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import ambit2.core.data.MoleculeTools;

/**
 * 
 * @author Nina Jeliazkova <br>
 *         <b>Modified</b> 2005-8-8
 */
public class FuncGroupsTest {
	protected SmilesParser gen = new SmilesParser(
			SilentChemObjectBuilder.getInstance());
	protected Logger logger = Logger.getLogger(FuncGroupsTest.class);
	CDKSourceCodeWriter debugWriter = new CDKSourceCodeWriter(System.err);

	/*
	 * protected boolean querySalt(String smiles, QueryAtomContainer q) { try {
	 * Molecule mol = gen. return query(mol,q); } catch (InvalidSmilesException
	 * x ) { x.printStackTrace(); return false; } }
	 */
	protected boolean query(String smiles, QueryAtomContainer q)
			throws Exception {
		IAtomContainer mol = FunctionalGroups.createAtomContainer(smiles, true);
		debugWriter.write(mol);
		debugWriter.write(q);
		return (FunctionalGroups.hasGroup(mol, q));
	}

	@Test
	public void testmatchInherited() throws Exception {

		SymbolQueryAtom c1 = new SymbolQueryAtom(MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON));
		SymbolAndChargeQueryAtom c2 = new SymbolAndChargeQueryAtom(
				MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
						Elements.CARBON));
		IAtomContainer c = MoleculeFactory.makeAlkane(2);

		UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
		QueryAtomContainer query1 = new QueryAtomContainer(
				SilentChemObjectBuilder.getInstance());
		query1.addAtom(c1);
		query1.addAtom(c2);
		query1.addBond(new OrderQueryBond(c1, c2,
				CDKConstants.BONDORDER_SINGLE, c1.getBuilder()));
		Assert.assertTrue(uit.isSubgraph(c, query1));

		QueryAtomContainer query = new QueryAtomContainer(
				SilentChemObjectBuilder.getInstance());
		query.addAtom(c1);
		query.addAtom(c2);
		query.addBond(new AnyOrderQueryBond(c1, c2,
				CDKConstants.BONDORDER_SINGLE, c1.getBuilder()));
		Assert.assertTrue(uit.isSubgraph(c, query));

	}

	@Test
	public void testhydrochlorideOfAmine1() throws Exception {
		QueryAtomContainer q = FunctionalGroups.hydrochlorideOfAmine(1); // primary
		IAtomContainer c = FunctionalGroups
				.createAtomContainer("[Cl-].[NH3+]C1CCCCC1");
		try {
			MolAnalyser.analyse(c);
		} catch (MolAnalyseException x) {
			throw x;
		}
		Assert.assertTrue(FunctionalGroups.hasGroup(c, q));

	}

	@Test
	public void testhydrochlorideOfAmine3() throws Exception {

		QueryAtomContainer q = FunctionalGroups.hydrochlorideOfAmine3(); // tertiary
		IAtomContainer c = FunctionalGroups
				.createAtomContainer("oc1c=ccc(c1)=[N+](CC)CC.[Cl-]");
		try {
			MolAnalyser.analyse(c);
		} catch (MolAnalyseException x) {
			throw x;
		}
		Assert.assertTrue(FunctionalGroups.hasGroup(c, q));
	}

	@Test
	public void sulphateOfAmine() throws Exception {
		IAtomContainer c = phenazineMethosulphate();
		QueryAtomContainer q = FunctionalGroups.sulphateOfAmine(0); // any amine
		MolAnalyser.analyse(c);
		Assert.assertTrue(FunctionalGroups.hasGroup(c, q));
	}

	@Test
	public void testSulphateOfPrimaryAmine() throws Exception {
		QueryAtomContainer q = FunctionalGroups.sulphateOfAmine(1); // primary
		IAtomContainer c = FunctionalGroups
				.createAtomContainer("S(=O)(=O)([O-])O.[NH3+]C1CCCCC1");
		MolAnalyser.analyse(c);
		Assert.assertTrue(FunctionalGroups.hasGroup(c, q));

	}

	@Test
	public void testSulphateOfTertiaryAmine() throws Exception {
		QueryAtomContainer q = FunctionalGroups.sulphateOfAmine(3); // tertiary
		IAtomContainer c = FunctionalGroups
				.createAtomContainer("S(=O)(=O)([O-])O.[NH+](CCC)(CC)CCC1CCCCC1");
		MolAnalyser.analyse(c);
		Assert.assertTrue(FunctionalGroups.hasGroup(c, q));

	}

	@Test
	public void testAmine() throws Exception {
		QueryAtomContainer q = FunctionalGroups.primaryAmine(false);
		Assert.assertTrue(query("CCCN", q));
		Assert.assertFalse(query("c1ccc(NC)cc1", q));
		q = FunctionalGroups.secondaryAmine(false);
		Assert.assertTrue(query("CCCNCCC", q));
		Assert.assertTrue(query("c1ccc(NC)cc1", q));
		q = FunctionalGroups.secondaryAmine(true);
		Assert.assertFalse(query("c1ccc(NC)cc1", q));
	}

	@Test
	public void testTertiaryAmine() throws Exception {
		QueryAtomContainer q = FunctionalGroups.tertiaryAmine();
		Assert.assertTrue(query("CCCN(CC)CC", q));
	}

	@Test
	public void testCyano() throws Exception {
		QueryAtomContainer q = FunctionalGroups.cyano();
		Assert.assertTrue(query("CC#NCCC", q));
		Assert.assertFalse(query("CSCCCSC#N", q));
		Assert.assertFalse(query("c1ccc(NC)cc1", q));
	}

	@Test
	public void testNnitroso() throws Exception {
		QueryAtomContainer q = FunctionalGroups.Nnitroso();
		Assert.assertTrue(query("CCN(CCC)N=O", q));
		Assert.assertFalse(query("CCNN=O", q));
	}

	@Test
	public void testDiazo() throws Exception {
		QueryAtomContainer q = FunctionalGroups.diAzo();
		Assert.assertTrue(query("C=N#N", q));
		Assert.assertFalse(query("CCNN=O", q));
	}

	@Test
	public void testTriAzeno() throws Exception {
		QueryAtomContainer q = FunctionalGroups.triAzeno();
		Assert.assertTrue(query("CCCN=NN", q));
		Assert.assertFalse(query("CCCN=NNCCCCCCC", q));
	}

	@Test
	public void testQuaternaryN1() throws Exception {
		QueryAtomContainer q = FunctionalGroups.quaternaryNitrogen1(true);
		Assert.assertFalse(query("CCN(CC)(CC)OS(=O(=O)O)", q));
		Assert.assertTrue(query("[Cl-].[NH3+]C1CCCCC1", q));
		Assert.assertTrue(query("[Cl-].[NH3+]C1CCCCC1", q));
		Assert.assertFalse(query("Cl.[H]N([H])([H])C1CCCCC1", q));

		q = FunctionalGroups.quaternaryNitrogen1(false);
		Assert.assertTrue(query("Cl.[H]N([H])([H])C1CCCCC1", q));
		Assert.assertTrue(query("[NH3+]C1CCCCC1", q));
		Assert.assertFalse(query("CNC", q));
		Assert.assertFalse(query(
				"O=C(O[Na])C2=NN(=C(O)C2(N=NC1C=CC(CC1)S(=O)(=O)O[Na]))c3ccc(cc3)S(=O)(=O)O[Na]",
				q));

	}

	@Test
	public void testQuaternaryN2() throws Exception {
		QueryAtomContainer q = FunctionalGroups.quaternaryNitrogen2(true);
		Assert.assertFalse(query("CCN(CC)(CC)OS(=O(=O)O)", q));
		Assert.assertFalse(query("[Cl-].[NH3+]C1CCCCC1", q));
		Assert.assertTrue(query("CCC[NH+](=C)CCC", q));
		Assert.assertFalse(query("CN=C", q));

	}

	@Test
	public void testNitro() throws Exception {
		QueryAtomContainer q = FunctionalGroups.nitro1double();
		Assert.assertTrue(query(
				"NC(=O)C1=CC(=CC(=C1)[N+]([O-])=O)[N+]([O-])=O", q));

	}

	@Test
	public void testQNitrogenException() throws Exception {
		QueryAtomContainer q = FunctionalGroups.quarternaryNitrogenException();
		Assert.assertTrue(query("C(CC)(CC)=[N+](C)CCC", q));
		Assert.assertTrue(query("C(CC)(CC)=[N+]([H])[H]", q));
		Assert.assertTrue(query("[N+](O)(C)=C(C)(C)", q));
	}

	@Test
	public void testSaltOfCarboxylicAcid() throws Exception {
		QueryAtomContainer q = FunctionalGroups
				.saltOfCarboxylicAcid(new String[] { "Na", "K", "Ca", "Mg",
						"Al" });
		Assert.assertTrue(query("CCCCC(=O)O[Na]", q));
		Assert.assertTrue(query("CC(CC(CC))CCC(=O)O[Ca]", q));
		Assert.assertTrue(query("c1ccccc1CCCCC(=O)O[Mg]", q));
		Assert.assertTrue(query("CC(CCCCCC)CCC(=O)O[Al]", q));

		// assertTrue(query("[Na+].OC[C@H](O)C1OC(=O)C(O)=C1[O-]",q));

		IAtomContainer mol = (IAtomContainer) FunctionalGroups
				.createAtomContainer("[Na]OC(=O)C1C=C(O)CC1", true);
		List list = FunctionalGroups.getBondMap(mol, q, false);
		FunctionalGroups.markMaps(mol, q, list);
		Assert.assertNotNull(list);
		// System.out.println(FunctionalGroups.mapToString(mol));

	}

	@Test
	public void testSulphonate() throws Exception {
		QueryAtomContainer q = FunctionalGroups.sulphonate(new String[] { "Na",
				"K", "Ca" });
		Assert.assertTrue(query("CS(=O)(=O)[O-][Na+]", q));
		Assert.assertTrue(query("CS(=O)(=O)O[Ca]", q));

		IAtomContainer mol = (IAtomContainer) FunctionalGroups
				.createAtomContainer("O=S(=O)(O[Na])c1ccccc1", true);
		List list = FunctionalGroups.getBondMap(mol, q, false);
		FunctionalGroups.markMaps(mol, q, list);
		Assert.assertNotNull(list);

		IAtomContainer c = FunctionalGroups
				.createAtomContainer("[Na+].[O-]S(=O)(=O)NC1CCCCC1");
		// FunctionalGroups.associateIonic(c);
		MolAnalyser.analyse(c);
		Assert.assertFalse(FunctionalGroups.hasGroup(c, q)); // this is a

	}

	@Test
	public void testSulphonate1() throws Exception {
		QueryAtomContainer q = FunctionalGroups.sulphonate(null, false);

		Assert.assertTrue(query("O=S1(=O)([O-]1)CCCC", q));
		/*
		 * Molecule mol = (Molecule)
		 * FunctionalGroups.createAtomContainer("O=S1(=O)([O-]1)CCCC",true);
		 * 
		 * List list = FunctionalGroups.getBondMap(mol,q,false);
		 * FunctionalGroups.markMaps(mol,q,list); assertNotNull(list);
		 * System.out.println(FunctionalGroups.mapToString(mol));
		 */

	}

	@Test
	public void testSulphate() throws Exception {
		QueryAtomContainer q = FunctionalGroups.sulphate(null);
		Assert.assertTrue(query("[Na+].CCCCCCCCCCCCOCCOCCOCCOS([O-])(=O)=O", q));

		IAtomContainer c = FunctionalGroups
				.createAtomContainer("C=CCC(=NOS(=O)(=O)[O-])SC1OC(CO)C(O)C(O)C1(O).[Na+].O");

		MolAnalyser.analyse(c);
		Assert.assertTrue(FunctionalGroups.hasGroup(c, q));

		c = FunctionalGroups
				.createAtomContainer("C=CCC(=NOS(=O)(=O)[O-])SC1OC(CO)C(O)C(O)C1(O).[K+].O");
		Assert.assertNotNull(c); // cannot parse K for whatever reason

		MolAnalyser.analyse(c);
		Assert.assertTrue(FunctionalGroups.hasGroup(c, q));

	}

	@Test
	public void testSulphamate() throws Exception {
		QueryAtomContainer q = FunctionalGroups.sulphamate(new String[] { "Na",
				"K", "Ca" });
		Assert.assertTrue(query("O=S(=O)(O[Na])NC1CCCCC1", q));
		Assert.assertFalse(query("CS(=O)(=O)O[Na]", q));

		IAtomContainer mol = (IAtomContainer) FunctionalGroups
				.createAtomContainer("O=S(=O)(O[Na])NC1CCCCC1", true);
		List list = FunctionalGroups.getBondMap(mol, q, false);
		FunctionalGroups.markMaps(mol, q, list);
		Assert.assertNotNull(list);

		IAtomContainer c = FunctionalGroups
				.createAtomContainer("[Na+].[O-]S(=O)(=O)NC1CCCCC1");

		// FunctionalGroups.associateIonic(c);
		MolAnalyser.analyse(c);
		Assert.assertTrue(FunctionalGroups.hasGroup(c, q)); // this is a
															// sulphamate

	}

	@Test
	public void testEster() throws Exception {
		QueryAtomContainer q = FunctionalGroups.ester();
		Assert.assertTrue(query("CC(=O)OCCCCCC", q));
		Assert.assertTrue(query("O=C1OC(=O)CC1", q));
		Assert.assertTrue(query("C1OC(=O)C=C1", q)); // crotonolactone
	}

	@Test
	public void testThioEster() throws Exception {
		QueryAtomContainer q = FunctionalGroups.thioester();
		Assert.assertTrue(query("CCSC(=O)CCC", q));
		Assert.assertFalse(query("O=C1OC(=O)CC1", q));
	}

	@Test
	public void testKetone() throws Exception {
		QueryAtomContainer q = FunctionalGroups.ketone();
		Assert.assertTrue(query("CCCCCC(=O)CCCCCCCCCC", q));
		Assert.assertFalse(query("CCCCOC(=O)CCCCCCCCCC", q));
		Assert.assertFalse(query("C1OC(=O)C=C1", q));
	}

	@Test
	public void testAlcohol() throws Exception {
		QueryAtomContainer q = FunctionalGroups.alcohol(true);
		IAtomContainer mol = FunctionalGroups.createAtomContainer("CCCCCCO",
				true);
		// FunctionalGroups.markCHn(mol); //note markCH is necessary
		Assert.assertTrue(FunctionalGroups.hasGroup(mol, q));

		mol = FunctionalGroups
				.createAtomContainer("CCCCOC(=O)CCCCCCCCCC", true);
		// FunctionalGroups.markCHn(mol); //note markCH is necessary
		Assert.assertFalse(FunctionalGroups.hasGroup(mol, q));
	}

	@Test
	public void testEther() throws Exception {
		QueryAtomContainer q = FunctionalGroups.ether();
		IAtomContainer mol = FunctionalGroups.createAtomContainer("COC", true);
		Assert.assertTrue(FunctionalGroups.hasGroup(mol, q));

		mol = FunctionalGroups.createAtomContainer("CCCOCCCCOCOCCCC", true);
		Assert.assertTrue(FunctionalGroups.hasGroup(mol, q));

		mol = FunctionalGroups.createAtomContainer("CS(=O)(=O)OC", true);
		Assert.assertFalse(FunctionalGroups.hasGroup(mol, q));

		mol = FunctionalGroups.createAtomContainer("C1CCCOC1", true);
		Assert.assertTrue(FunctionalGroups.hasGroup(mol, q));

		mol = FunctionalGroups
				.createAtomContainer(
						"CCCCCCCCCCCCCCCC(=O)O[C@]1(O[C@H](CO)[C@@H](O)[C@H](O)[C@H]1O)[C@@]2(CO)O[C@H](CO)[C@@H](O)[C@@H]2O",
						true);
		Assert.assertTrue(FunctionalGroups.hasGroup(mol, q));

	}

	@Test
	public void testCarbonyl() throws Exception {
		QueryAtomContainer q = FunctionalGroups.carbonyl();
		Assert.assertTrue(query("CCCCCC=O", q));
		Assert.assertTrue(query("CCCCOC(=O)CCCCCCCCCC", q));
	}

	@Test
	public void testAldehyde() throws Exception {
		QueryAtomContainer q = FunctionalGroups.aldehyde();
		Assert.assertTrue(query("CCCCCC=O", q));
		Assert.assertFalse(query("CCCCCC(=O)CCCCCCCCCC", q)); // ketone
		Assert.assertTrue(query("C=O", q)); // formaldehyde
	}

	@Test
	public void testCarboxylicAcid() throws Exception {
		QueryAtomContainer q = FunctionalGroups.carboxylicAcid();
		Assert.assertTrue(query("C(=O)O", q));
		Assert.assertTrue(query("CCCC(=O)O", q));
		Assert.assertFalse(query("CCCC(=O)OC", q));
		Assert.assertFalse(query("CCCCCC(=O)CCCCCCCCCC", q)); // ketone
	}

	@Test
	public void testAcetal() throws Exception {
		QueryAtomContainer q = FunctionalGroups.acetal();
		Assert.assertTrue(query("COCOC", q));
		Assert.assertTrue(query("CCCCOCOCCCC", q));
		Assert.assertTrue(query("C1CCCOCOCC1CC", q));
		Assert.assertFalse(query("COCO", q));
	}

	@Test
	public void testSulphide() throws Exception {
		QueryAtomContainer q = FunctionalGroups.sulphide();
		Assert.assertTrue(query("CSC", q));
		Assert.assertTrue(query("CCCSCCCCOCOCCCC", q));
		Assert.assertFalse(query("CS(=O)(=O)OC", q)); // have to check S valency
														// =2
	}

	@Test
	public void testMercaptan() throws Exception {
		QueryAtomContainer q = FunctionalGroups.mercaptan();
		Assert.assertTrue(query("CCCS", q));
		Assert.assertTrue(query("c1cc(S)ccc1", q));
		Assert.assertFalse(query("CSC", q));
		Assert.assertFalse(query("CS(=O)(=O)OC", q));
	}

	@Test
	public void testpolyoxyethylene1() throws Exception {
		QueryAtomContainer q = FunctionalGroups.polyoxyethylene(1);
		Assert.assertTrue(query("OCC", q));
		Assert.assertTrue(query("OCCOCCOCC", q));
		Assert.assertFalse(query("CS(=O)(=O)OC", q)); // have to check S valency

	}

	@Test
	public void testpolyoxyethylene4() throws Exception {
		QueryAtomContainer q = FunctionalGroups.polyoxyethylene(4);
		Assert.assertFalse(query("OCC", q));
		Assert.assertTrue(query("OCCOCCOCCOCC", q));
	}

	@Test
	public void testMethoxy() throws Exception {
		QueryAtomContainer q = FunctionalGroups.methoxy();
		Assert.assertTrue(query("Oc1ccc(cc1(OC))CC=C", q));
	}

	@Test
	public void testKetalAttachedToTerminalVinyl() throws Exception {
		QueryAtomContainer q = FunctionalGroups.ketalAttachedToTerminalVinyl();
		Assert.assertTrue(query("CCC(OC)(OC)C=C", q));
		Assert.assertFalse(query("CC(OC)(OC)C=CC", q));
	}

	@Test
	public void testUniqueMap() throws Exception {
		QueryAtomContainer q1 = FunctionalGroups.polyoxyethylene(1);
		debugWriter.write(q1);
		
		IAtomContainer mol = FunctionalGroups.createAtomContainer("OCC", true);
		

		debugWriter.write(mol);
		
		List list = FunctionalGroups.getUniqueBondMap(mol, q1, false);
		
		
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());

		QueryAtomContainer q2 = FunctionalGroups.polyoxyethylene(2);
		list = FunctionalGroups.getUniqueBondMap(mol, q2, false);
		Assert.assertNull(list);

		mol = FunctionalGroups.createAtomContainer("CCOCCOCC", true);
		list = FunctionalGroups.getUniqueBondMap(mol, q2, false);
		Assert.assertEquals(1, list.size());

		list = FunctionalGroups.getUniqueBondMap(mol, q1, false);
		// FunctionalGroups.markMaps(mol,q1,list);
		// System.out.println(FunctionalGroups.mapToString(mol));
		Assert.assertEquals(2, list.size());

		q2 = FunctionalGroups.polyoxyethylene(3);

		mol = (IAtomContainer) FunctionalGroups.createAtomContainer(
				"CCOCCOCCOCC", true);
		list = FunctionalGroups.getUniqueBondMap(mol, q2, false);
		Assert.assertEquals(1, list.size());

		mol = (IAtomContainer) FunctionalGroups.createAtomContainer(
				"CCOCCOCCOCC", true);
		list = FunctionalGroups.getUniqueBondMap(mol, q1, false);
		// FunctionalGroups.markMaps(mol,q1,list);
		// System.out.println(FunctionalGroups.mapToString(mol));
		Assert.assertEquals(3, list.size());

		q1 = FunctionalGroups.ester();
		mol = (IAtomContainer) FunctionalGroups.createAtomContainer(
				"CCCCC(=O)OC(=O)CCCC", true);

		list = FunctionalGroups.getUniqueBondMap(mol, q1, false);
		// FunctionalGroups.markMaps(mol,q1,list);

		Assert.assertEquals(1, list.size());
	}

	@Test
	public void testGetSubgraphBondMaps() throws Exception {
		QueryAtomContainer q = FunctionalGroups.alcohol(false);
		String[] ids = new String[5];
		ids[0] = q.getID();
		IAtomContainer mol = FunctionalGroups.createAtomContainer(
				"CNC1=C(C=CC=C1)C(=O)OC", true);

		MolAnalyser.analyse((IAtomContainer) mol);
		if (mol != null) {
			List list = FunctionalGroups.getBondMap(mol, q, false);
			q = FunctionalGroups.ketone();
			list = FunctionalGroups.getBondMap(mol, q, false);
			Assert.assertEquals(0, list.size());
			ids[1] = q.getID();

			q = FunctionalGroups.aldehyde();
			list = FunctionalGroups.getBondMap(mol, q, false);
			Assert.assertEquals(0, list.size());
			ids[2] = q.getID();

			q = FunctionalGroups.carboxylicAcid();
			list = FunctionalGroups.getBondMap(mol, q, false);
			Assert.assertEquals(0, list.size());
			ids[3] = q.getID();

			q = FunctionalGroups.ester();
			list = FunctionalGroups.getBondMap(mol, q, false);
			Assert.assertEquals(1, list.size());
			ids[4] = q.getID();

			int inRingAromatic = 0;
			int atomsInEsterGroup = 0;
			for (int i = 0; i < mol.getAtomCount(); i++) {
				IAtom a = mol.getAtom(i);
				if (a.getFlag(CDKConstants.ISAROMATIC)
						&& a.getFlag(CDKConstants.ISINRING))
					inRingAromatic++;
				if (a.getProperty(ids[4]) != null)
					atomsInEsterGroup++;
			}
			// System.out.println(FunctionalGroups.mapToString(mol));
			Assert.assertEquals(6, inRingAromatic);
			Assert.assertEquals(3, atomsInEsterGroup);

		}
	}

	@Test
	public void testRings() throws Exception {

		IAtomContainer mol = FunctionalGroups.createAtomContainer(
				"CNC1=C(C=CC=C1)C(=O)OCCCC2CCCCC2", true);
		// "CC(C)CCC1=C(C(O)=O)C2=C(C=C1C)C=C(C=C2)C(C)C");
		// "CNC1=C(C=CC=C1)C(=O)OC");

		MolAnalyser.analyse(mol);

		ArrayList ids = new ArrayList();
		ids.add(FunctionalGroups.C);
		ids.add(FunctionalGroups.CH);
		ids.add(FunctionalGroups.CH2);
		ids.add(FunctionalGroups.CH3);
		FunctionalGroups.markCHn(mol);
		QueryAtomContainer q;
		List list;

		q = FunctionalGroups.ester();
		ids.add(q.getID());
		list = FunctionalGroups.getUniqueBondMap(mol, q, false);
		Assert.assertEquals(1, list.size()); // one ester group
		FunctionalGroups.markMaps(mol, q, list);

		q = FunctionalGroups.secondaryAmine(false);
		ids.add(q.getID());
		list = FunctionalGroups.getUniqueBondMap(mol, q, false);
		Assert.assertEquals(1, list.size()); // one secondary amine
		FunctionalGroups.markMaps(mol, q, list);

		Assert.assertTrue(FunctionalGroups.hasMarkedOnlyTheseGroups(mol, ids));

		// System.out.println(FunctionalGroups.mapToString(mol,ids).toString());

		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		Assert.assertNotNull(mf);
		IRingSet rs = mf.getRingset();
		int size = ((IRingSet) rs).getAtomContainerCount();
		Assert.assertNotNull(rs);
		Assert.assertEquals(2, size);
		for (int i = 0; i < size; i++) {
			IRing r = (IRing) rs.getAtomContainer(i);
			logger.debug("Ring\t" + (i + 1));

			IAtomContainer mc = FunctionalGroups.cloneDiscardRingAtomAndBonds(
					mol, r);

			logger.debug("\tmol atoms\t" + mc.getAtomCount());
			// assertEquals(mc.getAtomCount(),atoms-removedAtoms);
			IAtomContainerSet s = ConnectivityChecker
					.partitionIntoMolecules(mc);
			logger.debug("partitions\t" + s.getAtomContainerCount());
			for (int k = 0; k < s.getAtomContainerCount(); k++) {
				logger.debug("Partition\t" + (k + 1));
				IAtomContainer m = s.getAtomContainer(k);
				if (m != null)
					logger.debug(FunctionalGroups.mapToString(m).toString());
				else
					logger.debug(m);
			}
		}

	}

	protected IAtomContainer salt() {
		IAtomContainer mol = MoleculeTools.newMolecule(SilentChemObjectBuilder
				.getInstance());
		IAtom a1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a1);
		IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a7);
		IAtom a8 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CHLORINE);
		mol.addAtom(a8);
		Bond b1 = new Bond(a2, a1, IBond.Order.SINGLE);
		mol.addBond(b1);
		Bond b2 = new Bond(a3, a2, IBond.Order.SINGLE);
		mol.addBond(b2);
		Bond b3 = new Bond(a4, a3, IBond.Order.SINGLE);
		mol.addBond(b3);
		Bond b4 = new Bond(a5, a4, IBond.Order.SINGLE);
		mol.addBond(b4);
		Bond b5 = new Bond(a6, a5, IBond.Order.SINGLE);
		mol.addBond(b5);
		Bond b6 = new Bond(a7, a5, IBond.Order.SINGLE);
		mol.addBond(b6);
		return mol;
	}

	@Test
	public void testSalt() throws Exception {
		String smiles = "CCCC[N+](C)C.[Cl-]";

		IAtomContainer mol = (IAtomContainer) FunctionalGroups
				.createAtomContainer(smiles, false);
		Assert.assertNotNull(mol);
		IAtomContainerSet s = ConnectivityChecker.partitionIntoMolecules(mol);
		Assert.assertEquals(2, s.getAtomContainerCount());
		StringWriter stringWriter = new StringWriter();
		IChemObjectWriter writer = new CDKSourceCodeWriter(stringWriter);
		SmilesGenerator sg = SmilesGenerator.generic();
		try {
			String newSmiles = sg.create(mol);
			String newSmiles1 = sg.create(salt());
			logger.debug(newSmiles);
			logger.debug(newSmiles1);
			writer.write((IAtomContainer) mol);
			writer.close();
		} catch (Exception x) {
			logger.warn(x.getMessage(), x);
		}
		// logger.debug(stringWriter.toString());
	}

	@Test
	public void testQuery() throws Exception {

		IAtomContainer a = gen.parseSmiles("C(=O)C(=O)");

		// assertEquals(a.getAtomCount(),6);
		// h.addExplicitHydrogensToSatisfyValency((IAtomContainer)a);
		// assertEquals(a.getAtomCount(),6);
		/*
		 * for (int i=0; i < a.getAtomCount(); i++)
		 * System.out.println(a.getAtomAt(i).getSymbol());
		 */
		QueryAtomContainer q = QueryAtomContainerCreator
				.createBasicQueryContainer(a);

		IAtomContainer mol = (IAtomContainer) FunctionalGroups
				.createAtomContainer("CCCCCC(=O)C(=O)CCCCCCCCCC", true);
		Assert.assertTrue(FunctionalGroups.hasGroup(mol, q));

	}

	@Test
	public void testTerpene() throws Exception {
		IAtomContainer mol = MoleculeFactory.makeAlphaPinene();
		MolAnalyser.analyse(mol);
		Assert.assertTrue(FunctionalGroups.isCommonTerpene(mol));
		// mol =
		// (IAtomContainer)FunctionalGroups.createAtomContainer("CC12(CCC(CC1)C2(C)(C))",true);
		// assertFalse(FunctionalGroups.isCommonTerpene(mol));

	}

	protected void associateIonic(IAtomContainer mol, int ionicBonds)
			throws CDKException {
		Assert.assertEquals(ionicBonds, FunctionalGroups.associateIonic(mol));
		// SetOfAtomContainers c =
		// ConnectivityChecker.partitionIntoMolecules(mol);
		// assertEquals(1,c.getAtomContainerCount());
		// SmilesGenerator g = new SmilesGenerator();
		// System.out.println(g.createSMILES((IAtomContainer)mol));
	}

	@Test
	public void testAssociateIonic() throws Exception {

		IAtomContainer c = null;
		/*
		 * try {
		 * 
		 * c = FunctionalGroups.createAtomContainer(
		 * "[Na+].[Na+].CCN(CC1=CC(=CC=C1)S([O-])(=O)=O)C2=CC=C(C=C2)C(=C3//C=CC(\\C=C3)=[N+](//CC)CC4=CC=CC(=C4)S([O-])(=O)=O)\\C5=C(C=C(O)C=C5)S([O-])(=O)=O"
		 * ); associateIonic(c,2); } catch (CDKException x) {
		 * assertTrue(x.getMessage
		 * ().startsWith("Can't find an ionic bond for atom(s)\t")); }
		 * 
		 * c = FunctionalGroups.createAtomContainer(
		 * "[Na+].[Na+].[Na+].OC1=C(N=NC2=CC=C(C=C2)S([O-])(=O)=O)C(=NN1C3=CC=C(C=C3)S([O-])(=O)=O)C([O-])=O"
		 * ); associateIonic(c,3);
		 * 
		 * 
		 * c = FunctionalGroups.createAtomContainer(
		 * "OC1=CC=C2C=C(C=CC2=C1N=NC3=CC=C(C=C3)S(=O)(=O)[O-])S(=O)(=O)[O-].[Na+].[Na+]"
		 * ); associateIonic(c,2);
		 */
		c = FunctionalGroups.createAtomContainer("[Ca+2].[O-]C=O.[O-]C=O");
		associateIonic(c, 2);

	}

	@Test
	public void testAssociateIonic1to1() throws Exception {
		IAtomContainer c;
		c = FunctionalGroups.createAtomContainer("[Cl-].[NH3+]C1CCCCC1");
		associateIonic(c, 1);

		c = FunctionalGroups
				.createAtomContainer("[Na+].[O-]S(=O)(=O)NC1CCCCC1");
		associateIonic(c, 1);
	}

	public IAtomContainerSet removeGroup(String smiles, QueryAtomContainer q)
			throws Exception {
		IAtomContainer c = FunctionalGroups.createAtomContainer(smiles);
		MolAnalyser.analyse(c);

		List map = FunctionalGroups.getBondMap(c, q, false);
		FunctionalGroups.markMaps(c, q, map);
		// System.out.println(FunctionalGroups.mapToString(c));
		Assert.assertNotNull(map);
		IAtomContainerSet sc = FunctionalGroups.detachGroup(c, q);
		// System.out.println();
		/*
		 * if (sc != null) { SmilesGenerator g = new SmilesGenerator(); for (int
		 * i=0;i<sc.getAtomContainerCount();i++)
		 * System.out.println(g.createSMILES
		 * ((IAtomContainer)sc.getAtomContainer(i))); }
		 */
		return sc;
	}

	@Test
	public void testBreakLactone() throws Exception {
		QueryAtomContainer q = FunctionalGroups.lactoneBreakable();
		IAtomContainerSet c = null;
		c = removeGroup("O=C1CCCO1", q);
		Assert.assertEquals(1, c.getAtomContainerCount());
		// hydroxy acid
		IAtomContainer a = FunctionalGroups.createAtomContainer("O=C(O)CCCO",
				true);
		UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
		Assert.assertTrue(uit.isIsomorph(a, c.getAtomContainer(0)));

		c = removeGroup("OCC(O)C1OC(=O)C(O)=C1(O)", q);
		Assert.assertEquals(1, c.getAtomContainerCount());

	}

	@Test
	public void testLactone() throws Exception {
		QueryAtomContainer q = FunctionalGroups.lactone(false);
		QueryAtomContainer q1 = FunctionalGroups.anhydride();
		Object[][] answer = { { "C1OC(=O)C=C1", new Boolean(true) },
				{ "O=C1OC(O)C(O)=C1(O)", new Boolean(true) },
				{ "O=C1CCC(=O)O1", new Boolean(false) } };

		for (int i = 0; i < answer.length; i++) {
			IAtomContainer c = FunctionalGroups
					.createAtomContainer(answer[i][0].toString());
			MolAnalyser.analyse(c);
			logger.info(answer[i][0] + "\t" + q.getID() + "\t"
					+ ((Boolean) answer[i][1]).toString());
			Assert.assertEquals(true, FunctionalGroups.hasGroup(c, q));
			Assert.assertEquals(!((Boolean) answer[i][1]).booleanValue(),
					FunctionalGroups.hasGroup(c, q1));

		}

	}

	/**
	 * DeduceBondTool loops forever
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRemoveSulphonateGroup_big() throws Exception {
		QueryAtomContainer q = FunctionalGroups.sulphonate(null, false);
		IAtomContainerSet c = null;
		c = removeGroup(
				"[Na+].[Na+].CCN(CC1=CC(=CC=C1)S([O-])(=O)=O)C2=CC=C(C=C2)C(=C3//C=CC(\\C=C3)=[N+](//CC)CC4=CC=CC(=C4)S([O-])(=O)=O)\\C5=C(C=C(O)C=C5)S([O-])(=O)=O",
				q);
		Assert.assertEquals(4, c.getAtomContainerCount());
	}

	@Test
	public void testRemoveSulphonateGroup() throws Exception {
		QueryAtomContainer q = FunctionalGroups.sulphonate(null, false);
		IAtomContainerSet c = null;
		c = removeGroup("[Na+].[O-]S(=O)(=O)CC1CCCCC1", q);
		Assert.assertEquals(2, c.getAtomContainerCount());
		c = removeGroup("[Na+].[O-]S(=O)(=O)CCCCS(=O)(=O)[O-].[Na+]", q);
		Assert.assertEquals(3, c.getAtomContainerCount());
	}

	@Test
	public void testRemoveSulphateOfAmineGroup() throws Exception {
		QueryAtomContainer q = FunctionalGroups.sulphateOfAmineBreakable();
		IAtomContainer a = phenazineMethosulphate();
		MolAnalyser.analyse(a);
		IAtomContainerSet c = FunctionalGroups.detachGroup(a, q);
		Assert.assertNotNull(c);
		/*
		 * SmilesGenerator g = new SmilesGenerator();
		 * System.out.println("Sulphate of amine"); MFAnalyser mf = new
		 * MFAnalyser(a); a = mf.removeHydrogensPreserveMultiplyBonded();
		 * System.out.println(g.createSMILES((IAtomContainer)a));
		 * System.out.println(); for (int i=0;i<c.getAtomContainerCount();i++)
		 * System
		 * .out.println(g.createSMILES((IAtomContainer)c.getAtomContainer(i)));
		 */
		Assert.assertEquals(2, c.getAtomContainerCount());
	}

	@Test
	public void testRemoveSulphateGroup() throws Exception {
		QueryAtomContainer q = FunctionalGroups.sulphate(null);
		IAtomContainerSet c = null;
		c = removeGroup("[Na+].CCCCCCCCCCCCOCCOCCOCCOS([O-])(=O)=O", q);
		Assert.assertEquals(2, c.getAtomContainerCount());
		/*
		 * c = removeGroup("[Na+].[O-]S(=O)(=O)CC1CCCCC1",q);
		 * assertEquals(2,c.getAtomContainerCount()); c =
		 * removeGroup("[Na+].[O-]S(=O)(=O)CCCCS(=O)(=O)[O-].[Na+]",q);
		 * assertEquals(3,c.getAtomContainerCount());
		 */
	}

	@Test
	public void testBreakSalt() throws Exception {
		QueryAtomContainer q = FunctionalGroups
				.saltOfCarboxylicAcidBreakable(new String[] { "Na", "K" });
		IAtomContainer salt = FunctionalGroups
				.createAtomContainer("CCCCC(=O)[O-].[Na+]");
		IAtomContainer acid = FunctionalGroups.createAtomContainer(
				"CCCCC(=O)O", true);
		MolAnalyser.analyse(salt);
		// MolAnalyser.analyse(acid);

		IAtomContainerSet c = FunctionalGroups.detachGroup(salt, q);
		Assert.assertNotNull(c);

		// SmilesGenerator g = new SmilesGenerator();
		Assert.assertEquals(2, c.getAtomContainerCount());
		// System.out.println(q.getID());
		// System.out.println(g.createSMILES((IAtomContainer)acid));
		for (int i = 0; i < c.getAtomContainerCount(); i++) {
			IAtomContainer a = c.getAtomContainer(i);
			// MFAnalyser mf = new MFAnalyser(a);
			// a = mf.removeHydrogensPreserveMultiplyBonded();
			// System.out.println(g.createSMILES((IAtomContainer)a));
			if (FunctionalGroups.hasGroupMarked(a, q.getID())) {
				// if (a.getAtomCount() > 3)
				UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
				Assert.assertTrue(uit.isIsomorph(acid, a));
			}

		}
	}

	//
	@Test
	public void testBreakHydrochlorideOfAmine() throws Exception {
		QueryAtomContainer q = FunctionalGroups.hydrochlorideOfAmineBreakable();
		SmilesGenerator g = SmilesGenerator.generic();

		String[] examples = { "[Cl-].[NH3+]C1CCCCC1", "[Cl-].[N+]C1CCCCC1",
				"[Cl-].[N+]([H])([H])([H])C1CCCCC1" };
		IAtomContainer amine = FunctionalGroups.createAtomContainer(
				"NC1CCCCC1", true);

		for (int e = 0; e < examples.length; e++) {
			IAtomContainer hydrochlorideAmine = FunctionalGroups
					.createAtomContainer(examples[e]);

			MolAnalyser.analyse(hydrochlorideAmine);
			IAtomContainerSet c = FunctionalGroups.detachGroup(
					hydrochlorideAmine, q);
			Assert.assertNotNull(c);

			Assert.assertEquals(2, c.getAtomContainerCount());
			// System.out.print(q.getID());
			System.out.println("Amine "
					+ g.createSMILES((IAtomContainer) amine));
			UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
			for (int i = 0; i < c.getAtomContainerCount(); i++) {
				IAtomContainer a = c.getAtomContainer(i);

				System.out.println("Product "
						+ g.createSMILES((IAtomContainer) a));
				if (FunctionalGroups.hasGroupMarked(a, q.getID())) {
					Assert.assertEquals(amine.getAtomCount(), a.getAtomCount());

					Assert.assertTrue(uit.isIsomorph(amine, a));
				}

			}
		}
	}

	public static IAtomContainer phenazineMethosulphate() throws Exception {
		IAtomContainer mol = MoleculeTools.newMolecule(SilentChemObjectBuilder
				.getInstance());
		IAtom nq = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		nq.setFormalCharge(+1);
		mol.addAtom(nq);
		IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.SULFUR);
		mol.addAtom(a2);
		IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		mol.addAtom(a3);
		IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a4);
		IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a5);
		IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a6);
		IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.CARBON);
		mol.addAtom(a7);
		IAtom o1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		o1.setFormalCharge(-1);
		mol.addAtom(o1);
		IAtom a9 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.OXYGEN);
		mol.addAtom(a9);
		IAtom a10 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN);
		mol.addAtom(a10);
		IAtom a11 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.OXYGEN);
		mol.addAtom(a11);
		IAtom a12 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a12);
		IAtom a13 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a13);
		IAtom a14 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a14);
		IAtom a15 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a15);
		IAtom a16 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a16);
		IAtom a17 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a17);
		IAtom a18 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a18);
		IAtom a19 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a19);
		IAtom a20 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a20);
		IAtom a21 = MoleculeTools.newAtom(
				SilentChemObjectBuilder.getInstance(), Elements.CARBON);
		mol.addAtom(a21);
		Bond b1 = new Bond(a3, a7, IBond.Order.SINGLE);
		mol.addBond(b1);
		Bond b2 = new Bond(a4, nq, IBond.Order.SINGLE);
		mol.addBond(b2);
		Bond b3 = new Bond(a5, nq, IBond.Order.DOUBLE);
		mol.addBond(b3);
		Bond b4 = new Bond(a6, a5, IBond.Order.SINGLE);
		mol.addBond(b4);
		Bond b5 = new Bond(a7, a4, IBond.Order.DOUBLE);
		mol.addBond(b5);
		Bond b6 = new Bond(o1, a2, IBond.Order.SINGLE);
		mol.addBond(b6);
		Bond b7 = new Bond(a9, a2, IBond.Order.DOUBLE);
		mol.addBond(b7);
		Bond b8 = new Bond(a10, a2, IBond.Order.DOUBLE);
		mol.addBond(b8);
		Bond b9 = new Bond(a11, a2, IBond.Order.SINGLE);
		mol.addBond(b9);
		Bond b10 = new Bond(a12, nq, IBond.Order.SINGLE);
		mol.addBond(b10);
		Bond b11 = new Bond(a13, a4, IBond.Order.SINGLE);
		mol.addBond(b11);
		Bond b12 = new Bond(a14, a5, IBond.Order.SINGLE);
		mol.addBond(b12);
		Bond b13 = new Bond(a15, a6, IBond.Order.SINGLE);
		mol.addBond(b13);
		Bond b14 = new Bond(a16, a7, IBond.Order.SINGLE);
		mol.addBond(b14);
		Bond b15 = new Bond(a17, a11, IBond.Order.SINGLE);
		mol.addBond(b15);
		Bond b16 = new Bond(a18, a13, IBond.Order.DOUBLE);
		mol.addBond(b16);
		Bond b17 = new Bond(a19, a14, IBond.Order.DOUBLE);
		mol.addBond(b17);
		Bond b18 = new Bond(a20, a19, IBond.Order.SINGLE);
		mol.addBond(b18);
		Bond b19 = new Bond(a21, a16, IBond.Order.DOUBLE);
		mol.addBond(b19);
		Bond b20 = new Bond(a6, a3, IBond.Order.DOUBLE);
		mol.addBond(b20);
		Bond b21 = new Bond(a18, a21, IBond.Order.SINGLE);
		mol.addBond(b21);
		Bond b22 = new Bond(a15, a20, IBond.Order.DOUBLE);
		mol.addBond(b22);
		return mol;
	}

}
