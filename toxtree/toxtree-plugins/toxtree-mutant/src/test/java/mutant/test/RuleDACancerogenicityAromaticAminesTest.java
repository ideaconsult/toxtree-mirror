/*
Copyright Ideaconsult Ltd. (C) 2005-2007  

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

package mutant.test;

import java.util.List;

import junit.framework.Assert;
import mutant.rules.RuleDACancerogenicityAromaticAmines;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.qsar.IMolecularDescriptor;

import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.SimpleReactions;

public class RuleDACancerogenicityAromaticAminesTest {
	@Test
	public void testVerifyRule() throws Exception {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		IAtomContainer mol = (IAtomContainer) FunctionalGroups
				.createAtomContainer(
						"CC(O)(CS(=O)(=O)C1=CC=C(F)C=C1)C(=O)NC2=CC=C(C(=C2)C(F)(F)F)C(N)=O",
						"test");
		MolAnalyser.analyse(mol);
		rule.verifyRule(mol);
	}

	@Test
	public void testHasDescriptors() throws Exception {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		Assert.assertNotNull(d);
		Assert.assertEquals(10, d.size());
	}

	@Test
	public void testDescriptorLRImplemented() throws Exception {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		Assert.assertNotNull(d);
		Assert.assertNotNull(d.get(0));
	}

	@Test
	public void testDescriptorB5RImplemented() throws Exception {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		Assert.assertNotNull(d);
		Assert.assertNotNull(d.get(1));
	}

	@Test
	public void testDescriptorEHOMOImplemented() throws Exception {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		Assert.assertNotNull(d);
		Assert.assertNotNull(d.get(2));
	}

	@Test
	public void testDescriptorELUMOImplemented() throws Exception {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		Assert.assertNotNull(d);
		Assert.assertNotNull(d.get(3));
	}

	@Test
	public void testDescriptorMR3Implemented() throws Exception {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		Assert.assertNotNull(d);
		Assert.assertNotNull(d.get(4));
	}

	@Test
	public void testDescriptorMR5Implemented() throws Exception {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		Assert.assertNotNull(d);
		Assert.assertNotNull(d.get(5));
	}

	@Test
	public void testDescriptorMR6Implemented() throws Exception {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		Assert.assertNotNull(d);
		Assert.assertNotNull(d.get(6));
	}

	@Test
	public void testDescriptorIAnImplemented() throws Exception {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		Assert.assertNotNull(d);
		Assert.assertNotNull(d.get(7));
	}

	@Test
	public void testDescriptorINO2Implemented() throws Exception {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		Assert.assertNotNull(d);
		Assert.assertNotNull(d.get(8));
	}

	@Test
	public void testDescriptorIBiBrImplemented() throws Exception {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		Assert.assertNotNull(d);
		Assert.assertNotNull(d.get(9));
	}

	@Test
	public void testAromaticDiAzo() throws Exception {

		SimpleReactions sr = new SimpleReactions();
		IReaction r = sr.getMetabolicReaction(0);
		IAtomContainer mol = FunctionalGroups.createAtomContainer(
				"Cc1ccc(cc1)N=Nc2ccccc2", true);
		IAtomContainerSet products = SimpleReactions.process(mol, r);
		Assert.assertNotNull(products);
		Assert.assertEquals(2, products.getAtomContainerCount());

	}

}
