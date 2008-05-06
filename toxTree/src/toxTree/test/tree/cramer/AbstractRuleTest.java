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

package toxTree.test.tree.cramer;

import junit.framework.TestCase;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;

import toxTree.core.IDecisionRule;
import toxTree.logging.TTLogger;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;

public abstract class AbstractRuleTest extends TestCase {
	protected static TTLogger logger = new TTLogger(AbstractRuleTest.class);
	protected IDecisionRule rule2test = null;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		rule2test = createRule();
		TTLogger.configureLog4j(true);
	}
	@Override
	protected void tearDown() throws Exception {
		rule2test = null;
		super.tearDown();
	}
	protected abstract IDecisionRule createRule();
	/**
	 * 
	 * @param smiles_and_answer  {{String smiles, Boolean answer},...}
	 * @throws Exception
	 */
	public void ruleTest(Object[][] smiles_and_answer) throws Exception {

        int success = 0;
	    for (int i = 0; i < smiles_and_answer.length; i++) {
	    	IMolecule mol = (IMolecule)FunctionalGroups.createAtomContainer(smiles_and_answer[i][0].toString());
	        if (mol != null) {
	            	MolAnalyser.analyse(mol);
	                Boolean b = (Boolean) smiles_and_answer[i][1];
	                
	                boolean r = rule2test.verifyRule(mol);
	                
	                if (b.booleanValue() == r) {
	                	success++;
	                    logger.info(smiles_and_answer[i][0],"\tOK");
	                } else 
	                	logger.error(smiles_and_answer[i][0],"\tFAILED");
	        } else throw new Exception("Null molecule "+smiles_and_answer[i][0]);        
	            
	            
	    }
	    assertEquals(smiles_and_answer.length,success);
	}	
	public abstract void test() throws Exception;
	public void testExampleYes() throws Exception {
		IAtomContainer a = rule2test.getExampleMolecule(true);
		MolAnalyser.analyse(a);
		assertTrue(
				rule2test.verifyRule(a)
		);
	}
	public void testExampleNo() throws Exception {
		IAtomContainer a = rule2test.getExampleMolecule(false);
		MolAnalyser.analyse(a);		
		assertFalse(
				rule2test.verifyRule(a)
		);
	}	
}


