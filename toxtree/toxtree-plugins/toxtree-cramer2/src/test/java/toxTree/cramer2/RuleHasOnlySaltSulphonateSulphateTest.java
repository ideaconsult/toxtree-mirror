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
package toxTree.cramer2;

import org.openscience.cdk.config.Elements;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.silent.Bond;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import toxTree.core.IDecisionRule;
import toxTree.cramer.AbstractRuleTest;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import ambit2.core.data.MoleculeTools;
import cramer2.rules.RuleHasOnlySaltSulphonateSulphate;

/**
 * TODO add description
 * @author ThinClient
 * <b>Modified</b> 2005-9-25
 */
public class RuleHasOnlySaltSulphonateSulphateTest extends AbstractRuleTest {
	
	
	@Override
	protected IDecisionRule createRule() {
		return new RuleHasOnlySaltSulphonateSulphate();
	}
	public void test() throws Exception {
		Object[][] answer = {
				{"CCCCC(=O)[O-].[Na+]",new Integer(1)},
				{"[Ca+2].O=C[O-].O=C[O-]",new Integer(2)}
		};
		
		for (int i=0; i < 2;i++) {
			IAtomContainer a = FunctionalGroups.createAtomContainer(answer[i][0].toString());
			
				MolAnalyser.analyse(a);
				assertTrue(rule2test.verifyRule(a));
				Object mf = a.getProperty(MolFlags.MOLFLAGS);
				assertNotNull(mf);
				IAtomContainerSet residues = ((MolFlags)mf).getResidues();
				assertNotNull(residues);
				assertEquals(((Integer)answer[i][1]).intValue(),residues.getAtomContainerCount());
		}
	}
	public void testSulphateOfAmine() throws Exception  {
		IAtomContainer a = phenazineMethosulphate();
			MolAnalyser.analyse(a);
			assertTrue(rule2test.verifyRule(a));
			Object mf = a.getProperty(MolFlags.MOLFLAGS);
			assertNotNull(mf);
			IAtomContainerSet residues = ((MolFlags)mf).getResidues();
			assertNotNull(residues);
			assertEquals(1,residues.getAtomContainerCount());
	}	
	
	public void testSulphonate() throws Exception  {
		IAtomContainer a = FunctionalGroups.createAtomContainer("[Na+].[O-]S(=O)(=O)CCCCS(=O)(=O)[O-].[Na+]");
			MolAnalyser.analyse(a);
			assertTrue(rule2test.verifyRule(a));
			Object mf = a.getProperty(MolFlags.MOLFLAGS);
			assertNotNull(mf);
			IAtomContainerSet residues = ((MolFlags)mf).getResidues();
			assertNotNull(residues);
			assertEquals(1,residues.getAtomContainerCount());
	}
	public void testSulphate() throws Exception {
		IAtomContainer a = FunctionalGroups.createAtomContainer("[Na+].CCCCCCCCCCCCOCCOCCOCCOS([O-])(=O)=O");
			MolAnalyser.analyse(a);
			assertTrue(rule2test.verifyRule(a));
			Object mf = a.getProperty(MolFlags.MOLFLAGS);
			assertNotNull(mf);
			IAtomContainerSet residues = ((MolFlags)mf).getResidues();
			assertNotNull(residues);
			assertEquals(1,residues.getAtomContainerCount());
	}			

	public void testPhosphate() throws Exception {
		IAtomContainer a = FunctionalGroups.createAtomContainer("[O-]P(CC1=CC(C(C)(C)C)=C(O)C(C(C)(C)C)=C1)(OCC)=O.[O-]P(CC2=CC(C(C)(C)C)=C(O)C(C(C)(C)C)=C2)(OCC)=O.[Ca+2]");
			MolAnalyser.analyse(a);
			assertTrue(rule2test.verifyRule(a));
			Object mf = a.getProperty(MolFlags.MOLFLAGS);
			assertNotNull(mf);
			IAtomContainerSet residues = ((MolFlags)mf).getResidues();
			assertNull(residues);
			//assertEquals(2,residues.getAtomContainerCount());
	}			

	public void testPhosphate1() throws Exception {
		IAtomContainer a = FunctionalGroups.createAtomContainer("[O-]P(CC1=CC(C(C)(C)C)=C(O)C(C(C)(C)C)=C1)(OCC)=O.[O-]P(CC2=CC(C(C)(C)C)=C(O)C(C(C)(C)C)=C2)(OCC)=O");
			MolAnalyser.analyse(a);
			assertTrue(rule2test.verifyRule(a));
			Object mf = a.getProperty(MolFlags.MOLFLAGS);
			assertNotNull(mf);
			IAtomContainerSet residues = ((MolFlags)mf).getResidues();
			assertNull(residues);
			//assertEquals(2,residues.getAtomContainerCount());
	}			

	public void testPhosphorus() throws Exception {
		IAtomContainer a = FunctionalGroups.createAtomContainer("P(CC1=CC(C(C)(C)C)=C(O)C(C(C)(C)C)=C1)(OCC)=O");
			MolAnalyser.analyse(a);
			assertFalse(rule2test.verifyRule(a));
			Object mf = a.getProperty(MolFlags.MOLFLAGS);
			assertNotNull(mf);
			IAtomContainerSet residues = ((MolFlags)mf).getResidues();
			assertNull(residues);
			//assertEquals(2,residues.getAtomContainerCount());
	}			
		
	public void testHydroChloridOfAmine() throws Exception  {
		IAtomContainer a = FunctionalGroups.createAtomContainer("[Cl-].[NH3+]C1CCCCC1");
			MolAnalyser.analyse(a);
			assertTrue(rule2test.verifyRule(a));
			Object mf = a.getProperty(MolFlags.MOLFLAGS);
			assertNotNull(mf);
			IAtomContainerSet residues = ((MolFlags)mf).getResidues();
			assertNotNull(residues);
			assertEquals(1,residues.getAtomContainerCount());
			assertTrue(FunctionalGroups.hasGroup(residues.getAtomContainer(0),
					FunctionalGroups.primaryAmine(false)));
	}
	public static IMolecule phenazineMethosulphate() 
	{
		  IMolecule mol = MoleculeTools.newMolecule(SilentChemObjectBuilder.getInstance());  
		  IAtom nq = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.NITROGEN);
		  nq.setFormalCharge(+1);
		  mol.addAtom(nq);
		  IAtom a2 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.SULFUR);
		  mol.addAtom(a2);
		  IAtom a3 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.NITROGEN);
		  mol.addAtom(a3);
		  IAtom a4 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a4);
		  IAtom a5 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a5);
		  IAtom a6 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a6);
		  IAtom a7 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a7);
		  IAtom o1 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.OXYGEN);
		  o1.setFormalCharge(-1);
		  mol.addAtom(o1);
		  IAtom a9 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.OXYGEN);
		  mol.addAtom(a9);
		  IAtom a10 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.OXYGEN);
		  mol.addAtom(a10);
		  IAtom a11 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.OXYGEN);
		  mol.addAtom(a11);
		  IAtom a12 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a12);
		  IAtom a13 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a13);
		  IAtom a14 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a14);
		  IAtom a15 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a15);
		  IAtom a16 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a16);
		  IAtom a17 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a17);
		  IAtom a18 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a18);
		  IAtom a19 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a19);
		  IAtom a20 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a20);
		  IAtom a21 = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a21);
		  IBond b1 = new Bond(a3, a7, IBond.Order.SINGLE);
		  mol.addBond(b1);
		  IBond b2 = new Bond(a4, nq, IBond.Order.SINGLE);
		  mol.addBond(b2);
		  IBond b3 = new Bond(a5, nq, IBond.Order.DOUBLE);
		  mol.addBond(b3);
		  IBond b4 = new Bond(a6, a5, IBond.Order.SINGLE);
		  mol.addBond(b4);
		  IBond b5 = new Bond(a7, a4, IBond.Order.DOUBLE);
		  mol.addBond(b5);
		  IBond b6 = new Bond(o1, a2, IBond.Order.SINGLE);
		  mol.addBond(b6);
		  IBond b7 = new Bond(a9, a2, IBond.Order.DOUBLE);
		  mol.addBond(b7);
		  IBond b8 = new Bond(a10, a2, IBond.Order.DOUBLE);
		  mol.addBond(b8);
		  IBond b9 = new Bond(a11, a2, IBond.Order.SINGLE);
		  mol.addBond(b9);
		  IBond b10 = new Bond(a12, nq, IBond.Order.SINGLE);
		  mol.addBond(b10);
		  IBond b11 = new Bond(a13, a4, IBond.Order.SINGLE);
		  mol.addBond(b11);
		  IBond b12 = new Bond(a14, a5, IBond.Order.SINGLE);
		  mol.addBond(b12);
		  IBond b13 = new Bond(a15, a6, IBond.Order.SINGLE);
		  mol.addBond(b13);
		  IBond b14 = new Bond(a16, a7, IBond.Order.SINGLE);
		  mol.addBond(b14);
		  IBond b15 = new Bond(a17, a11, IBond.Order.SINGLE);
		  mol.addBond(b15);
		  IBond b16 = new Bond(a18, a13, IBond.Order.DOUBLE);
		  mol.addBond(b16);
		  IBond b17 = new Bond(a19, a14, IBond.Order.DOUBLE);
		  mol.addBond(b17);
		  IBond b18 = new Bond(a20, a19, IBond.Order.SINGLE);
		  mol.addBond(b18);
		  IBond b19 = new Bond(a21, a16, IBond.Order.DOUBLE);
		  mol.addBond(b19);
		  IBond b20 = new Bond(a6, a3, IBond.Order.DOUBLE);
		  mol.addBond(b20);
		  IBond b21 = new Bond(a18, a21, IBond.Order.SINGLE);
		  mol.addBond(b21);
		  IBond b22 = new Bond(a15, a20, IBond.Order.DOUBLE);
		  mol.addBond(b22);
		  return mol;
		}
		
}
