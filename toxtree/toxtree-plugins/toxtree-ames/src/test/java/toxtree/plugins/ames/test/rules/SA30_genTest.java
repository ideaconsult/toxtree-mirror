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

package toxtree.plugins.ames.test.rules;

import junit.framework.Assert;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.core.IDecisionRule;
import toxTree.query.MolAnalyser;
import toxtree.plugins.ames.rules.SA30_gen;
import toxtree.plugins.ames.test.TestAmesMutagenicityRules;

public class SA30_genTest extends TestAmesMutagenicityRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA30_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA30/onc_16.sdf";

	}
	@Override
	public String getResultsFolder() {
		return "NA30";
		
	}
	
	/**
	 * https://sourceforge.net/tracker/?func=detail&aid=3138566&group_id=152702&atid=785126
	 */
	public void test_bug3138566() throws Exception {
		SmilesParser p = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IMolecule m = p.parseSmiles("O=C1\\C=C/c2ccccc2O1");
		verifyExample(m, true);
		
	}
	
	/**
	 * Coumarin heteroring is not recognized as aromatic
	 * https://sourceforge.net/tracker/?func=detail&aid=3138566&group_id=152702&atid=785126
	 */
	public void test_bug3138566_coumarin_aromaticity() throws Exception {
		SmilesParser p = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IMolecule m = p.parseSmiles("O=C1\\C=C/c2ccccc2O1");
		MolAnalyser.analyse(m);
		int aromatic = 0;
		for (IAtom a : m.atoms()) {
			aromatic += a.getFlag(CDKConstants.ISAROMATIC)?1:0;
		}
		//OK, the heteroring is not aromatic indeed 
		Assert.assertEquals(6,aromatic);
		
	}
}
