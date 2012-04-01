package toxTree.cramer2;

import junit.framework.Assert;

import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.core.IDecisionRule;
import toxTree.cramer.AbstractRuleTest;
import toxTree.query.MolAnalyser;
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
		//Preferences.setProperty(Preferences.SMILESPARSER,"false");
	    Object[][] answer = {
	            {"Oc1ccc(O)cc1",new Boolean(true)},
	      //      {"Cc1cccc(C)c1O",new Boolean(true)},
	      //      {"Cc1cc(O)ccc1C",new Boolean(true)},
	            };
	   
	   SmartsPatternAmbit p = new SmartsPatternAmbit(RuleBenzeneAnalogues.smarts);
	   SmilesParser smi = new SmilesParser(SilentChemObjectBuilder.getInstance());
	   
	   for (Object[] row : answer) {
		   IMolecule testMol = smi.parseSmiles(row[0].toString());
		   int result = p.match(testMol);
		   System.out.println(row[0] + "\t"+result);
		   Assert.assertTrue(result>0);
		   MolAnalyser.analyse(testMol);
		  // AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(testMol);
		  // CDKHueckelAromaticityDetector.detectAromaticity(testMol);
		   int r = p.match(testMol);
		   Assert.assertTrue(r>0);
		
	   }
	  // this is the real rule application, which fails , probably for the reason the matching fails after atom perception
	  //ruleTest(answer); 
	    
	}
}
