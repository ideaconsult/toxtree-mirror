package mutant.test.descriptors;

import junit.framework.TestCase;
import mutant.descriptors.AromaticAmineSubstituentsDescriptor;

import org.jmol.util.Logger;
import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.result.DoubleArrayResult;
import org.openscience.cdk.qsar.result.IDescriptorResult;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import ambit2.core.helper.CDKHueckelAromaticityDetector;
import ambit2.core.smiles.SmilesParserWrapper;
import ambit2.core.smiles.SmilesParserWrapper.SMILES_PARSER;

public class AromaticAmineSubstituentsDescriptorTest extends TestCase {
	protected AromaticAmineSubstituentsDescriptor adescriptor;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		adescriptor = new AromaticAmineSubstituentsDescriptor();
		SmilesParserWrapper.getInstance(SMILES_PARSER.OPENBABEL);
	}

	@Test
	public void test() throws Exception {

		IAtomContainer a = FunctionalGroups.createAtomContainer("c1(CC)c(O)c(Cl)c(P(=O)(F)F)c(N)c(S)1", false);
		AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(a);
		CDKHueckelAromaticityDetector.detectAromaticity(a);
		DescriptorValue value = adescriptor.calculate(a);
		IDescriptorResult r = value.getValue();
		if (r instanceof DoubleArrayResult) {

			for (int i = 0; i < value.getNames().length; i++) {
				System.out.print(value.getNames()[i]);
				System.out.print(" = ");
				System.out.println(((DoubleArrayResult) r).get(i));
			}
		} else
			System.out.println(r.getClass().getName());

	}

	@Test
	public void testChain() throws Exception {

		Object[][] smiles = new Object[][] { { "CCCCC=1C=C(C=CC=1(N))C2=CC=CC=C2", "MR2", new Double(1.96), "MR3",
				new Double(0.1), "MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * iv) if two amino groups in ortho position are present and there are other
	 * substituents attached to the ring, the amino group that is considered to
	 * be the functional group is that which allow to assign the minimum
	 * position number to the substituent.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSubRule4() throws Exception {
		Object[][] smiles = new Object[][] {
		// ammine89
		// {"NC=1C=CC(=CC=1(N))Cl","MR3",new Double(0.1),"MR5",new
		// Double(0.1),"MR6",new Double(0.1)},
		// ammine157
		// {"NC=1C=CC(=CC=1(N))C=2C=CC(N)=C(N)C=2","MR3",new
		// Double(0.1),"MR5",new Double(0.1),"MR6",new Double(0.1)},
		};
		amineGroupSubstituent(smiles);
	}

	/**
	 * the numbering is assigned in order to give the minimum position number to
	 * the second amino group.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSubRule4_ammine195() throws Exception {
		Object[][] smiles = new Object[][] {
		// ammine 195
		// In the following molecule, the numbering is assigned in order to give
		// the minimum position number to the second amino group.
		{ "NC=1C(F)=C(N)C(F)=C(F)C=1(F)", "MR3", new Double(0.54), "MR5", new Double(0.09), "MR6", new Double(0.09) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * For this compound ToxTree was not able to calculate partial MR5. It
	 * should be: MR3 0.1, MR5 0.54 MR6 =0.1 In fact, as in case of fluorenes,
	 * the substituents in position 4 and 5 (in the following molecule) have to
	 * be considered CH3 and NH2, respectively.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRodo996() throws Exception {
		Object[][] smiles = new Object[][] { { "CC1=CC3=C(C=C1(N))NC=2C=CC=CC=23", "MR3", new Double(0.1), "MR5",
				new Double(0.54), "MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * (ID 86) Rodo105 should be: MR3 0.8, MR5 0.1 MR6 =0.1 In cases like this,
	 * you can consider the more extended aromatic system as the one bearing the
	 * functional amino group.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExtendedAromaticSystem_Rodo105() throws Exception {
		Object[][] smiles = new Object[][] { { "c1ccc(cc1)Nc2ccc3ccccc3(c2)", "MR2", new Double(0.1), "MR3",
				new Double(0.8), "MR4", new Double(0.8), "MR5", new Double(0.1), "MR6", new Double(0.1) },
		// {"C=1C=CC2=CC(=CC=C2(C=1))NC3=CC=C(C=C3)NC4=CC=C5C=CC=CC5(=C4","MR3",new
		// Double(0.8),"MR5",new Double(0.1),"MR6",new Double(0.1)},
		};
		amineGroupSubstituent(smiles);
	}

	/**
	 * (ID 88) Rodo1109 should be: MR3 0.69, MR5 0.1 MR6 =0.1, with the
	 * following numbering of ring positions In fact, in case of fluorenes, the
	 * substituents in positions 2 and 3 (in the following example) have to be
	 * consdidered as CH3. In case position a of the molecule is substituted,
	 * the partial MR changes accordingly, ex.: <br>
	 * a=CH2 (fluorene), partial MR = 0.56 (CH3)<br>
	 * a=NH (carbazole), partial MR = 0.54 (NH2)<br>
	 * a= C=O (this case), partial MR = 0.69 (CHO)<br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRodo1109() throws Exception {
		Object[][] smiles = new Object[][] { { "CC(=O)NC1=CC=C2C=3C=CC=CC=3(C(=O)C2(=C1))", "MR3", new Double(0.69),
				"MR5", new Double(0.1), "MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * should be: MR2 2.98 (C6H6N), MR3 0.1 MR6 =0.1
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAmmine13() throws Exception {
		Object[][] smiles = new Object[][] { { "c1cccc(N)c1c2c(N)cccc2", "MR2", new Double(2.98), "MR3",
				new Double(0.1), "MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	@Test
	public void testOrtho() throws Exception {
		Object[][] smiles = new Object[][] {
				{ "Nc1ccc(O)c(N)c1", "MR2", new Double(0.28), "MR3", new Double(0.1), "MR6", new Double(0.1) },
				{ "Nc2cccc(c1ccccc1)c2(N)", "MR3", new Double(0.1), "MR2", new Double(2.54), "MR6", new Double(0.54) } };
		amineGroupSubstituent(smiles);

	}

	/**
	 * ammine39 should be: MR2 0.1, MR3 0.54 (NH2), MR6 =0.1 In fact, as in case
	 * of fluorenes, the substituents in position 3 and 4 (in the following
	 * molecule) have to be considered NH2 and CH3, respectively.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testOrder_Ammine39() throws Exception {
		Object[][] smiles = new Object[][] {

				{ "NC1=CC=C2C(=C1)[NH]C3=CC=CC=C23", "MR2", new Double(0.1), "MR3", new Double(0.54), "MR6", new Double(0.1) },
				{ "N2C3=CC=CC=C3(C=1C=CC(N)=CC=12)", "MR2", new Double(0.1), "MR3", new Double(0.54), "MR6",
						new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	// 39 , 40, 54, 8, 82
	/**
	 * ammine8<br>
	 * N2C3=CC=CC=C3(C=1C=CC=C(N)C=12)<br>
	 * should be: MR2 0.54 (NH2), MR3 0.56 (CH3), MR6 =0.1 In fact, as in case
	 * of fluorenes, the substituents in position 2 and 3 (in the following
	 * molecule) have to be considered NH2 and CH3, respectively.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testOrder_Ammine8() throws Exception {
		Object[][] smiles = new Object[][] {
				{ "[H]N2C3=CC=CC=C3(C=1C=CC=C(N)C=12)", "MR2", new Double(0.54), "MR3", new Double(0.56), "MR6",
						new Double(0.1) },
				{ "N2C3=CC=CC=C3(C=1C=CC=C(N)C=12)", "MR2", new Double(0.54), "MR3", new Double(0.56), "MR6",
						new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * ammine54 should be: MR2 0.1, MR3 2.98 (C6H6N), MR6 =0.1
	 * 
	 * @throws Exception
	 */
	@Test
	public void testOrder_Ammine54() throws Exception {
		Object[][] smiles = new Object[][] { { "NC=1C=CC=C(C=1)C=2C=CC=C(N)C=2", "MR2", new Double(0.1), "MR3",
				new Double(2.98), "MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * ammine40 NC2=CC3=CC=CC=4C1=CC=CC=C1C(=C2)C3=4<br>
	 * should be: MR2 0.1, MR3 0.56 (C6H6N), MR6 =0.1 We choose arbitrarily the
	 * opposite way of numbering, with respect to the one I draw in the
	 * document. - When the numbering can go in two opposite directions, the
	 * substituent position with highest steric hindrance is given the lowest
	 * substitution number (see chemical 40).
	 * 
	 * @throws Exception
	 */
	@Test
	public void testArbitraryorder_Ammine40() throws Exception {
		Object[][] smiles = new Object[][] { { "NC2=CC3=CC=CC=4C1=CC=CC=C1C(=C2)C3=4", "MR2", new Double(0.1), "MR3",
				new Double(0.56), "MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * ammine69 <br>
	 * NC=1C=NC=2C=CC=CC=2(C=1) <br>
	 * should be: MR2 0.1, MR3 0.8 (C2H2/2), MR6 =0.1
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAmmine69() throws Exception {
		Object[][] smiles = new Object[][] { { "NC=1C=NC=2C=CC=CC=2(C=1)", "MR2", new Double(0.1), "MR3",
				new Double(0.8), "MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	@Test
	public void testOrder_Ammine180() throws Exception {
		Object[][] smiles = new Object[][] { { "Nc1ccc(NCCO)c(c1)[N+](=O)[O-]", "MR2", new Double(0.1), "MR3",
				new Double(0.74), "MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * amine82 [H]N2C3=CC=CC=C3(C=1C(N)=CC=CC=12)<br>
	 * should be: MR2 0.56 (CH3), MR3 0.54 (NH2), MR6 =0.1<br>
	 * In fact, as in case of fluorenes, the substituents in position 2 and 3
	 * (in the following molecule) have to be considered CH3 and NH2,
	 * respectively.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testOrder_Ammine82() throws Exception {
		Object[][] smiles = new Object[][] { { "N2C3=CC=CC=C3(C=1C(N)=CC=CC=12)", "MR2", new Double(0.56), "MR3",
				new Double(0.54), "MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * 
	 * <pre>
	 * Idamm	B5R	LR
	 * 		Rodo 094	4.49	6.12
	 * 		Rodo 457	7.28	6.83
	 * 		Rodo 887	3.11	6.28
	 * 		Rodo 962 (1042) 	4.16	5.85
	 * 		Rodo 983 (1063)	4.31	9.5
	 * 		Rodo 1051 (1131)	7.99	10.79
	 * 		Rodo 1054 (1134) 	6.58	5.78
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSterimol_Rodo() throws Exception {
		Object[][] smiles = new Object[][] {

				// Rodo 94
				{ "C=1C=CC(=C(C=1)NC2=NC(=NC(=N2)Cl)Cl)Cl", "LSTM1", new Double(6.12), "B5STM1", new Double(4.49) },

				// Rodo 457
				{ "CC2=CC=CC(NC=1C=C(N=C(N=1)SCC(=O)O)Cl)=C2(C)", "LSTM1", new Double(6.83), "B5STM1", new Double(7.28) },

				// Rodo 962
				{ "CCOC1=CC=C(C=C1)NC(=O)CC(C)O", "LSTM1", new Double(5.85), "B5STM1", new Double(4.16) },

				// Rodo 1054
				{ "CC=2C=CC=C(C)C=2(NC(=O)CN1CCCC1(=O))", "LSTM1", new Double(5.78), "B5STM1", new Double(6.58) },
				// Rodo 1051
				{ "C=1C=CC2=CC(=CC=C2(C=1))NC3=CC=C(C=C3)NC4=CC=C5C=CC=CC5(=C4)", "LSTM1", new Double(10.79), "B5STM1",
						new Double(7.99) },

				// Rodo 887
				{ "C1=CC=C(C=C1)NC2=CC=C(C=C2)NC3=CC=CC=C3", "LSTM1", new Double(6.28), "B5STM1", new Double(3.11) },

		};
		amineGroupSubstituent(smiles);
	}

	/**
	 * in case the amino group is attached to more than one aromatic ring, with
	 * the aromatic systems equally extended, the ring bearing the functional
	 * amino group is chosen in such a way that the sterimol is the highest
	 * possible.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSterimol_Rodo983() throws Exception {
		Object[][] smiles = new Object[][] {
		// Rodo 983
		{ "CC(C)OC1=CC=C(C=C1)NC2=CC=CC=C2", "LSTM1", new Double(9.5), "B5STM1", new Double(4.31) }, };
		amineGroupSubstituent(smiles);

	}

	@Test
	public void testSterimol() throws Exception {
		Object[][] smiles = new Object[][] {

		{ "CNC1=CC=CC=C1", "LSTM1", new Double(2.87), "B5STM1", new Double(2.04) },
				{ "CC(=O)NC1=CC2=C(C=C1)C3=CC=CC=C3C2", "LSTM1", new Double(4.06), "B5STM1", new Double(3.13) },
				{ "CC(C)OC(=O)NC1=CC(=CC=C1)Cl", "LSTM1", new Double(5.97), "B5STM1", new Double(3.43) },
				{ "CN(C)C(=O)NC1=CC=C(C=C1)Cl", "LSTM1", new Double(4.77), "B5STM1", new Double(4.04) },
		// {"C1=CC=C(C=C1)N=C(N)S","LSTM1",new Double(4.77),"B5STM1",new
		// Double(4.04)},
		};
		amineGroupSubstituent(smiles);
	}

	@Test
	public void testNBenzene() throws Exception {
		Object[][] smiles = new Object[][] {
				{ "Nc1ccccc1c2ccccc2(N)", "MR2", new Double(2.98), "MR3", new Double(0.1), "MR5", new Double(0.1),
						"MR6", new Double(0.1) },
				{ "Nc1ccc(cc1)c2ccccc2(N)", "MR2", new Double(2.98), "MR3", new Double(0.1), "MR5", new Double(0.1),
						"MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/*
	 * public void testException() throws Exception { Object[][] smiles = new
	 * Object[][] {
	 * 
	 * {"Nc1cnc2ccccc2(c1)","MR2",new Double(0.1),"MR3",new
	 * Double(0.8),"MR5",new Double(0.1),"MR6",new Double(0.1)}, };
	 * amineGroupSubstituent(smiles); }
	 */

	@Test
	public void testSubstituentsOrder() throws Exception {
		Object[][] smiles = new Object[][] {
		/*
		 * {"Cc1ccc(cc1(N))c2ccccc2","MR2",new Double(0.56),"MR3",new
		 * Double(0.1),"MR4",new Double(0.1),"MR5",new Double(2.54),"MR6",new
		 * Double(0.1)}, {"Nc1ccc(cc1Cl)c2ccc(N)c(c2)Cl","MR2",new
		 * Double(0.6),"MR3",new Double(0.1),"MR5",new Double(0.1),"MR6",new
		 * Double(0.1)}, {"Nc1cccc2cccnc12","MR2",new Double(0.65),"MR3",new
		 * Double(0.8),"MR4",new Double(0.1),"MR5",new Double(0.1),"MR6",new
		 * Double(0.1)}, {"Nc1ccc2ncccc2(c1)","MR2",new Double(0.1),"MR3",new
		 * Double(0.8),"MR4",new Double(0.65),"MR5",new Double(0.1),"MR6",new
		 * Double(0.1)}, {"[H]n2c3ccccc3(c1cccc(N)c12)","MR2",new
		 * Double(0.54),"MR3",new Double(0.56),"MR4",new Double(0.1),"MR5",new
		 * Double(0.1),"MR6",new Double(0.1)},
		 * {"[H]n2c3ccccc3(c1cc(N)ccc12)","MR2",new Double(0.1),"MR3",new
		 * Double(0.56),"MR5",new Double(0.1),"MR6",new Double(0.1)}, //good for
		 * a test {"Cc1cc(C)c(N)cc1(C)","MR2",new Double(0.56),"MR3",new
		 * Double(0.1),"MR4",new Double(0.56),"MR5",new Double(0.56),"MR6",new
		 * Double(0.1)}, {"Cc1ccc(O)c(N)c1","MR2",new Double(0.28),"MR3",new
		 * Double(0.1),"MR4",new Double(0.1),"MR5",new Double(0.56),"MR6",new
		 * Double(0.1)},
		 * {"Cc2cc(Cc1cc(C)c(N)c(c1)C(C)(C)C)cc(c2(N))C(C)(C)C","MR2",new
		 * Double(1.96),"MR3",new Double(0.1),"MR4",Double.NaN,"MR5",new
		 * Double(0.1),"MR6",new Double(0.56)},
		 */
		{ "Cc1ccc2ccccc2(c1(N))", "MR2", new Double(0.56), "MR3", new Double(0.1), "MR4", new Double(0.1), "MR5",
				new Double(0.8), "MR6", new Double(0.8) },

		};
		amineGroupSubstituent(smiles);
	}

	@Test
	public void testCyclicNGroup_assubstituent() throws Exception {
		Object[][] smiles = new Object[][] {

		{ "[H]n2c3ccccc3(c1cccc(N)c12)", "MR2", new Double(0.54), "MR3", new Double(0.56), "MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);

	}

	@Test
	public void testMultipleAromamine() throws Exception {
		Object[][] smiles = new Object[][] {
				{ "Nc1cccc(c1)c2cccc(N)c2", "MR2", new Double(0.1), "MR3", new Double(2.98), "MR4", new Double(0.1),
						"MR5", new Double(0.1), "MR6", new Double(0.1) },
				{ "CC(C)c1ccc(N)cc1(N)", "MR2", new Double(1.5), "MR3", new Double(0.1), "MR4", new Double(0.1), "MR5",
						new Double(0.54), "MR6", new Double(0.1) },
				{ "Cc1cc(N)ccc1(N)", "MR2", new Double(0.56), "MR3", new Double(0.1), "MR4", new Double(0.54), "MR5",
						new Double(0.1), "MR6", new Double(0.1) },
				{ "Nc2cccc(c1ccccc1)c2(N)", "MR2", new Double(2.54), "MR3", new Double(0.1), "MR4", new Double(0.1),
						"MR5", new Double(0.1), "MR6", new Double(0.54) },
				{ "Nc1cc(c(N)c(c1)Cl)Cl", "MR2", new Double(0.6), "MR3", new Double(0.1), "MR4", new Double(0.54),
						"MR5", new Double(0.1), "MR6", new Double(0.6) },
				{ "Nc1cc(c(N)c(c1)Cl)Cl", "MR2", new Double(0.6), "MR3", new Double(0.1), "MR4", new Double(0.54),
						"MR5", new Double(0.1), "MR6", new Double(0.6) },
				{ "Nc1ccc(N)c(c1)[N+](=O)[O-]", "MR2", new Double(0.74), "MR3", new Double(0.1), "MR4",
						new Double(0.54), "MR5", new Double(0.1), "MR6", new Double(0.1) },

				{ "Nc1ccc(cc1)c2cccc(N)c2", "MR2", new Double(0.1), "MR3", new Double(2.98), "MR4", new Double(0.1),
						"MR5", new Double(0.1), "MR6", new Double(0.1) },

		};
		amineGroupSubstituent(smiles);
	}

	/**
	 * Partial MR=1.6/2 (C4H4/2) Mr5 mr3 mr2 mr6 2-naphthylamine (42) 0.1 0.8
	 * 0.1 0.1
	 * 
	 * N-(1-naphthyl)-ethyl...(187) 0.1 0.8 0.8 0.1
	 * 
	 * @throws Exception
	 */
	public void testNaphtalenes() throws Exception {
		Object[][] smiles = new Object[][] {
				{ "Nc1ccc2ccccc2(c1)", "MR5", new Double(0.1), "MR3", new Double(0.8), "MR4", new Double(0.8), "MR2",
						new Double(0.1), "MR6", new Double(0.1) },
				{ "NCCNC2=CC=CC1=CC=CC=C12", "MR5", new Double(0.1), "MR3", new Double(0.8), "MR2", new Double(0.8),
						"MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * Anthracenes (6, 37) and phenanthrenes (10, 43) were treated as
	 * naphthalenes MR=1.6/2 (C4H4/2).
	 * 
	 * @throws Exception
	 */
	public void testAnthracenes() throws Exception {
		Object[][] smiles = new Object[][] {
				{ "Nc1cccc2cc3ccccc3(cc12)", "MR5", new Double(0.1), "MR3", new Double(0.8), "MR2", new Double(0.8),
						"MR6", new Double(0.1) },
				{ "Nc1ccc2cc3ccccc3(cc2(c1))", "MR5", new Double(0.1), "MR3", new Double(0.8), "MR2", new Double(0.1),
						"MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	public void testPhenanthrenes() throws Exception {
		Object[][] smiles = new Object[][] {
				{ "Nc3cccc2c3(ccc1ccccc12)", "MR5", new Double(0.1), "MR4", new Double(0.1), "MR3", new Double(0.8),
						"MR2", new Double(0.8), "MR6", new Double(0.1) },
				{ "Nc3ccc2c(ccc1ccccc12)c3", "MR5", new Double(0.1), "MR3", new Double(0.8), "MR2", new Double(0.1),
						"MR4", new Double(0.8), "MR6", new Double(0.1) },

		};
		amineGroupSubstituent(smiles);
	}

	/**
	 * Pyrenes (12, 45, 87) were treated as naphthalenes mr5 mr3 mr2 mr6
	 * 1-aminopyrene 0,1 0,8 0,8 0,1 2-aminopyrene 0,8 0,8 0,1 0,1 4-aminopyrene
	 * 0,8 0,8 0,8 0,1
	 * 
	 * @throws Exception
	 */
	public void testPyrenes() throws Exception {
		Object[][] smiles = new Object[][] {
				{ "Nc1ccc2ccc3cccc4ccc1c2c34", "MR5", new Double(0.1), "MR3", new Double(0.8), "MR2", new Double(0.8),
						"MR6", new Double(0.1) },
				{ "Nc1cc2ccc3cccc4ccc(c1)c2c34", "MR5", new Double(0.8), "MR3", new Double(0.8), "MR2",
						new Double(0.1), "MR6", new Double(0.1) },
				{ "Nc1cc4cccc3ccc2cccc1c2c34", "MR5", new Double(0.8), "MR3", new Double(0.8), "MR2", new Double(0.8),
						"MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * 5-carbons ring of fluorenes Partial MR = 0.56 (CH3) Mr5 mr3 mr2 mr6
	 * 4-Acetylaminofluorene (168) 0.1 0.56 0.56 0.1
	 * 
	 * Fluoranthenes are treated as fluorenes (103, 104) mr5 mr3 mr2 mr6
	 * 
	 * 7-aminofluoranthene 0,1 0,56 0,56 0,1 8-aminofluoranthene 0,1 0,56 0,1
	 * 0,1
	 * 
	 * @throws Exception
	 */
	public void test5Crings() throws Exception {
		Object[][] smiles = new Object[][] {
				{ "CC(=O)NC3=CC=CC=2CC=1C=CC=CC=1C=23", "MR5", new Double(0.1), "MR3", new Double(0.56), "MR2",
						new Double(0.56), "MR6", new Double(0.1) },
				{ "Nc1cccc2c4cccc3cccc(c12)c34", "MR5", new Double(0.1), "MR3", new Double(0.56), "MR2",
						new Double(0.56), "MR6", new Double(0.1) },
				{ "Nc1ccc2c4cccc3cccc(c2(c1))c34", "MR5", new Double(0.1), "MR3", new Double(0.56), "MR2",
						new Double(0.1), "MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * Mixed Naphathalene / Fluorene
	 * 
	 * 1-aminofluoranthene: (1) mr5 mr3 mr2 mr6 0,1 0,8 0,56 0,1
	 * 
	 * Position 2 as in fluorene, position 3 as naphthalene. (40) mr5 mr3 mr2
	 * mr6 2-aminofluoranthene 0,8 0,56 0,1 0,1 Position 5 as naphthalene,
	 * position 3 as fluorene.
	 * 
	 * @throws Exception
	 */
	public void testMixedNaphtaleneFluorene() throws Exception {
		Object[][] smiles = new Object[][] {
				{ "Nc2ccc3cccc4c1ccccc1c2c34", "MR5", new Double(0.1), "MR3", new Double(0.8), "MR2", new Double(0.56),
						"MR6", new Double(0.1) },
				// When the numbering can go in two opposite directions, the
				// substituent position with highest steric hindrance is given
				// the lowest substitution number
				{ "Nc2cc3cccc4c1ccccc1c(c2)c34", "MR5", new Double(0.8), "MR3", new Double(0.56), "MR2",
						new Double(0.1), "MR6", new Double(0.1) }, };
		amineGroupSubstituent(smiles);
	}

	/**
	 * Heterocyclic rings
	 * 
	 * Partial MR = 0.65 (CNH). (105) mr5 mr3 mr2 mr6 8-aminoquinoline 0,1 0,8
	 * 0,65 0,1 Position 2 CNH, position3 as naphthalene
	 * ------------------------
	 * ----------------------------------------------------
	 * 
	 * (2, 3) mr5 mr3 mr2 mr6 1,7-diaminophenazine 0,1 0,65 0,1 0,1
	 * 1,9-diaminophenazine 0,1 0,65 0,65 0,1
	 * 
	 * @throws Exception
	 */
	public void testHeterocyclicRings() throws Exception {
		Object[][] smiles = new Object[][] {
				// 105
				{ "Nc1cccc2cccnc12", "MR5", new Double(0.1), "MR3", new Double(0.8), "MR2", new Double(0.65), "MR6",
						new Double(0.1) },
				// 3
				{ "NC1=CC=CC2=NC3=CC=CC(N)=C3(N=C12)", "MR5", new Double(0.1), "MR3", new Double(0.65), "MR2",
						new Double(0.65), "MR6", new Double(0.1) },
				// 2
				{ "NC=1C=CC2=NC=3C(N)=CC=CC=3(N=C2(C=1))", "MR5", new Double(0.1), "MR3", new Double(0.65), "MR2",
						new Double(0.65), "MR6", new Double(0.1) },

		};
		amineGroupSubstituent(smiles);
	}

	/*
	 * public void testAmineGroupSubstituent() throws Exception { String[]
	 * smiles = new String[] { "[H]n2c3ccccc3(c1c(N)cccc12)",
	 * "Nc1ccc(cc1(N))[N+](=O)[O-]", "[H]n2c3ccccc3(c1cccc(N)c12)",
	 * "Nc1cc(c(N)c(c1)Cl)Cl", "Cc1cc(C)c(N)cc1(C)",
	 * "Nc1cc(ccc1(O))[N+](=O)[O-]", "Nc1ccc(O)c(N)c1", "Cc1ccc(O)c(N)c1",
	 * "CC(=O)Nc1ccc3c(c1)Cc2ccccc23", "Nc1ccc(O)c(c1)[N+](=O)[O-]",
	 * "Nc2ccc3cccc4c1ccccc1c2c34", "Nc3cccc2c3(ccc1ccccc12)",
	 * "Nc1cc3c4ccccc4(ccc3(c2ccccc12))", "Nc1cccc2c4cccc3cccc(c12)c34",
	 * "Nc2c3ccccc3(cc1ccccc12)", "Nc2cc1ccccc1c3ccccc23",
	 * "Nc1ccc2ccc3cccc4ccc1c2c34", "Cc1ccccc1(N)", "Nc1ccc(N)cc1"
	 * 
	 * }; amineGroupSubstituent(smiles); }
	 */
	public void amineGroupSubstituent(Object[][] smiles) throws Exception {
		// fail("verify how substituted amine group should be treated (params of amine group + substituent, only substituent, largest substituent, average, etc.)");

		for (int i = 0; i < smiles.length; i++) {
			Logger.info(smiles[i][0].toString());
			IAtomContainer a = FunctionalGroups.createAtomContainer(smiles[i][0].toString(), false);
			// AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(a);
			// CDKHueckelAromaticityDetector.detectAromaticity(a);
			MolAnalyser.analyse(a);
			DescriptorValue value = adescriptor.calculate(a);
			IDescriptorResult r = value.getValue();
			if (r instanceof DoubleArrayResult) {

				for (int j = 0; j < value.getNames().length; j++) {
					// System.out.println(value.getNames()[j] + " = " +
					// ((DoubleArrayResult)r).get(j));
					a.setProperty(value.getNames()[j], ((DoubleArrayResult) r).get(j));
				}
				boolean ok = true;
				for (int j = 1; j < smiles[i].length; j += 2) {
					System.out.print(smiles[i][j]);
					Object o = a.getProperty(smiles[i][j]);

					System.out.print("\tExpected\t");
					System.out.print(smiles[i][j + 1]);
					System.out.print("\tCalculated\t");
					System.out.println(o);
					ok = ok && (smiles[i][j + 1].equals(o));

				}
				assertTrue(ok);
			} // else System.out.println(r.getClass().getName());
		}
	}
}
