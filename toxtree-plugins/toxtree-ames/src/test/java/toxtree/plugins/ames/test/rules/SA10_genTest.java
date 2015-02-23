/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

Contact: jeliazkova.nina@gmail.com

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

package toxtree.plugins.ames.test.rules;

import java.util.Hashtable;

import javax.vecmath.Point2d;

import org.openscience.cdk.config.Elements;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.smiles.smarts.SMARTSQueryTool;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxtree.plugins.ames.rules.SA10_gen;
import toxtree.plugins.ames.test.TestAmesMutagenicityRules;
import ambit2.core.data.MoleculeTools;

public class SA10_genTest extends TestAmesMutagenicityRules {
	@Override
	public String getHitsFile() {
		return "NA10/NA10_newhits.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA10";
	}
	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA10_gen();
	}
	
	public void test663() throws Exception {
		IAtomContainer m663 = create663();
		assertTrue(applySmarts("[#6]:,=[#6][#6](=O)[!O]",m663));
		
	}
	public void testAromaticSubstituent() throws Exception {
		assertFalse(applySmarts("[!a,#1][#6]([!a,#1])!:;=[#6][#6](=O)[!O]","[H]C(=O)C([H])=C([H])C1=C([H])C([H])=C([H])C([H])=C1([H])"));
	}
	

	public void testCarboxylate() throws Exception {
			assertTrue(applySmarts("C!@;=C!@C(=O)[O]","CC=CC(=O)O"));
	}
	public void testDetachSubstituentAtBetaCarbon() throws Exception {
		QueryAtomContainer q = FunctionalGroups.ab_unsaturated_carbonyl();
		IAtomContainer c = FunctionalGroups.createAtomContainer("CC(C)CCC=CC=O");
			MolAnalyser.analyse(c);
		
		IAtomContainerSet sc = ((SA10_gen)ruleToTest).detachSubstituentAtBetaCarbon(c);
		assertNotNull(sc);
		Hashtable<String,Integer> results = new Hashtable<String,Integer>();
		results.put("[H]C([H])([H])C([H])([H])C([H])(C([H])([H])[H])C([H])([H])[H]",new Integer(5));
		results.put("[H]C([H])=C([H])C(=O)[H]",new Integer(3));
		if (sc != null) {
			SmilesGenerator g = new SmilesGenerator(true);
			for (int i=0;i<sc.getAtomContainerCount();i++) {
				String s = g.createSMILES((IAtomContainer)sc.getAtomContainer(i));
//				System.out.println(s);
				assertNotNull(results.get(s));
				IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(sc.getAtomContainer(i));
				int catoms = MolecularFormulaManipulator.getElementCount(formula,
						MoleculeTools.newElement(formula.getBuilder(),"C"));
				
				assertEquals(results.get(s),new Integer(catoms));
				//System.out.println(FunctionalGroups.mapToString(sc.getAtomContainer(i)));
				//System.out.println(FunctionalGroups.hasGroupMarked(sc.getAtomContainer(i),q.getID()));
			}	

		}
	}
	protected IAtomContainer create663() throws Exception {
			  IAtomContainer mol = MoleculeTools.newMolecule(SilentChemObjectBuilder.getInstance());
			  IAtom a1 = MoleculeTools.newAtom(mol.getBuilder(),Elements.CARBON);
			  a1.setPoint2d(new Point2d(2.598076211353316, -4.440892098500626E-16));  mol.addAtom(a1);
			  IAtom a2 = MoleculeTools.newAtom(mol.getBuilder(),Elements.CARBON);
			  a2.setPoint2d(new Point2d(2.5980762113533165, 1.5));  mol.addAtom(a2);
			  IAtom a3 = MoleculeTools.newAtom(mol.getBuilder(),Elements.NITROGEN);
			  a3.setPoint2d(new Point2d(1.2990381056766576, -0.7500000000000002));  mol.addAtom(a3);
			  IAtom a4 = MoleculeTools.newAtom(mol.getBuilder(),Elements.CARBON);
			  a4.setPoint2d(new Point2d(3.8971143170299736, -0.7500000000000011));  mol.addAtom(a4);
			  IAtom a5 = MoleculeTools.newAtom(mol.getBuilder(),Elements.CARBON);
			  a5.setPoint2d(new Point2d(1.2990381056766582, 2.25));  mol.addAtom(a5);
			  IAtom a6 = MoleculeTools.newAtom(mol.getBuilder(),Elements.HYDROGEN);
			  a6.setPoint2d(new Point2d(3.897114317029974, 2.2500000000000004));  mol.addAtom(a6);
			  IAtom a7 = MoleculeTools.newAtom(mol.getBuilder(),Elements.CARBON);
			  a7.setPoint2d(new Point2d(0.0, 0.0));  mol.addAtom(a7);
			  IAtom a8 = MoleculeTools.newAtom(mol.getBuilder(),Elements.HYDROGEN);
			  a8.setPoint2d(new Point2d(5.196152422706632, -2.220446049250313E-15));  mol.addAtom(a8);
			  IAtom a9 = MoleculeTools.newAtom(mol.getBuilder(),Elements.HYDROGEN);
			  a9.setPoint2d(new Point2d(2.9329329025001645, -1.8990666646784682));  mol.addAtom(a9);
			  IAtom a10 = MoleculeTools.newAtom(mol.getBuilder(),Elements.HYDROGEN);
			  a10.setPoint2d(new Point2d(4.861295731559783, -1.8990666646784684));  mol.addAtom(a10);
			  IAtom a11 = MoleculeTools.newAtom(mol.getBuilder(),Elements.CARBON);
			  a11.setPoint2d(new Point2d(0.0, 1.5));  mol.addAtom(a11);
			  IAtom a12 = MoleculeTools.newAtom(mol.getBuilder(),Elements.HYDROGEN);
			  a12.setPoint2d(new Point2d(1.2990381056766582, 3.75));  mol.addAtom(a12);
			  IAtom a13 = MoleculeTools.newAtom(mol.getBuilder(),Elements.NITROGEN);
			  a13.setPoint2d(new Point2d(-1.2990381056766584, -0.7500000000000001));  mol.addAtom(a13);
			  IAtom a14 = MoleculeTools.newAtom(mol.getBuilder(),Elements.CARBON);
			  a14.setPoint2d(new Point2d(-1.2990381056766582, 2.2500000000000004));  mol.addAtom(a14);
			  IAtom a15 = MoleculeTools.newAtom(mol.getBuilder(),Elements.CARBON);
			  a15.setPoint2d(new Point2d(-2.598076211353316, -2.220446049250313E-16));  mol.addAtom(a15);
			  IAtom a16 = MoleculeTools.newAtom(mol.getBuilder(),Elements.CARBON);
			  a16.setPoint2d(new Point2d(-1.2990381056766587, -2.25));  mol.addAtom(a16);
			  IAtom a17 = MoleculeTools.newAtom(mol.getBuilder(),Elements.CARBON);
			  a17.setPoint2d(new Point2d(-2.5980762113533165, 1.5));  mol.addAtom(a17);
			  IAtom a18 = MoleculeTools.newAtom(mol.getBuilder(),Elements.OXYGEN);
			  a18.setPoint2d(new Point2d(-1.2990381056766582, 3.7500000000000004));  mol.addAtom(a18);
			  IAtom a19 = MoleculeTools.newAtom(mol.getBuilder(),Elements.HYDROGEN);
			  a19.setPoint2d(new Point2d(-3.897114317029974, -0.7500000000000004));  mol.addAtom(a19);
			  IAtom a20 = MoleculeTools.newAtom(mol.getBuilder(),Elements.CARBON);
			  a20.setPoint2d(new Point2d(-2.598076211353317, -2.9999999999999996));  mol.addAtom(a20);
			  IAtom a21 = MoleculeTools.newAtom(mol.getBuilder(),Elements.HYDROGEN);
			  a21.setPoint2d(new Point2d(-0.7860078906881565, -3.659538931178863));  mol.addAtom(a21);
			  IAtom a22 = MoleculeTools.newAtom(mol.getBuilder(),Elements.HYDROGEN);
			  a22.setPoint2d(new Point2d(0.17817352384165352, -1.9895277334996049));  mol.addAtom(a22);
			  IAtom a23 = MoleculeTools.newAtom(mol.getBuilder(),Elements.CARBON);
			  a23.setPoint2d(new Point2d(-3.897114317029975, 2.249999999999999));  mol.addAtom(a23);
			  IAtom a24 = MoleculeTools.newAtom(mol.getBuilder(),Elements.HYDROGEN);
			  a24.setPoint2d(new Point2d(-2.5980762113533173, -4.5));  mol.addAtom(a24);
			  IAtom a25 = MoleculeTools.newAtom(mol.getBuilder(),Elements.HYDROGEN);
			  a25.setPoint2d(new Point2d(-3.111106426341819, -1.5904610688211367));  mol.addAtom(a25);
			  IAtom a26 = MoleculeTools.newAtom(mol.getBuilder(),Elements.HYDROGEN);
			  a26.setPoint2d(new Point2d(-4.0752878408716295, -3.2604722665003947));  mol.addAtom(a26);
			  IAtom a27 = MoleculeTools.newAtom(mol.getBuilder(),Elements.OXYGEN);
			  a27.setPoint2d(new Point2d(-3.8971143170299745, 3.749999999999999));  mol.addAtom(a27);
			  IAtom a28 = MoleculeTools.newAtom(mol.getBuilder(),Elements.OXYGEN);
			  a28.setPoint2d(new Point2d(-5.196152422706633, 1.499999999999999));  mol.addAtom(a28);
			  IAtom a29 = MoleculeTools.newAtom(mol.getBuilder(),Elements.HYDROGEN);
			  a29.setPoint2d(new Point2d(-5.196152422706631, 4.500000000000001));  mol.addAtom(a29);
			  IBond b1 = MoleculeTools.newBond(mol.getBuilder(),a1, a2, IBond.Order.DOUBLE);
			  mol.addBond(b1);
			  IBond b2 = MoleculeTools.newBond(mol.getBuilder(),a1, a3, IBond.Order.SINGLE);
			  mol.addBond(b2);
			  IBond b3 = MoleculeTools.newBond(mol.getBuilder(),a1, a4, IBond.Order.SINGLE);
			  mol.addBond(b3);
			  IBond b4 = MoleculeTools.newBond(mol.getBuilder(),a2, a5, IBond.Order.SINGLE);
			  mol.addBond(b4);
			  IBond b5 = MoleculeTools.newBond(mol.getBuilder(),a2, a6, IBond.Order.SINGLE);
			  mol.addBond(b5);
			  IBond b6 = MoleculeTools.newBond(mol.getBuilder(),a3, a7, IBond.Order.DOUBLE);
			  mol.addBond(b6);
			  IBond b7 = MoleculeTools.newBond(mol.getBuilder(),a4, a8, IBond.Order.SINGLE);
			  mol.addBond(b7);
			  IBond b8 = MoleculeTools.newBond(mol.getBuilder(),a4, a9, IBond.Order.SINGLE);
			  mol.addBond(b8);
			  IBond b9 = MoleculeTools.newBond(mol.getBuilder(),a4, a10, IBond.Order.SINGLE);
			  mol.addBond(b9);
			  IBond b10 = MoleculeTools.newBond(mol.getBuilder(),a5, a11, IBond.Order.DOUBLE);
			  mol.addBond(b10);
			  IBond b11 = MoleculeTools.newBond(mol.getBuilder(),a5, a12, IBond.Order.SINGLE);
			  mol.addBond(b11);
			  IBond b12 = MoleculeTools.newBond(mol.getBuilder(),a7, a11, IBond.Order.SINGLE);
			  mol.addBond(b12);
			  IBond b13 = MoleculeTools.newBond(mol.getBuilder(),a7, a13, IBond.Order.SINGLE);
			  mol.addBond(b13);
			  IBond b14 = MoleculeTools.newBond(mol.getBuilder(),a11, a14, IBond.Order.SINGLE);
			  mol.addBond(b14);
			  IBond b15 = MoleculeTools.newBond(mol.getBuilder(),a15, a13, IBond.Order.SINGLE);
			  mol.addBond(b15);
			  IBond b16 = MoleculeTools.newBond(mol.getBuilder(),a13, a16, IBond.Order.SINGLE);
			  mol.addBond(b16);
			  IBond b17 = MoleculeTools.newBond(mol.getBuilder(),a14, a17, IBond.Order.SINGLE);
			  mol.addBond(b17);
			  IBond b18 = MoleculeTools.newBond(mol.getBuilder(),a14, a18, IBond.Order.DOUBLE);
			  mol.addBond(b18);
			  IBond b19 = MoleculeTools.newBond(mol.getBuilder(),a17, a15, IBond.Order.DOUBLE);
			  mol.addBond(b19);
			  IBond b20 = MoleculeTools.newBond(mol.getBuilder(),a15, a19, IBond.Order.SINGLE);
			  mol.addBond(b20);
			  IBond b21 = MoleculeTools.newBond(mol.getBuilder(),a16, a20, IBond.Order.SINGLE);
			  mol.addBond(b21);
			  IBond b22 = MoleculeTools.newBond(mol.getBuilder(),a16, a21, IBond.Order.SINGLE);
			  mol.addBond(b22);
			  IBond b23 = MoleculeTools.newBond(mol.getBuilder(),a16, a22, IBond.Order.SINGLE);
			  mol.addBond(b23);
			  IBond b24 = MoleculeTools.newBond(mol.getBuilder(),a17, a23, IBond.Order.SINGLE);
			  mol.addBond(b24);
			  IBond b25 = MoleculeTools.newBond(mol.getBuilder(),a20, a24, IBond.Order.SINGLE);
			  mol.addBond(b25);
			  IBond b26 = MoleculeTools.newBond(mol.getBuilder(),a20, a25, IBond.Order.SINGLE);
			  mol.addBond(b26);
			  IBond b27 = MoleculeTools.newBond(mol.getBuilder(),a20, a26, IBond.Order.SINGLE);
			  mol.addBond(b27);
			  IBond b28 = MoleculeTools.newBond(mol.getBuilder(),a23, a27, IBond.Order.SINGLE);
			  mol.addBond(b28);
			  IBond b29 = MoleculeTools.newBond(mol.getBuilder(),a23, a28, IBond.Order.DOUBLE);
			  mol.addBond(b29);
			  IBond b30 = MoleculeTools.newBond(mol.getBuilder(),a27, a29, IBond.Order.SINGLE);
			  mol.addBond(b30);
			  return mol;
	}
	
	public void testException() throws Exception {
		SmilesParser sp = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IAtomContainer ac = sp.parseSmiles("CCCC(=O)C=C"); //"OC(=O)C=C");
		CDKHydrogenAdder h = CDKHydrogenAdder.getInstance(SilentChemObjectBuilder.getInstance());
		h.addImplicitHydrogens(ac);
		AtomContainerManipulator.convertImplicitToExplicitHydrogens(ac);
		/**
		 *  This runs fine
		 */
		SMARTSQueryTool sqt1 = new SMARTSQueryTool("[#6]=[#6][#6](=O)[!O]",SilentChemObjectBuilder.getInstance());
		assertTrue(sqt1.matches(ac));
		
		/**
		 * The tree smarts below gives NullPointerException when the queryBond is LogicalOperatorBond, because for some reason queryBond.getAtom(0) and getAtom(1) return null
	java.lang.NullPointerException
	at org.openscience.cdk.isomorphism.UniversalIsomorphismTester.nodeConstructor(UniversalIsomorphismTester.java:697)
	at org.openscience.cdk.isomorphism.UniversalIsomorphismTester.buildRGraph(UniversalIsomorphismTester.java:385)
	at org.openscience.cdk.isomorphism.UniversalIsomorphismTester.search(UniversalIsomorphismTester.java:414)
	at org.openscience.cdk.isomorphism.UniversalIsomorphismTester.getSubgraphMaps(UniversalIsomorphismTester.java:228)
	at org.openscience.cdk.smiles.smarts.SMARTSQueryTool.matches(SMARTSQueryTool.java:158)

		 */
		
		SMARTSQueryTool sqt2 = new SMARTSQueryTool("[#6]!:;=[#6][#6](=O)[!O]",SilentChemObjectBuilder.getInstance());
		assertTrue(sqt2.matches(ac));
		
		SMARTSQueryTool sqt3 = new SMARTSQueryTool("[N;$([N!X4])]!@;-[N;$([N!X4])]",SilentChemObjectBuilder.getInstance());
		assertFalse(sqt3.matches(ac));		

		SMARTSQueryTool sqt4 = new SMARTSQueryTool("[N]=[N]-,=[N]",SilentChemObjectBuilder.getInstance());
		assertFalse(sqt4.matches(ac));		
		
		/**
		 * The common feature of the three SMARTS is a logical operator between bonds
		 */
	}
}


