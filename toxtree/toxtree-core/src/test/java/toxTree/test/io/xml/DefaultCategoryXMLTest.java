/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/


package toxTree.test.io.xml;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import toxTree.tree.CategoriesList;
import toxTree.tree.DefaultCategory;

public class DefaultCategoryXMLTest extends XMLTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(DefaultCategoryXMLTest.class);
	}

	/*
	 * Test method for 'toxTree.tree.DefaultCategory.fromXML(Element)'
	 */
	public void testFromXML() {

		
	}


	public void testEncoder() {
		try {
			
			FileOutputStream os = new FileOutputStream("category.xml");
			XMLEncoder encoder = new XMLEncoder(os);
			DefaultCategory c = new DefaultCategory("class1",1);
			c.setExplanation(null);
			c.setThreshold("0.99");
			encoder.writeObject(c);
			encoder.close();
			
			FileInputStream is = new FileInputStream("category.xml");
			XMLDecoder decoder = new XMLDecoder(is);
			DefaultCategory p = (DefaultCategory)decoder.readObject();
			
			decoder.close(); 

			assertEquals(c,p);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	public void testEncoder1() {
		try {
			CategoriesList list = new CategoriesList();
			DefaultCategory c = new DefaultCategory("class1",1);
			c.setExplanation(null);
			c.setThreshold("0.99");
			list.addCategory(c);
			
			DefaultCategory c1 = new DefaultCategory("class2",2);
			c1.setExplanation("Second class");
			c1.setThreshold("0.90");
			list.addCategory(c1);

			FileOutputStream os = new FileOutputStream("categories.xml");
			XMLEncoder encoder = new XMLEncoder(os);
			encoder.writeObject(list);
			encoder.close();
			
			FileInputStream is = new FileInputStream("categories.xml");
			XMLDecoder decoder = new XMLDecoder(is);
			Object list1 = decoder.readObject();
			decoder.close();
			assertTrue(list1 instanceof CategoriesList);
			assertEquals(2,((CategoriesList)list1).size());
			assertEquals(c,((CategoriesList)list1).get(0));
			assertEquals(c1,((CategoriesList)list1).get(1));
			
			 

			
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	

	public void testRoundtripXML() {
		DefaultCategory c = new DefaultCategory("class1",1);
		c.setExplanation(null);
		c.setThreshold("0.99");
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	        Document doc = builder.newDocument();
	        Element e = c.toXML(doc);
		    doc.appendChild(e);
		    
		    //System.out.println(print(doc));
		    //save(doc,new File("category.xml"));
		    
		    DefaultCategory c1 = new DefaultCategory();
		    assertNotSame(c,c1);
		    c1.fromXML(e);
		    assertEquals(c,c1);
		} catch (Exception x) {
			x.printStackTrace();
			fail();
		}
        
	}


}


