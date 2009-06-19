package mutant.test.descriptors;

import javax.swing.JOptionPane;

import junit.framework.TestCase;
import mutant.rules.RuleDACancerogenicityAromaticAmines;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.IDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;
import org.openscience.cdk.qsar.result.DoubleArrayResult;

import toxTree.qsar.DescriptorMopacShell;
import toxTree.qsar.LinearDiscriminantRule;
import toxTree.qsar.Mopac7Reader;
import toxTree.query.FunctionalGroups;
import toxTree.ui.tree.qsar.DescriptorOptions;

public class DescriptorMopacShellTest extends TestCase {

	/*
	 * 
	 */
	public void testCalculate() throws Exception {
		DescriptorMopacShell d = new DescriptorMopacShell();
		IAtomContainer ac = FunctionalGroups.createAtomContainer("c1ccccc1", true);
		try {
			DescriptorValue v = (DescriptorValue) d.calculate(ac);
			assertEquals(Mopac7Reader.parameters.length,v.getNames().length);
			assertEquals(DescriptorMopacShell.EHOMO,v.getNames()[7]);
			assertEquals(DescriptorMopacShell.ELUMO,v.getNames()[8]);
			
			DoubleArrayResult r = (DoubleArrayResult) v.getValue();
			assertEquals(-9.75051,r.get(7),1E-2); //ehomo
			assertEquals(0.39523,r.get(8),1E-2); //elumo
			assertEquals(78.113,r.get(6),1E-2); //Molecular weight
			assertEquals(97.85217,r.get(2),1E-2); //FINAL HEAT OF FORMATION


		} catch (CDKException x) {
			fail(x.getMessage());
		}
	}
//	NC1=C(F)C(N)=C(F)C(F)=C1(F).[H]Cl.[H]Cl

	public void testCalculateUnsupportedAtom() throws Exception {
		DescriptorMopacShell d = new DescriptorMopacShell();
		IAtomContainer ac = FunctionalGroups.createAtomContainer("C[Si]", true);
		try {
			d.calculate(ac);
			fail("Shouldn't get here");
		} catch (CDKException x) {
			assertEquals(DescriptorMopacShell.MESSAGE_UNSUPPORTED_TYPE + "Si",x.getMessage());
		}
	}
	
	public void testCalculate1() throws Exception  {
		DescriptorMopacShell d = new DescriptorMopacShell();
		
		IAtomContainer ac = FunctionalGroups.createAtomContainer("CCCCCCCCCCC", true);
		try {
			DescriptorValue v = (DescriptorValue) d.calculate(ac);
			assertEquals(Mopac7Reader.parameters.length,v.getNames().length);
			assertEquals(DescriptorMopacShell.EHOMO,v.getNames()[7]);
			assertEquals(DescriptorMopacShell.ELUMO,v.getNames()[8]);



		} catch (CDKException x) {
			fail(x.getMessage());
		}
	}
	public void testOptions() throws Exception {
		IDescriptor d = new DescriptorMopacShell();
		DescriptorOptions o = new DescriptorOptions();
		o.setObject(d);
		JOptionPane.showConfirmDialog(null,o);
		o.setObject(d);
		JOptionPane.showConfirmDialog(null,o);
		
		d = new XLogPDescriptor();
		
		o.setObject(d);
		JOptionPane.showConfirmDialog(null,o);		
	}	
	public void testEditor() {
		LinearDiscriminantRule d = new RuleDACancerogenicityAromaticAmines();
		
		JOptionPane.showConfirmDialog(null,d.getModel().getEditor());
	}		
}
