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
package toxtree.plugins.verhaar2.test.rules.class2;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.rules.Rule22;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;

public class Rule22Test extends AbstractRuleTest {
	@Override
	protected IDecisionRule createRule() {
		return new Rule22();
	}
	public void test() throws Exception {
	    Object[][] answer = {
	    		//aniline with subst.
            	{"Nc1ccccc1[N+](=O)[O-]",Boolean.TRUE},
            	{"Nc1cccc(Cl)c1",Boolean.TRUE},
            	{"Nc1ccc(Cl)c(Cl)c1",Boolean.TRUE},
            	{"Nc1cc(Cl)c(Cl)c(Cl)c1",Boolean.TRUE},
            	{"Nc1cc(Cl)c(Cl)c(Cl)c1[N+](=O)[O-]",Boolean.TRUE},
            	{"Nc1cccc(CCC)c1",Boolean.TRUE},
            	//
            	
            	//chlorine subst
            	{"Nc1c(C(F)(F)F)cc(F)cc1",Boolean.FALSE},

            	//CCN(CC)c1ccccc1  true

            	
	    };
	    
	    ruleTest(answer); 
	}	
	
	
}
