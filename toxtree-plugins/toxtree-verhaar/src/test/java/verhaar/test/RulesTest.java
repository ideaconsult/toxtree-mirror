/*
Copyright Nina Jeliazkova (C) 2005-2006  
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
package verhaar.test;

import junit.framework.TestCase;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.MolAnalyseException;
import toxTree.logging.TTLogger;
import toxTree.query.MolAnalyser;
import verhaar.VerhaarScheme;
import verhaar.query.FunctionalGroups;
import verhaar.rules.Rule01;
import verhaar.rules.Rule11;
import verhaar.rules.Rule13;
import verhaar.rules.Rule14;
import verhaar.rules.Rule141;
import verhaar.rules.Rule142;
import verhaar.rules.Rule143;
import verhaar.rules.Rule171;
import verhaar.rules.Rule21;
import verhaar.rules.RuleIonicGroups;
import verhaar.rules.RuleLogPRange;

/**
 * JUnit test case for {@link verhaar.rules}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-30
 */
public class RulesTest extends TestCase {
	protected static TTLogger logger ;
	public static void main(String[] args) {
		junit.textui.TestRunner.run(RulesTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for RulesTest.
	 * @param arg0
	 */
	public RulesTest(String arg0) {
		super(arg0);
		logger = new TTLogger(this.getClass());
		TTLogger.configureLog4j(true);		
	}
	public void ruleTest(IDecisionRule rule, String[] smiles, boolean[] answers) {
		for (int i=0;i< smiles.length;i++) {
			IAtomContainer mol = FunctionalGroups.createAtomContainer(smiles[i]);
			try {
				MolAnalyser.analyse(mol);
				boolean b = rule.verifyRule(mol);
				logger.debug(smiles[i],"\tobserved\t",Boolean.toString(b),"\texpected\t",Boolean.toString(answers[i]));
				
				assertEquals(answers[i],b);
				System.out.println(smiles[i] + "\tOK");
			} catch (MolAnalyseException x) {
				logger.error(x);
				fail();
			} catch (DecisionMethodException x) {
				logger.error(x);
				fail();
			}
		}
	}
	/**
	 * test for  {@link Rule141}
	 *
	 */
	public void testRule141() {
		//String[] smiles = {"c1ccccc1CCl","CCCCC=C(Cl)CCC"};
		//boolean[] answers = {true,false};
		String[] smiles = {"CCC=CCCl","c1ccccc1CCl","CCC=CCl"};
		boolean[] answers = {false,false,true};		
		ruleTest(new Rule141(),smiles,answers);		
		
	}
	public void testRule14() {
		String[] smiles = {"ClC(Cl)(Cl)CCCCC(N)","CCCCC(N)","CCC(Cl)CC(Br)","CCC"};
		boolean[] answers = {false,false,true,false};		
		ruleTest(new Rule14(),smiles,answers);
		
	}
	/**
	 * test for  {@link Rule142}
	 *
	 */
	public void testRule142() {
		String[] smiles = {"c1ccccc1Cl"};
		boolean[] answers = {true};
		ruleTest(new Rule142(),smiles,answers);
				
	}
	/**
	 * test for  {@link Rule143}
	 *
	 */
	public void testRule143() {
		String[] smiles = {"c1cc(CCCCCCCC)ccc1CCl","c1(CCCCCCCC)ccccc1CCCl"};
		boolean[] answers = {false,true};
		ruleTest(new Rule143(),smiles,answers);
				
	}	
	/**
	 * test for  {@link verhaar.rules.Rule21}
	 *
	 */
	public void testRule21() {
		String[] smiles = {"c1ccccc1(O)","c1ccc(Cl)cc1(O)","Clc1cc(O)c(Cl)c(Cl)c1(Cl)","O=[N+]([O-])c1cc(O)cc(c1)[N+](=O)[O-]"};
		boolean[] answers = {true,true,false,false};
		ruleTest(new Rule21(),smiles,answers);
				
		
	}
	
	/**
	 * test for {@link verhaar.rules.Rule171}
	 *
	 */
	public void testRule171() {
		String[] smiles = {"ClCC=C"};
		boolean[] answers = {false};
		ruleTest(new Rule171(),smiles,answers);
				
		
	}	

	/**
	 * test for {@link RuleLogPRange}
	 *
	 */
	public void testRuleLogPRange() {
		String[] smiles = {"[O-][N+](=O)OCCOCCO[N+]([O-])=O"};
		boolean[] answers = {true};
		ruleTest(new RuleLogPRange(),smiles,answers);
				
		
	}	
	
	

	public void testNitroPhenolRule21() {
		
		IAtomContainer mol = FunctionalGroups.makeNitroPhenol();
		try {
			MolAnalyser.analyse(mol);
			assertTrue(new Rule21().verifyRule(mol));
		} catch (Exception x) {
			x.printStackTrace();
			fail();
		}
	}
	
	public void testRule01() {
		String[] smiles = {"c1ccccc1Cl","c1ccccc1O","c1ccccc1I","OP(O)(O)=O.CCOC(=O)C=1CC(N)C(NC(C)=O)C(OC(CC)CC)C=1"};
		boolean[] answers = {true,true,false,false};
		ruleTest(new Rule01(),smiles,answers);
	}
	public void testRule11() {
		String[] smiles = {"c1ccccc1Cl","c1ccccc1O","c1ccccc1I"};
		boolean[] answers = {true,true,false};
		ruleTest(new Rule11(),smiles,answers);
	}
	public void testRule13() {
		String[] smiles = {"c1ccccc1","c1ccccc1O","c1ccccc1I"};
		boolean[] answers = {true,false,false};
		ruleTest(new Rule13(),smiles,answers);
	}		

	public void testIonic() {
		String[] smiles = {"[NH3+]CCC1=CC=C([NH3+])C=C1.[S-]C(=S)N(CC1=CC=CC=C1)CC1=CC=CC=C1.[S-]C(=S)N(CC1=CC=CC=C1)CC1=CC=CC=C1"};
		boolean[] answers = {true};
		ruleTest(new RuleIonicGroups(),smiles,answers);
	}
	
	 
}
