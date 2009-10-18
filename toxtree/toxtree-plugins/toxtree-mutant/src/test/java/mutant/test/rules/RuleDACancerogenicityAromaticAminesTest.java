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

import java.util.List;

import mutant.rules.RuleDACancerogenicityAromaticAmines;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.IMolecularDescriptor;

import toxTree.qsar.LinearDiscriminantRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;

public class RuleDACancerogenicityAromaticAminesTest extends LDARuleTest {

	public LinearDiscriminantRule createRuleToTest() throws Exception {
		return new RuleDACancerogenicityAromaticAmines();
	}
	
	public void testHasDescriptors() throws Exception {
		hasDescriptors(10);
	}
	public void testDescriptorLRImplemented() {

		List<IMolecularDescriptor> d = ruleToTest.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(0));
	}
	public void testDescriptorB5RImplemented() {
		List<IMolecularDescriptor> d = ruleToTest.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(1));
	}	
	public void testDescriptorEHOMOImplemented() {
		List<IMolecularDescriptor> d = ruleToTest.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(2));
	}
	public void testDescriptorELUMOImplemented() {
		List<IMolecularDescriptor> d = ruleToTest.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(3));
	}	
	public void testDescriptorMR3Implemented() {
		List<IMolecularDescriptor> d = ruleToTest.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(4));
	}		
	public void testDescriptorMR5Implemented() {
		List<IMolecularDescriptor> d = ruleToTest.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(5));
	}

	public void testDescriptorMR6Implemented() {
		List<IMolecularDescriptor> d = ruleToTest.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(6));
	}	
	public void testDescriptorIAnImplemented() {
		List<IMolecularDescriptor> d = ruleToTest.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(7));
	}
	public void testDescriptorINO2Implemented() {
		List<IMolecularDescriptor> d = ruleToTest.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(8));
	}	
	public void testDescriptorIBiBrImplemented() {
		List<IMolecularDescriptor> d = ruleToTest.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(9));
	}	
	
	public void test() {
		try {
			IAtomContainer c = FunctionalGroups.createAtomContainer("CC=1C=CC=CC=1(N)",true);
			MolAnalyser.analyse(c);
			ruleToTest.verifyRule(c);
			System.out.println(c.getProperties());
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
}


