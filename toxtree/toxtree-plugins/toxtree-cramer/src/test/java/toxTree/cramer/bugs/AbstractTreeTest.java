package toxTree.cramer.bugs;

import org.junit.BeforeClass;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.core.IDecisionResult;
import toxTree.tree.cramer.CramerRules;

public class AbstractTreeTest {
	protected static CramerRules cr ;
	
	@BeforeClass
	public static void setup() throws Exception {
		cr = new CramerRules();
		cr.setResiduesIDVisible(false);

	}
	
	public IDecisionResult classify(String smiles) throws Exception {
		IDecisionResult result = cr.createDecisionResult();
		result.setDecisionMethod(cr);
		
		SmilesParser parser = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IAtomContainer ac  = parser.parseSmiles(smiles);
		result.classify(ac);
		return result;
	}
}
