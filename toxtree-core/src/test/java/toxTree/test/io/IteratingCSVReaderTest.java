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

import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.Molecule;

import ambit2.core.io.IteratingDelimitedFileReader;

/**
 * A test for {@link toxTree.io.IteratingDelimitedFileReader} class
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-6
 */
public class IteratingCSVReaderTest{
	@Test
    public void testCSVFile() throws Exception {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("data/Misc/Debnath_smiles.csv");
        
            IteratingDelimitedFileReader reader = new IteratingDelimitedFileReader(in);
            
            int molCount = 0;
            while (reader.hasNext()) {
                Object object = reader.next();
                Assert.assertNotNull(object);
                Assert.assertTrue(object instanceof Molecule);
                molCount++;
            }
            
            Assert.assertEquals(88, molCount);

    }

}
