/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

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
package toxTree.cramer2;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import cramer2.rules.RuleSomeBenzeneDerivatives;

/**
 * TODO add description
 * @author ThinClient
 * <b>Modified</b> 2005-10-1
 */
public class RuleSomeBenzeneDerivativesTest extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new RuleSomeBenzeneDerivatives();
	}
	public void test() throws Exception {
		Object[][] answer = {
				{"c1cc2OCOc2(cc1CCC)",new Boolean(true)}, 
				{"c1cc(cc(c1)CCC)OC",new Boolean(false)} //not at para place
		};
		ruleTest(answer);
	}
	public void testNumbering() {
		//AtomContainer c = FunctionalGroups.createAtomContainer("C1C(OC(=C)C)C(CC(C)C1C)C(C)");
		IAtomContainer c = FunctionalGroups.createAtomContainer("C1CCCCC1",true);
		QueryAtomContainer q = FunctionalGroups.ring(6);
		FunctionalGroups.markAtomsInRing(c,q);
		int count = 0;
		for (int i=0;i<c.getAtomCount();i++) {
			Object o = c.getAtom(i).getProperty(FunctionalGroups.RING_NUMBERING);
			if (o != null) count ++;
		}
		assertEquals(12,count);
		
	}	
}
