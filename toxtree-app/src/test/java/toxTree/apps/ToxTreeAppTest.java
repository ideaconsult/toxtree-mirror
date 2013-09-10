package toxTree.apps;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import toxTree.core.IDecisionResult;
import toxTree.tree.cramer.CramerRules;
import ambit2.core.io.MDLWriter;

public class ToxTreeAppTest {

	@Test
	public void test() throws Exception {
		CramerRules rules = new CramerRules();
		IDecisionResult result = rules.createDecisionResult();
		result.setDecisionMethod(rules);
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("toxtree/apps/test.sdf");
		IteratingSDFReader reader = new IteratingSDFReader(in,SilentChemObjectBuilder.getInstance());
		OutputStream out = new FileOutputStream("result.sdf");
		MDLWriter writer = new MDLWriter(out);
		while (reader.hasNext()) {
			IAtomContainer molecule = ((IAtomContainer)reader.next());
			result.classify(molecule);
			result.assignResult(molecule);
			for (String resultProperty : result.getResultPropertyNames())
				System.out.println(String.format("%s=%s",resultProperty,molecule.getProperty(resultProperty)));
			writer.setSdFields(molecule.getProperties());
			writer.write(molecule);
		}
		reader.close();
		out.close();
		
	}
}
