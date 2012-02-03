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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.io.batch.FileState;

/**
 * TODO add description
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-4
 */
public class FileStateTest {

	
	@Test
	public void testMatch() throws Exception {
		URL url = this.getClass().getClassLoader().getResource("data/Misc/test.cml");
		File f = new File(url.getFile());
		Assert.assertTrue(f.exists());
		
		
		FileState fs = new FileState(f);
		Assert.assertTrue(fs.match());
		File f1 = new File(url.getFile());
		Assert.assertTrue(fs.match(f1));

	}
	@Test
	public void testRoundTrip() throws Exception {
		FileState fs = new FileState();
		fs.setFilename("test.cml");
		fs.setLastModified(999);
		fs.setLength(777);
		fs.setHashCode(888);
		fs.setCurrentRecord(10);
		
		FileState newFS = new FileState();
		Assert.assertNotSame(fs,newFS);
		
			//writing
			File fwrite = File.createTempFile("FileStateTest","test");
			fwrite.deleteOnExit();
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fwrite));
			os.writeObject(fs);
			os.close();
			
			String filename = fwrite.getAbsolutePath();
			
			//reading
			File fread = new File(filename);
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fread));
			newFS =(FileState) is.readObject();
			is.close();
			fread.delete();
			Assert.assertEquals(fs,newFS);

		
		
		
	}

}
