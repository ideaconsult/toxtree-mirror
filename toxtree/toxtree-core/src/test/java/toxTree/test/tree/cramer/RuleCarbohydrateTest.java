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
package toxTree.test.tree.cramer;

import toxTree.core.IDecisionRule;
import toxTree.tree.rules.RuleCarbohydrate;

public class RuleCarbohydrateTest extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new RuleCarbohydrate();
	}
	public void test() throws Exception  {
	    Object[][] answer = {
//PG Cramer appendix	    		
	    		{"OCC(O)C",new Boolean(false)},
//	    		BG Cramer appendix	    		
	    		{"OCCC(O)C",new Boolean(false)},
//	    		Actn Cramer appendix	    		
	    		{"O=C(C(O)CC)C",new Boolean(false)},	    			    		
//	    	DHAC, Cramer appendix	    	
	            {"O=C1OC(=CC(=O)C1C(=O)C)C",new Boolean(false)},
//	          Cramer, xylose	            
	            {"OC1COC(O)C(O)C1(O)",new Boolean(true)},
	            };
        
	    ruleTest(answer);
	    
	}
	
	public void test_xylitol() throws Exception  {
	    Object[][] answer = {
//	          Munro, xylitol
	            {"OCC(O)C(O)C(O)CO",new Boolean(true)},
	            };
        
	    ruleTest(answer);
	    
	}
	public void test_sucrose_monopalmitate() throws Exception  {
	    Object[][] answer = {
//	          Munro,  sucrose monopalmitate
	            {"CCCCCCCCCCCCCCCC(=O)O[C@]1(O[C@H](CO)[C@@H](O)[C@H](O)[C@H]1O)[C@@]2(CO)O[C@H](CO)[C@@H](O)[C@@H]2O",new Boolean(true)}, 
	            };
        
	    ruleTest(answer);
	    
	}
	
	public void test_sucrose_monostearate() throws Exception  {
	    Object[][] answer = {
// Munro, sucrose monostearate
	            {"CCCCCCCCCCCCCCCCCC(=O)O[C@]1(O[C@H](CO)[C@@H](O)[C@H](O)[C@H]1O)[C@@]2(CO)O[C@H](CO)[C@@H](O)[C@@H]2O",new Boolean(true)}
	            };
        
	    ruleTest(answer);
	    
	}	

}
