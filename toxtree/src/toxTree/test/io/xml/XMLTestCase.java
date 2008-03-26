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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.w3c.dom.Document;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public abstract class XMLTestCase extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(XMLTestCase.class);
	}
	public static String print(Document doc) throws Exception {
	   		 StringWriter outWriter = new StringWriter();
	  		 OutputFormat format = new OutputFormat(doc);
	   		 format.setLineWidth(65);
	   		 format.setIndenting(true);
	   		 format.setIndent(2);
	
	   		 XMLSerializer output = new XMLSerializer(outWriter, format);
	   		 output.serialize(doc);
	         return outWriter.toString();
	}
	public static void save(Document doc, File file) throws Exception {
        Source source = new DOMSource(doc);

        // Write the DOM document to the file
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(source, new StreamResult(file));
	}
	   public static void write(Object f, String filename) throws Exception{
	        XMLEncoder encoder =
	           new XMLEncoder(
	              new BufferedOutputStream(
	                new FileOutputStream(filename)));
	        encoder.writeObject(f);
	        encoder.close();
	    }

	    public static Object read(String filename) throws Exception {
	        XMLDecoder decoder =
	            new XMLDecoder(new BufferedInputStream(
	                new FileInputStream(filename)));
	        Object o = (Object)decoder.readObject();
	        decoder.close();
	        return o;
	    }
	
}


