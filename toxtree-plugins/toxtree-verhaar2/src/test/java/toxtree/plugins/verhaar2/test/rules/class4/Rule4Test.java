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

package toxtree.plugins.verhaar2.test.rules.class4;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;
import verhaar.rules.Rule4;


public class Rule4Test extends AbstractRuleTest {
	@Override
	protected IDecisionRule createRule() throws Exception {
		return new Rule4();
	}
	public void test() throws Exception {
	    Object[][] answer = {
	    		//Organotin
	            	{"C[Sn]",new Boolean(true)},
	            //DDT	
	            	{"Clc1ccc(cc1)C(c2ccc(Cl)cc2)C(Cl)(Cl)Cl",new Boolean(true)},
	            	{"Clc2ccc(C(=C(Cl)Cl)c1ccc(Cl)cc1)cc2",new Boolean(true)},
	            	{"Clc1ccc(cc1)C(c2ccc(Cl)cc2)C(Cl)Cl",new Boolean(true)},
	            	
	            	//pyrethroids
	            	{"O=C(O)C1C(C=C(C)C)C1(C)(C)",new Boolean(true)},
	            	//dithiocarbamates
	            	{"NC(=S)S",new Boolean(true)},
	            	//organophosphorothionate esters
	            	{"O=P(Oc1ccccc1)(Oc2ccccc2)Oc3ccccc3",new Boolean(true)},
	            	
	    };
	    ruleTest(answer); 
	}	
	
	
}



