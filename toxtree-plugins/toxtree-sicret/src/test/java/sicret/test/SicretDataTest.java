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

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;

import sicret.SicretRules;
import sicret.rules.RuleLogP;
import sicret.rules.RuleMeltingPoint;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.query.FunctionalGroups;
import ambit2.core.io.IteratingDelimitedFileReader;

public class SicretDataTest {
	protected SicretRules cr = null;
	protected static Logger logger = Logger.getLogger(SicretDataTest.class.getName());

	// smiles,class,name,tree path
	/*
	 * public static String[][] compoundsClass1 ={
	 * {"CC1(C(CC=C1C)C=CC(C)(C)C(O)C)C","1","R38-I",""},
	 * 
	 * }; public static String[][] compoundsClass2 ={
	 * {"O=C1C=COC(=C1(O))CC","2","EM","1N,2N,3N,5N,6N,7Y,8N,10N,11N,12N,22Y"},
	 * 
	 * }; //smiles,class,name,tree
	 * path,MeltingPoint,LogKow,LipidSolubility,MoleculWeight
	 * ,SurfaceTension,VapourPressure,AqueousSolubility public static String[][]
	 * compoundsClass3 ={
	 * {"BrCCCCCCBr","3","1.6 - Dibromohexane","","7.880843","3.60806786664511"
	 * ,"423","243.979991","0","0.250285","10.293935"}
	 * 
	 * };
	 * 
	 * public static String[][] compoundsClass4 ={
	 * {"CC1(C(CC=C1C)C=CC(C)(C)C(O)C)C","4",
	 * "(+/-) trans-3,3-dimethyl-5-(2,2,3-trimethyl-cyclopent-3-en-1-yl)-pent-4-en-2-ol"
	 * ,"","63.639511","3.61402048827646","0.2","222.371995","0","0.000125",
	 * "2.710853" }
	 * 
	 * };
	 */
	public static String[][] compoundsClass = new String[100][11];
	public static String[][] compoundsClass_1 = new String[9][11];

	/**
	 * Constructor for CramerDataTest.
	 * 
	 * @param arg0
	 */
	public SicretDataTest() throws Exception {
		super();

		cr = new SicretRules();
		cr.setResiduesIDVisible(false);

	}

	protected IDecisionResult classify(String smiles, String id, SicretRules rules) throws Exception {
		IDecisionResult result = rules.createDecisionResult();

		result.setDecisionMethod(rules);
		IAtomContainer mol = (IAtomContainer) FunctionalGroups.createAtomContainer(smiles, id);
		try {
			result.classify(mol);
		} catch (DecisionResultException x) {
			logger.log(Level.SEVERE, x.getMessage(), x);
			result = null;
		}
		return result;
	}

	protected IDecisionResult classify(String smiles, String id, SicretRules rules, String MeltingPoint, String LogKow,
			String LipidSolubility, String MoleculWeight, String SurfaceTension, String VapourPressure,
			String AqueousSolubility) throws Exception {
		IDecisionResult result = rules.createDecisionResult();

		result.setDecisionMethod(rules);
		IAtomContainer mol = (IAtomContainer) FunctionalGroups.createAtomContainer(smiles, id);
		mol.setProperty(RuleMeltingPoint.MeltingPoint, MeltingPoint);
		mol.setProperty(RuleLogP.LogKow, LogKow);
		mol.setProperty("LipidSolubility", LipidSolubility);
		mol.setProperty("MoleculWeight", MoleculWeight);
		mol.setProperty("SurfaceTension", SurfaceTension);
		mol.setProperty("VapourPressure", VapourPressure);
		mol.setProperty("AqueousSolubility", AqueousSolubility);
		try {
			result.classify(mol);
		} catch (DecisionResultException x) {
			logger.log(Level.SEVERE, x.getMessage(), x);
			result = null;
		}
		return result;
	}

	protected int classify(String[][] molecules, int classID) throws Exception {
		int success = 0;
		logger.log(Level.SEVERE, "These compounds should be of class\t" + classID);

		for (int i = 0; i < molecules.length; i++) {
			IDecisionResult result = classify(molecules[i][0], molecules[i][2], cr, molecules[i][4], molecules[i][5],
					molecules[i][6], molecules[i][7], molecules[i][8], molecules[i][9], molecules[i][10]);
			if (result == null) {
				logger.log(Level.SEVERE, "Error on processing molecule\t" + molecules[i][2]);
			} else {
				boolean ok = (result.getCategory().getID() == classID);
				if (ok)
					success++;

				String explanation = "";
				try {
					explanation = cr.explainRules(result, false).toString();
				} catch (DecisionMethodException x) {
					logger.log(Level.SEVERE, x.getMessage(), x);
				}
				if (ok) {
					if (!explanation.equals(molecules[i][3])) {
						logger.warning(molecules[i][0] + "\t" + molecules[i][2] + "\t" + result.getCategory() + "\t"
								+ explanation);
						logger.warning("Should be\t" + molecules[i][3]);
					}
				} else {
					logger.severe(molecules[i][0] + "\t" + molecules[i][2] + "\t" + result.getCategory() + "\t"
							+ explanation);
					logger.severe("Should be\t" + molecules[i][3]);
				}

			}
		}
		System.err.println("Wrong classification of " + (molecules.length - success) + " compounds");
		return success;
	}

	/*
	 * public void testClass1() {
	 * assertEquals(compoundsClass1.length,classify(compoundsClass1,1)); }
	 * public void testClass2() {
	 * assertEquals(compoundsClass2.length,classify(compoundsClass2,2)); }
	 * public void testClass3() {
	 * assertEquals(compoundsClass3.length,classify(compoundsClass3,3)); }
	 * public void testClass4() {
	 * assertEquals(compoundsClass4.length,classify(compoundsClass4,4)); }
	 */
	@Test
	public void testClass() throws Exception {
		testCSVFile(compoundsClass);
		Assert.assertEquals(compoundsClass.length, classify_without_classID(compoundsClass));
	}

	@Test
	public void testClass1() throws Exception {
		testCSVFile1(compoundsClass_1);
		Assert.assertEquals(compoundsClass_1.length, classify_without_classID(compoundsClass_1));
	}

	protected void writeCompounds(String[][] array, DataOutputStream f) throws IOException {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (j == 3)
					f.writeBytes("\"");
				f.writeBytes(array[i][j]);
				if (j == 3)
					f.writeBytes("\"");
				else
					f.writeBytes(",");
			}
			f.writeBytes("\n");
		}
	}

	@Test
	public void testWriteCompounds() {
		try {
			DataOutputStream f = new DataOutputStream(new FileOutputStream("appendix1.csv"));
			f.writeBytes("SMILES,CLASS,Name,Path\n");
			// writeCompounds(compoundsClass1,f);
			// writeCompounds(compoundsClass2,f);
			// writeCompounds(compoundsClass3,f);
			// writeCompounds(compoundsClass4,f);
			// writeCompounds(compoundsClass,f);
			f.close();
		} catch (IOException x) {
			x.printStackTrace();
		}
	}

	public void testCSVFile(String[][] molecules) throws Exception {
		String filename = "toxTree/test/tree/sicret/BfR_irritation.csv";
		System.out.println("Testing: " + filename);

		// molecules = new String[118][11];

		InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
		// FileInputStream ins = new FileInputStream(filename);

		IteratingDelimitedFileReader reader = new IteratingDelimitedFileReader(ins);

		int molCount = 0;
		while (reader.hasNext()) {
			Object object = reader.next();
			Assert.assertNotNull(object);
			if (object instanceof IAtomContainer) {

				IAtomContainer mol = (IAtomContainer) object;
				// smiles,class,name,tree
				// path,MeltingPoint,LogKow,LipidSolubility,MoleculWeight,SurfaceTension,VapourPressure,AqueousSolubility
				molecules[molCount][0] = mol.getProperty("SMILES").toString();
				molecules[molCount][1] = "1";
				molecules[molCount][2] = mol.getProperty("Name").toString();
				molecules[molCount][3] = "";
				if (mol.getProperty("Melting Point").toString().equals("#N/A")
						|| mol.getProperty("Melting Point").toString().length() == 0) {
					molecules[molCount][4] = "0";
				} else {
					molecules[molCount][4] = mol.getProperty("Melting Point").toString();
				}
				if (mol.getProperty("log P").toString().equals("#N/A")
						|| mol.getProperty("log P").toString().length() == 0) {
					molecules[molCount][5] = "0";
				} else {
					molecules[molCount][5] = mol.getProperty("log P").toString();
				}
				molecules[molCount][6] = "423";
				if (mol.getProperty("Molecular Mass").toString().equals("#N/A")
						|| mol.getProperty("Molecular Mass").toString().length() == 0) {
					molecules[molCount][7] = "0";
				} else {
					molecules[molCount][7] = mol.getProperty("Molecular Mass").toString();
				}
				molecules[molCount][8] = "0";
				if (mol.getProperty("Vapour Pressure").toString().equals("#N/A")
						|| mol.getProperty("Vapour Pressure").toString().length() == 0) {
					molecules[molCount][9] = "62.0";
				} else {
					molecules[molCount][9] = mol.getProperty("Vapour Pressure").toString();
				}
				if (mol.getProperty("Water Solubility").toString().equals("#N/A")
						|| mol.getProperty("Water Solubility").toString().length() == 0) {
					molecules[molCount][10] = "0";
				} else {
					molecules[molCount][10] = mol.getProperty("Water Solubility").toString();
				}
			}
			molCount++;
		}

		System.out.println("MolCount: " + molCount);
		// assertEquals(88, molCount);

	}

	public void testCSVFile1(String[][] molecules) throws Exception {
		String filename = "toxTree/test/tree/sicret/BfR_corrosion.csv";
		System.out.println("Testing: " + filename);
		// InputStream ins =
		// this.getClass().getClassLoader().getResourceAsStream(filename);
		// molecules = new String[118][11];
		IteratingDelimitedFileReader reader = null;
		try {
			InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
			// FileInputStream ins = new FileInputStream(filename);

			reader = new IteratingDelimitedFileReader(ins);

			int molCount = 0;
			while (reader.hasNext()) {
				Object object = reader.next();
				Assert.assertNotNull(object);
				Assert.assertTrue((object instanceof IAtomContainer));
				/*
				 * if(molCount==1){ IAtomContainer mol = (IAtomContainer)object;
				 * Hashtable temp = mol.getProperties(); Enumeration e =
				 * temp.keys(); while(e.hasMoreElements()){
				 * System.out.println(e.nextElement().toString());
				 * 
				 * } }
				 */
				if (object instanceof IAtomContainer) {

					IAtomContainer mol = (IAtomContainer) object;
					// smiles,class,name,tree
					// path,MeltingPoint,LogKow,LipidSolubility,MoleculWeight,SurfaceTension,VapourPressure,AqueousSolubility
					molecules[molCount][0] = mol.getProperty("SMILES").toString();
					molecules[molCount][1] = "1";
					molecules[molCount][2] = mol.getProperty("NAME").toString();
					molecules[molCount][3] = "";
					if (mol.getProperty("Meltingpt").toString().equals("#N/A")
							|| mol.getProperty("Meltingpt").toString().length() == 0) {
						molecules[molCount][4] = "0";
					} else {
						molecules[molCount][4] = mol.getProperty("Meltingpt").toString();
					}
					if (mol.getProperty("LogKow").toString().equals("#N/A")
							|| mol.getProperty("LogKow").toString().length() == 0) {
						molecules[molCount][5] = "0";
					} else {
						molecules[molCount][5] = mol.getProperty("LogKow").toString();
					}
					molecules[molCount][6] = "423";
					/*
					 * if(mol.getProperty("Molecular Mass").toString().equals("#N/A"
					 * ) ||
					 * mol.getProperty("Molecular Mass").toString().length() ==
					 * 0 ){ molecules[molCount][7] = "0"; } else{
					 * molecules[molCount][7] =
					 * mol.getProperty("Molecular Mass").toString(); }
					 */
					molecules[molCount][7] = "0";
					molecules[molCount][8] = "0";
					if (mol.getProperty("Vapour Pressure").toString().equals("#N/A")
							|| mol.getProperty("Vapour Pressure").toString().length() == 0) {
						molecules[molCount][9] = "0";
					} else {
						molecules[molCount][9] = mol.getProperty("Vapour Pressure").toString();
					}
					if (mol.getProperty("Water solub(exp)").toString().equals("#N/A")
							|| mol.getProperty("Water solub(exp)").toString().length() == 0) {
						molecules[molCount][10] = "0";
					} else {
						molecules[molCount][10] = mol.getProperty("Water solub(exp)").toString();
					}
				}
				molCount++;
			}

			System.out.println("MolCount: " + molCount);
			// assertEquals(88, molCount);
		} catch (Exception e) {
			throw e;
		} finally {
			reader.close();
		}
	}

	protected int classify_without_classID(String[][] molecules) throws Exception {
		int success = 1;
		// testCSVFile(molecules);
		// testCSVFile1(molecules);

		for (int i = 0; i < molecules.length; i++) {
			// System.out.println("Number:"+i+" "+molecules[i][0]+"/"+molecules[i][2]+"/"+molecules[i][4]+"/"+molecules[i][5]+"/"+molecules[i][6]+"/"+molecules[i][7]+"/"+molecules[i][8]+"/"+molecules[i][9]+"/"+molecules[i][10]);
			IDecisionResult result = classify(molecules[i][0], molecules[i][2], cr, molecules[i][4], molecules[i][5],
					molecules[i][6], molecules[i][7], molecules[i][8], molecules[i][9], molecules[i][10]);
			if (result == null) {
				logger.log(Level.SEVERE, "Error on processing molecule\t", molecules[i][2]);
			} else {
				// boolean ok = (result.getCategory().getID() == classID);
				// if (ok) success++;

				String explanation = "";
				try {
					explanation = cr.explainRules(result, false).toString();
				} catch (DecisionMethodException x) {
					logger.log(Level.SEVERE, x.getMessage(), x);
				}
				// logger.warn(molecules[i][0],"\t",molecules[i][2],"\t",result.getCategory()+"\t"+explanation);
				System.out.println("Number:" + i + " " + result.getCategory() + "\t" + molecules[i][2] + "\t"
						+ explanation + "\t" + molecules[i][0]);
				// System.out.println("Should be\t" + molecules[i][3]);

			}
		}
		// System.err.println("Wrong classification of " +
		// (molecules.length-success) + " compounds");
		return success;
	}

}
