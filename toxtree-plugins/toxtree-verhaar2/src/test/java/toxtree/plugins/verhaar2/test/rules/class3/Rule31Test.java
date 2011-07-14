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

package toxtree.plugins.verhaar2.test.rules.class3;

import toxTree.core.IDecisionRule;
import toxtree.plugins.verhaar2.rules.Rule31;
import toxtree.plugins.verhaar2.test.rules.AbstractRuleTest;


public class Rule31Test extends AbstractRuleTest {
	@Override
	protected IDecisionRule createRule() {
		return new Rule31();
	}
	public void test() throws Exception {
	    Object[][] answer = {
	    		//halogen
	            	{"CC=CC(Cl)", Boolean.TRUE},
	            	{"CC=CC(Br)", Boolean.TRUE},
	            	{"CC=CC(I)", Boolean.TRUE},
	            	//cyano
	            	{"CC=CC(C#N)", Boolean.TRUE},
	            	//hydroxyl
	            	{"CC=CCO", Boolean.TRUE},
	            	//ketone
	            	{"CC=CC(C(C)=O)", Boolean.TRUE},
	            	//aldehyde
	            	{"CC=CC(C=O)", Boolean.TRUE},
	            	
	            	//halogen
	            	{"CC#CC(C)Cl", Boolean.TRUE},
	            	{"CC#CC(C)Br", Boolean.TRUE},
	            	{"CC#CC(C)I", Boolean.TRUE},
	            	//cyano
	            	{"CC#CC(C)C#N", Boolean.TRUE},
	            	//hydroxyl
	            	{"CC#CC(C)O", Boolean.TRUE},	            	
	            	//ketone
	            	{"CC#CC(C)(C(C)=O)", Boolean.TRUE},
	            	//aldehyde
	            	{"CC#CC(C)C=O", Boolean.TRUE},	
	            	//aromatic bond should not be considered as activating
	            	{"n1cc(cc(c1Cl)Cl)Cl",Boolean.FALSE}	
	            	

	    };
	    ruleTest(answer); //      	{"OCCOC1=CC=CC=C1",new Boolean(false)} ,
	}	
	
	
}



