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
package toxTree.test;

import org.junit.Test;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;

/**
 * TODO add description
 * @author ThinClient
 * <b>Modified</b> 2005-9-22
 */
public class IonicSMILEStest {
	@Test
	public void testCHA()  throws Exception {
		ionicSmiles("[Cl-].[NH3+]C1CCCCC1");
	}
	@Test
	public void test()  throws Exception  {
		ionicSmiles("[Na+].[Na+].[Na+].OC1=C(N=NC2=CC=C(C=C2)S([O-])(=O)=O)C(=NN1C3=CC=C(C=C3)S([O-])(=O)=O)C([O-])=O");
	}
	@Test
	public void ionicSmiles(String smiles) throws Exception {
		
		SmilesParser p = new SmilesParser(SilentChemObjectBuilder.getInstance());

			IAtomContainer m = p.parseSmiles(smiles);
			IAtomContainerSet c = ConnectivityChecker.partitionIntoMolecules(m);
			System.out.println(c.getAtomContainerCount());
			SmilesGenerator g = SmilesGenerator.generic();
			for (int i=0; i < c.getAtomContainerCount(); i++) {
				IAtomContainer m1 = c.getAtomContainer(i); 
				//System.out.println(g.createSMILESWithoutCheckForMultipleMolecules(m1,false,new boolean[m1.getBondCount()]));
				System.out.println(g.create(m1,new int[m1.getBondCount()]));
			}
	
		
	}
}
