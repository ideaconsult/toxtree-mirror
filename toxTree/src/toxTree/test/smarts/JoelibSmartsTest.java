/*
Copyright (C) 2005-2007  

Contact: nina@acad.bg

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

package toxTree.test.smarts;

import javax.vecmath.Point3d;

import joelib.molecule.JOEAtom;
import joelib.molecule.JOEMol;
import joelib.smarts.JOESmartsPattern;
import joelib.smiles.JOESmilesParser;
import junit.framework.TestCase;

import org.openscience.cdk.Atom;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.exception.NoSuchAtomException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.isomorphism.IsomorphismTester;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.rules.smarts.Convertor;
import toxTree.tree.rules.smarts.RuleSMARTSubstructure;

public class JoelibSmartsTest extends TestCase {
	   public void testCDKJoeMolMatch() {
	        Molecule mol = new Molecule();
	        
	        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 0
	        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 1
	        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 2
	        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 3
	        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 4
	        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.OXYGEN)); // 5

	        mol.addBond(0, 1, IBond.Order.SINGLE); // 1
	        mol.addBond(1, 2, IBond.Order.SINGLE); // 2
	        mol.addBond(2, 3, IBond.Order.SINGLE); // 3
	        mol.addBond(3, 4, IBond.Order.SINGLE); // 4
	        mol.addBond(4, 5, IBond.Order.SINGLE); // 5

		
	        JOEMol converted = Convertor.convert(mol);
	        
	        JOEMol joemol=new JOEMol();
	    	String smiles="CCCCCO";
	    	if (!JOESmilesParser.smiToMol(joemol, smiles,""))
	    	 {
	    	    System.err.println("SMILES entry \"" + smiles + "\" could not be loaded.");
	    	 }
	    	 assertEquals(joemol.toString(),converted.toString());
	    	

	        JOESmartsPattern smartPatern = new JOESmartsPattern();
			if(!smartPatern.init("CCCCO")){				   
				System.err.println("Invalid SMARTS pattern.");
			}		
			
			assertTrue(smartPatern.match(converted));
			

	    }
	    public void testCDKJoeMol() {
	        Molecule mol = new Molecule();
	        
	        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 0
	        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.OXYGEN)); // 5

	        mol.addBond(0, 1, IBond.Order.SINGLE); // 1

		
	        JOEMol converted = Convertor.convert(mol);
	        IMolecule reverted = Convertor.convert(converted);

	        assertEquals(mol.getAtomCount(), reverted.getAtomCount());
	        assertEquals(mol.getBondCount(), reverted.getBondCount());
	        
	        try {
	            IsomorphismTester it = new IsomorphismTester(mol);
	            assertTrue(it.isIsomorphic(reverted));
	        } catch (NoSuchAtomException e) {
	        	e.printStackTrace();
	            assertTrue(false);
	        }
	    }
	    public void testAtom() {
	        IAtom a = DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON);
	        a.setPoint3d(new Point3d(1,1,1));

	        JOEAtom converted = Convertor.convert(a);
	        IAtom reverted = Convertor.convert(converted);

	        assertEquals(a.getAtomTypeName(), reverted.getAtomTypeName());
	        /*
	        assertTrue(a.getPoint3d().x == reverted.getPoint3d().x);
	        assertTrue(a.getPoint3d().y == reverted.getPoint3d().y);
	        assertTrue(a.getPoint3d().z == reverted.getPoint3d().z);
	        */
	    }
	    
	    public void testCDKJoeMolAllC() {
	        Molecule mol = new Molecule();
	        
	        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 0
	        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 5

	        mol.addBond(0, 1, IBond.Order.SINGLE); // 1

		
	        JOEMol converted = Convertor.convert(mol);
	        IMolecule reverted = Convertor.convert(converted);

	        assertEquals(mol.getAtomCount(), reverted.getAtomCount());
	        assertEquals(mol.getBondCount(), reverted.getBondCount());
	        
	        try {
	            IsomorphismTester it = new IsomorphismTester(mol);
	            assertTrue(it.isIsomorphic(reverted));
	        } catch (NoSuchAtomException e) {
	            assertTrue(false);
	        }
	    }

	    public void testDirectJoeMolMatch() {
	    	String[][] testSmarts = {
	    			//name,smarts,smiles
	    			{"Hydroperoxide","[OX2H][OX2]","OO"},
	    			{"Aldehyde","[$([CX3H][#6]),$([CX3H2])]=[OX1]","CC=O"},
	    			{"Epoxide","[OX2r3]1[#6r3][#6r3]1","C1OC1C"},
	    			{"Ketene","[CX3]=[CX2]=[OX1]","CC=C=O"},
	    			{"Ketone","[#6][CX3](=[OX1])[#6]","C=CC(=O)C"},
	    			{"Alkene","[CX3;$([H2]),$([H1][#6]),$(C([#6])[#6])]=[CX3;$([H2]),$([H1][#6]),$(C([#6])[#6])]","C=C"},
	    			{"Thioamide","[$([CX3;!R][#6]),$([CX3H;!R])](=[SX1])[#7X3;$([H2]),$([H1][#6;!$(C=[O,N,S])]),$([#7]([#6;!$(C=[O,N,S])])[#6;!$(C=[O,N,S])])]","CCOC(=O)N1C=CN(C)C1(=S)"}
	    			
	    	};
	    	
	    	for (int i=0; i < testSmarts.length;i++) {
	    		RuleSMARTSubstructure rule = new RuleSMARTSubstructure();
	    		System.out.print(testSmarts[i][0]);
	    		try {
	    			rule.addSubstructure(testSmarts[i][1]);
	    			assertTrue(rule.verifyRule(FunctionalGroups.createAtomContainer(testSmarts[i][2],true)));
	    			System.out.println("\tOK");	
	    		} catch (DecisionMethodException x) {
	    			System.out.println(x.getMessage());
	    			fail();
	    		}
	    	}
	    	
	    	/*
	    	try{
	    		String Thioamide = "[$([CX3;!R][#6]),$([CX3H;!R])](=[SX1])[#7X3;$([H2]),$([H1][#6;!$(C=[O,N,S])]),$([#7]([#6;!$(C=[O,N,S])])[#6;!$(C=[O,N,S])])]";
	        	//String Hydroperoxide = "[OX2H][OX2]";
	        	//String Aldehyde = "[$([CX3H][#6]),$([CX3H2])]=[OX1]";
	        	//String Ketene = "[CX3]=[CX2]=[OX1]";
	        	//String Epoxide = "[OX2r3]1[#6r3][#6r3]1";
	        	//String Ketone = "[#6][CX3](=[OX1])[#6]";
	        	//String Alkene = "[CX3;$([H2]),$([H1][#6]),$(C([#6])[#6])]=[CX3;$([H2]),$([H1][#6]),$(C([#6])[#6])]";
	        	//rule.initSingleSMARTS(rule.smartsPatterns,"1", Alkene); 
	    		
	    		rule.addSubstructure(Thioamide);
	        	//rule.initSingleSMARTS(rule.getSmartsPatterns(),"1", Thioamide);
	        	//rule.initSingleSMARTS(rule.smartsPatterns,"1", Hydroperoxide);
	        	//rule.initSingleSMARTS(rule.smartsPatterns,"1", Aldehide);
	        	//rule.initSingleSMARTS(rule.smartsPatterns,"1", Ketene);
	        	//rule.initSingleSMARTS(rule.smartsPatterns,"1", Epoxide);
	        	  //rule.initSingleSMARTS(rule.smartsPatterns,"1", Ketone);
	    		//Carbimazole
	    		assertTrue(rule.verifyRule(FunctionalGroups.createAtomContainer("CCOC(=O)N1C=CN(C)C1(=S)",true)));
	    		//Ethylene
	    		//assertTrue(rule.verifyRule(FunctionalGroups.createAtomContainer("C=C",true)));
	    		//Hydrogen peroxide
	 		    //assertTrue(rule.verifyRule(FunctionalGroups.createAtomContainer("OO",true)));
	    		//Ethanal(Aldehide)
	    		//assertTrue(rule.verifyRule(FunctionalGroups.createAtomContainer("CC=O",true)));
	    		//Methyl ketene  
	    		//assertTrue(rule.verifyRule(FunctionalGroups.createAtomContainer("CC=C=O",false)));
	    		//Propylene oxide
	    		//assertTrue(rule.verifyRule(FunctionalGroups.createAtomContainer("C1OC1C",false)));
	    		//Methyl vinyl ketone  
	    		  //assertTrue(rule.verifyRule(FunctionalGroups.createAtomContainer("C=CC(=O)C",false)));
	    	} 
	    	catch (SMARTSException x) {
	    		fail();
	    	}
	    	catch (toxTree.exceptions.DecisionMethodException x) {
	    		fail();		
	    	}
	    	*/
	    }	    
}


