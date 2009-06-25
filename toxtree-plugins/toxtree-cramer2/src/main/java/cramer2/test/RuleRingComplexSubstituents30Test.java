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
package cramer2.test;

import toxTree.core.IDecisionRule;
import toxTree.tree.cramer.RuleRingComplexSubstituents30;

/**
 * Test for {@link RuleRingComplexSubstituents30}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-19
 */
public class RuleRingComplexSubstituents30Test extends AbstractRuleTest {
	@Override
	protected IDecisionRule createRule() {
		return  new RuleRingComplexSubstituents30();
	}
	public void testRule() throws Exception{

	    //
	    Object[][] answer = {
	            {"CC1=CC(=C(O)C(=C1)C(C)(C)C)C(C)(C)C",new Boolean(false)}, //30N
	            {"SC1=CC=CC=C1",new Boolean(true)},  //30Y
	            {"COC(OC)C1=CC=CC=C1",new Boolean(true)},  //31Y
				
	            {"O=C(OCC)c1ccccc1(NC)",new Boolean(true)},  //DMA Appendix 1				
	            {"CC(C)CCC(=O)C1=CC=CC=C1",new Boolean(true)},  //32Y
	            {"O=C(Oc1ccc(cc1)C(C)(C)C)c2ccccc2(O)",new Boolean(false)},  //TBPS
	            {"CCCCC(CC)COC(=O)c1ccccc1(C(=O)OCC(CC)CCCC)",new Boolean(false)},  //DEHP
	            
	            //{"O=C(OCC(OC(=O)c1ccccc1)C)C(C=C)=CC=CC",new Boolean(false)},  //PGDB Appendix 1
	            {"O=C(OC)c1ccc(O)cc1",new Boolean(false)},  //MHB Appendix 1
	            {"O=C(OCC)c1ccc(O)cc1",new Boolean(false)},  //EHB Appendix 1
	            {"O=C(OCCC)c1ccc(O)cc1",new Boolean(false)},  //EHB Appendix 1
	            {"Oc1ccc(cc1(OC))CC=C",new Boolean(false)},  //Eugenol Appendix 1
				
				
	            };
        
	    ruleTest(answer);
	}
	
	public void test() throws Exception {
	    Object[][] answer = {
	            {"CCCCC(CC)COC(=O)c1ccccc1(C(=O)OCC(CC)CCCC)",new Boolean(false)}, //!!! long chaines are hydrolised  
	            };
	    ruleTest(answer);
	}
	
}
