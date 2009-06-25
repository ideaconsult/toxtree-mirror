/*
Copyright (C) 2005-2008  

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

package eye;

import junit.framework.TestCase;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IMolecule;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolAnalyser;

public abstract class TestExamples extends TestCase {
	protected IDecisionRule ruleToTest;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ruleToTest = createRuleToTest();
	}
	protected abstract IDecisionRule createRuleToTest();

	protected void verifyMol(IMolecule m,boolean answer) throws DecisionMethodException {

		try {

			MolAnalyser.analyse(m);
			for (int i=0; i < m.getAtomCount();i++)
				System.out.println(m.getAtom(i).getFlag(CDKConstants.ISAROMATIC));
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
		assertEquals(answer,ruleToTest.verifyRule(m));
	}	
	protected void verifyExample(boolean answer) throws DecisionMethodException {
		IMolecule m = ruleToTest.getExampleMolecule(answer);
		verifyMol(m, answer);
	}
	
	public void testExampleNo() throws DecisionMethodException {
		verifyExample(false);
	}
	public void testExampleYes()  throws DecisionMethodException {
		verifyExample(true);
	}
}


