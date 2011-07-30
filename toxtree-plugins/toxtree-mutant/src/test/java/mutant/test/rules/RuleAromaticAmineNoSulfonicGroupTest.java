/*
Copyright (C) 2005-2006  

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

package mutant.test.rules;

import java.io.InputStream;

import mutant.rules.RuleAromaticAmineNoSulfonicGroup;
import mutant.test.TestMutantRules;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import ambit2.core.io.IteratingDelimitedFileReader;

/**
 * When writing 4'-butylbiphenyl-4-amine, 4'-tert-butylbiphenyl-4-amine, 4'-(trifluoromethyl)biphenyl-4-amine 
 * from CSV to SDF, the aromatic bonds are lost!!!!!! This is the reason of the tree compounds failing
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Jan 5, 2008
 */
public class RuleAromaticAmineNoSulfonicGroupTest extends TestMutantRules {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
	public void testAmine_and_SulphonicGroup1() throws Exception {
		assertFalse(verify("[H]OC1=C([H])C([H])=C2C([H])=C(C([H])=C([H])C2(=C1(N)))S(=O)(=O)[O-]"));
	}
	/*
	 * Nitro group restriction lifted
	public void testAmine_and_nitroGroup() throws Exception {
		assertFalse(verify("[H]OC1=C([H])C([H])=C2C([H])=C(C([H])=C([H])C2(=C1(N)))[N+]([O-])=O"));
	}
	*/	
    public void testAmine_and_SulphonicGroup() throws Exception {
        assertFalse(verify("CCCCNC=1C=CC=C(C=1)S(=O)(=O)O"));
    }
    
    public void testHeterocyclic_aromatic_amine() throws Exception {
        assertFalse(verify("CCCCNC1=CC=CN=C1"));
    }
    public void testHeterocyclic_aromatic_amine_1() throws Exception {
        assertFalse(verify("NC=1N=C(N)N=C(N=1)C2=CC=CC=C2"));
    }    
    
    /**
     * https://sourceforge.net/tracker/index.php?func=detail&aid=3089694&group_id=152702&atid=785126
     */
    public void test_bug3089694() throws Exception {
        assertFalse(verify("c12-c3c(cccc3)Nc1nc(N)cc2 "));
    }       
    /**
     * https://sourceforge.net/tracker/?func=detail&aid=3138561&group_id=152702&atid=785126
     */
    public void test_bug3138561_1() throws Exception {
        assertFalse(verify("CCc1nc(N)nc(N)c1c2ccc(Cl)cc2"));
    }   
    /**
     * https://sourceforge.net/tracker/?func=detail&aid=3138561&group_id=152702&atid=785126
     */
    public void test_bug3138561_2() throws Exception {
        assertFalse(verify("Nc2nc(N)nc1nc(N)c(nc12)c3ccccc3"));
    }   
    
    public void testAromaticN_methylols() throws Exception {
        assertFalse(verify("CN(C1=CC=CC=C1)C([H])([H])O"));
    }    
    public void testAromaticNmustards() throws Exception {
        assertFalse(verify("C1=CC=C(C=C1)CN(CCCl)CCCl"));
    }        
    public void testAromaticHydrazines() throws Exception {
        assertFalse(verify("CCN(C1=CC=CC=C1)N([H])[H]"));
    }  
    public void testAromatic_aryl_N_nitroso() throws Exception {
        assertFalse(verify("CCN(N=O)C1=CC=CC=C1"));
    }
    public void testAromatic_azide_and_triazeneGroups() throws Exception {
        assertFalse(verify("[H]C1=C([H])C([H])=C(C([H])=C1([H]))N(N=N)C([H])([H])C([H])([H])[H]"));
       
    }        
    public void testAromaticHudroxylAmines() throws Exception {
        assertFalse(verify("CN(O[H])C1=CC=CC=C1"));
       
    }     
    public void testcyclicN() throws Exception {
        assertFalse(verify("[H]C1=C([H])C([H])=C(C([H])=C1([H]))N2CCCC2"));
       
    }     

	public void testYes() throws Exception  {
		try {
		
			IAtomContainer yes = FunctionalGroups.createAtomContainer("c1ccc(N)cc1", true);
			MolAnalyser.analyse(yes);
			
			assertTrue(ruleToTest.verifyRule(yes));
			

				
		} catch (Exception x) {
			fail(x.getMessage());
		}
	}
    protected IIteratingChemObjectReader getReader(InputStream stream, DefaultChemObjectBuilder b) {
    	try {
    		return  new IteratingDelimitedFileReader(stream);
    	} catch (Exception x) {
    		logger.error(x);
    		return null;
    	}
    }
    @Override
    protected String getTestFileName() {
        return getResultsFolder() + "/qsar6train.csv";
    }
    @Override
    protected IDecisionRule createRuleToTest() throws Exception {
        return new RuleAromaticAmineNoSulfonicGroup();
    }
    @Override
    public String getHitsFile() {
        return getResultsFolder() +"/qsar6train.csv";
    }
    @Override
    public String getResultsFolder() {
        return "aromatic_amines";
    }	
    @Override
    protected String getRuleID(IDecisionRule rule) {
        return "aromatic_amines";
    }
    @Override
    protected String getSubstanceID() {
        return "Chemical Name (IUPAC)";
    }
}


