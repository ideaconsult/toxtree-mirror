package mutant.test.rules;

import mutant.rules.SA29;
import mutant.test.TestMutantRules;

import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.ringsearch.SSSRFinder;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.smiles.smarts.SMARTSQueryTool;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;

public class SA29Test extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new SA29();
	}
	@Override
	public String getHitsFile() {
		return "NA29/sa3iss2.sdf";		
	}
	@Override
	public String getResultsFolder() {
		return "NA29";
	}
	
	//"[$(a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a:a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a:a:a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1])][N]=[N][$(a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a:a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1]),$(a:a:a:a:a[#16X4](=[OX1])(=[OX1])[OX2H1])]"
	
	public int CDKSmarts(String smarts, String smiles) throws Exception  {

	   SMARTSQueryTool sqt = new SMARTSQueryTool(smarts);
        
	   SmilesParser sp = new SmilesParser(SilentChemObjectBuilder.getInstance());
	        	
	   IAtomContainer atomContainer = sp.parseSmiles(smiles);
	           
       AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(atomContainer);
   		CDKHueckelAromaticityDetector.detectAromaticity(atomContainer);
        
        int n = 0;
        
        if (sqt.matches(atomContainer)) {
        	n = sqt.countMatches();
        }
        /* infinite recursion if clone is attemted
        atomContainer.clone();
        */
        return n;
	}	
	//infinite recursion if RingSet is a property of Atom
	public void testCDKBug1838820() throws Exception {
		 IAtomContainer benzene = MoleculeFactory.makeBenzene();
		 
		 SSSRFinder sssr = new SSSRFinder(benzene);
		 IRingSet rings = sssr.findEssentialRings();
	     
		 benzene.getAtom(0).setProperty("ring",rings);
	     benzene.clone();
	     assertTrue(true);
	}
	public void testCDKSmarts890() throws Exception  {
		String smiles="CCCN(CCC)C1=CC=C(C=C1)N=NC2=CC=[N+]([O-])C=C2";
        assertTrue(CDKSmarts("a[N]=[N]a", smiles)>0);
	}
	
	public void testCDKSmartsSO3H() throws Exception  {
		String smiles=
			//"[H]OC2=C([H])C([H])=C3C([H])=C(C([H])=C([H])C3(=C2(N=NC1=C([H])C([H])=C(C([H])=C1([H]))S(=O)(=O)[O-])))S(=O)(=O)[O-]";
			"[H]OC2=CC=C(C([H])=C2(N=NC1=CC=C(C([H])=C1([H]))S(=O)(=O)[O-]))S(=O)(=O)[O-]";
        assertTrue(CDKSmarts(
        		SA29.SA29_noSO3H
        		, smiles)>0);
	}
	public void testCDKSmarts82() throws Exception  {
		String smiles="[H]OC=3C(N=NC=1C([H])=C([H])C(=C2C([H])=C([H])C([H])=C([H])C=12)S(=O)(=O)[O-])=C([H])C(=C4C([H])=C([H])C([H])=C([H])C=34)S(=O)(=O)[O-]";
        assertTrue(CDKSmarts(SA29.SA29_noSO3H, smiles)>0);
	}	
	public void testCDKSmarts138() throws Exception  {
		String smiles="[H]OC2=C([H])C([H])=C3C([H])=C(C([H])=C([H])C3(=C2(N=NC1=C([H])C([H])=C(C([H])=C1([H]))S(=O)(=O)[O-])))S(=O)(=O)[O-]";
        assertTrue(CDKSmarts(SA29.SA29_noSO3H, smiles)>0);
	}
	public void testRule29_138() throws Exception  {
		String smiles="[H]OC2=C([H])C([H])=C3C([H])=C(C([H])=C([H])C3(=C2(N=NC1=C([H])C([H])=C(C([H])=C1([H]))S(=O)(=O)[O-])))S(=O)(=O)[O-]";
		IAtomContainer ac = FunctionalGroups.createAtomContainer(smiles, true);
		MolAnalyser.analyse(ac);
        assertFalse(ruleToTest.verifyRule(ac));
	}			
}
