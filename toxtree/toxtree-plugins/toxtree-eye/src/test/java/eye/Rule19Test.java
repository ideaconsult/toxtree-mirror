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

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.core.IDecisionRule;
import eye.rules.Rule19;

public class Rule19Test extends TestExamples {
	@Override
	protected IDecisionRule createRuleToTest() {
		return new Rule19();
	}
    public void haloniumIons(String smiles,boolean answer) throws Exception {
        //IMolecule a = (IMolecule)FunctionalGroups.createAtomContainer("c1(ccc(cc1)CC=C)[Cl+]c2ccc(cc2)Cl");
        SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IMolecule a = p.parseSmiles(smiles);
        verifyMol(a, answer);
    }    
    
    public void testIodoniumIons() throws Exception {
        haloniumIons("C[I+]C",false);
    }
    
    public void testChloroniumIons() throws Exception {
        haloniumIons("C[Cl+]C",false);
    }
    
    public void testFluoroniumIons() throws Exception {
        haloniumIons("C[F+]C",false);
    }
    public void testBromoniumIons() throws Exception {
        haloniumIons("C[Br+]C",false);
    }         
    
}


