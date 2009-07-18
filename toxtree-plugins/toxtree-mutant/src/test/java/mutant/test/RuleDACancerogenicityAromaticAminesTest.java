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

import junit.framework.TestCase;
import mutant.rules.RuleDACancerogenicityAromaticAmines;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.qsar.IMolecularDescriptor;

import toxTree.exceptions.ReactionException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.SimpleReactions;

public class RuleDACancerogenicityAromaticAminesTest extends TestCase {

	public void testVerifyRule() throws Exception  {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();	
		IMolecule mol = (IMolecule) FunctionalGroups.createAtomContainer("CC(O)(CS(=O)(=O)C1=CC=C(F)C=C1)C(=O)NC2=CC=C(C(=C2)C(F)(F)F)C(N)=O","test");
		MolAnalyser.analyse(mol);
		rule.verifyRule(mol);
	}
	public void testHasDescriptors() {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertEquals(10,d.size());
	}
	public void testDescriptorLRImplemented() {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(0));
	}
	public void testDescriptorB5RImplemented() {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(1));
	}	
	public void testDescriptorEHOMOImplemented() {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(2));
	}
	public void testDescriptorELUMOImplemented() {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(3));
	}	
	public void testDescriptorMR3Implemented() {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(4));
	}		
	public void testDescriptorMR5Implemented() {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(5));
	}

	public void testDescriptorMR6Implemented() {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(6));
	}	
	public void testDescriptorIAnImplemented() {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(7));
	}
	public void testDescriptorINO2Implemented() {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(8));
	}	
	public void testDescriptorIBiBrImplemented() {
		RuleDACancerogenicityAromaticAmines rule = new RuleDACancerogenicityAromaticAmines();
		List<IMolecularDescriptor> d = rule.getModel().getDescriptors();
		assertNotNull(d);
		assertNotNull(d.get(9));
	}	
	public void testAromaticDiAzo() {
		try {
			SimpleReactions sr = new SimpleReactions();
			IReaction r = sr.getMetabolicReaction(0);
			IAtomContainer mol = FunctionalGroups.createAtomContainer("Cc1ccc(cc1)N=Nc2ccccc2",true);
			IMoleculeSet products = SimpleReactions.process(mol,r);
			assertNotNull(products);
			assertEquals(2,products.getAtomContainerCount());
		} catch (CDKException x) {
			fail(x.getMessage());
		} catch (ReactionException x) {
			fail(x.getMessage());
		}
	}
	
}


