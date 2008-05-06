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
package mutant.test;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;
import mutant.BB_CarcMutRules;

import org.openscience.cdk.Molecule;
import org.openscience.cdk.interfaces.IMolecule;

import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRuleList;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.io.IteratingDelimitedFileReader;
import toxTree.logging.TTLogger;
import toxTree.query.FunctionalGroups;
import toxTree.ui.tree.actions.SaveTreeAction;


public class MutantDataTest extends TestCase {
	protected BB_CarcMutRules cr = null;
	static protected TTLogger logger = new TTLogger(MutantDataTest.class);
	//smiles,class,name,tree path
	/*public static String[][] compoundsClass1 ={
		{"CC1(C(CC=C1C)C=CC(C)(C)C(O)C)C","1","R38-I",""},
			
	};
	public static String[][] compoundsClass2 ={
		{"O=C1C=COC(=C1(O))CC","2","EM","1N,2N,3N,5N,6N,7Y,8N,10N,11N,12N,22Y"},
			
	};
	//smiles,class,name,tree path,MeltingPoint,LogKow,LipidSolubility,MoleculWeight,SurfaceTension,VapourPressure,AqueousSolubility
	public static String[][] compoundsClass3 ={
		{"BrCCCCCCBr","3","1.6 - Dibromohexane","","7.880843","3.60806786664511","423","243.979991","0","0.250285","10.293935"}
				
	};	
	
	public static String[][] compoundsClass4 ={
		{"CC1(C(CC=C1C)C=CC(C)(C)C(O)C)C","4","(+/-) trans-3,3-dimethyl-5-(2,2,3-trimethyl-cyclopent-3-en-1-yl)-pent-4-en-2-ol","","63.639511","3.61402048827646","0.2","222.371995","0","0.000125","2.710853"
		}
				
	};*/
	public static String[][] compoundsClass =new String[100][11];
	public static String[][] compoundsClass_1 =new String[9][11];
	public static void main(String[] args) {
		junit.textui.TestRunner.run(MutantDataTest.class);
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
	public MutantDataTest(String arg0) {
		super(arg0);
		try {
			cr = new BB_CarcMutRules();
			cr.setResiduesIDVisible(false);
			TTLogger.configureLog4j(false);
		} catch (DecisionMethodException x) {
			fail();
		}	
	}
	
	protected  IDecisionResult classify(String smiles,String id,BB_CarcMutRules rules){
		IDecisionResult result = rules.createDecisionResult();
		
		result.setDecisionMethod(rules);
		IMolecule mol = (IMolecule) FunctionalGroups.createAtomContainer(smiles,id);
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
	
	/*public void testClass1() {
		assertEquals(compoundsClass1.length,classify(compoundsClass1,1));
	}
	public void testClass2() {
		assertEquals(compoundsClass2.length,classify(compoundsClass2,2));
	}
	public void testClass3() {
		assertEquals(compoundsClass3.length,classify(compoundsClass3,3));
	}
	public void testClass4() {
		assertEquals(compoundsClass4.length,classify(compoundsClass4,4));
	}*/
	public void testClass() {
	    testCSVFile(compoundsClass);
		assertEquals(compoundsClass.length,classify_without_classID(compoundsClass));
	}
	public void testClass1() {
		testCSVFile1(compoundsClass_1);
		assertEquals(compoundsClass_1.length,classify_without_classID(compoundsClass_1));
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
			//writeCompounds(compoundsClass1,f);
			//writeCompounds(compoundsClass2,f);
			//writeCompounds(compoundsClass3,f);
			//writeCompounds(compoundsClass4,f);
			//writeCompounds(compoundsClass,f);
			f.close();
		} catch (IOException x) {
			x.printStackTrace();
		}
	}
	public void testCSVFile(String[][] molecules) {
        String filename = "toxTree/test/tree/sicret/BfR_irritation.csv";
        System.out.println("Testing: " + filename);
        
        //molecules = new String[118][11];
        try {
        	InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
        //FileInputStream ins = new FileInputStream(filename);
        
            IteratingDelimitedFileReader reader = new IteratingDelimitedFileReader(ins);
            
            int molCount = 0;
            while (reader.hasNext()) {
                Object object = reader.next();
                assertNotNull(object);
                if(object instanceof IMolecule){
              
                IMolecule mol = (IMolecule)object; 
                //smiles,class,name,tree path,MeltingPoint,LogKow,LipidSolubility,MoleculWeight,SurfaceTension,VapourPressure,AqueousSolubility
                molecules[molCount][0] = mol.getProperty("SMILES").toString();               
                molecules[molCount][1] = "1";
                molecules[molCount][2] = mol.getProperty("Name").toString();
                molecules[molCount][3] = "";
                if(mol.getProperty("Melting Point").toString().equals("#N/A") || mol.getProperty("Melting Point").toString().length() == 0){
                	molecules[molCount][4] = "0";
                }
                else{
                	molecules[molCount][4] = mol.getProperty("Melting Point").toString();
                }
                if(mol.getProperty("log P").toString().equals("#N/A") || mol.getProperty("log P").toString().length() == 0 ){
                	molecules[molCount][5] = "0";
                }
                else{
                	molecules[molCount][5] = mol.getProperty("log P").toString();
                }
                molecules[molCount][6] = "423";
                if(mol.getProperty("Molecular Mass").toString().equals("#N/A") || mol.getProperty("Molecular Mass").toString().length() == 0 ){
                	molecules[molCount][7] = "0";
                }
                else{
                	molecules[molCount][7] = mol.getProperty("Molecular Mass").toString();
                }
                molecules[molCount][8] = "0";
                if(mol.getProperty("Vapour Pressure").toString().equals("#N/A") || mol.getProperty("Vapour Pressure").toString().length() == 0 ){
                	molecules[molCount][9] = "62.0";
                }
                else{
                	 molecules[molCount][9] = mol.getProperty("Vapour Pressure").toString();
                }
                if(mol.getProperty("Water Solubility").toString().equals("#N/A") || mol.getProperty("Water Solubility").toString().length() == 0){
                	molecules[molCount][10] = "0";
                }
                else{
                	 molecules[molCount][10] = mol.getProperty("Water Solubility").toString();
                }
                }
                molCount++;
            }
                
            System.out.println("MolCount: " + molCount);
            //assertEquals(88, molCount);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
	public void testCSVFile1(String[][] molecules) {
        String filename = "toxTree/test/tree/sicret/BfR_corrosion.csv";
        System.out.println("Testing: " + filename);
        //InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
        //molecules = new String[118][11];
        try {
        	InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
        //FileInputStream ins = new FileInputStream(filename);
        
            IteratingDelimitedFileReader reader = new IteratingDelimitedFileReader(ins);
            
            int molCount = 0;
            while (reader.hasNext()) {
                Object object = reader.next();
                assertNotNull(object);
                assertTrue((object instanceof Molecule));
                /*if(molCount==1){
                	Molecule mol = (Molecule)object; 
                	Hashtable temp = mol.getProperties();
                	Enumeration e  = temp.keys();            		
                	while(e.hasMoreElements()){ 
                		 System.out.println(e.nextElement().toString());
                		
                	}
                }*/
                if(object instanceof Molecule){
              
                Molecule mol = (Molecule)object; 
                //smiles,class,name,tree path,MeltingPoint,LogKow,LipidSolubility,MoleculWeight,SurfaceTension,VapourPressure,AqueousSolubility
                molecules[molCount][0] = mol.getProperty("SMILES").toString();               
                molecules[molCount][1] = "1";
                molecules[molCount][2] = mol.getProperty("NAME").toString();
                molecules[molCount][3] = "";
                if(mol.getProperty("Meltingpt").toString().equals("#N/A") || mol.getProperty("Meltingpt").toString().length() == 0){
                	molecules[molCount][4] = "0";
                }
                else{
                	molecules[molCount][4] = mol.getProperty("Meltingpt").toString();
                }
                if(mol.getProperty("LogKow").toString().equals("#N/A") || mol.getProperty("LogKow").toString().length() == 0 ){
                	molecules[molCount][5] = "0";
                }
                else{
                	molecules[molCount][5] = mol.getProperty("LogKow").toString();
                }
                molecules[molCount][6] = "423";
                /*if(mol.getProperty("Molecular Mass").toString().equals("#N/A") || mol.getProperty("Molecular Mass").toString().length() == 0 ){
                	molecules[molCount][7] = "0";
                }
                else{
                	molecules[molCount][7] = mol.getProperty("Molecular Mass").toString();
                }*/
                molecules[molCount][7] = "0";
                molecules[molCount][8] = "0";
                if(mol.getProperty("Vapour Pressure").toString().equals("#N/A") || mol.getProperty("Vapour Pressure").toString().length() == 0 ){
                	molecules[molCount][9] = "0";
                }
                else{
                	 molecules[molCount][9] = mol.getProperty("Vapour Pressure").toString();
                }
                if(mol.getProperty("Water solub(exp)").toString().equals("#N/A") || mol.getProperty("Water solub(exp)").toString().length() == 0){
                	molecules[molCount][10] = "0";
                }
                else{
                	 molecules[molCount][10] = mol.getProperty("Water solub(exp)").toString();
                }
                }
                molCount++;
            }
                
            System.out.println("MolCount: " + molCount);
            //assertEquals(88, molCount);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
	 protected int classify_without_classID(String[][] molecules) {
			int success = 1;
			//testCSVFile(molecules);
			//testCSVFile1(molecules);
			
			for (int i=0; i < molecules.length; i++ ) {
				//System.out.println("Number:"+i+" "+molecules[i][0]+"/"+molecules[i][2]+"/"+molecules[i][4]+"/"+molecules[i][5]+"/"+molecules[i][6]+"/"+molecules[i][7]+"/"+molecules[i][8]+"/"+molecules[i][9]+"/"+molecules[i][10]);
				IDecisionResult result = classify(molecules[i][0],molecules[i][2],cr);
				if (result == null) {
					logger.error("Error on processing molecule\t",molecules[i][2]);
					System.err.println("Error on processing molecule\t"+molecules[i][2]);
				} else {
					//boolean ok = (result.getCategory().getID() == classID);
					//if (ok) success++;
					
					String explanation = "";
					try {
						    explanation = cr.explainRules(result,false).toString();
					} catch (DecisionMethodException x) {
						System.out.println(x);
							logger.error(x);
					}
					//logger.warn(molecules[i][0],"\t",molecules[i][2],"\t",result.getCategory()+"\t"+explanation);
					System.out.println("Number:"+i+" "+result.getCategory()+"\t" + molecules[i][2]+"\t"+explanation+"\t"+molecules[i][0]);
					//System.out.println("Should be\t" + molecules[i][3]);
					
					
				}
			}
			//System.err.println("Wrong classification of " + (molecules.length-success) + " compounds");
			return success;
		}
	public void test() {

		IDecisionResult r = classify("CCC=CC=O", "CCC=CC=O", cr);
		System.out.println(r.getAssignedCategories());
	}
	public void testReport() {
		try {
			BB_CarcMutRules rules = new BB_CarcMutRules();
			SaveTreeAction a = new SaveTreeAction(rules);
			a.actionPerformed(null);
		} catch (Exception x) {
			fail(x.getMessage());
		}
	}
    public void testHasUnreachableRules() {
    	IDecisionRuleList unvisited = cr.hasUnreachableRules();
    	if ((unvisited == null) || (unvisited.size()==0))
    		assertTrue(true);
    	else {
    		System.out.println("Unvisited rules:");
    		System.out.println(unvisited);
    		fail();
    	} 
    	
    }	
}
