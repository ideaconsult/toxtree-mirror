package mutant.test.rules;

import junit.framework.Assert;
import mutant.rules.SA30;
import mutant.test.TestMutantRules;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.core.IDecisionRule;
import toxTree.query.MolAnalyser;

public class SA30Test extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA30();
	}
	@Override
	public String getHitsFile() {
		return "NA30/onc_16.sdf";

	}
	@Override
	public String getResultsFolder() {
		return "NA30";
		
	}
	
	/**
	 * https://sourceforge.net/tracker/?func=detail&aid=3138566&group_id=152702&atid=785126
	 */
	public void test_bug3138566() throws Exception {
		SmilesParser p = new SmilesParser(NoNotificationChemObjectBuilder.getInstance());
		IMolecule m = p.parseSmiles("O=C1\\C=C/c2ccccc2O1");
		verifyExample(m, true);
		
	}
	
	/**
	 * Coumarin heteroring is not recognized as aromatic
	 * https://sourceforge.net/tracker/?func=detail&aid=3138566&group_id=152702&atid=785126
	 */
	public void test_bug3138566_coumarin_aromaticity() throws Exception {
		SmilesParser p = new SmilesParser(NoNotificationChemObjectBuilder.getInstance());
		IMolecule m = p.parseSmiles("O=C1\\C=C/c2ccccc2O1");
		MolAnalyser.analyse(m);
		int aromatic = 0;
		for (IAtom a : m.atoms()) {
			aromatic += a.getFlag(CDKConstants.ISAROMATIC)?1:0;
		}
		Assert.assertEquals(10,aromatic);
		
	}
}
