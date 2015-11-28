/*
Copyright Ideaconsult Ltd. (C) 2005-2007  

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

package mutant.test.rules;

import mutant.rules.SA17_nogen;
import mutant.test.TestMutantRules;

import org.junit.Test;

import toxTree.core.IDecisionRule;

public class SA17_nogenTest extends TestMutantRules {
	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA17_nogen();
	}
	@Override
	public String getHitsFile() {
		return "NA17/NA17_newhits.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA17";
	}
	@Test
	public void test_Overlap16() throws Exception {
		Object[][] smiles = {
				{"NC(=S)OC",new Boolean(false)},
				{"NC(=S)OCC(N)=S",new Boolean(true)}
		};
		for (int i=0; i < smiles.length;i++) {
			//System.out.print(smiles[i]);
			assertEquals(smiles[i][1],verify(smiles[i][0].toString()));
			//System.out.println(" ok");
		}
		
	}	
}


