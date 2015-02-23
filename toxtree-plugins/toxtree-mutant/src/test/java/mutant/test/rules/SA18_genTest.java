/* SA18Test.java
 * Author: Nina Jeliazkova
 * Date: Jul 21, 2007 
 * Revision: 0.1 
 * 
 * Copyright (C) 2005-2006  Nina Jeliazkova
 * 
 * Contact: nina@acad.bg
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 */

package mutant.test.rules;

import mutant.rules.SA18_gen;
import mutant.test.TestMutantRules;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import ambit2.core.helper.CDKHueckelAromaticityDetector;

public class SA18_genTest extends TestMutantRules {
    String[] smiles = {
            "c1ccc2c(c1)c1c(C2)cccc1NC(C)=O",
            "c1cc2ccc3c4c2c(c1)ccc4ccc3",
            "c1ccc2c(c1)Cc1c2ccc(c1)NC(=O)C",
            "c1c2ccc3cccc4ccc(c5c1cccc5)c2c34",
            "C1c2c3c(C1)c(ccc3cc1c2ccc2c1cccc2)C",
            "c1ccc2c(c1)c1c(C2)cc(cc1)NC(=O)C(F)(F)F",
            "C1c2c(c3c1c(ccc3)N(C(C)=O)C(C)=O)cccc2",
            "c1c2ccc3c(c2cc2c1c1c(cc2)cccc1)cccc3",
            "C1c2c(c3c1cc(cc3)N(C(C)=O)O)cccc2",
            "c1ccc2c(c1)C(c1c3c2ccc2c3c(cc1)c1c(C2=O)cccc1)=O",
            "CC(=O)Nc1c2Cc3ccccc3c2ccc1",
            "Cc1c2ccccc2c(c2ccc3c(c12)cccc3)C"
    };
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testTrue() throws Exception {
        //IAtomContainer c = FunctionalGroups.createAtomContainer("CC(=O)NC=1C=CC=C2C3=CC=CC=C3(CC=12)",true);
        IAtomContainer c = FunctionalGroups.createAtomContainer("C=1C=C2C=CC3=CC=CC4=CC=C(C=1)C2=C34",true);
        
            MolAnalyser.analyse(c);
            assertTrue(ruleToTest.verifyRule(c));


    }
    public void test3()  throws Exception  {
        IAtomContainer c = FunctionalGroups.createAtomContainer("c1(Cl)cc2c3cc(Cl)c(Cl)cc3c2cc1Cl",true);
            MolAnalyser.analyse(c);
            assertTrue(ruleToTest.verifyRule(c));


    }
    
    public void test2rings()  throws Exception {
        IAtomContainer c = FunctionalGroups.createAtomContainer("C=1C=CC=2C=CC=CC=2(C=1)",true);
        
            MolAnalyser.analyse(c);
            assertFalse(ruleToTest.verifyRule(c));


    }
    public void test1()  throws Exception {
    		assertTrue(
    		verifyRule(ruleToTest , "[H]C=1C([H])=C([H])C3=C(C=1([H]))C=2C(=C([H])C([H])=C([H])C=2C3([H])([H]))N([H])C(=O)C([H])([H])[H]")
    		);

    }

    public void testIndene()  throws Exception {
    		assertTrue(
    		verifyRule(ruleToTest , "C=1C=CC=2CCCC=2(C=1)")
    		);

    }    
	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA18_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA18/neoSA19_fixed.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA18";
	}
    public void testAromaticity() throws Exception {
    	assertEquals(12,printAromaticity());
    }
    public void testSMILES() throws Exception {
    	for (String smile : smiles) {
    		assertTrue(verify(smile));
    	}
    }
    public void testAromaticitySmilesParser() throws Exception {


        SmilesParser sp = new SmilesParser 
(SilentChemObjectBuilder.getInstance());
        CDKHydrogenAdder ha = CDKHydrogenAdder.getInstance(SilentChemObjectBuilder.getInstance());
        for (String smile : smiles) {
            IAtomContainer mol = sp.parseSmiles(smile);
            countAromaticAtoms(mol);
            System.out.println(smile);
            
    		for (int i=0; i < mol.getAtomCount();i++)
    			mol.getAtom(i).setFlag(CDKConstants.ISAROMATIC,false);
            ha.addImplicitHydrogens(mol);
            countAromaticAtoms(mol);
            System.out.println(smile);    
            
            AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(mol);
        	CDKHueckelAromaticityDetector.detectAromaticity(mol);
        	countAromaticAtoms(mol);
        	System.out.println(smile);    
        }

    }
    protected int countAromaticAtoms(IAtomContainer mol) {
        int aromatic = 0;
		for (int i=0; i < mol.getAtomCount();i++) {
			if (mol.getAtom(i).getFlag(CDKConstants.ISAROMATIC))
				aromatic ++;
			
	//		System.out.print(mol.getAtom(i).getHybridization());
			//System.out.print('\t');
			
		}		
		
        System.out.print(aromatic + "\t");
        return aromatic;
    }
    public void testAromaticitySmiles() throws Exception {


        SmilesParser sp = new SmilesParser(SilentChemObjectBuilder.getInstance());

        for (String smile : smiles) {
            IAtomContainer mol = sp.parseSmiles(smile);
           
    		for (int i=0; i < mol.getAtomCount();i++)
    			mol.getAtom(i).setFlag(CDKConstants.ISAROMATIC,false);
    		for (int i=0; i < mol.getBondCount();i++)
    			mol.getBond(i).setFlag(CDKConstants.ISAROMATIC,false);  
    		
            AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(mol);
        	CDKHueckelAromaticityDetector.detectAromaticity(mol);
    		
    		int aromatic = 0;
    		for (int i=0; i < mol.getAtomCount();i++)
    			if (mol.getAtom(i).getFlag(CDKConstants.ISAROMATIC))
    				aromatic ++;    		
            System.out.print(aromatic + "\t");
            System.out.println(smile);
        }

    }    
}
