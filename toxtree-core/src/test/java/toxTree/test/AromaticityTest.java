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
package toxTree.test;

import java.util.Iterator;

import junit.framework.TestCase;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.atomtype.CDKAtomTypeMatcher;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IBond.Order;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.AtomTypeManipulator;

import ambit2.core.smiles.DeduceBondSystemTool;

/**
 * Test for some peculiarities of aromaticity detection in CDK
 * @author Nina Jeliazkova
 * <b>Modified</b> 2008-3-5
 */
public class AromaticityTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(AromaticityTest.class);
	}
	public void aromaticRoundTrip(String smiles) throws Exception  {
		SmilesParser p = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IMolecule m = p.parseSmiles(smiles);
		AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(m);
		CDKHueckelAromaticityDetector.detectAromaticity(m);
		aromaticRoundTrip(m);
	}
	
	public void aromaticRoundTrip(IMolecule m)  throws Exception  {
		SmilesParser p = new SmilesParser(SilentChemObjectBuilder.getInstance());
		SmilesGenerator sg = new SmilesGenerator(true);

			AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(m);
			boolean b = CDKHueckelAromaticityDetector.detectAromaticity(m);

			for (int i=0; i<m.getBondCount();i++) {
				System.out.print("order "+m.getBond(i).getOrder());
				if (m.getBond(i).getFlag(CDKConstants.ISAROMATIC))
				System.out.print("\taromatic");
				System.out.println();
			}

			assertTrue(b);
	        DeduceBondSystemTool dbst = new DeduceBondSystemTool();
	        m = dbst.fixAromaticBondOrders(m);				
			String s = sg.createSMILES(m);
			System.out.println(s);
		
			IMolecule m1 = p.parseSmiles(s);
			
			b = CDKHueckelAromaticityDetector.detectAromaticity(m);
			
			for (int i=0; i<m1.getBondCount();i++) {
				System.out.println("order "+m1.getBond(i).getOrder());
				if (m1.getBond(i).getFlag(CDKConstants.ISAROMATIC))
				System.out.println("\taromatic");
			}
			
			assertTrue(b);
			
			assertTrue(UniversalIsomorphismTester.isIsomorph(m,m1));
			
	
	}
	public void testDeduceBondOrders() throws Exception  {
        SmilesParser p = new SmilesParser(SilentChemObjectBuilder.getInstance());
        IMolecule m = p.parseSmiles("c1ccccc1");
        
    	CDKAtomTypeMatcher matcher = CDKAtomTypeMatcher.getInstance(m.getBuilder());
    	Iterator<IAtom> atoms = m.atoms().iterator();
    	while (atoms.hasNext()) {
    		IAtom atom = atoms.next();
    		IAtomType type = matcher.findMatchingAtomType(m, atom);
        	assertNotNull(type);
        	AtomTypeManipulator.configure(atom, type);
    	}
    	
        boolean b = CDKHueckelAromaticityDetector.detectAromaticity(m);
        assertTrue(b);
        DeduceBondSystemTool dbst = new DeduceBondSystemTool();
        IMolecule m1 = dbst.fixAromaticBondOrders(m);
        int single_bonds = 0;
        int double_bonds = 0;
        int aromatic_bonds = 0;
        for (int i=0; i<m1.getBondCount();i++) {
            
            Order o = m1.getBond(i).getOrder();
            if (o.equals(Order.DOUBLE)) double_bonds++;
            if (o.equals(Order.SINGLE)) single_bonds++;
            if (m1.getBond(i).getFlag(CDKConstants.ISAROMATIC))
                aromatic_bonds++;
            //System.out.println(o);
        }
        assertEquals(6,aromatic_bonds);
        assertEquals(3,double_bonds);
        assertEquals(3,single_bonds);        
    }
	public void test1() throws Exception  {
		//this fails, but should not 
		aromaticRoundTrip("C1=CC=CC=C1C2=C(C(=O)OCC)OC=C2");
	}
	public void testBenzeneKekule() throws Exception  {
		//this is ok
		aromaticRoundTrip("C1=CC=CC=C1");
	}
	public void testBenzene() throws Exception {
		//this is ok
		aromaticRoundTrip("c1ccccc1");
	}	
	public void test2() throws Exception  {
		//this fails, but should not 
		aromaticRoundTrip("c1ccco1");
	}
	public void test3() throws Exception {
		aromaticRoundTrip("C1=CC=CC=C1CCCC1=CC=C(CCC)C=C1");
	}	
	public void test5() throws Exception {
		aromaticRoundTrip("CCc2c(N)ccc1ccccc12");
	}	
	public void test6() throws Exception  {
		aromaticRoundTrip("CC(=O)Nc1ccc3c(c1)Cc2ccccc23");
	}
	public void test7() throws Exception  {
		aromaticRoundTrip("Nc2c3ccccc3(cc1ccccc12)");
	}	
	public void test8() throws Exception  {
		aromaticRoundTrip("Nc2ccc3cccc4c1ccccc1c2c34");
	}	
	public void test9() throws Exception  {
		aromaticRoundTrip("Nc1ccc2ccc3cccc4ccc1c2c34");
	}		
	public void test10() throws Exception  {
		aromaticRoundTrip("Nc2ccc3Cc1ccccc1c3(c2)");
	}		
	public void test11() throws Exception  {
		aromaticRoundTrip("Nc2cc1ccccc1c3ccccc23");
	}		
	public void test12() throws Exception  {
		aromaticRoundTrip("Nc3ccc2c(ccc1ccccc12)c3");
	}		
	public void test13() throws Exception  {
		aromaticRoundTrip("Nc2ccc4c1ccccc1c3cccc2c34");
	}		
	public void test14() throws Exception  {
		aromaticRoundTrip("Nc1cc3c4ccccc4(ccc3(c2ccccc12))");
	}
	public void test15() throws Exception  {
		aromaticRoundTrip("Nc1cc4cccc3ccc2cccc1c2c34");
	}	
	public void test16()  throws Exception {
		aromaticRoundTrip("c1ccc(cc1)Nc2ccc3ccccc3(c2)");
	}
	public void test17() throws Exception {
		aromaticRoundTrip("CCC1=C(N)C=CC=2C=3C=CC=CC=3(CC1=2)");
	}
	public void test18() throws Exception  {
		aromaticRoundTrip("[H]n2c3ccccc3(c1cc(N)ccc12)");
	}
	public void test19() throws Exception  {
		aromaticRoundTrip("Nc1cnc2ccccc2(c1)");
	}	
	public void test20() throws Exception  {
		aromaticRoundTrip("[H]n2c3ccccc3(c1cccc(N)c12)");
	}	
	public void test84() throws Exception  {
		aromaticRoundTrip("Nc2cccc3ccc1ccccc1c23");
	}	
	

	public void test4() throws Exception {
		//this fails, but should not 
		aromaticRoundTrip(MoleculeFactory.makeIndole());
	
	}

	
}

