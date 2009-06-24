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
package toxTree.test.io.xml;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.TestCase;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.exceptions.DecisionMethodException;
import toxTree.logging.TTLogger;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.tree.AbstractRule;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.DefaultCategory;
import toxTree.tree.RulesList;
import toxTree.tree.TreeResult;
import toxTree.tree.UserDefinedTree;
import toxTree.tree.cramer.CramerClass1;
import toxTree.tree.cramer.CramerClass3;
import toxTree.tree.cramer.CramerRules;
import toxTree.tree.cramer.RuleCommonComponentOfFood;
import toxTree.tree.cramer.RuleRingComplexSubstituents30;
import toxTree.tree.rules.RuleAllSubstructures;
import toxTree.tree.rules.RuleAnySubstituents;
import toxTree.tree.rules.RuleAnySubstructure;
import toxTree.tree.rules.RuleAromatic;
import toxTree.tree.rules.RuleCommonTerpene;
import toxTree.tree.rules.RuleDescriptorRange;
import toxTree.tree.rules.RuleElements;
import toxTree.tree.rules.RuleHeterocyclic;
import toxTree.tree.rules.RuleLipinski5;
import toxTree.tree.rules.RuleManyAromaticRings;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;
import toxTree.tree.rules.RuleOpenChain;
import toxTree.tree.rules.RuleReadilyHydrolised;
import toxTree.tree.rules.RuleRingAllowedSubstituents;
import toxTree.tree.rules.RuleStructuresList;

/**
 * TODO add description
 * @author ThinClient
 * <b>Modified</b> 2005-9-5
 */
public class TreeSerializerTest extends TestCase {
	protected static TTLogger logger = new TTLogger(TreeSerializerTest.class);
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TreeSerializerTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for TreeSerializerTest.
	 * @param arg0
	 */
	public TreeSerializerTest(String arg0) {
		super(arg0);
		TTLogger.configureLog4j(true);
	}

	public void testAbstractRule() {
		IDecisionRule r = new RuleAromatic();
		r.setID("100");
		r.setNum(99);
		r.setTitle("test");
		r.setExplanation("rule explanation");
		r.setExampleMolecule(MoleculeFactory.makeBenzene(),true);
		r.setExampleMolecule(MoleculeFactory.makeAlkane(2),false);
		
		try {
			FileOutputStream os = new FileOutputStream("rule.xml");
			XMLEncoder encoder = new XMLEncoder(os);
			encoder.writeObject(r);
			encoder.close();
			
			FileInputStream is = new FileInputStream("rule.xml");
			XMLDecoder decoder = new XMLDecoder(is);
			Object r1 = decoder.readObject();
			decoder.close();			

			assertEquals(r,r1);
			
			try {
				assertTrue(UniversalIsomorphismTester.isIsomorph(
						r.getExampleMolecule(true),((AbstractRule)r1).getExampleMolecule(true)
						));
				assertTrue(UniversalIsomorphismTester.isIsomorph(
						r.getExampleMolecule(false),((AbstractRule)r1).getExampleMolecule(false)
						));
			} catch (DecisionMethodException x) {
				x.printStackTrace();
				fail();
			} catch (CDKException x) {
				x.printStackTrace();
				fail();
			}
			
		} catch (Exception x) {
			x.printStackTrace();
			fail();

		}
		
	}
	
	public void test() {
		TreeResult tr = new TreeResult();
		try {
			Object tr1 = objectRoundTrip(tr);
			assertTrue(tr1 instanceof IDecisionResult);
			assertEquals(tr,tr1);
			
		} catch (Exception x) {
			x.printStackTrace();
			fail();
		}
	}
	public void testTreeResult() throws Exception {
		CramerRules cr=null;
		cr = new CramerRules();
		IDecisionResult tr = cr.createDecisionResult();
		tr.setDecisionMethod(cr);
	    tr.classify(MoleculeFactory.makeAlkane(3));
			Object tr1 = objectRoundTrip(tr);
			assertTrue(tr1 instanceof IDecisionResult);
			//System.out.println(tr.explain(true).toString());
			//System.out.println(((IDecisionResult)tr1).explain(true).toString());
			assertEquals(tr,tr1);
	}
	
	public void testTreeResultNullMethod() throws Exception  {
		CramerRules rules = null;
		rules = new CramerRules();
		IDecisionResult tr = rules.createDecisionResult();
		tr.setDecisionMethod(null);
		assertEquals(tr.getCategory(),null);
			Object tr1 = objectRoundTrip(tr);
			assertTrue(tr1 instanceof IDecisionResult);
			assertNull(((IDecisionResult)tr1).getCategory());
	}			
	public void testCategoryRoundTrip() throws Exception  {
			FileOutputStream os = new FileOutputStream("category.xml");
			XMLEncoder encoder = new XMLEncoder(os);
			DefaultCategory c = new DefaultCategory("class1",1);
			c.setExplanation(null);
			c.setThreshold("0.99");
			encoder.writeObject(c);
			encoder.close();
			
			FileInputStream is = new FileInputStream("category.xml");
			XMLDecoder decoder = new XMLDecoder(is);
			DefaultCategory p = (DefaultCategory)decoder.readObject();
			
			decoder.close(); 

			assertEquals(c,p);

	}
	
	protected AbstractRule ruleRoundTrip(AbstractRule rule) throws Exception {
		return (AbstractRule)objectRoundTrip(rule);
	}
	protected Object objectRoundTrip(Object rule) throws Exception {		
			//writing
			FileOutputStream os = new FileOutputStream(rule.getClass().getName()+".xml");
			XMLEncoder encoder = new XMLEncoder(os);
			encoder.writeObject(rule);
			encoder.close();			
			
			//reading
			FileInputStream is = new FileInputStream(rule.getClass().getName()+".xml");
			XMLDecoder decoder = new XMLDecoder(is);
			Object rule2 = decoder.readObject();
			decoder.close();	
			
			logger.debug(rule.toString());
			assertEquals(rule,rule2);
			return rule2;

	}	
	
	public void testRules() throws Exception  {
		//ruleRoundTrip(new RuleSubstructures());

		ruleRoundTrip(new RuleAnySubstructure());
		
		ruleRoundTrip(new RuleRingComplexSubstituents30());
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
		try {
			MolAnalyser.analyse(m);
			assertTrue(rule2.verifyRule(m));
			
			m = FunctionalGroups.createAtomContainer("CCC(=O)OCCC");
			MolAnalyser.analyse(m);
			assertTrue(rule2.verifyRule(m));			
		} catch (Exception x) {
			x.printStackTrace();
			fail();
		}
		
		ruleRoundTrip(new RuleAnySubstituents());
		ruleRoundTrip(new RuleAllSubstructures());
		RuleElements rule = new RuleElements();
		rule.addElement("C");
		rule.addElement("N");
		ruleRoundTrip(rule);
		
		ruleRoundTrip(new RuleStructuresList());
		
		RuleManyAromaticRings r = new RuleManyAromaticRings();
		r.setMinValue(3);
		r.setMaxValue(6);
		ruleRoundTrip(r);
		
		RuleDescriptorRange d = new RuleDescriptorRange();
		d.setMaxValue(5);
		d.setMinValue(3);
		XLogPDescriptor x = new XLogPDescriptor();
		d.setDescriptor(x);
		RuleDescriptorRange dd = (RuleDescriptorRange) ruleRoundTrip(d);
		
		IAtomContainer mol = MoleculeFactory.makeBenzene();
		//x.calculate(m);
		try {
			//12.4
			assertFalse(dd.verifyRule(mol));
			dd.setMaxValue(15);
			dd.setMinValue(10);
			assertTrue(dd.verifyRule(mol));
		} catch (Exception e) {
			fail();
		}
	}
	
	public void testRulesList() throws Exception  {
		IDecisionRuleList rules = new RulesList();
		rules.addRule(new RuleCommonComponentOfFood());
		rules.addRule(new RuleLipinski5());
		objectRoundTrip(rules);
		assertTrue(rules.get(0) instanceof RuleCommonComponentOfFood);
		assertTrue(rules.get(1) instanceof RuleLipinski5);
	}
	
	public void testDecisionNodesList()  throws Exception{
		IDecisionRuleList rules = new DecisionNodesList();
		rules.addRule(new RuleCommonComponentOfFood());
		rules.addRule(new RuleLipinski5());
		objectRoundTrip(rules);
		assertTrue(rules.getRule(0) instanceof RuleCommonComponentOfFood);
		assertTrue(rules.getRule(1) instanceof RuleLipinski5);
	}	
	public void testCramer() throws Exception{
		CramerRules rules = null;
		try {
			rules = new CramerRules();
		} catch (DecisionMethodException x) {
			fail();
		}
		objectRoundTrip(rules);
	}

	/*
	public void testSubstructureTree() {
		SubstructureTree rules = null;
		try {
			rules = new SubstructureTree();
		} catch (DecisionMethodException x) {
			fail();
		}
		objectRoundTrip(rules);
	}	
	*/
	
	public void testUserDefinedTree() {
		UserDefinedTree rules = null;
		try {
			rules = new UserDefinedTree();
			rules.getRules().addRule(new RuleCommonComponentOfFood());
			rules.getCategories().addCategory(new CramerClass1());
			rules.getCategories().addCategory(new CramerClass3());
			assertEquals(rules.getCategories().size(),2);
			Object r1 = objectRoundTrip(rules);
			assertEquals(rules,r1);
			assertEquals(rules.getCategories().size(),((IDecisionMethod)r1).getCategories().size());
		} catch (Exception x) {
			fail();
		}
		
	}	
	
}
