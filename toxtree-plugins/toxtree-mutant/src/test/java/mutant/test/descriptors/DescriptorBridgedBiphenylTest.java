package mutant.test.descriptors;

import mutant.descriptors.DescriptorBridgedBiphenyl;

import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.qsar.IMolecularDescriptor;

public class DescriptorBridgedBiphenylTest extends DescriptorsTest {
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		addPropertiesToTest("I(BiBr)_Expected", "I(BiBr)");

	}

	/*
	 * public void testCalculate() { DescriptorBridgedBiphenyl d = new
	 * DescriptorBridgedBiphenyl(); Object[][] test = new Object[][] {
	 * {"c1cc(ccc1Oc2ccc(cc2)",true}, {"c1cc(ccc1Cc2ccc(cc2)(NCCC)",true},
	 * {"c1cc(ccc1c2ccc(cc2)",false}, {"c1cc(ccc1OCc2ccc(cc2)",false}, }; try {
	 * for (int i=0; i < test.length;i++) { IAtomContainer ac =
	 * FunctionalGroups.createAtomContainer(test[i][0].toString(), true);
	 * HueckelAromaticityDetector.detectAromaticity(ac);
	 * 
	 * BooleanResult r = (BooleanResult) ((DescriptorValue)
	 * d.calculate(ac)).getValue(); assertEquals( new
	 * Boolean(test[i][1].toString()).booleanValue(), r.booleanValue());
	 * 
	 * //System.out.println(test[i][0]+"\t"+r.booleanValue()); } } catch
	 * (CDKException x) { fail(x.getMessage()); } }
	 */
	@Override
	protected IMolecularDescriptor createDescriptorToTest() throws Exception {
		return new DescriptorBridgedBiphenyl();
	}

	@Override
	public String getResultsFile() {
		return "aromatic_amines/qsar8train_BiBr.csv";
	}

	@Override
	public String getSourceFile() {
		return "aromatic_amines/qsar8train.csv";

	}

	@Override
	public String getStructureID() {
		return "CAS Number";
	}

	/**
	 * In substance ammine192,the amino group in the bridge is not considered as
	 * the functional one, as there is another amino group (not in the bridge)
	 * that is more suitable to be the functional one. So, in similar cases, if
	 * the amino group in the bridge is the only one in the molecule, then it
	 * should be the functional one and the molecule will be treated as aniline
	 * like (so that I(An)=1; I(Bibr)=0); however, if another amino group is
	 * present on the aromatic ring, this will be the functional one (so that
	 * I(An)=0 and I(Bibr)=1).
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAmmine192() throws Exception {

		Object[][] smiles = new Object[][] { { "C1=CC=C(C=C1)NC2=CC=C(C=C2)N", "I(BiBr)", new Boolean(true) }, };
		calculate(smiles);
	}

	@Test
	public void testNBridge() throws Exception {

		Object[][] smiles = new Object[][] { { "CC(C)OC1=CC=C(C=C1)NC2=CC=CC=C2", "I(BiBr)", new Boolean(false) }, };
		calculate(smiles);
	}

	@Test
	public void testHeteroRing() throws Exception {

		Object[][] smiles = new Object[][] {
				{ "C=1C=CC(=C(C=1)NC2=NC(=NC(=N2)Cl)Cl)Cl", "I(BiBr)", new Boolean(false) },
				{ "CC1=CC=CC(=C1(C))N([H])C=2C=C(N=C(N=2)SCC(O)=O)Cl", "I(BiBr)", new Boolean(false) },
				{ "CC1=CC=CC(=C1(C))CC=2C=C(N=C(N=2)SCC(O)=O)Cl", "I(BiBr)", new Boolean(false) }, };
		calculate(smiles);
	}
}
