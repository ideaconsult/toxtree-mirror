/*
Copyright (C) 2005-2008  

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

package toxTree.test.qsar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.openscience.cdk.qsar.descriptors.molecular.BCUTDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;

import toxTree.qsar.QSARDescriptor;

public class QSARDescriptortest extends TestCase {
	public void test() throws Exception {
		QSARDescriptor d = new QSARDescriptor("LogP",new XLogPDescriptor());
		assertEquals("LogP", d.getDescriptorName());
		assertEquals("org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor", d.getDescriptor().getClass().getName());
	}
	public void testSerialize() throws Exception {
		QSARDescriptor d = new QSARDescriptor("LogP",new XLogPDescriptor()); 
			//writing
		File f = File.createTempFile("QSARDescriptor","test");
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
		os.writeObject(d);
		os.close();
			
			//reading
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(f));
		QSARDescriptor d2 =(QSARDescriptor) is.readObject();
		is.close();
		f.delete();
		assertEquals(d.getDescriptorName(),d2.getDescriptorName());
		assertNotNull(d2.getDescriptor());
		assertEquals(d.getDescriptor().getClass().getName(),d2.getDescriptor().getClass().getName());
	}	
	public void testList() throws Exception {
		QSARDescriptor d = new QSARDescriptor("LogP",new XLogPDescriptor());
		QSARDescriptor d1 = new QSARDescriptor("BCUT",new BCUTDescriptor());
		ArrayList<QSARDescriptor> list = new ArrayList<QSARDescriptor>();
		list.add(d);
		list.add(d1);
		System.out.println(list.indexOf("BCUT"));
	}
}



