/*
Copyright (C) 2005-2007  

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

package toxTree.test.smarts;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import toxTree.tree.rules.smarts.ISmartsPattern;
import toxTree.tree.rules.smarts.SmartsPatternFactory;
import toxTree.tree.rules.smarts.SmartsPatternFactory.SmartsParser;
import junit.framework.TestCase;

public class SmartsPatternsTest extends TestCase {
	  public void testXMLSerializingSmartsPatternsJoelib() throws Exception  {
	    	XMLSerializingSmartsPatterns(SmartsParser.smarts_joelib,"[OX2H][OX2]");
	    	
	    }
	    public void testXMLSerializingSmartsPatternsCDK() throws Exception  {
	    	XMLSerializingSmartsPatterns(SmartsParser.smarts_cdk,"[OX2H][OX2]");
	    }
	    public void XMLSerializingSmartsPatterns(SmartsParser parser,String smarts) throws Exception {
	    		ISmartsPattern rule = SmartsPatternFactory.createSmartsPattern(parser,smarts,false);
	    		String filename = "smarts.xml";
	    		File file = new File(filename);
	    		if (file.exists()) file.delete();
				FileOutputStream os = new FileOutputStream(filename);
				XMLEncoder encoder = new XMLEncoder(os);
				encoder.writeObject(rule);
				encoder.close();	
				
				FileInputStream in = new FileInputStream(filename);
				XMLDecoder decoder = new XMLDecoder(in);
				ISmartsPattern rule1 = (ISmartsPattern)decoder.readObject();
				assertEquals(smarts,rule1.getSmarts());
				decoder.close();
	    }
}


