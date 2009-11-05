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
package toxTree.test.smarts;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.tools.LoggingTool;

import toxTree.tree.rules.RuleAllSubstructures;
import toxTree.tree.rules.smarts.IRuleSMARTSubstructures;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;




public class RuleSMARTSubstructureTest extends TestCase {
	
	public static LoggingTool logger = new LoggingTool(RuleAllSubstructures.class);
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(RuleSMARTSubstructureTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for RuleAnySubstructureTest.
	 * @param arg0
	 */
	public RuleSMARTSubstructureTest(String arg0) {
		super(arg0);
		
	}
	public void testRuleSMARTSubstructure() throws Exception  {
		RuleSMARTSSubstructureAmbit rule = new RuleSMARTSSubstructureAmbit();		
//		Molecule mol = MoleculeFactory.makeBenzene();
		
        IMolecule mol = DefaultChemObjectBuilder.getInstance().newMolecule();
        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 0
        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 1
        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 2
        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 3
        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 4
        mol.addAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON)); // 5

        mol.addBond(0, 1, IBond.Order.SINGLE); // 1
        mol.addBond(1, 2, IBond.Order.DOUBLE); // 2
        mol.addBond(2, 3, IBond.Order.SINGLE); // 3
        mol.addBond(3, 4, IBond.Order.DOUBLE); // 4
        mol.addBond(4, 5, IBond.Order.SINGLE); // 5
        mol.addBond(5, 0, IBond.Order.DOUBLE); // 6

        
		String  smarts = ("c1ccccc1");		
		String  smarts1 = ("CCCCCC");
		
			rule.setContainsAllSubstructures(false);
			rule.addSubstructure(smarts);
			rule.addSubstructure(smarts1);
//			HueckelAromaticityDetector.detectAromaticity(mol);
			//assertTrue(rule.verifyRule(mol,smarts));
			//assertFalse(rule.verifyRule(mol,smarts1));
			assertTrue(rule.verifyRule(mol));
			//assertFalse(rule.verifyRule(mol));
			
	}
 

  
    
    public void testXMLSerializingRuleSmartsJoelib() throws Exception {
    	XMLSerializingRule(new RuleSMARTSSubstructureAmbit(),"[OX2H][OX2]");
    }
    
    public void testXMLSerializingRuleSmartsCDK() throws Exception {
    	XMLSerializingRule(new RuleSMARTSubstructureCDK(),"[OX2H][OX2]");
    }
    
    public void testSerializingRuleSmartsJoelib() throws Exception {
    	serializingRule(new RuleSMARTSSubstructureAmbit(),"[OX2H][OX2]");
    }
    
    public void testSerializingRuleSmartsCDK() throws Exception {
    	serializingRule(new RuleSMARTSubstructureCDK(),"[OX2H][OX2]");
    }
        
    public void serializingRule(IRuleSMARTSubstructures rule, String smarts) throws Exception {
		final String title = "1"; 
			rule.addSubstructure(title,smarts);
			assertEquals(smarts,rule.getSubstructure(title));
		String filename = "rulesmarts.xml";
		Object rule1 = objectRoundTrip(rule, filename);
		assertTrue(rule1 instanceof IRuleSMARTSubstructures);
		assertEquals(rule.getSubstructure(title),((IRuleSMARTSubstructures)rule1).getSubstructure(title));
		System.out.println(rule.getImplementationDetails());
        System.out.println(((IRuleSMARTSubstructures)rule1).getImplementationDetails());
    }
	protected Object objectRoundTrip(Object rule,String filename) throws Exception {		
			//writing
			File f = File.createTempFile(filename,"test");
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(rule);
			os.close();
			
			//reading
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(f));
			Object rule2 =is.readObject();
			is.close();
			f.delete();
			System.out.println(rule.toString());
			assertEquals(rule,rule2);
			return rule2;

	}	    
    public void XMLSerializingRule(IRuleSMARTSubstructures rule, String smarts) throws Exception {
    		final String title = "1"; 
   			rule.addSubstructure(title,smarts);
   			assertEquals(smarts,rule.getSubstructure(title));
    		String filename = "rulesmarts.xml";
    		File file = new File(filename);
    		if (file.exists()) file.delete();
   			
			FileOutputStream os = new FileOutputStream(filename);
			XMLEncoder encoder = new XMLEncoder(os);
			encoder.writeObject(rule);
			encoder.close();	
			
			FileInputStream in = new FileInputStream(filename);
			XMLDecoder decoder = new XMLDecoder(in);
			IRuleSMARTSubstructures rule1 = (IRuleSMARTSubstructures)decoder.readObject();
			assertEquals(smarts,rule1.getSubstructure(title));
			decoder.close();

    }
    public void testGetImplementationDetails() throws Exception  {
		RuleSMARTSSubstructureAmbit rule = new RuleSMARTSSubstructureAmbit();
		String smarts = "[OX2H][OX2]";
			rule.addSubstructure("1",smarts);
			rule.addSubstructure("2","C=O",true);
		
		
		//System.out.println(rule.getImplementationDetails());
		assertEquals("\t\tName\tSMARTS\n\tNOT\t\"2\"\tC=O\nOR\t\t\"1\"\t[OX2H][OX2]\n",rule.getImplementationDetails());
    }
    
}
