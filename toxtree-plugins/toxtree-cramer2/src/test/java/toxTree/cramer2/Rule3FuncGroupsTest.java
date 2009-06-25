/*
Copyright Ideaconsult Ltd. (C) 2005-2008 

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

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import toxTree.tree.AbstractRule;
import cramer2.rules.Rule3FuncGroups;
import cramer2.rules.RuleHasOnlySaltSulphonateSulphate;

/**
 * @author nina
 *
 */
public class Rule3FuncGroupsTest extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new Rule3FuncGroups();
	}

	@Override
	public void test() throws Exception {
	    Object[][] answer = {
            	{"CC(=O)CCC(O)=O",new Boolean(false)},
            	{"[H]OC(=O)C([H])([H])C([H])([H])C(=O)C([H])([H])[H]",new Boolean(false)},
	    };
	    ruleTest(answer); 
		
	}


	public void testSalt() throws Exception {
		IMolecule acid_original = (IMolecule)FunctionalGroups.createAtomContainer("CC(=O)CCC(=O)O");
		assertFalse(verify(acid_original));
		
    	IMolecule mol = (IMolecule)FunctionalGroups.createAtomContainer("CC(=O)CCC(=O)[O-].[Na+]");
    	RuleHasOnlySaltSulphonateSulphate rule4 = new RuleHasOnlySaltSulphonateSulphate();
       	MolAnalyser.analyse(mol);
       	assertTrue(rule4.verifyRule(mol));
       	
		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		if (mf == null) throw new DecisionMethodException(AbstractRule.ERR_STRUCTURENOTPREPROCESSED);
		
		IAtomContainerSet acid = mf.getResidues();
		assertNotNull(acid);
		assertEquals(1,acid.getAtomContainerCount());
		
		assertTrue(UniversalIsomorphismTester.isIsomorph(acid_original,acid.getAtomContainer(0)));
		assertFalse(verify(acid.getAtomContainer(0)));	
                
	}
	protected boolean verify(IAtomContainer a) throws Exception {

		FunctionalGroups.clearMarks(a);
		MolAnalyser.analyse(a);
		/*
		for (int i=0;i < a.getAtomCount(); i++) { 
			//if ("H".equals(a.getAtom(i).getSymbol())) {

				System.out.print(a.getAtom(i).getSymbol());
				System.out.print('\t');
				System.out.print(a.getAtom(i).getAtomTypeName());
				System.out.print('\t');				
				System.out.print(a.getAtom(i).getCharge());
				System.out.print('\t');				
				System.out.print(a.getAtom(i).getFormalCharge());
				System.out.print('\t');				
				System.out.print(a.getAtom(i).getFormalNeighbourCount());
				System.out.print('\t');				
				System.out.print(a.getAtom(i).getBondOrderSum());
				System.out.print('\t');				
				System.out.print(a.getAtom(i).getHybridization());
				System.out.print('\t');				
				System.out.print(a.getAtom(i).getHydrogenCount());
				System.out.print('\t');				
				System.out.print(a.getAtom(i).getValency());
				System.out.print('\t');
				System.out.print(a.getAtom(i).getProperties());				
				System.out.println();
			}
			*/
		return rule2test.verifyRule(a);
	}
}
