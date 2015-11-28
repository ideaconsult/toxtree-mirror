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

package toxtree.plugins.ames.test;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.result.IDescriptorResult;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.core.ToxTreeDescriptor;
import ambit2.core.data.ArrayResult;

public class ToxTreeDescriptorTest {
	@Test
	public void test() throws Exception {
		ToxTreeDescriptor d = new ToxTreeDescriptor();
		d.setParameters(new Object[] {toxtree.plugins.ames.AmesMutagenicityRules.class.getName()});
		IAtomContainer mol = MoleculeFactory.makeAlkane(10);
		DescriptorValue value = d.calculate(mol);
		IDescriptorResult result = value.getValue();
		Assert.assertTrue(result instanceof ArrayResult);
		Assert.assertEquals("YES",((ArrayResult)result).get(2));
		Assert.assertEquals("No alerts for carcinogenic activity", value.getNames()[2]);

	}
}
