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

package toxtree.plugins.proteinbinding.test;

import toxTree.core.IDecisionRule;
import toxtree.plugins.proteinbinding.rules.AcylTransferRule;

public class AcylTransferRuleTest extends TestProteinBindingRules {
    String[] smiles = {

    };
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /*
    public void testTrue() throws Exception {
        //IAtomContainer c = FunctionalGroups.createAtomContainer("CC(=O)NC=1C=CC=C2C3=CC=CC=C3(CC=12)",true);
        IAtomContainer c = FunctionalGroups.createAtomContainer("C=1C=C2C=CC3=CC=CC4=CC=C(C=1)C2=C34",true);
        
            MolAnalyser.analyse(c);
            assertTrue(ruleToTest.verifyRule(c));


    }
    
    */
	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new AcylTransferRule();
	}
	@Override
	public String getHitsFile() {
		return "AC/test.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "AC";
	}
  
}
