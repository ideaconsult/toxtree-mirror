package toxTree;

import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.result.IDescriptorResult;

import toxTree.core.ToxTreeDescriptor;
import toxTree.query.FunctionalGroups;
import toxTree.tree.cramer.CramerRules;
import ambit2.core.data.ArrayResult;

public class ToxTreeDescriptorTest {
	@Test
	public void test() throws Exception {
		ToxTreeDescriptor d = new ToxTreeDescriptor();
		d.setParameters(new Object[] {"toxTree.tree.cramer.CramerRules"});
		IAtomContainer mol = FunctionalGroups.createAtomContainer("CCC(c1ccc(O)cc1)=C(CC)c2ccc(O)cc2");
		
		DescriptorValue value = d.calculate(mol);
		IDescriptorResult result = value.getValue();
		Assert.assertTrue(result instanceof ArrayResult);
		Assert.assertEquals("High (Class III)",((ArrayResult)result).get(0));
	}
	
	@Test
	public void testReactions() throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream("toxTree/config/reactions/reaction-hydrolize-C(dO)O.cml");
		Assert.assertNotNull(in);
	}	
	
	@Test
	public void testCramerRulesAsDescriptor() throws Exception {
		CramerRules d = new CramerRules();
		IAtomContainer mol = FunctionalGroups.createAtomContainer("CCC(c1ccc(O)cc1)=C(CC)c2ccc(O)cc2");
		
		DescriptorValue value = d.calculate(mol);
		IDescriptorResult result = value.getValue();
		Assert.assertTrue(result instanceof ArrayResult);
		Assert.assertEquals("High (Class III)",((ArrayResult)result).get(0));
	}	
}
