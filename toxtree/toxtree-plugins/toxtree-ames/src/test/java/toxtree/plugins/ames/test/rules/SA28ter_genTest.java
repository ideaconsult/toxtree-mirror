/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

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

package toxtree.plugins.ames.test.rules;

import toxTree.core.IDecisionRule;
import toxtree.plugins.ames.rules.SA28ter_gen;
import toxtree.plugins.ames.test.TestAmesMutagenicityRules;

public class SA28ter_genTest extends TestAmesMutagenicityRules {
	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA28ter_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA28ter/sa27_l_iss2.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA28ter";
	}
	
	public void testSO3H() {
		try {
			//no SO3 group
			assertTrue(
					verifyRule(ruleToTest, "CC(=O)NC1=CC=C(C=C1)N=NC2=CC(C)=CC=C2(O)")
					);
			//SO3 on the amino ring
			assertFalse(
					verifyRule(ruleToTest, "CC(=O)NC=2C=CC(N=NC1=CC(C)=CC=C1(O))=C(C=2)S(=O)(=O)[O-]")
					);			
			//SO3H on the other ring
			assertTrue(
					verifyRule(ruleToTest, "CC(=O)NC1=CC=C(C=C1)C=CC=2C(O)=CC=C(C)C=2S(=O)(=O)[O-]")
					);			 

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
//
