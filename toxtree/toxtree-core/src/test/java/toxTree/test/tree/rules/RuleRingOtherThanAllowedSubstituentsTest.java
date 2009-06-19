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
package toxTree.test.tree.rules;

import junit.framework.TestCase;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.LoggingTool;

import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.MolAnalyseException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.tree.rules.RuleRingOtherThanAllowedSubstituents;

/**
 * TODO add description
 * @author Vedina
 * <b>Modified</b> 2005-8-19
 */
public class RuleRingOtherThanAllowedSubstituentsTest extends TestCase {
	public static LoggingTool logger = new LoggingTool(RuleRingOtherThanAllowedSubstituents.class);
	public static void main(String[] args) {
		junit.textui.TestRunner
				.run(RuleRingOtherThanAllowedSubstituentsTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		LoggingTool.configureLog4j();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for RuleRingOtherThanAllowedSubstituentsTest.
	 * @param arg0
	 */
	public RuleRingOtherThanAllowedSubstituentsTest(String arg0) {
		super(arg0);
	}
	public void testRuleRingOtherThanAllowedSubstituents() {
	    Object[][] answer = {
	            {"CCCCCCCCCCC",new Boolean(false),new Boolean(false)} 
	            ,{"c1c(CCCCCC)cccc1",new Boolean(false),new Boolean(false)} 
	            ,{"c1c(CCCCC(=O)C)cccc1",new Boolean(true),new Boolean(false)}
	            ,{"CCCCC(=O)C",new Boolean(false),new Boolean(false)}
	            ,{"C1CCCC(C=O)C1CC",new Boolean(true),new Boolean(false)}
	            ,{"C1CCCC(=O)C1CC",new Boolean(true),new Boolean(false)}
	            
	            };		
		RuleRingOtherThanAllowedSubstituents[] rule = new RuleRingOtherThanAllowedSubstituents[2];
		rule[0] = new RuleRingOtherThanAllowedSubstituents() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7609496754051392386L;

			@Override
			public boolean substituentIsAllowed(IAtomContainer a, int[] place) throws DecisionMethodException {
				return true;
			}
		};
		AtomContainer a = MoleculeFactory.makeAlkane(3);
		a.setID("CCC");
		rule[0].addSubstructure(a);
		
		rule[1] = new RuleRingOtherThanAllowedSubstituents() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8013631035232505488L;

			@Override
			public boolean substituentIsAllowed(IAtomContainer a, int[] place) throws DecisionMethodException {
 				return true;
			}
		};
		rule[1].addSubstructure(FunctionalGroups.ketone());
		rule[1].addSubstructure(FunctionalGroups.aldehyde());		
		
		SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		IMolecule mol;
		
		logger.debug("Fragment CCC");
		int r = 0;
		for (int i=0; i < answer.length; i++) 
			try {
				logger.debug(answer[i][0]); 				
					
				mol = sp.parseSmiles((String) answer[i][0]);
				MolAnalyser.analyse(mol);
				assertEquals(((Boolean) answer[i][r+1]).booleanValue(), rule[r].verifyRule(mol));
				
			} catch (InvalidSmilesException x) {
				fail();
			} catch (MolAnalyseException x) {
				x.printStackTrace();
				fail();
			} catch (DecisionMethodException x) {
				x.printStackTrace();
				fail();
			}
			logger.debug("Fragment Ketone");
		r = 1;
		for (int i=0; i < answer.length; i++) 
				try {
					logger.debug(answer[i][0]);
					mol = sp.parseSmiles((String) answer[i][0]);
					MolAnalyser.analyse(mol);
					assertEquals(((Boolean) answer[i][r+1]).booleanValue(), rule[r].verifyRule(mol));					

				} catch (InvalidSmilesException x) {
					fail();
				} catch (MolAnalyseException x) {
					x.printStackTrace();
					fail();
				} catch (DecisionMethodException x) {
					x.printStackTrace();
					fail();
				}			
	}

}
