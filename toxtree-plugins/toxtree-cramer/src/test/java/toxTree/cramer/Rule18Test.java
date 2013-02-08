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
package toxTree.cramer;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.tree.cramer.RuleKetoneAlcoholEtc;

/**
 * TODO add description
 * @author Vedina
 * <b>Modified</b> 2005-8-20
 */
public class Rule18Test extends AbstractRuleTest {
	@Override
	protected IDecisionRule createRule() {
		return new RuleKetoneAlcoholEtc();
	}
	public void testRuleA() {
	    Object[][] answer = {
	    		//vicinal diketone
	            {"O=C(C(=O)Cc1ccccc1)CC(C)C(C)C",new Boolean(true)}, 
	            {"O=C(CC(=O)C(C)C(C)C)Cc1ccccc1",new Boolean(false)}, 
	            {"OC(=O)C(O)=O",new Boolean(false)},				
				//ketone attached to terminal vinyl
	            {"O=C(C=C)C(CC)CC(C)c1ccccc1",new Boolean(true)}, 
	            {"O=C(C=CC(C)C)C(C)C",new Boolean(false)},  
				//ketal attached to terminal vinyl
	            {"OC(O)(C=C)CCC(C)C",new Boolean(false)}, 
	            {"O(C)C(C=C)(OC)CCC",new Boolean(true)},  //not an acetal  RC(R)(OR)OR
	            {"O(C)C(OC)(C=CC)CC",new Boolean(false)}, //not a terminal vinyl
	            
	            };
	    QueryAtomContainer q = FunctionalGroups.vicinalDiKetone();
	    for (int i=0; i < 3; i++)
	    assertEquals(FunctionalGroups.hasGroup(
	    		FunctionalGroups.createAtomContainer((String)answer[i][0],true),q),
				((Boolean)answer[i][1]).booleanValue());

	    q = FunctionalGroups.ketoneAttachedToTerminalVinyl();
	    for (int i=3; i < 5; i++) {
		    logger.fine(q.getID()+" "+answer[i][0]);	    	
		    assertEquals(FunctionalGroups.hasGroup(
		    		FunctionalGroups.createAtomContainer((String)answer[i][0],true),q),
					((Boolean)answer[i][1]).booleanValue());

	    }    
	    
	    q = FunctionalGroups.ketalAttachedToTerminalVinyl();
	    for (int i=5; i < 8; i++) {
	        logger.finer(q.getID()+answer[i][0]);
		    assertEquals(FunctionalGroups.hasGroup(
		    		FunctionalGroups.createAtomContainer((String)answer[i][0],true),q),
					((Boolean)answer[i][1]).booleanValue());
	    }
	    //classify(answer);
	}	

	public void testRuleB() throws Exception {
	    Object[][] answer = {
	    		//secondary alcohol attached to terminal vinyl
	            {"OC(C=C)C(C)C",new Boolean(true)}, 
	            {"OC(C=C)(C)C(C)C",new Boolean(false)},
	            {"OC(C=CC)C(C)C",new Boolean(false)}, 
				//ester of secondary alcohol attached to terminal vinyl

	            {"C=CC(C(=O)OC)C",new Boolean(true)}, 
	            {"O=C(O)C(C)C=C",new Boolean(false)},  
	            {"C=CC(COC)C",new Boolean(false)},
	            };
	    assertTrue(FunctionalGroups.hasGroup(
	    		FunctionalGroups.createAtomContainer((String)answer[0][0],true),
				FunctionalGroups.alcoholSecondaryAttachedToTerminalVinyl()));
	    assertFalse(FunctionalGroups.hasGroup(
	    		FunctionalGroups.createAtomContainer((String)answer[1][0],true),
				FunctionalGroups.alcoholSecondaryAttachedToTerminalVinyl()));
	    assertFalse(FunctionalGroups.hasGroup(
	    		FunctionalGroups.createAtomContainer((String)answer[2][0],true),
				FunctionalGroups.alcoholSecondaryAttachedToTerminalVinyl()));
	    
	    
	    
	    assertTrue(FunctionalGroups.hasGroup(
	    		FunctionalGroups.createAtomContainer((String)answer[3][0],true),
				FunctionalGroups.esterOfalcoholSecondaryAttachedToTerminalVinyl()));
	    assertFalse(FunctionalGroups.hasGroup(
	    		FunctionalGroups.createAtomContainer((String)answer[4][0],true),
				FunctionalGroups.esterOfalcoholSecondaryAttachedToTerminalVinyl()));
	    assertFalse(FunctionalGroups.hasGroup(
	    		FunctionalGroups.createAtomContainer((String)answer[5][0],true),
				FunctionalGroups.esterOfalcoholSecondaryAttachedToTerminalVinyl()));

	    ruleTest(answer);
	}	
	
	public void testRuleC() throws Exception {
	    Object[][] answer = {
	    		//allyl alcohol
	            {"OCC=C",new Boolean(true)}, 
	            {"O(C)C(C=C)C",new Boolean(false)},
	            {"C=CCOC",new Boolean(false)}, 
				//allyl alcohol acetal
	            {"O(CC=C)C(OCC=C)C",new Boolean(true)}, 
	            {"O(CC=C)C(OCC=C)(C)C",new Boolean(false)},  
	            {"O(CC=C)C(OCC=CC)C",new Boolean(false)},
				//allyl alcohol ester derivative
	            {"O=C(OCC=C)C",new Boolean(true)}, 
	            {"O=C(OCC=C)c1ccccc1",new Boolean(true)},  
	            {"O=C(OCCC)cc",new Boolean(false)},				
	            };
	    IAtomContainer q = FunctionalGroups.allylAlcohol();
	    assertTrue(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[0][0],true),
				q));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[1][0],true),
				q));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[2][0],true),
				q));
	    
	    
	    q = FunctionalGroups.allylAlcoholAcetal();
	    assertTrue(FunctionalGroups.hasGroup(
	    		FunctionalGroups.createAtomContainer((String)answer[3][0],true),
				q));
	    assertFalse(FunctionalGroups.hasGroup(
	    		FunctionalGroups.createAtomContainer((String)answer[4][0],true),
				q));
	    assertFalse(FunctionalGroups.hasGroup(
	    		FunctionalGroups.createAtomContainer((String)answer[5][0],true),
				q));

	    q = FunctionalGroups.allylAlcoholEsterDerivative();
	    assertTrue(FunctionalGroups.hasGroup(
	    		FunctionalGroups.createAtomContainer((String)answer[6][0],true),
				q));
	    assertTrue(FunctionalGroups.hasGroup(
	    		FunctionalGroups.createAtomContainer((String)answer[7][0],true),
				q));
	    IAtomContainer mol = FunctionalGroups.createAtomContainer((String)answer[8][0],true);
	    assertNotNull(mol);
	    assertFalse(FunctionalGroups.hasGroup(
	    		mol,
				q));	    
	    ruleTest(answer);
	}
	public void testRuleD()  throws Exception {
	    Object[][] answer = {
	    		//allyl mercaptan
	            {"SCC=C",new Boolean(true)}, 
	            {"SC(C=C)CC",new Boolean(false)},
	            {"SCC=CC",new Boolean(false)}, 
				//allyl sulphide
	            {"S(CC=C)CC=C",new Boolean(true)}, 
	            {"S(C(C=C)C)C(C=C)C",new Boolean(false)},  
	            {"S(CC=C)CC=CC",new Boolean(false)},
				//allyl thioester
	            {"O=CSCC=C",new Boolean(true)}, 
	            {"O=CSCC(=C)C",new Boolean(false)},  
	            {"O=CSC(C=C)C",new Boolean(false)},
				//allyl amine
	            {"C=CCN",new Boolean(true)}, 
	            {"NCC=CC",new Boolean(false)},  
	            {"C=CC(N)C",new Boolean(false)},				
	            };
	    IAtomContainer a = FunctionalGroups.allylMercaptan();
	    assertTrue(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[0][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[1][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[2][0],true),
				a));
	    
	    a = FunctionalGroups.allylSulphide();
	    assertTrue(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[3][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[4][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[5][0],true),
				a));

	    a = FunctionalGroups.allylThioester();
	    assertTrue(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[6][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[7][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[8][0],true),
				a));

	    a = FunctionalGroups.allylAmine();
	    assertTrue(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[9][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[10][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[11][0],true),
				a));	    
	    ruleTest(answer);
	}	

	public void testRuleE() throws Exception  {
	    Object[][] answer = {
	    		//acrolein
	            {"C=CC=O",new Boolean(true)}, 
	            {"CC=CC=O",new Boolean(false)},
	            {"C=C(CC)C=O",new Boolean(false)}, 
				//methacrolein
	            {"C=C(C)C=O",new Boolean(true)}, 
	            {"CCC=C(C)C=O",new Boolean(false)},  
	            {"C=C(CCC)C=O",new Boolean(false)},
	            };
	    IAtomContainer a = FunctionalGroups.acrolein();
	    assertTrue(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[0][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[1][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[2][0],true),
				a));
	    
	    a = FunctionalGroups.methacrolein();
	    assertTrue(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[3][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[4][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[5][0],true),
				a));

	    ruleTest(answer);
	}	

	public void testRuleF() throws Exception{
	    Object[][] answer = {
	    		//acrylic acid
	            {"C=CC(O)=O",new Boolean(true)}, 
	            {"CCC=CC(O)=O",new Boolean(false)},
	            {"C=CCCCCCCC(O)=O",new Boolean(false)}, 
				//methacrylic acid
	            {"C=C(C)C(O)=O",new Boolean(true)}, 
	            {"CCC=C(C)C(O)=O",new Boolean(false)},  
	            {"C=C(CC)C(O)=O",new Boolean(false)},
	            };
	    IAtomContainer a = FunctionalGroups.acrylicAcid();
	    assertTrue(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[0][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[1][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[2][0],true),
				a));
	    
	    a = FunctionalGroups.methacrylicAcid();
	    assertTrue(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[3][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[4][0],true),
				a));
	    assertFalse(FunctionalGroups.isSubstance(
	    		FunctionalGroups.createAtomContainer((String)answer[5][0],true),
				a));

	    ruleTest(answer);
	}	
	public void testRuleI() {
	    Object[][] answer = {
	    		//sterically hindered
	            {"Oc1ccccc1C(C)C",new Boolean(false)}, 
	            {"O=C(Cc1ccccc1)CC(=O)C(C)C(C)C",new Boolean(false)},
	            {"O=C(C)CC(=O)C(CC)C(C)C",new Boolean(false)},
	            {"C1CCC(C(C1)C(C)(C)C)C(C)(C)C",new Boolean(false)},
				{"OC1C(CCCC1C(C)(C)C)C(C)(C)C",new Boolean(true)}
				
	            };
		
		    QueryAtomContainer q = FunctionalGroups.stericallyHindered();
		    
		    for (int i=0; i < answer.length; i++) {
		    	logger.finer(q.getID()+answer[i][0]);	    			    	
			    assertEquals(FunctionalGroups.hasGroup(
			    		FunctionalGroups.createAtomContainer((String)answer[i][0],true),q),
						((Boolean)answer[i][1]).booleanValue());
		    }

	    //classify(answer);
	}	
		/*
	public void classify(Object[][] answer) throws Exception {
	    IDecisionRule rule18 = new RuleKetoneAlcoholEtc();
	    for (int i = 0; i < answer.length; i++) {
	        IMolecule mol = (IMolecule) FunctionalGroups.createAtomContainer((String)answer[i][0]);
	        if (mol != null) {
	            	MolAnalyser.analyse(mol);
	                Boolean b = (Boolean) answer[i][1];
	                System.out.println("ANALYZING\t"+answer[i][0]);
	                assertEquals(b.booleanValue(),rule18.verifyRule(mol));
	        }        
	    }
	    
	}
	*/
	public void test() throws Exception {
	    Object[][] answer = {
	    		{"CC(OC)(OC)C=C",new Boolean(true)},  //18Y
	            {"C1C(OC(=C)C)C(CC(C)C1C)C(C)C",new Boolean(false)}, //18N
	            
	            //
	            };
	    ruleTest(answer);
	}

	
}
