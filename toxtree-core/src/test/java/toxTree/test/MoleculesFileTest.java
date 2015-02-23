/*
Copyright (C) 2005-2006  

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

package toxTree.test;

import java.io.File;
import java.io.FileReader;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.data.MoleculesFile;
import toxTree.io.Tools;
import ambit2.core.helper.CDKHueckelAromaticityDetector;
import ambit2.core.io.MyIteratingMDLReader;

public class MoleculesFileTest  {
	@Test
	public void test() throws Exception  {

			IChemObjectBuilder b = SilentChemObjectBuilder.getInstance();
			File file = Tools.getFileFromResource("substituents.sdf");
			MoleculesFile mf = new MoleculesFile(file,b);
			File file1 = Tools.getFileFromResource("substituents.sdf");
			MyIteratingMDLReader reader = new MyIteratingMDLReader(new FileReader(file1),b);
			int record=0;
            int found = 0;
			while (reader.hasNext()) {
				Object o = reader.next();
				if (o instanceof IAtomContainer) {
                    AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms((IAtomContainer)o);
					boolean aromatic = CDKHueckelAromaticityDetector.detectAromaticity((IAtomContainer)o);
					long now = System.currentTimeMillis();
					System.out.print(record);
                    System.out.print('\t');                    
                    System.out.print(((IAtomContainer)o).getProperty("#"));                    
					System.out.print('\t');
					System.out.print(((IAtomContainer)o).getProperty("SMILES"));
					System.out.print("\tAromatic ");
					System.out.print(aromatic);
					System.out.print('\t');
					int index = mf.indexOf("SMILES",((IAtomContainer)o).getProperty("SMILES"));
					if (index >-1) {
						System.out.print("found ");
						System.out.print(System.currentTimeMillis()-now);
						System.out.print(" ms\tMR\t");
						System.out.print(mf.getAtomContainer(index).getProperty("MR"));
                        System.out.print(" ms\tB5STM\t");
                        System.out.println(mf.getAtomContainer(index).getProperty("B5STM"));                        
                        found++;
					} else System.out.println("not found"); 
					record++;

				}
				
			}
			Assert.assertEquals(record,found);

	}
}


