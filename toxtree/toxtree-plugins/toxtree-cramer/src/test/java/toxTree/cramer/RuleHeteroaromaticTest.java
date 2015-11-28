/*
Copyright (C) 2005-2007  

Contact: nina@acad.bg

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

package toxTree.cramer;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import toxTree.tree.cramer.RuleHeteroaromatic;

public class RuleHeteroaromaticTest extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		return new RuleHeteroaromatic();
	}

	@Override
	public void test() throws Exception {
		
		Object[][] answer = {
			{"c12-c3c(cccc3)Nc1nc(N)cc2",true},
	};
	
	for (int i=0; i < answer.length;i++) {
		IAtomContainer a = FunctionalGroups.createAtomContainer(answer[i][0].toString());
		
			MolAnalyser.analyse(a);
			Boolean result = rule2test.verifyRule(a);
			Object mf = a.getProperty(MolFlags.MOLFLAGS);
			assertNotNull(mf);
			
			assertEquals(((Boolean)answer[i][1]),result);
	}
	
		
	}

}


