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
package toxTree.cramer;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.logging.TTLogger;
import toxTree.query.FunctionalGroups;
import toxTree.tree.cramer.CramerRules;
import ambit2.core.smiles.SmilesParserWrapper;
import ambit2.core.smiles.SmilesParserWrapper.SMILES_PARSER;

/**
 * This is to test the classification by CramerRules class.
 * Compounds are taken from Cramer et al.,1979, Appendix 1
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-23
 */
public class CramerDataTest extends TestCase {
	protected CramerRules cr = null;
	static protected TTLogger logger = new TTLogger(CramerDataTest.class);
	//smiles,class,name,tree path
	public static String[][] compoundsClass1 ={
		{"O=C(OCC(OC(=O)c1ccccc1)C)c2ccccc2","1","PGDB","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28Y,29Y(30N,18N)(19Y,20Y,21N,18N)"},
		{"OCC(O)C","1","PG","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20Y,21N,18N"},
//		{"[Na+].CCN(CC=1C=CC=C(C=1)S(=O)(=O)[O-])C=2C=CC=C(C=2)C(=C3C=CC(C=C3)=[N+](CC)CC=4C=CC=C(C=4)S(=O)(=O)[O-])C=5C=CC(O)=CC=5S(=O)(=O)[O-].[Na+]","1","Gr3","1N,2N,3Y,4Y,7N,16N,17N,19N,23Y,27Y,28Y,29N,33Y"},
		{"O=S(=O)([O-])c5cccc(CN(CC)c1ccc(cc1)C(=C2C=CC(C=C2)=[N+](Cc3cccc(c3)S(=O)(=O)[O-])CC)c4ccccc4S(=O)(=O)[O-])c5","1","Blu1","1N,2N,3Y,4Y,7N,16N,17N,19N,23Y,27Y,28Y,29N,33Y"},
		{"[NH4+].[NH4+].CCN(CC1=CC=CC(=C1)S([O-])(=O)=O)C2=CC=C(C=C2)C(=C3//C=CC(\\C=C3)=[N+](/CC)CC4=CC=CC(=C4)S([O-])(=O)=O)\\C5=CC=CC=C5S([O-])(=O)=O","1","Blu1","1N,2N,3Y,4Y,7N,16N,17N,19N,23Y,27Y,28Y,29N,33Y"},
		{"O=S(=O)([O-].[Na+])NC1CCCCC1","1","SCY","1N,2N,3Y,4Y,7N,16N,17N,19N,23N,24Y,18N"},
		{"OCCC(O)C","1","BG","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20Y,21N,18N"},
		{"O=C1c4cc(ccc4(NC1=C2Nc3ccc(cc3(C2(=O)))S(=O)(=O)[O-].[Na+]))S(=O)(=O)[O-].[Na+]","1","Blu2","1N,2N,3Y,4Y,7Y,8N,10N,11Y,33Y"},
		{"O=C(OC)c1ccc(O)cc1","1","MHB","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30N,18N"},
		{"O=C(OCC)c1ccc(O)cc1","1","EHB","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30N,18N"},
		{"O=C(OCCC)c1ccc(O)cc1","1","PHB","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30N,18N"},
		{"O=S(=O)([O-].[Na+])c3ccc(N=Nc1c(O)ccc2cc(ccc12)S(=O)(=O)[O-].[Na+])cc3","1","Yel6","1N,2N,3Y,4Y,7N,16N,17N,19N,23Y,27Y,28Y,29N,33Y"},
		{"O=C(OCCCCC)CCC","1","Abu","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20Y,21N,18N"},
		
		{"C([O-])(=O)C1=NN(C2=CC=C(C=C2)S([O-])(=O)=O)C(=O)C1\\N=N\\C3=CC=C(C=C3)S([O-])(=O)=O","1","Yel5","1N,2N,3Y,4Y,7Y,8N,10N,11Y,33Y"},
		//{"C([O-].[Na+])(=O)C1=NN(C2=CC=C(C=C2)S([O-].[Na+])(=O)=O)C(=O)C1\\N=N\\C3=CC=C(C=C3)S([O-].[Na+])(=O)=O","1","Yel5","1N,2N,3Y,4Y,7Y,8N,10N,11Y,33Y"},
		{"Oc1ccc(cc1(OC))CC=C","1","Eug","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30N,18N"},
		{"O=C(OCCCC)CC(OC(=O)C)(C(=O)OCCCC)CC(=O)OCCCC","1","TBACi","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20Y,21N,18N"},
		{"O=C(C(O)CC)C","1","Actn","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20Y,21N,18N"},
		{"O=S(=O)([O-])CC(O)COCCCCCCCCCCCC.[Na+]","1","SLGS","1N,2N,3Y,4Y,7N,16N,17N,19Y,20N,22N,33Y"},
		{"O=S(=O)([O-])OCCOCCOCCOCCCCCCCCCCCC.[Na+]","1","SLTS","1N,2N,3Y,4Y,7N,16N,17N,19Y,20Y,21N,18N"},
		{"[H]C(=C([H])C(=O)O)C(=O)O","1","MlcA","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20Y,21N,18N"},
		{"O=S(=O)([O-].[Na+])c3cc(N=Nc1cc(c(cc1C)C)S(=O)(=O)[O-].[Na+])c(O)c2ccccc23","1","Rd4","1N,2N,3Y,4Y,7N,16N,17N,19N,23Y,27Y,28Y,29N,33Y"},
		{"CCCCC(=O)SC","1","MTB","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20Y,21N,18N"},
		{"O=C(C)CCc1ccc(O)cc1","1","HBA","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30N,18N"},
		{"O=CC=Cc1ccccc1","1","Cnal","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30N,18N"},
		{"O=C(OC)c1ccccc1(O)","1","Msal","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30N,18N"},
		{"O=C(Oc1ccc(cc1)C(C)(C)C)c2ccccc2(O)","1","TBPS","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28Y,29Y,30N,18N"},
		{"O=CC=CCCC","1","tr2H","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20Y,21N,18N"},
		{"O=C(OCC(CC)CCCC)c1ccccc1(C(=O)OCC(CC)CCCC)","1","DEHP","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30N,18N"},
		{"O=CCC=CCC","1","Ci3H","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20Y,21N,18N"},
		{"O=Cc1ccccc1","1","Bzal","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30N,18N"},
		{"CC(=O)OC(C)c1ccccc1","1","MPCA","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30N,18N"},
		{"O=CC(c1ccccc1)C","1","2PP","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30N,18N"}		
	};
	public static String[][] compoundsClass2 ={
		{"O=C1C=COC(=C1(O))CC","2","EM","1N,2N,3N,5N,6N,7Y,8N,10N,11N,12N,22Y"},
		{"Oc1c(cc(cc1C(C)(C)C)C)C(C)(C)C","2","BHT","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30N,18Y"},
		{"O=C(C(=O)C)C","2","DiAc","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20N,22Y"},
		{"CSCCCN=C=S","2","MTPI","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20N,22Y"},
		{"O=C(C)CCCCC","2","2H","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20Y,21N,18Y"},
		{"O=C(OC1CC2CCC1(C)C2(C)(C))C","2","IBA","1N,2N,3N,5N,6N,7N,16N,17N,19N,23N,24N,25N,26N,22Y"},
		{"CCCCCCC(=O)OCC=C","2","AH","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20Y,21N,18Y"}
		
	};
	public static String[][] compoundsClass3 ={
		{"O=C(N)C=3C(=O)C4(O)(C(O)=C2C(=O)c1c(O)ccc(c1C(O)(C)C2CC4(C(C=3(O))N(C)C))Cl)","3","Cltcy","1N,2N,3Y,4N"},
		{"O=S(=O)([O-].[Na+])c5ccc(N=Nc3cc(c(O)c(N=Nc1ccc(c2ccccc12)S(=O)(=O)[O-].[Na+])c3(O))CO)c4ccccc45","3","CB","1N,2N,3Y,4Y,7N,16N,17N,19N,23Y,27Y,28Y,29N,33N"},
		{"O=C([O-].[Na+])c1ccccc1C3=C4C=C(C(=O)C(=C4(Oc2c3(cc(c([O-].[Na+])c2I)I)))I)I","3","Rd3","1N,2N,3Y,4N"},
		{"O=C2OC(COc1ccccc1(OC))CN2","3","Mox","1N,2N,3N,5N,6N,7Y,8N,10N,11Y,33N"},
		{"O=C(NCCC)NS(=O)(=O)c1ccc(cc1)Cl","3","ClPr","1N,2N,3Y,4N"},
		{"O=C(C1CC(CCC1C(C)C)C)NCC","3","NEC","1N,2N,3N,5N,6N,7N,16N,17N,19N,23N,24N,25N,26N,22N,33N"},
		{"O=C1OC(=CC(=O)C1C(=O)C)C","3","DHAc","1N,2N,3N,5N,6N,7Y,8Y,9N,20N,22N,33N"},
		{"CC1CC=CC=CC=CC=CC(CC(O)C(O)(=O)C(O)CC(=O)CC(O)CC2OC2(C=CC(=O)O1))OC3OC(C)C(O)C(N)C3(O)","3","Pim","1N,2N,3N,5N,6N,7Y,8N,10Y"},
		{"c1cc(ccc1OC)C=CC","3","Anth","1N,2N,3N,5N,6Y"},
		{"c1ccc(cc1)c2ccccc2","3","BiP","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28Y,29N,33N"},
		{"O=C([O-])C(=O)[O-].[Sn+2]","3","TS","1N,2N,3Y,4N"},
		{"OCCCl","3","ECH","1N,2N,3Y,4N"},
		{"[Cl-].[NH3+]C1CCCCC1","3","CHA","1N,2N,3Y,4Y,7N,16N,17N,19N,23N,24N,25N,26N,22N,33N"},
		{"O=C(N)c1cc(cc(c1)N(=O)=O)N(=O)=O","3","DNB","1N,2Y"},
		{"c1ccc(cc1)N=Nc3c(ccc2ccccc23)N","3","YAB","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28Y,29N,33N"},
		{"N(=Nc1c(N)ccc2ccccc12)c3ccccc3C","3","YOB","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28Y,29N,33N"},
		{"O=S(=O)(O[Be]OS(=O)(=O)c3cc(c(cc3(N=Nc1c(O)ccc2ccccc12))C)Cl)c6cc(c(cc6(N=NCc4c(O)ccc5ccccc45))C)Cl","3","Rd9","1N,2N,3Y,4N"},
		{"O=S(=O)([O-].[Na+])c4c(N=Nc1ccc2ccccc2(c1(O)))ccc3ccccc34","3","Rd10","1N,2N,3Y,4Y,7N,16N,17N,19N,23Y,27Y,28Y,29N,33N"},
		{"O=C(NN)c1ccncc1","3","NAH","1N,2N,3N,5N,6N,7Y,8N,10N,11Y,33N"},
		{"O=C(O)c1cc(cc(c1(OC))Cl)Cl","3","MCB","1N,2N,3Y,4N"},
		{"C1=CC=CC=C1C2=C(C(=O)OCC)OC=C2","3","PCF","1N,2N,3N,5N,6N,7Y,8N,10N,11N,12Y,13Y,14Y,15N,33N"},  // "O=C(OCC)c1occc1c2ccccc2" this is the same compound, but aromaticity is not detected
		{"O=C([O-].[Na+])C(C)(Cl)Cl","3","SCP","1N,2N,3Y,4N"},
		{"Clc1cc(OP(=S)(OC)OC)c(cc1(Cl))Cl","3","Ron","1N,2N,3Y,4N"},
		{"O(c1ccc(cc1)Cl)C(Oc2ccc(cc2)Cl)C","3","BCPM","1N,2N,3Y,4N"},
		{"O=C(OC)c1ccccc1(NC)","3","DMA","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28N,30Y,31N,32N,22N,33N"},
		{"O=C(Oc2cccc1ccccc12)NC","3","NNMC","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28Y,29N,33N"},
		{"O=S(=O)(OCCOc1ccc(cc1Cl)Cl)[O-].[Na+]","3","Ses","1N,2N,3Y,4N"},
		{"c1ccc2c(c1)cccc2(NC(N)=S)","3","NTU","1N,2N,3N,5N,6N,7N,16N,17N,19N,23Y,27Y,28Y,29N,33N"},
		{"O(N(OCNc1ccc(c(c1)Cl)Cl)C)C","3","Lin","1N,2N,3Y,4N"},
		{"CC(=NOC(=O)NC)SC","3","Met","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20N,22N,33N"},
		{"O=S(OCCCl)OC(C)COc1ccc(cc1)C(C)(C)C","3","Ara","1N,2N,3Y,4N"},
		{"Oc2c(Cc1c(O)c(cc(c1Cl)Cl)Cl)c(c(cc2Cl)Cl)Cl","3","HCP","1N,2N,3Y,4N"},
		{"O=N(=O)c1cc(c(c(c1)C(=O)N)C)N(=O)=O","3","DNTa","1N,2Y"},
		{"N#Cc1c(cccc1Cl)Cl","3","DCBN","1N,2N,3Y,4N"},
		{"N=C(N)NCCCCCCCCCCCC.O=C(O)C","3","Dod","1N,2N,3N,5N,6N,7N,16N,17N,19Y,20N,22N,33N"},
		{"CCCC[Sn](CCCC)(Cl)Cl","3","DBTC","1N,2N,3Y,4N"},
		{"O=P(Oc1ccc(cc1Cl)C(C)(C)C)(OC)NC","3","Rue","1N,2N,3Y,4N"},
		{"O=C2ON=C(C2(=NNc1ccccc1Cl))C","3","Draz","1N,2N,3Y,4N"},
		{"O=S(=O)(Oc1ccc(cc1)Cl)c2ccc(cc2)Cl","3","CPCB","1N,2N,3Y,4N"},
		{"c1cc(ccc1Cl)Sc2cc(c(cc2Cl)Cl)Cl","3","TDS","1N,2N,3Y,4N"},
		{"Clc1c(Cl)c(Cl)c(c(Cl)c1(Cl))Cl","3","HClB","1N,2N,3Y,4N"},
		{"C1N(CSC(N1C)=S)C","3","Myl","1N,2N,3N,5N,6N,7Y,8N,10N,11Y,33N"},
		{"C[Hg+].[Cl-]","3","MMC","1N,2N,3Y,4N"},		
	};	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(CramerDataTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for CramerDataTest.
	 * @param arg0
	 */
	public CramerDataTest(String arg0) {
		super(arg0);
		try {
			cr = new CramerRules();
			cr.setResiduesIDVisible(false);
			TTLogger.configureLog4j(false);
		} catch (DecisionMethodException x) {
			fail();
		}	
	}
	
	protected  IDecisionResult classify(String smiles,String id,CramerRules rules){
		IDecisionResult result = rules.createDecisionResult();
		
		result.setDecisionMethod(rules);
		IAtomContainer mol = (IAtomContainer) FunctionalGroups.createAtomContainer(smiles,id);
		try {
			result.classify(mol);
		} catch (DecisionResultException x) {
			x.printStackTrace();
			logger.error(x);
			result = null;
		}
		return result;		
	}
	protected int classify(String[][] molecules, int classID) {
		int success = 0;
		logger.error("These compounds should be of class\t",classID);
		System.err.println("\nThese compounds should be of class\t"+classID);
		for (int i=0; i < molecules.length; i++ ) {
			IDecisionResult result = classify(molecules[i][0],molecules[i][2],cr);
			if (result == null) {
				logger.error("Error on processing molecule\t",molecules[i][2]);
				System.err.println("Error on processing molecule\t"+molecules[i][2]);
			} else {
				boolean ok = (result.getCategory().getID() == classID);
				if (ok) success++;
				
				String explanation = "";
				try {
					    explanation = cr.explainRules(result,false).toString();
				} catch (DecisionMethodException x) {
						logger.error(x);
				}
				if (ok) {
					if (!explanation.equals(molecules[i][3])) {
						logger.warn(molecules[i][0],"\t",molecules[i][2],"\t",result.getCategory()+"\t"+explanation);
						System.out.println(result.getCategory()+"\t" + molecules[i][2]+"\t"+explanation+"\t"+molecules[i][0]);
						System.out.println("Should be\t" + molecules[i][3]);
					}
				} else {
					logger.error(molecules[i][0],"\t",molecules[i][2],"\t",result.getCategory()+"\t"+explanation);
					System.err.println(result.getCategory()+"\t" + molecules[i][2]+"\t"+explanation+"\t"+molecules[i][0]);
					System.err.println("Should be\t" + molecules[i][3]);
				}
				
			}
		}
		System.err.println("Wrong classification of " + (molecules.length-success) + " compounds");
		return success;
	}
	
	public void testClass1() {
		SMILES_PARSER p = SmilesParserWrapper.getInstance().getParser();
		//junit.framework.AssertionFailedError: expected:<31> but was:<22>
		
		SmilesParserWrapper.getInstance().setParser(SMILES_PARSER.OPENBABEL);
		assertEquals(compoundsClass1.length,classify(compoundsClass1,1));
		SmilesParserWrapper.getInstance().setParser(p);
		
	}
	//junit.framework.AssertionFailedError: expected:<7> but was:<6>
	
	public void testClass2() {
		assertEquals(compoundsClass2.length,classify(compoundsClass2,2));
	}
	//junit.framework.AssertionFailedError: expected:<43> but was:<42>
	
	public void testClass3() {
		assertEquals(compoundsClass3.length,classify(compoundsClass3,3));
	}	
	protected void writeCompounds(String[][] array, DataOutputStream f) throws IOException {
		for (int i=0; i < array.length; i++) {
			for (int j=0; j < array[i].length; j++) {
				if (j==3) f.writeBytes("\"");
				f.writeBytes(array[i][j]);
				if (j==3) f.writeBytes("\"");
				else f.writeBytes(",");
			}
			f.writeBytes("\n");
		}
	}
	public void testWriteCompounds() {
		try {
			DataOutputStream f = new DataOutputStream(
					new FileOutputStream("appendix1.csv"));
			f.writeBytes("SMILES,CLASS,Name,Path\n");
			writeCompounds(compoundsClass1,f);
			writeCompounds(compoundsClass2,f);
			writeCompounds(compoundsClass3,f);
			f.close();
		} catch (IOException x) {
			x.printStackTrace();
		}
	}
	
	public void testCa() {
		fail("[Ca+2].O=C[O-].O=C[O-]  - assigns class I, but doesn't highlight it");
	}
}
