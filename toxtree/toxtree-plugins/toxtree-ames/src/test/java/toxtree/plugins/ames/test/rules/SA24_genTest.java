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
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolAnalyser;
import toxtree.plugins.ames.rules.SA24_gen;
import toxtree.plugins.ames.test.TestAmesMutagenicityRules;

public class SA24_genTest extends TestAmesMutagenicityRules {
	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA24_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA24/sa26iss2_linear.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA24";
	}
	
	/**
	 * https://sourceforge.net/tracker/?func=detail&aid=3138570&group_id=152702&atid=785126
	 */
	public void test_bug3138570() throws Exception {
		SmilesParser p = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IMolecule m = p.parseSmiles("C1CC=CO1");

		try {
			/*
			HydrogenAdder ha = new HydrogenAdder();
			ha.addExplicitHydrogensToSatisfyValency(m);
			*/
			MolAnalyser.analyse(m);
			for (int i=0; i < m.getAtomCount();i++)
				/*
            	https://sourceforge.net/tracker/?func=detail&aid=3020065&group_id=20024&atid=120024
                System.out.println(m.getAtom(i).getSymbol() + '\t'+ m.getAtom(i).getHydrogenCount());
            	*/
				System.out.println(m.getAtom(i).getSymbol() + '\t'+ m.getAtom(i).getImplicitHydrogenCount());
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
		assertEquals(false,ruleToTest.verifyRule(m));
		
	}
	
}
