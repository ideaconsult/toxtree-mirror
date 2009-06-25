/*
Copyright Ideaconsult Ltd.(C) 2006  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package eye;



import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.core.IDecisionRuleList;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.MolAnalyseException;
import toxTree.query.MolAnalyser;

/**
 * TODO Add SicretRulesTest description
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-4-30
 */
public class EyeRulesTest extends RulesTestCase {
    protected SmilesParser gen = null;

	/**
	 * 
	 */
	public EyeRulesTest() {
		super();
		try {
			rules = new EyeIrritationRules();
			((EyeIrritationRules)rules).setResiduesIDVisible(false);
		} catch (DecisionMethodException x) {
			fail();
		}	
        gen = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        
	}

	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}


    protected IMolecule getMolecule(String smiles) {
        try {
        	IMolecule mol = gen.parseSmiles(smiles);
            MolAnalyser.analyse(mol);
            return mol;
	    } catch (InvalidSmilesException x ) {
	        x.printStackTrace();
	        return null;
	    } catch (MolAnalyseException x) {
	        x.printStackTrace();
	        return null;
	    }    
    }
	public void testEye() {
		EyeIrritationRules rulesNew = (EyeIrritationRules)objectRoundTrip(rules,"Eye");
		rulesNew.setResiduesIDVisible(false);
		rules = rulesNew;
		tryImplementedRules();
	}
	public void testPrintEye() throws Exception  {
		System.out.println(new EyeIrritationRules().getRules());
	}
    public void testHasUnreachableRules()  {
    	try {
	    	EyeIrritationRules cr = new EyeIrritationRules();
	    	IDecisionRuleList unvisited = cr.hasUnreachableRules();
	    	if ((unvisited == null) || (unvisited.size()==0))
	    		assertTrue(true);
	    	else {
	    		System.out.println("Unvisited rules:");
	    		System.out.println(unvisited);
	    		fail();
	    	} 
    	} catch (Exception x) {
    		fail(x.getMessage());
    	}
    	
    }		
}
