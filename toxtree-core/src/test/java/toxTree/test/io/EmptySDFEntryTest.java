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
package toxTree.test.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.openscience.cdk.ChemFile;
import org.openscience.cdk.ChemObject;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.MDLV2000Reader;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;

import toxTree.logging.TTLogger;

public class EmptySDFEntryTest extends TestCase {
	public void testEmptyEntry() {
		try {
			MDLV2000Reader reader = new MDLV2000Reader(new FileInputStream("toxTree/data/Misc/emptyStructure.sdf"));
        	ChemFile content = (ChemFile)reader.read((ChemObject)new ChemFile());
        	List c = ChemFileManipulator.getAllAtomContainers(content);
        	assertNotNull(c);
        	assertEquals(2,c.size());
        	IAtomContainer a = (IAtomContainer)c.get(1);
        	assertEquals(0,a.getAtomCount());
        	assertEquals(0,a.getBondCount());
        	
		} catch (IOException x) {
			x.printStackTrace();
			fail();
		} catch (CDKException x) {
			x.printStackTrace();
			fail();
		}
	}
	/*
	public void testEmptyEntry30() {
		try {
			MDLReader reader = new MDLReader(new FileInputStream("toxTree/data/Misc/CPDBAS_v2a_1451_1Mar05_fixed.sdf"));
        	ChemFile content = (ChemFile)reader.read((ChemObject)new ChemFile());
        	List c = ChemFileManipulator.getAllAtomContainers(content);
        	assertNotNull(c);
        	assertTrue(c.size()>29);
        	IAtomContainer a = (IAtomContainer) c.get(29);
        	assertEquals(0,a.getAtomCount());
        	assertEquals(0,a.getBondCount());
        	
		} catch (IOException x) {
			x.printStackTrace();
			fail();
		} catch (CDKException x) {
			x.printStackTrace();
			fail();
		}
	}
	*/
	public void testEmptyEntryIteratingReader() {
		TTLogger.configureLog4j(true);
		try {
			IteratingMDLReader reader = new IteratingMDLReader(new FileInputStream("toxTree/data/Misc/emptyStructure.sdf"),
			        DefaultChemObjectBuilder.getInstance());
            int molCount = 0;
            while (reader.hasNext()) {
                Object object = reader.next();
                assertNotNull(object);
                assertTrue(object instanceof Molecule);
                molCount++;
            }
            
            assertEquals(2,molCount);
		} catch (Exception x) {
			x.printStackTrace();
			fail();
		}
	}
	
}
