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
package cramer2.test;

import org.openscience.cdk.interfaces.IMolecule;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.MolAnalyseException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import cramer2.rules.RuleSimplyBranchedAliphaticHydrocarbon;

/**
 * A test for Cramer rule No 5 {@link RuleSimplyBranchedAliphaticHydrocarbon}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-24
 */
public class RuleSimplyBranchedAliphaticHydrocarbonTest extends AbstractRuleTest {
	@Override
	protected IDecisionRule createRule() {
		return  new RuleSimplyBranchedAliphaticHydrocarbon();
	}
	public void test() throws Exception {
	    Object[][] answer = {
	            {"CCCCCCCCCCC",new Boolean(true)},  
	            {"OC1COC(O)C(O)C1(O)",new Boolean(true)},
	            {"O=C1OC(=CC(=O)C1C(=O)C)C",new Boolean(false)},
	            {"CC(=O)C1=C(O)C=C(C)OC1=O",new Boolean(false)},
				
	            
	            };
	    
	    IMolecule m = (IMolecule) FunctionalGroups.createAtomContainer((String)answer[2][0]);
	    try {
	    MolAnalyser.analyse(m);
	    } catch (MolAnalyseException  x) {
	    	fail();
	    }
	    assertTrue(FunctionalGroups.hasGroup(
	    		m,
				FunctionalGroups.lactone(false)));
	    ruleTest(answer);
	}

}
