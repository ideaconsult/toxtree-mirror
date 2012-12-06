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
import toxTree.query.FunctionalGroups;
import toxtree.plugins.ames.rules.SA26_gen;
import toxtree.plugins.ames.test.TestAmesMutagenicityRules;

public class SA26_genTest extends TestAmesMutagenicityRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA26_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA26/sa4iss2.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA26";
	}
	public void testGroup() throws Exception {
		assertTrue(FunctionalGroups.hasGroup(ruleToTest.getExampleMolecule(true), FunctionalGroups.noxide_aromatic()));
	}
	public void testAromaticity_from_SMILES() throws Exception {
		assertTrue(verifyRule(ruleToTest,"O=[N+]([O-])C5=CC=C2C=CC3=C1C=CC=CC1=[N+]([O-])C=4C=CC5(=C2C3=4)"));
	}
}
