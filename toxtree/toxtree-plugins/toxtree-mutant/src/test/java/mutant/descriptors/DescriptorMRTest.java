package mutant.test.descriptors;

import junit.framework.TestCase;
import mutant.descriptors.DescriptorMolarRefractivity;

import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.result.DoubleResult;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.query.FunctionalGroups;

public class DescriptorMRTest extends TestCase {
	public void test() {
		DescriptorMolarRefractivity mr = new DescriptorMolarRefractivity();
		//Values taken from Benigni QSAR13
		Object[][] test = new Object[][] {
				{"C=CC=O",new Double(1.655)},
				{"C=C(Cl)C=O",new Double(2.147)},
				{"BrC(=C)C=O",new Double(2.432)},
				{"C\\C=C\\C=O",new Double(2.119)},
				{"C\\C=C\\C=C\\C=O",new Double(3.325)},				
				{"O=C\\C=C\\c1ccccc1",new Double(4.394)},
				{"C\\C(C)=C\\CCC(\\C)=C\\C=O",new Double(4.876)},
				{"COc1ccccc1/C=C/C=O",new Double(5.011)},
				{"ClC(Cl)C(\\C=O)=C(/Cl)Cl",new Double(4.085)},
				{"C\\C=C(/Cl)C=O",new Double(1.173)},
				{"C/C(C)=C(/Cl)C=O",new Double(3.074)},
				{"CCC(=C)C=O",new Double(2.583)},
				{"CCCC(=C)C=O",new Double(3.047)},
				{"CCCC\\C=C\\C=O",new Double(3.51)},
				{"Cl\\C(=C/c1ccccc1)C=O",new Double(4.886)},
				{"Br\\C(C=O)=C/c1ccccc1",new Double(5.171)}				
		};
		try {
			System.out.println("Smiles\tExpected\tCalculated");
			for (int i=0; i < test.length;i++) {
				IAtomContainer ac = FunctionalGroups.createAtomContainer(test[i][0].toString());
				AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(ac);
				CDKHueckelAromaticityDetector.detectAromaticity(ac);
				
				DoubleResult r = (DoubleResult) ((DescriptorValue) mr.calculate(ac)).getValue();
				assertEquals(
						((Double) test[i][1]).doubleValue()*10,
						r.doubleValue(),
						1E-1);

				
				System.out.println(test[i][0]+"\t"+test[i][1]+"\t"+r.doubleValue());
			}
//			fail("Verify MR values");
		} catch (CDKException x) {
			fail(x.getMessage());
		}		
	}
}
