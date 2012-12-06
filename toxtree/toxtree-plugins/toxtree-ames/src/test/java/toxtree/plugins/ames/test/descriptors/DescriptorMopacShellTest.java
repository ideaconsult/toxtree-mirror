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

import javax.swing.JOptionPane;

import junit.framework.TestCase;

import org.junit.Assert;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.result.DoubleArrayResult;

import toxTree.qsar.LinearDiscriminantRule;
import toxTree.query.FunctionalGroups;
import toxtree.plugins.ames.rules.RuleDACancerogenicityAromaticAmines;
import ambit2.mopac.DescriptorMopacShell;
import ambit2.mopac.Mopac7Reader;

public class DescriptorMopacShellTest extends TestCase {

	/*
	 * 
	 */
	public void testCalculate() throws Exception {
		DescriptorMopacShell d = new DescriptorMopacShell();
		IAtomContainer ac = FunctionalGroups.createAtomContainer("c1ccccc1", true);

			DescriptorValue v = (DescriptorValue) d.calculate(ac);
			assertEquals(Mopac7Reader.parameters.length,v.getNames().length);
			assertEquals(DescriptorMopacShell.EHOMO,v.getNames()[7]);
			assertEquals(DescriptorMopacShell.ELUMO,v.getNames()[8]);
			
			DoubleArrayResult r = (DoubleArrayResult) v.getValue();
			assertEquals(-9.75051,r.get(7),1E-2); //ehomo
			assertEquals(0.39523,r.get(8),1E-2); //elumo
			assertEquals(78.113,r.get(6),1E-2); //Molecular weight
			assertEquals(97.85217,r.get(2),1E-2); //FINAL HEAT OF FORMATION

	}
//	NC1=C(F)C(N)=C(F)C(F)=C1(F).[H]Cl.[H]Cl

	public void testCalculateUnsupportedAtom() throws Exception {
		DescriptorMopacShell d = new DescriptorMopacShell();
		IAtomContainer ac = FunctionalGroups.createAtomContainer("C[Si]", true);
		DescriptorValue value = d.calculate(ac);
		Assert.assertNotNull(value.getException());
		assertEquals(" UNSUPPORTED TYPE " + "Si",value.getException().getMessage());
	}
	
	public void testCalculate1() throws Exception  {
		DescriptorMopacShell d = new DescriptorMopacShell();
		
		IAtomContainer ac = FunctionalGroups.createAtomContainer("CCCCCCCCCCC", true);
		DescriptorValue v = (DescriptorValue) d.calculate(ac);
		assertEquals(Mopac7Reader.parameters.length,v.getNames().length);
		assertEquals(DescriptorMopacShell.EHOMO,v.getNames()[7]);
		assertEquals(DescriptorMopacShell.ELUMO,v.getNames()[8]);
		assertNull(v.getException());

	}
	public void testOptions() throws Exception {
		/*
		IDescriptor d = new DescriptorMopacShell();
		DescriptorOptions o = new DescriptorOptions();
		o.setObject(d);
		JOptionPane.showConfirmDialog(null,o);
		o.setObject(d);
		JOptionPane.showConfirmDialog(null,o);
		
		d = new XLogPDescriptor();
		try {d.setParameters(new Object[] {Boolean.TRUE});} catch (Exception x) {}
		o.setObject(d);
		JOptionPane.showConfirmDialog(null,o);		
		*/
	}	
	public void testEditor() {
		LinearDiscriminantRule d = new RuleDACancerogenicityAromaticAmines();
		
		JOptionPane.showConfirmDialog(null,d.getModel().getEditor());
	}		
}
