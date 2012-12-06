/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

Contact: jeliazkova.nina@gmail.com

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

package toxtree.plugins.ames.test.descriptors;

import org.openscience.cdk.qsar.IMolecularDescriptor;

import toxtree.plugins.ames.descriptors.DescriptorBridgedBiphenyl;

public class DescriptorBridgedBiphenylTest extends DescriptorsTest {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        addPropertiesToTest("I(BiBr)_Expected", "I(BiBr)");
        
    }
    /*
	public void testCalculate() {
		DescriptorBridgedBiphenyl d = new DescriptorBridgedBiphenyl();
		Object[][] test = new Object[][] {
				{"c1cc(ccc1Oc2ccc(cc2)",true},
				{"c1cc(ccc1Cc2ccc(cc2)(NCCC)",true},
				{"c1cc(ccc1c2ccc(cc2)",false},
				{"c1cc(ccc1OCc2ccc(cc2)",false},
		};
		try {
			for (int i=0; i < test.length;i++) {
				IAtomContainer ac = FunctionalGroups.createAtomContainer(test[i][0].toString(), true);
				HueckelAromaticityDetector.detectAromaticity(ac);
				
				BooleanResult r = (BooleanResult) ((DescriptorValue) d.calculate(ac)).getValue();
				assertEquals(
						new Boolean(test[i][1].toString()).booleanValue(),
						r.booleanValue());

				//System.out.println(test[i][0]+"\t"+r.booleanValue());
			}
		} catch (CDKException x) {
			fail(x.getMessage());
		}
	}
    */
    @Override
    protected IMolecularDescriptor createDescriptorToTest() throws Exception {
        return new DescriptorBridgedBiphenyl();
    }

    @Override
    public String getResultsFile() {
        return "aromatic_amines/qsar8train_BiBr.csv";      
    }

    @Override
    public String getSourceFile() {
        return "aromatic_amines/qsar8train.csv";

    }

    @Override
    public String getStructureID() {
        return "CAS Number";
    }
    
    /**
     * In substance ammine192,the amino group in the bridge is not considered as the functional one, 
     * as there is another amino group (not in the bridge) that is more suitable to be the functional one.
	 * So, in similar cases, if the amino group in the bridge is the only one in the molecule, 
	 * then it should be the functional one and the molecule will be treated as aniline like 
	 * (so that I(An)=1; I(Bibr)=0); 
	 * however, if another amino group is present on the aromatic ring, 
	 * this will be the functional one (so that I(An)=0 and I(Bibr)=1).
     * @throws Exception
     */
	public void testAmmine192() throws Exception {
		 
        Object[][] smiles = new Object[][] {
         {"C1=CC=C(C=C1)NC2=CC=C(C=C2)N","I(BiBr)",new Boolean(true)},
         };
        calculate(smiles);
	}
    
	public void testNBridge() throws Exception {
		 
        Object[][] smiles = new Object[][] {
         {"CC(C)OC1=CC=C(C=C1)NC2=CC=CC=C2","I(BiBr)",new Boolean(false)},
         };
        calculate(smiles);
	}
	public void testHeteroRing() throws Exception {
		 
        Object[][] smiles = new Object[][] {
         {"C=1C=CC(=C(C=1)NC2=NC(=NC(=N2)Cl)Cl)Cl","I(BiBr)",new Boolean(false)},
         {"CC1=CC=CC(=C1(C))N([H])C=2C=C(N=C(N=2)SCC(O)=O)Cl","I(BiBr)",new Boolean(false)},
         {"CC1=CC=CC(=C1(C))CC=2C=C(N=C(N=2)SCC(O)=O)Cl","I(BiBr)",new Boolean(false)},         
         };
        calculate(smiles);
	}    	
}
