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

import junit.framework.TestCase;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SmartsPatternFactory;
import ambit2.smarts.query.SmartsPatternFactory.SmartsParser;

public class SmartsPatternsTest extends TestCase {
	  public void testXMLSerializingSmartsPatternsJoelib() throws Exception  {
	    	XMLSerializingSmartsPatterns(SmartsParser.smarts_nk,"[OX2H][OX2]");
	    	
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
	    public void testComplexSmartsJoeLib() throws Exception {
	        complexSmarts(SmartsParser.smarts_nk);
	    }
        public void testComplexSmartsAmbit() throws Exception {
            complexSmarts(SmartsParser.smarts_nk);
        }
        public void testComplexSmartsCDK() throws Exception {
            complexSmarts(SmartsParser.smarts_cdk);
        }        
	    
        protected void complexSmarts(SmartsParser parser) throws Exception {
            final String[] smarts = {
                 "[CH,CH2,CH3;!$([CH2]CC=[O,S])][F,Cl,Br,I,$(OS(=O)(=O)[#6,#1]),$(OS(=O)(=O)O[#6,#1])]", 
                 "[$(C=C),$(C#C),a][CH2,CH][O,S][$([CH]=O),$([CH]=S),$(C(C)=O),$(C(C)=S),a]", 
                 "[#6][O,SX2,N][O,SX2,N][$([CH]=O),$([CH]=S),$(C(C)=O),$(C(C)=S),$([a])]" 

            };
            for (int i=0; i < smarts.length; i++) {
                ISmartsPattern rule = SmartsPatternFactory.createSmartsPattern(parser,smarts[i],false);
                assertNotNull(rule);
                assertEquals(smarts[i],rule.getSmarts());
            }    

        }
	    
	    
}


