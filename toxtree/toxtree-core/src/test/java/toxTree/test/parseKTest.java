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
package toxTree.test;

import java.io.FileOutputStream;

import junit.framework.TestCase;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

/**
 * TODO add description
 * @author ThinClient
 * <b>Modified</b> 2005-9-24
 */
public class parseKTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(parseKTest.class);
	}
	public void testParseK() {
		SmilesParser p = new SmilesParser(SilentChemObjectBuilder.getInstance());
		try {
			IMolecule mol = p.parseSmiles("C=CCC(=NOS(=O)(=O)[O-])SC1OC(CO)C(O)C(O)C1(O).[Na+]");
			assertNotNull(mol);
			mol = p.parseSmiles("C=CCC(=NOS(=O)(=O)[O-])SC1OC(CO)C(O)C(O)C1(O).[K]");
			assertNotNull(mol);
			mol = p.parseSmiles("C=CCC(=NOS(=O)(=O)[O-])SC1OC(CO)C(O)C(O)C1(O).[K+]");
			assertNotNull(mol);			
		} catch (CDKException x) {
			x.printStackTrace();
			fail();
			
		}
	}
    public void testWrite() { 
    	try {
	    	FileOutputStream f = new FileOutputStream("data/mynewfile.txt");
	    	f.write('N');
	    	f.close();
    	} catch (Exception x) {
    		
    	}
    }	
}
