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

package eye;

import java.io.StringReader;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.MDLV2000Reader;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import eye.rules.Rule22;

public class Rule22Test extends TestExamples {
	@Override
	protected IDecisionRule createRuleToTest() {
		return new Rule22();
	}
	public void test() throws Exception {
		IMolecule a = (IMolecule)FunctionalGroups.createAtomContainer("c1ccccc1[N+]");
		SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		//IMolecule a = p.parseSmiles("c1ccccc1[N+]");
		for (int f = 0; f < a.getElectronContainerCount(); f++)
		{
			IBond bond = a.getBond(f);
			System.out.println(bond);
		}	
		//verifyMol(a, false);
		
	}
	
	public void testRadical() throws Exception {
		String sdf = 
			"obabel.smi\n"+
			" OpenBabel04230816092D\n"+
			"\n"+
			"  7  7  0  0  0  0  0  0  0  0999 V2000\n"+
			"    0.0000    0.0000    0.0000 C   0  0  0  0  0\n"+
			"    0.0000    0.0000    0.0000 C   0  0  0  0  0\n"+
			"    0.0000    0.0000    0.0000 C   0  0  0  0  0\n"+
			"    0.0000    0.0000    0.0000 C   0  0  0  0  0\n"+
			"    0.0000    0.0000    0.0000 C   0  0  0  0  0\n"+
			"    0.0000    0.0000    0.0000 C   0  0  0  0  0\n"+
			"    0.0000    0.0000    0.0000 N   0  3  0  0  0\n"+
			"  1  6  2  0  0  0\n"+
			"  1  2  1  0  0  0\n"+
			"  2  3  2  0  0  0\n"+
			"  3  4  1  0  0  0\n"+
			"  4  5  2  0  0  0\n"+
			"  5  6  1  0  0  0\n"+
			"  6  7  1  0  0  0\n"+
			"M  RAD  1   7   3\n"+
			"M  END\n"+
			"$$$$\n";
		MDLV2000Reader reader = new MDLV2000Reader(new StringReader(sdf));
		IMolecule obabelMol = DefaultChemObjectBuilder.getInstance().newMolecule();
		reader.read(obabelMol);
		reader.close();		
		
		SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		IMolecule cdkMol = p.parseSmiles("c1ccccc1[N+]");
		
		assertEquals(cdkMol.getAtomCount(), obabelMol.getAtomCount());
		assertEquals(cdkMol.getBondCount(), obabelMol.getBondCount());
		assertEquals(cdkMol.getElectronContainerCount(), obabelMol.getElectronContainerCount());
	}
}


