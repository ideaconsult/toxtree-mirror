package toxTree;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.result.IDescriptorResult;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.core.ToxTreeDescriptor;
import ambit2.core.data.ArrayResult;

public class ToxTreeDescriptorTest {
	@Test
	public void test() throws Exception {
		ToxTreeDescriptor d = new ToxTreeDescriptor();
		d.setParameters(new Object[] {"toxTree.tree.cramer.CramerRules"});
		IMolecule mol = MoleculeFactory.makeAlkane(10);
		DescriptorValue value = d.calculate(mol);
		IDescriptorResult result = value.getValue();
		Assert.assertTrue(result instanceof ArrayResult);
		Assert.assertEquals("Low (Class I)",((ArrayResult)result).get(0));
	}
}
