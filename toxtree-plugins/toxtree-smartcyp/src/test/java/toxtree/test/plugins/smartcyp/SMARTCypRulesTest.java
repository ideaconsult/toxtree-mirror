/*
Copyright Ideaconsult Ltd.(C) 2006  
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
package toxtree.test.plugins.smartcyp;



import org.junit.Test;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.exceptions.MolAnalyseException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxtree.plugins.smartcyp.SMARTCYPPlugin;

/**
 * TODO Add SMARTCypRulesTest description
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-4-30
 */
public class SMARTCypRulesTest extends RulesTestCase {
    protected SmilesParser gen = null;

	/**
	 * 
	 */
	public SMARTCypRulesTest() throws Exception  {
		super();
		rules = new SMARTCYPPlugin();
		((SMARTCYPPlugin)rules).setResiduesIDVisible(false);
        gen = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        
	}

	
	/*
	 * Class under test for int classify(IMolecule)
	 */
	@Test
	public void testClassifyMolecule() {
		IAtomContainer mol = FunctionalGroups.createAtomContainer("Nc1ccc3c(c1)Cc2cc(ccc23)Br",true);
        /*
		mol.setProperty(RuleMeltingPoint.MeltingPoint,"100");
		mol.setProperty(RuleLogP.LogKow,"100");
		mol.setProperty(RuleLipidSolubility.LipidSolubility,"100");	
		mol.setProperty("SurfaceTension","100");
		mol.setProperty("VapourPressure","100");
		mol.setProperty("AqueousSolubility","100");
        */
		//assign properties
        rules.setParameters(mol);
		classify(mol,rules,rules.getNumberOfClasses());

	}

    protected IMolecule getMolecule(String smiles) {
        try {
        	IMolecule mol = gen.parseSmiles(smiles);
            MolAnalyser.analyse(mol);
            return mol;
	    } catch (InvalidSmilesException x ) {
	        x.printStackTrace();
	        return null;
	    } catch (MolAnalyseException x) {
	        x.printStackTrace();
	        return null;
	    }    
    }
    @Test
	public void testSMARTCypSerialization() throws Exception {
		SMARTCYPPlugin rulesNew = (SMARTCYPPlugin)objectRoundTrip(rules,"SMARTCYPPlugin");
		rulesNew.setResiduesIDVisible(false);
		rules = rulesNew;
		tryImplementedRules();
	}
    @Test
	public void testPrintSmartCyp() throws Exception  {
			System.out.println(new SMARTCYPPlugin().getRules());
	}
}
