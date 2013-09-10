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
package toxTree.test.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.tree.AbstractRule;
import toxTree.tree.DefaultCategory;
import toxTree.tree.rules.RuleAllSubstructures;
import toxTree.tree.rules.RuleAnySubstituents;
import toxTree.tree.rules.RuleAnySubstructure;
import toxTree.tree.rules.RuleAromatic;
import toxTree.tree.rules.RuleCommonTerpene;
import toxTree.tree.rules.RuleElements;
import toxTree.tree.rules.RuleHeterocyclic;
import toxTree.tree.rules.RuleManyAromaticRings;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;
import toxTree.tree.rules.RuleOpenChain;
import toxTree.tree.rules.RuleReadilyHydrolised;
import toxTree.tree.rules.RuleRingAllowedSubstituents;
import toxTree.tree.rules.RuleStructuresList;

/**
 * tests {@link IDecisionMethod} serialization
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-5
 */
public class TreeSerializerTest {
	protected UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
	protected static Logger logger = Logger.getLogger(TreeSerializerTest.class.getName()); 
	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testAbstractRule() throws Exception {
		IDecisionRule r = new RuleAromatic();
		r.setID("100");
		r.setNum(99);
		r.setTitle("test");
		r.setExplanation("explanation");
		r.setExampleMolecule(MoleculeFactory.makeBenzene(),true);
		r.setExampleMolecule(MoleculeFactory.makeAlkane(2),false);
		
		File temp = File.createTempFile("test", ".rule");  
		temp.deleteOnExit();
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(temp));
			out.writeObject(r);
			out.close();
			ObjectInputStream in = new ObjectInputStream(
					new FileInputStream(temp));
			
			Object r1 = in.readObject();
			Assert.assertEquals(r,r1);
			
			in.close();

			Assert.assertTrue(uit.isIsomorph(
						r.getExampleMolecule(true),((AbstractRule)r1).getExampleMolecule(true)
						));
			Assert.assertTrue(uit.isIsomorph(
						r.getExampleMolecule(false),((AbstractRule)r1).getExampleMolecule(false)
						));

		
	}
	@Test
	public void testCategoryRoundTrip() throws Exception {
		DefaultCategory c1 = new DefaultCategory("My Class",100);
		DefaultCategory c2 = new DefaultCategory();
		Assert.assertNotSame(c1,c2);
		
			//writing
			File f = File.createTempFile("Category","test");
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(c1);
			os.close();
			
			//reading
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(f));
			c2 =(DefaultCategory) is.readObject();
			is.close();
			f.delete();
			Assert.assertEquals(c1,c2);
			
	}
	
	protected AbstractRule ruleRoundTrip(AbstractRule rule) throws Exception {
		return (AbstractRule)objectRoundTrip(rule,"Rule");
	}
	protected Object objectRoundTrip(Object rule,String filename) throws Exception {		

			//writing
			File f = File.createTempFile(filename,"test");
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(rule);
			os.close();
			
			//reading
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(f));
			Object rule2 =is.readObject();
			is.close();
			f.delete();
			logger.finer(rule.toString());
			Assert.assertEquals(rule,rule2);
			return rule2;
	}	
	@Test
	public void testRules() throws Exception {
		//ruleRoundTrip(new RuleSubstructures());
		ruleRoundTrip(new RuleAnySubstructure());
		
		ruleRoundTrip(new RuleRingAllowedSubstituents());
		ruleRoundTrip(new RuleReadilyHydrolised());
		ruleRoundTrip(new RuleOpenChain());
		ruleRoundTrip(new RuleOnlyAllowedSubstructures());
		ruleRoundTrip(new RuleManyAromaticRings());
		ruleRoundTrip(new RuleHeterocyclic());
		
		ruleRoundTrip(new RuleCommonTerpene());
		ruleRoundTrip(new RuleAromatic());
		RuleAnySubstructure rule1 = new RuleAnySubstructure();
		rule1.addSubstructure(FunctionalGroups.ester());
		rule1.addSubstructure(FunctionalGroups.acrolein());
		RuleAnySubstructure rule2 = (RuleAnySubstructure)ruleRoundTrip(rule1);
		IAtomContainer m = FunctionalGroups.acrolein();

			MolAnalyser.analyse(m);
			Assert.assertTrue(rule2.verifyRule(m));
			
			m = FunctionalGroups.createAtomContainer("CCC(=O)OCCC");
			MolAnalyser.analyse(m);
			Assert.assertTrue(rule2.verifyRule(m));			

		
		ruleRoundTrip(new RuleAnySubstituents());
		ruleRoundTrip(new RuleAllSubstructures());
		RuleElements rule = new RuleElements();
		rule.addElement("C");
		rule.addElement("N");
		ruleRoundTrip(rule);
		
		ruleRoundTrip(new RuleStructuresList());
	}
	

}
