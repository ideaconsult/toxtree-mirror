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

package mutant.test;

import java.util.List;

import junit.framework.TestCase;
import mutant.rules.RuleDAMutagenicityAromaticAmines;

import org.openscience.cdk.qsar.IMolecularDescriptor;

public class RuleDAMutagenicityAromaticAminesTest extends TestCase {
	public void testHasDescriptors()  throws Exception {
		RuleDAMutagenicityAromaticAmines rule = new RuleDAMutagenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertEquals(6,d.size());
	}
	public void testDescriptorEHOMOImplemented()  throws Exception {
		RuleDAMutagenicityAromaticAmines rule = new RuleDAMutagenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(0));
	}
	public void testDescriptorELUMOImplemented() throws Exception  {
		RuleDAMutagenicityAromaticAmines rule = new RuleDAMutagenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(1));
	}	
	public void testDescriptorMR2Implemented() throws Exception  {
		RuleDAMutagenicityAromaticAmines rule = new RuleDAMutagenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(2));
	}
	public void testDescriptorMR3Implemented()  throws Exception {
		RuleDAMutagenicityAromaticAmines rule = new RuleDAMutagenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(3));
	}	
	public void testDescriptorMR6Implemented()  throws Exception {
		RuleDAMutagenicityAromaticAmines rule = new RuleDAMutagenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(4));
	}	
	public void testDescriptorIdistImplemented()  throws Exception {
		RuleDAMutagenicityAromaticAmines rule = new RuleDAMutagenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(5));
	}
	public void testVerifyRule() {
		fail("Not implemented");
	}

}


