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

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.Assert;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.core.Introspection;
import toxTree.tree.DecisionNode;
import toxtree.plugins.ames.rules.SA2_gen;
import toxtree.plugins.ames.test.TestAmesMutagenicityRules;
import toxtree.ui.tree.actions.NewRuleAction;

public class SA2_genTest extends TestAmesMutagenicityRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA2_gen();
	}
	@Override
	public String getHitsFile() {
		return "NA2/sa1_l_iss2.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "NA2";
	}
	
	public void testSave() throws Exception {
			IDecisionMethod tree = NewRuleAction.treeFromRule(new SA2_gen());
			System.out.println(tree.getTopRule());
			assertEquals("NO",tree.getCategories().get(0).getName());
			assertEquals("YES",tree.getCategories().get(1).getName());

			
			File file= File.createTempFile("testSA2", "tml");
			file.deleteOnExit();
			FileOutputStream os = new FileOutputStream(file);
			//Thread.currentThread().setContextClassLoader(Introspection.getLoader());
			XMLEncoder encoder = new XMLEncoder(os);
			encoder.writeObject(tree);
			encoder.close();	
			os.close();
			
			Assert.assertTrue(file.exists());
			
			Introspection.setLoader(getClass().getClassLoader());
			IDecisionMethod newtree = Introspection.loadRulesXML(new FileInputStream(file),"new tree");
			assertEquals("NO",newtree.getCategories().get(0).getName());
			assertEquals("YES",newtree.getCategories().get(1).getName());
			
			assertTrue(((DecisionNode)newtree.getTopRule()).getRule() instanceof SA2_gen);
			//System.out.println(newtree.getTitle());
			

	}
	public void testHalogenSubstituents() throws Exception {
		String[] smiles = {
				"S(=O)(=O)(C)OCCl",
				"S(=O)(=O)(C)OCCCl",
				"S(=O)(=O)(C)OC(F)CCl",
				"S(=O)(=O)(C)OC(F)C(Br)CCl",
				"S(=O)(=O)(C)OC(Cl)(CF)CBr",
				"S(=O)(=O)(C)OC(Cl)C(Cl)C(Cl)CCl",
				"S(=O)(=O)(C)OC(Cl)C(Cl)(CCl)(CCl)",
				"S(=O)(=O)(C)OC(CCl)(CCl)CCl",
				"S(=O)(=O)(C)OC(CCl)C(Cl)CCl","CC(C)OS(C)(=O)=O"
				//"COP(C)(=O)OCCl"
		};
		for (int i=0; i < smiles.length;i++) {
			//System.out.print(smiles[i]);
			assertTrue(verify(smiles[i]));
			//System.out.println(" ok");
		}
		
	}
}


