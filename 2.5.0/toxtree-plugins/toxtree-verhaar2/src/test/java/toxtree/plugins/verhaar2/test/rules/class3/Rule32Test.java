/*
Copyright Ideaconsult Ltd. (C) 2005-2011 

Contact: jeliazkova.nina@gmail.com

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
package toxtree.plugins.verhaar2.test.rules.class3;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;
import verhaar.rules.Rule32;

public class Rule32Test extends AbstractRuleTest {
	@Override
	protected IDecisionRule createRule() {
		return new Rule32();
	}
	public void test() throws Exception {
	    Object[][] answer = {
	           	{"c1ccc(cc1)CCl",Boolean.TRUE},
	            	{"c1ccc(cc1)C(C)Cl",Boolean.TRUE},
	            	{"c1ccc(cc1)CBr",Boolean.TRUE},
	            	{"c1ccc(cc1)CI",Boolean.TRUE},
	            	{"c1ccc(cc1)CC#N",Boolean.TRUE},
	            	{"c1ccc(cc1)CO",Boolean.TRUE},
	       //    {"O=[N+]([O-])c1cc(c(O)c(c1)C)[N+](=O)[O-]",Boolean.TRUE}, not sure what should be
	            	{"c1ccc(cc1)CC(C)=O",Boolean.TRUE},
	          	{"c1ccc(cc1)CC=O",Boolean.TRUE},
	    };
	    
	    ruleTest(answer); 
	}	
	
	
}
