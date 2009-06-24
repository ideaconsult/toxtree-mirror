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

import junit.framework.TestCase;

import org.openscience.cdk.Molecule;

import ambit2.core.io.IteratingDelimitedFileReader;

/**
 * A test for {@link toxTree.io.IteratingDelimitedFileReader} class
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-6
 */
public class IteratingCSVReaderTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(IteratingCSVReaderTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for IteratingCSVReaderTest.
	 * @param arg0
	 */
	public IteratingCSVReaderTest(String arg0) {
		super(arg0);
	}
    public void testCSVFile() {
        String filename = "toxTree/data/Misc/Debnath_smiles.csv";
        System.out.println("Testing: " + filename);
        //InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
        try {
        FileInputStream ins = new FileInputStream(filename);
        
            IteratingDelimitedFileReader reader = new IteratingDelimitedFileReader(ins);
            
            int molCount = 0;
            while (reader.hasNext()) {
                Object object = reader.next();
                assertNotNull(object);
                assertTrue(object instanceof Molecule);
                molCount++;
            }
            
            assertEquals(88, molCount);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
