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

import org.openscience.cdk.qsar.IMolecularDescriptor;

import toxtree.plugins.ames.descriptors.AromaticAmineSubstituentsDescriptor;

public class MRTest extends DescriptorsTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addPropertiesToTest("mr2", "MR2");
		addPropertiesToTest("mr3", "MR3");
		addPropertiesToTest("mr6", "MR6");
	}
	@Override
	protected IMolecularDescriptor createDescriptorToTest() throws Exception {
		return new AromaticAmineSubstituentsDescriptor();
	}

	@Override
	public String getResultsFile() {
		return "aromatic_amines/ammTA100_results.csv";		
	}

	@Override
	public String getSourceFile() {
		return "aromatic_amines/ammTA100.csv";

	}

	@Override
	public String getStructureID() {
		return "StructureID";
	}
	
}


