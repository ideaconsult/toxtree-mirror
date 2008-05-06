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
package toxTree.test.io.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;
import toxTree.io.batch.BatchProcessingException;
import toxTree.io.batch.FileState;

/**
 * TODO add description
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-4
 */
public class FileStateTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(FileStateTest.class);
	}
	public void testMatch() {
		File f = new File("toxTree/data/Misc/test.cml");
		assertTrue(f.exists());
		
		
		FileState fs = new FileState(f);
		try {
			assertTrue(fs.match());
			File f1 = new File("toxTree/data/Misc/test.cml");
			assertTrue(fs.match(f1));
		} catch (BatchProcessingException x) {
			x.printStackTrace();
			fail();
		}
	}
	public void testRoundTrip() {
		FileState fs = new FileState();
		fs.setFilename("test.cml");
		fs.setLastModified(999);
		fs.setLength(777);
		fs.setHashCode(888);
		fs.setCurrentRecord(10);
		
		FileState newFS = new FileState();
		assertNotSame(fs,newFS);
		
		try {
			//writing
			File fwrite = File.createTempFile("FileStateTest","test");
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fwrite));
			os.writeObject(fs);
			os.close();
			
			String filename = fwrite.getAbsolutePath();
			System.out.println(filename);
			
			//reading
			File fread = new File(filename);
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fread));
			newFS =(FileState) is.readObject();
			is.close();
			fread.delete();
			assertEquals(fs,newFS);
			
			//f.delete();
		} catch (IOException x) {
			x.printStackTrace();
			fail();
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
			fail();			
		}
		
		
		
	}

}
