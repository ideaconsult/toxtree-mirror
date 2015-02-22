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
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.tree.AbstractRule;
import toxTree.tree.DefaultCategory;
import toxTree.tree.TreeResult;
import toxTree.tree.rules.RuleAllSubstructures;
import toxTree.tree.rules.RuleAnySubstituents;
import toxTree.tree.rules.RuleAnySubstructure;
import toxTree.tree.rules.RuleAromatic;
import toxTree.tree.rules.RuleCommonTerpene;
import toxTree.tree.rules.RuleDescriptorRange;
import toxTree.tree.rules.RuleElements;
import toxTree.tree.rules.RuleHeterocyclic;
import toxTree.tree.rules.RuleManyAromaticRings;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;
import toxTree.tree.rules.RuleOpenChain;
import toxTree.tree.rules.RuleReadilyHydrolised;
import toxTree.tree.rules.RuleRingAllowedSubstituents;
import toxTree.tree.rules.RuleStructuresList;

/**
 * TODO add description
 * 
 * @author Nina Jeliazkova <b>Modified</b> 2005-9-5
 */
public class TreeSerializerTest {

	protected static Logger logger = Logger.getLogger(TreeSerializerTest.class
			.getName());

	@Test
	public void testAbstractRule() throws Exception {
		IDecisionRule r = new RuleAromatic();
		r.setID("100");
		r.setNum(99);
		r.setTitle("test");
		r.setExplanation("rule explanation");
		r.setExampleMolecule(MoleculeFactory.makeBenzene(), true);
		r.setExampleMolecule(MoleculeFactory.makeAlkane(2), false);

		FileOutputStream os = new FileOutputStream("rule.xml");
		XMLEncoder encoder = new XMLEncoder(os);
		encoder.writeObject(r);
		encoder.close();

		FileInputStream is = new FileInputStream("rule.xml");
		XMLDecoder decoder = new XMLDecoder(is);
		Object r1 = decoder.readObject();
		decoder.close();

		Assert.assertEquals(r, r1);
		UniversalIsomorphismTester uit = new UniversalIsomorphismTester();
		Assert.assertTrue(uit.isIsomorph(r.getExampleMolecule(true),
				((AbstractRule) r1).getExampleMolecule(true)));
		Assert.assertTrue(uit.isIsomorph(r.getExampleMolecule(false),
				((AbstractRule) r1).getExampleMolecule(false)));

	}

	@Test
	public void test() throws Exception {
		TreeResult tr = new TreeResult();
		Object tr1 = objectRoundTrip(tr);
		Assert.assertTrue(tr1 instanceof IDecisionResult);
		Assert.assertEquals(tr, tr1);
	}

	@Test
	public void testCategoryRoundTrip() throws Exception {
		FileOutputStream os = new FileOutputStream("category.xml");
		XMLEncoder encoder = new XMLEncoder(os);
		DefaultCategory c = new DefaultCategory("class1", 1);
		c.setExplanation(null);
		c.setThreshold("0.99");
		encoder.writeObject(c);
		encoder.close();

		FileInputStream is = new FileInputStream("category.xml");
		XMLDecoder decoder = new XMLDecoder(is);
		DefaultCategory p = (DefaultCategory) decoder.readObject();

		decoder.close();

		Assert.assertEquals(c, p);

	}

	protected AbstractRule ruleRoundTrip(AbstractRule rule) throws Exception {
		return (AbstractRule) objectRoundTrip(rule);
	}

	protected Object objectRoundTrip(Object rule) throws Exception {
		// writing
		FileOutputStream os = new FileOutputStream(rule.getClass().getName()
				+ ".xml");
		XMLEncoder encoder = new XMLEncoder(os);
		encoder.writeObject(rule);
		encoder.close();

		// reading
		FileInputStream is = new FileInputStream(rule.getClass().getName()
				+ ".xml");
		XMLDecoder decoder = new XMLDecoder(is);
		Object rule2 = decoder.readObject();
		decoder.close();

		logger.finer(rule.toString());
		Assert.assertEquals(rule, rule2);
		return rule2;

	}

	@Test
	public void testRules() throws Exception {
		// ruleRoundTrip(new RuleSubstructures());

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
		RuleAnySubstructure rule2 = (RuleAnySubstructure) ruleRoundTrip(rule1);
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
		// x.calculate(m);
		// 2.218
		Assert.assertFalse(dd.verifyRule(mol));
		dd.setMaxValue(3);
		dd.setMinValue(2);
		Assert.assertTrue(dd.verifyRule(mol));

	}

	/*
	 * public void testSubstructureTree() { SubstructureTree rules = null; try {
	 * rules = new SubstructureTree(); } catch (DecisionMethodException x) {
	 * fail(); } objectRoundTrip(rules); }
	 */

}
