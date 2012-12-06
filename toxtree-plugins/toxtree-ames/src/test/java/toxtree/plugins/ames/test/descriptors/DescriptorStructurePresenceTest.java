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

package toxtree.plugins.ames.test.descriptors;

import junit.framework.TestCase;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.result.BooleanResult;
import org.openscience.cdk.qsar.result.IDescriptorResult;

import toxTree.query.FunctionalGroups;
import toxtree.plugins.ames.descriptors.DescriptorStructurePresence;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SmartsPatternCDK;

public class DescriptorStructurePresenceTest extends TestCase {
	protected DescriptorStructurePresence descriptor;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		descriptor = new DescriptorStructurePresence<IAtomContainer>() {
      @Override
            protected ISmartsPattern<IAtomContainer> createSmartsPattern() {
                return new SmartsPatternCDK();
            }      
        };
	}

	public void testSetFragment() throws Exception {
        String s = "C";
		descriptor.setSMARTS(s);
		Object[] params = descriptor.getParameters();
		assertEquals(s,params[0]);
	}

	public void testCalculate() throws Exception {
		descriptor.setSMARTS("[CH3]");
		
		IAtomContainer a = FunctionalGroups.createAtomContainer("CCCCCCO");
			DescriptorValue v = descriptor.calculate(a);
			IDescriptorResult result = v.getValue();
			assertTrue(result instanceof BooleanResult);
			assertEquals(true,((BooleanResult)result).booleanValue());
			assertNull(v.getException());

	}

	public void testGetDescriptorResultType() {
		assertEquals(BooleanResult.class, descriptor.getDescriptorResultType().getClass());
	}

	public void testGetParameterType() {
		String[] s = descriptor.getParameterNames();
		assertEquals(2,s.length);
		assertNotNull(s[0]);
		assertNotNull(s[1]);
		assertEquals(QueryAtomContainer.class,descriptor.getParameterType(s[0]).getClass());
		assertEquals(String.class,descriptor.getParameterType(s[1]).getClass());		
	}

	public void testGetParameters() {
		try {
			descriptor.setParameters(new Object[] {"[CH3]","methyl"});
			Object[] params = descriptor.getParameters();
			assertNotNull(params);
			assertEquals(2,params.length);
			assertNotNull(params[0]);
			assertNotNull(params[1]);
		} catch (Exception x) {
			fail(x.getMessage());
		}
	}

	public void testGetSpecification() {
		DescriptorSpecification s = descriptor.getSpecification();
		assertEquals("ToxTree Ames mutagenicity  plugin", s.getSpecificationReference());
		assertEquals(descriptor.getClass().getName(), s.getImplementationTitle());
	}

	public void testSetParamNames() {
		descriptor.setParamNames(new String[]{"1","2"});
		String[] p = descriptor.getParamNames();
		assertEquals("1",p[0]);
		assertEquals("2",p[1]);
	}


	public void testGetResultName() {
		descriptor.setResultName("name");
		assertEquals("name",descriptor.getResultName());
	}

}


