/*
Copyright Ideaconsult Ltd. (C) 2005-2011

Contact: jeliazkova.nina@gmail.com

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

package toxtree.plugins.verhaar2.test.rules.class2;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.rules.Rule21;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;


public class Rule21Test extends AbstractRuleTest {
	@Override
	protected IDecisionRule createRule() {
		return new Rule21();
	}
	public void test() throws Exception {
		
	
	    Object[][] answer = {

	            	{"c1ccccc1(O)",new Boolean(true)},
	            	{"c1ccc(Cl)cc1(O)",new Boolean(true)},
	            	{"Clc1cc(O)c(Cl)c(Cl)c1(Cl)",new Boolean(false)},
	            	{"O=[N+]([O-])c1cc(O)cc(c1)[N+](=O)[O-]",new Boolean(false)},

	            	{"Oc1ccc([N+](=O)[O-])c([N+](=O)[O-])c1[N+](=O)[O-]",new Boolean(false)},
	            	
	           
	    };
	    

	    ruleTest(answer); 
	}	
	
	
}



