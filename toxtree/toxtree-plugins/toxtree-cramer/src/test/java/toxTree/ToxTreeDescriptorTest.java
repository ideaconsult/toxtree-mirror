package toxTree;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.result.IDescriptorResult;
import org.openscience.cdk.templates.MoleculeFactory;

import ambit2.core.data.StringDescriptorResultType;

import toxTree.core.ToxTreeDescriptor;

public class ToxTreeDescriptorTest {
	@Test
	public void test() throws Exception {
		ToxTreeDescriptor d = new ToxTreeDescriptor();
		d.setParameters(new Object[] {"toxTree.tree.cramer.CramerRules"});
		IMolecule mol = MoleculeFactory.makeAlkane(10);
		DescriptorValue value = d.calculate(mol);
		IDescriptorResult result = value.getValue();
		Assert.assertTrue(result instanceof StringDescriptorResultType);
		Assert.assertEquals("Low (Class I)",((StringDescriptorResultType)result));
	}
}
