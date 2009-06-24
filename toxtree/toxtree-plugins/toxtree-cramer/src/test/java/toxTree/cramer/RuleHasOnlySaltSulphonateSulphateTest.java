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
package toxTree.test.tree.cramer;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import toxTree.test.query.TestCasesFactory;
import toxTree.tree.cramer.RuleHasOnlySaltSulphonateSulphate;

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
		IAtomContainer a = TestCasesFactory.phenazineMethosulphate();
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
}
