package toxTree.cramer;

import java.io.InputStream;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionResult;
import toxTree.tree.cramer.CramerRules;
import ambit2.base.config.Preferences;
import ambit2.core.io.IteratingDelimitedFileReader;

public class CompatibilityTest {

	protected static CramerRules cr ;

	@BeforeClass
	public static void setup() throws Exception {
		cr = new CramerRules();
		cr.setResiduesIDVisible(false);

	}
/**
        CAS	  v1.0	  v1.6	  v2.5	Correct	Predicted	OK	Name
   106-35-4	2	1	2	2	2	true	BUTYL ETHYL KETONE	CCCCC(=O)CC
   110-43-0	2	1	2	2	2	true	METHYL AMYL KETONE	CCCCCC(=O)C
    60-18-4	1	1	2	1	1	true	TYROSINE (L-)	N[C@@H](Cc1ccc(O)cc1)C(=O)O
    63-91-2	1	1	2	1	1	true	PHENYLALANINE (L-)	NC(Cc1ccccc1)C(=O)O
    74-79-3	1	1	2	1	1	true	ARGININE (L-)	N=C(N)NCCCC(N)C(=O)O
   109-94-4	1	1	3	1	1	true	ETHYL FORMATE	CCOC=O
    50-81-7	1	1	3	1	1	true	ASCORBIC ACID (L-)	O=C1O[C@H]([C@H](CO)O)C(O)=C1O
    60-29-7	1	1	3	1	1	true	ETHYL ETHER	CCOCC
  6381-77-7	1	1	3	1	1	true	SODIUM ERYTHORBATE	O=C1O[C@H]([C@@H](CO)O)C(O)=C1O
  7235-40-7	2	2	1	1	1	true	BETA-CAROTENE	CC(/C=C/C=C(C)/C=C/C1=C(C)CCCC1(C)C)=C\C=C\C=C(C)\C=C\C=C(C)\C=C/C1=C(C)CCCC1(C)C
    77-92-9	2	2	1	1	1	true	CITRIC ACID	OC(CC(O)(CC(O)=O)C(O)=O)=O
   121-32-4	2	2	3	2	2	true	ETHYL VANILLIN	CCOc1cc(C=O)ccc1O
    59-02-9	2	2	3	3	3	true	TOCOPHEROL (D-)	CC(C)CCCC(C)CCCC(CCCC1(C)Oc2c(c(C)c(O)c(C)c2C)CC1)C
    94-86-0	2	2	3	3	3	true	ETHOXY-PROPENYLPHENOL	C/C=C/c1ccc(OCC)c(O)c1
 25085-02-3	2	3	2	2	3	false	ACRYLAMIDE/SODIUM ACRYLATE COPOLYMER (Comonomer)	C=CC(N)=O

 * @throws Exception
 */
	@Test
	public void testOBParser() throws Exception {
		Preferences.setProperty(Preferences.SMILESPARSER,"true");
		test();
	}
/**

CAS	v1.0	v1.6	v2.5	Correct	Predicted	OK	Name
   106-35-4	2	1	2	2	2	true	BUTYL ETHYL KETONE	CCCCC(=O)CC
   110-43-0	2	1	2	2	2	true	METHYL AMYL KETONE	CCCCCC(=O)C
    60-18-4	1	1	2	1	1	true	TYROSINE (L-)	N[C@@H](Cc1ccc(O)cc1)C(=O)O
    63-91-2	1	1	2	1	1	true	PHENYLALANINE (L-)	NC(Cc1ccccc1)C(=O)O
    74-79-3	1	1	2	1	1	true	ARGININE (L-)	N=C(N)NCCCC(N)C(=O)O
   109-94-4	1	1	3	1	3	false	ETHYL FORMATE	CCOC=O
    50-81-7	1	1	3	1	1	true	ASCORBIC ACID (L-)	O=C1O[C@H]([C@H](CO)O)C(O)=C1O
    60-29-7	1	1	3	1	3	false	ETHYL ETHER	CCOCC
  6381-77-7	1	1	3	1	1	true	SODIUM ERYTHORBATE	O=C1O[C@H]([C@@H](CO)O)C(O)=C1O
  7235-40-7	2	2	1	1	1	true	BETA-CAROTENE	CC(/C=C/C=C(C)/C=C/C1=C(C)CCCC1(C)C)=C\C=C\C=C(C)\C=C\C=C(C)\C=C/C1=C(C)CCCC1(C)C
    77-92-9	2	2	1	1	1	true	CITRIC ACID	OC(CC(O)(CC(O)=O)C(O)=O)=O
   121-32-4	2	2	3	2	3	false	ETHYL VANILLIN	CCOc1cc(C=O)ccc1O
    59-02-9	2	2	3	3	3	true	TOCOPHEROL (D-)	CC(C)CCCC(C)CCCC(CCCC1(C)Oc2c(c(C)c(O)c(C)c2C)CC1)C
    94-86-0	2	2	3	3	3	true	ETHOXY-PROPENYLPHENOL	C/C=C/c1ccc(OCC)c(O)c1
 25085-02-3	2	3	2	2	3	false	ACRYLAMIDE/SODIUM ACRYLATE COPOLYMER (Comonomer)	C=CC(N)=O


 * @throws Exception
 */
	@Test
	public void testCDKParser() throws Exception {
		Preferences.setProperty(Preferences.SMILESPARSER,"false");
		test();		
	}	
	public void test() throws Exception {
		IDecisionResult result = cr.createDecisionResult();
		result.setDecisionMethod(cr);
		
		InputStream in = getClass().getClassLoader().getResourceAsStream("compatibility/Toxtree1_6.csv");
		Assert.assertNotNull(in);
		IteratingDelimitedFileReader reader = new IteratingDelimitedFileReader(in);
		System.out.println(String.format("%11s\t%6s\t%6s\t%6s\t%6s\t%6s\t%s\t%s","CAS","v1.0","v1.6","v2.5","Correct","Predicted","OK","Name","SMILES"));
		while (reader.hasNext()) {
			Object object = reader.next();
			Assert.assertTrue(object instanceof IAtomContainer);
			IAtomContainer ac = ((IAtomContainer) object);
			result.classify(ac);	
			int classID = Integer.parseInt(ac.getProperty("Correct").toString());
			boolean ok = (result.getCategory().getID() == classID);
			System.out.println(
			String.format("%11s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
						ac.getProperty("CAS"),
						ac.getProperty("v1.0").toString(),
						ac.getProperty("v1.6").toString(),
						ac.getProperty("v2.5").toString(),
						classID,
						result.getCategory().getID(),
						ok,						
						ac.getProperty("Name"),
						ac.getProperty("SMILES")
						)
						);
		}
		reader.close();
	}
	

}
