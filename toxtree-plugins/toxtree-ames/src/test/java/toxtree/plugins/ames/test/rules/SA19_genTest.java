/*
Copyright Ideaconsult Ltd. (C) 2005-2015

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

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxtree.plugins.ames.rules.SA19_gen;
import toxtree.plugins.ames.test.TestAmesMutagenicityRules;

public class SA19_genTest extends TestAmesMutagenicityRules {
	protected String[] smiles = { "CCN2C3=CC=CC=C3(C=1C=C(N)C=CC=12)",
			"NC1=CC=C2C=C3C=CC(N)=CC3(=NC2(=C1))",
			"C1=CC=C3C(=C1)NC=2C=CC=CC=23", "CN2C(N)=NC1=C3C=CC=NC3(=CC=C12)",
			"CC1=CC3=C(N=C1(N))NC=2C=CC=CC=23",
			"CC2=NC(N)=C(C)C=3NC1=CC=CC=C1C2=3",
			"CC2=CC1=NC=CC=C1C=3N=C(N)N(C)C2=3",
			"NC=1C=CC=2C3=CC=CC=C3(NC=2(N=1))",
			"NC=2C=CC=3OC=1C=CC=CC=1C=3(C=2)",
			"COC1=CC3=C(C=C1(N))OC2=CC=CC=C23",
			"CC3=CC=CN2C3(=NC=1C=CC(N)=NC=12)",
			"NC=2C=CC=3N=C1C=CC=CN1C=3(N=2)"

	};

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA19_gen();
	}

	@Override
	public String getHitsFile() {
		return "NA19/heteroPAH.sdf";
	}

	@Override
	public String getResultsFolder() {
		return "NA19";
	}
	@Test
	public void testAromaticity() throws Exception {
		assertEquals(10, printAromaticity());
	}

	@Test
	public void testPeroxide() throws Exception {
		IAtomContainer c = FunctionalGroups.createAtomContainer(
				"C=1C=CC=2C=C3C=C4C=C5OC5(=CC4(=CC3(=CC=2(C=1))))", true);
		MolAnalyser.analyse(c);
		assertFalse(ruleToTest.verifyRule(c));
	}
	@Test
	public void testSMILES() throws Exception {
		for (String smile : smiles) {
			System.out.print(smile);
			assertTrue(verify(smile));
			System.out.println(" OK");
		}
	}

}
