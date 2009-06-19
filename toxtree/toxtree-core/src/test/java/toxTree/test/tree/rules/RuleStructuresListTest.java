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
package toxTree.test.tree.rules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.ChemFile;
import org.openscience.cdk.ChemObject;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.MDLReader;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.io.iterator.IteratingSMILESReader;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;

import toxTree.io.IteratingDelimitedFileReader;
import toxTree.io.MDLWriter;
import toxTree.logging.TTLogger;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.tree.cramer.RuleCommonComponentOfFood;
import toxTree.tree.rules.RuleStructuresList;

/**
 * Test for {@link RuleStructuresList}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2009-6-17
 */
public class RuleStructuresListTest  {
	protected static TTLogger logger = new TTLogger(RuleStructuresListTest.class);

	@Before
	public void setUp() throws Exception {
		TTLogger.configureLog4j(true);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRule() throws Exception  {
		RuleStructuresList rule = new RuleStructuresList(new File("bodymol.sdf"));
		Assert.assertTrue(rule.isImplemented());
		//default file
		IAtomContainer c = FunctionalGroups.createAtomContainer("NC1=NC2=C(NC=N2)C(=O)N1");
		MolAnalyser.analyse(c);
		Assert.assertTrue(rule.verifyRule(c));
	}
	//CSCCCN=C=S
	@Test
	public void testCachedRule() throws Exception  {
		RuleCommonComponentOfFood rule = new RuleCommonComponentOfFood(new File("foodmol.sdf"));
		Assert.assertTrue(rule.isImplemented());
		//default file
		IAtomContainer c = FunctionalGroups.createAtomContainer("O=C(C(=O)C)C");
		for (int i=0;i<2;i++) {
			MolAnalyser.analyse(c);
			boolean b = rule.verifyRule(c);
			Assert.assertTrue(b);
		}
	}	
	//MDL Writer assumes properties are of type String 
	@Test
	public void testMDLWriterWriteProperties() throws Exception {

			MDLWriter writer = new MDLWriter(new FileOutputStream(new File("bodymol.test.sdf")));
			writer.dontWriteAromatic();
			SmilesGenerator gen = new SmilesGenerator();
			
			//adenine NC1=C2N=CN=C2N=CN1
			//guanine NC1=NC2=C(NC=N2)C(=O)N1
			IAtomContainer c = FunctionalGroups.createAtomContainer("NC1=NC2=C(NC=N2)C(=O)N1");

				MolAnalyser.analyse(c);
				c.setProperty("SMILES",gen.createSMILES((IMolecule)c));
				writer.setSdFields(c.getProperties());
				writer.write(c);
				System.out.print(c.getProperty("NAME"));
				System.out.print("\t");
				System.out.print(c.getAtomCount());
				System.out.print("\t");
				System.out.println(c.getBondCount());			
							
		
	}
	@Test
	public void testBug() throws Exception {	

			MDLWriter writer = new MDLWriter(new FileOutputStream(new File("bodymol.test.sdf")));
			writer.dontWriteAromatic();
			SmilesGenerator gen = new SmilesGenerator();
			IAtomContainer c = FunctionalGroups.createAtomContainer("NC1=NC2=C(NC=N2)C(=O)N1");
			MolAnalyser.analyse(c);
			
			IIteratingChemObjectReader reader;

			InputStream in = this.getClass().getClassLoader().getResourceAsStream("bodymol.sdf");
			reader = new IteratingMDLReader(in,DefaultChemObjectBuilder.getInstance());
				int r = 0;
				boolean ok = false;
				while (reader.hasNext()) {
					r++;
					Object o = reader.next();
					
					if (o instanceof AtomContainer) {
						Molecule m = (Molecule) o;
						try {
							MolAnalyser.analyse(m);			
							//hadder.addExplicitHydrogensToSatisfyValency(m);
							if ( FunctionalGroups.isSubstance(m,c)) {
								ok = true;;
							} else {
								System.out.print(m.getProperty("NAME"));
								System.out.print("\t");
								System.out.print(m.getAtomCount());
								System.out.print("\t");
								System.out.println(m.getBondCount());
								
							}
							m.setProperty("SMILES",gen.createSMILES(m));
							writer.setSdFields(m.getProperties());
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
		verifyFile("bodymol.sdf");
	}
	@Test
	public void testFoodMol() throws Exception {
		verifyFile("foodmol.sdf");
	}	
	protected void verifyFile(String filename) throws Exception {
		ChemFile m = null;
		InputStream in = getClass().getClassLoader().getResourceAsStream(filename);
		MDLReader reader = new MDLReader(in);
		m = (ChemFile)reader.read((ChemObject)new ChemFile());
		reader.close();

		List c = ChemFileManipulator.getAllAtomContainers(m);
		
		Assert.assertNotNull(c);
		int okCount = 0; int allCount = 0;
		try {
			
			for (int i=0;i < c.size();i++) {
				IAtomContainer a = (IAtomContainer )c.get(i);
				MolAnalyser.analyse(a);
			}
			for (int i=0;i < c.size();i++) {
				if (((IAtomContainer)c.get(i)).getAtomCount() == 0) break;
				allCount++;
				IAtomContainer a = (IAtomContainer ) ((IAtomContainer ) c.get(i)).clone();
				if (a.getProperty("NAME") != null)
					a.setID(a.getProperty("NAME").toString());
				else if (a.getProperty("TITLE") != null)
					a.setID(a.getProperty("TITLE").toString());
				
				MolAnalyser.analyse(a);
							
				boolean ok = false;
				
				for (int j=0;j < c.size();j++) {
					IAtomContainer a1 = (IAtomContainer )c.get(i);
					if (a.getAtomCount() != a1.getAtomCount()) {
						logger.debug("Atom count does not match at record\t"+Integer.toString(j),"\t",
								Integer.toString(a.getAtomCount()),"\t",
								Integer.toString(a1.getAtomCount()));
						continue;
					}
					if (a.getBondCount() != a1.getBondCount()) {
						logger.debug("Bond count does not match at record\t"+Integer.toString(j),"\t",
								Integer.toString(a.getBondCount()),"\t",
								Integer.toString(a1.getBondCount()));
						continue;
					}					
					try {
						if (UniversalIsomorphismTester.isIsomorph(a,a1)) {
							ok = true;
							logger.info("Found\t"+a.getProperty("NAME")+"\tatoms\t"+a.getAtomCount()+"\tbonds\t"+a.getBondCount());
							//System.out.println(FunctionalGroups.mapToString(a));
							//System.out.println(FunctionalGroups.mapToString(a1));
						} else if (a.getProperty("NAME").equals(a1.getProperty("NAME"))) {
							logger.info("NOT Found\t"+a.getProperty("NAME")+"\tatoms\t"+a.getAtomCount()+"\tbonds\t"+a.getBondCount());
							
							//System.out.println(FunctionalGroups.mapToString(a));
							//printBonds(a);
							//System.out.println(FunctionalGroups.mapToString(a1));
							
							//printBonds(a1);							
						}
					} catch (CDKException x) {
						//x.printStackTrace();
					}
				}
				if (ok) okCount++; 
				if (!ok) {
					
					
										
				}
			}

		} catch (Exception x) {
			//x.printStackTrace();
			
		}

		Assert.assertEquals(okCount,allCount);
		
	}
	protected void printBonds(AtomContainer m) {
		for (int i=0; i < m.getBondCount(); i++)
			if (m.getBond(i).getFlag(CDKConstants.ISAROMATIC))
				System.out.println("Aromatic bond\t"+i+"\t");
		System.out.println("\n");
			
	}
}
