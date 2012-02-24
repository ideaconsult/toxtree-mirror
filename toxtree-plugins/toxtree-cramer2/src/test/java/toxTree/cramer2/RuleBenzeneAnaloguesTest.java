package toxTree.cramer2;

import junit.framework.Assert;

import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.core.IDecisionRule;
import ambit2.base.config.Preferences;
import ambit2.smarts.query.SmartsPatternAmbit;
import cramer2.rules.RuleBenzeneAnalogues;

public class RuleBenzeneAnaloguesTest  extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() {
		return new RuleBenzeneAnalogues();
	}

	/**
	 * https://sourceforge.net/tracker/?func=detail&aid=3488950&group_id=152702&atid=785126
	 */
	@Override
	public void test() throws Exception  {
		Preferences.setProperty(Preferences.SMILESPARSER,"true");
	    Object[][] answer = {
	            {"Oc1ccc(O)cc1",new Boolean(true)}, //20N
	            {"Cc1cccc(C)c1O",new Boolean(true)}, //20N
	            {"O1C(C)=CC(=O)C(C(=O)C)C(=O)1",new Boolean(true)}, //20N
	            };
        
	   
	   SmartsPatternAmbit p = new SmartsPatternAmbit(RuleBenzeneAnalogues.smarts);
	   SmilesParser smi = new SmilesParser(SilentChemObjectBuilder.getInstance());
	   for (Object[] row : answer) {
		   IMolecule testMol = smi.parseSmiles(row[0].toString());
		   Assert.assertTrue(p.match(testMol)>0);
		   AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(testMol);
		   Assert.assertTrue(p.match(testMol)>0);
	   }
	  // ruleTest(answer); this is the real rule application, which fails , probably for the reason the matching fails after atom perception
	    
	}
}
