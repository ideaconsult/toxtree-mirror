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
package toxTree.test.io.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import toxTree.tree.DecisionMethodsList;
import toxTree.tree.demo.SMARTSTree;

public class DecisionMethodsListTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(DecisionMethodsListTest.class);
	}
	public void testToXml() {
		DecisionMethodsList methods = new DecisionMethodsList();
		try {
				//methods.loadAllFromPlugins();
			methods.addDecisionMethod(new SMARTSTree());
				//TODO for test only, make it as plugin
				//methods.add(new SicretRules());
	            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	            Document doc = builder.newDocument();
	            Element e = methods.toXML(doc);
			    doc.appendChild(e);
	            // Prepare the DOM document for writing
	            Source source = new DOMSource(doc);
	    
	            // Prepare the output file
	            File file = new File("methods.xml");
	            Result result = new StreamResult(file);
	    
	            // Write the DOM document to the file
	            Transformer xformer = TransformerFactory.newInstance().newTransformer();
	            xformer.transform(source, result);			            
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
}


