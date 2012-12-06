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
import toxtree.plugins.ames.rules.SA31b_nogen;
import toxtree.plugins.ames.test.TestAmesMutagenicityRules;

public class SA31b_nogenTest extends TestAmesMutagenicityRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA31b_nogen();
	}
	@Override
	public String getHitsFile() {
		return "NA31b/HaloPAH.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA31b";
	}
	 
	public void test_new() throws Exception {
		assertFalse(verifyRule(ruleToTest,"CC1C=3C=C(F)C=CC=3(C=2C=CC(F)=CC1=2)"));
	}
	public void testAromaticity_from_smiles() throws Exception{
		String[] smiles = {
		 "CCOC(=O)C(O)(C1=CC=C(C=C1)Cl)C=2C=CC(=CC=2)Cl",
		"C1=CC(=CC=C1C(C2=CC=C(C=C2)Cl)=C(Cl)Cl)Cl",
		"O(C=1C(=C(C(=C(C=1Br)Br)Br)Br)Br)C2=C(C(=C(C(=C2Br)Br)Br)Br)Br",
		"OC(C=1C=CC(=CC=1)Cl)(C2=CC=C(C=C2)Cl)C(Cl)(Cl)Cl",
		"C=1C=C(C=CC=1C(C=2C=CC(=CC=2)Cl)C(Cl)Cl)Cl",
		"C=1C=C(C=CC=1C(C=2C=CC(=CC=2)Cl)C(Cl)(Cl)Cl)Cl",
		"O=S(=O)(C=1C=CC(=CC=1)Cl)C2=CC=C(C=C2)Cl",
		};
		for (int i=0; i < smiles.length; i++)
			assertTrue(verifyRule(ruleToTest,smiles[i]));
	}
}
