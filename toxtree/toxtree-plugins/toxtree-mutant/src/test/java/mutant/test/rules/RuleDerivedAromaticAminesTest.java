package mutant.test.rules;

import java.util.ArrayList;
import java.util.Hashtable;

import mutant.rules.RuleDerivedAromaticAmines;
import mutant.test.TestMutantRules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.smiles.SmilesGenerator;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.MolAnalyseException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;

public class RuleDerivedAromaticAminesTest extends TestMutantRules {

	@Override
	protected IDecisionRule createRuleToTest() throws Exception {
		return new RuleDerivedAromaticAmines();
	}
	@Override
	public String getHitsFile() {
		return "DerivedAmines/hits.sdf";
	}
	@Override
	public String getResultsFolder() {
		return "DerivedAmines";
	}
	public void testDerivedAmine1() {
		try {
			//IAtomContainer c = FunctionalGroups.createAtomContainer("COC=1C(N=C=C)=C(C=CC=1(N=C=O))C=2C=CC(N=C=O)=C(C=2)OC");
			//IAtomContainer c = FunctionalGroups.createAtomContainer("[H]C([H])C=1C=CC(=CC=1(N=C=O))N=C=O.[H]C([H])C=1C(=CC=CC=1(N=C=O))N=C=O");
			IAtomContainer c = FunctionalGroups.createAtomContainer("[H]C([H])C=1C=CC(=CC=1(N=C))N=C=O.[H]C([H])C=1C(=CC=CC=1(N=C=O))N=C");
			//IAtomContainer c = FunctionalGroups.createAtomContainer("c1ccccc1N=C",true);
			MolAnalyser.analyse(c);
			
			ruleToTest.verifyRule(c);
			MolFlags mf = (MolFlags) c.getProperty(MolFlags.MOLFLAGS);
			assertNotNull(mf);
			assertNotNull(mf.getResidues());
			IAtomContainerSet sc = mf.getResidues();
			SmilesGenerator g = new SmilesGenerator(true);
			ArrayList<String> results = new ArrayList<String>();
			results.add("[H]N([H])C=1C([H])=C([H])C([H])=C(C=1C([H])([H])[H])N([H])[H]");
			results.add("[H]N([H])C=1C([H])=C([H])C(=C(C=1([H]))N([H])[H])C([H])([H])[H]");
			results.add("[H]C([H])([H])[H]");
			/*
			results.add("[H]NC=1C([H])=C([H])C(=C([H])C=1(OC([H])([H])[H]))C=2C([H])=C([H])C(N[H])=C(OC([H])([H])[H])C=2(N[H])");
			results.add("[H]C=C([H])[H]");
			results.add("[H]NC1=C([H])C([H])=C([H])C(N[H])=C1C([H])([H])[H]");
			results.add("[H]NC=1C([H])=C([H])C(=C(N[H])C=1([H]))C([H])([H])[H]");
			*/
			for (int i=0;i<sc.getAtomContainerCount();i++) {
				String s = g.createSMILES((IMolecule)sc.getAtomContainer(i));
				//System.out.println("result "+s);
				assertTrue(results.indexOf(s)>-1);
			}	

		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	public void testDerivedAmine() {

		IAtomContainer c = FunctionalGroups.createAtomContainer("COC=1C(N=C=C)=C(C=CC=1(N=C=O))C=2C=CC(N=C=O)=C(C=2)OC");
		//IAtomContainer c = FunctionalGroups.createAtomContainer("[H]C([H])C=1C=CC(=CC=1(N=C=O))N=C=O.[H]C([H])C=1C(=CC=CC=1(N=C=O))N=C=O");
		

		//IAtomContainer c = FunctionalGroups.createAtomContainer("c1ccc(N=C=O)cc1");
		//[H]C([H])C=1C=CC(=CC=1(N=C=O))N=C=O.[H]C([H])C=1C(=CC=CC=1(N=C=O))N=C=O


		try {
			MolAnalyser.analyse(c);
		} catch (MolAnalyseException x) {
			fail();
		}
		
		
		
		IMoleculeSet sc = ((RuleDerivedAromaticAmines)ruleToTest).detachSubstituent(RuleDerivedAromaticAmines.group1(),c);
		assertNotNull(sc);
		Hashtable<String,Integer> results = new Hashtable<String,Integer>();
		results.put("[H]NC=1C([H])=C([H])C(=C([H])C=1(OC([H])([H])[H]))C=2C([H])=C([H])C(N[H])=C(OC([H])([H])[H])C=2([H])",new Integer(14));
		results.put("O=C[H]",new Integer(1));
		if (sc != null) {
			SmilesGenerator g = new SmilesGenerator(true);
			for (int i=0;i<sc.getAtomContainerCount();i++) {
				String s = g.createSMILES((IMolecule)sc.getAtomContainer(i));
				System.out.println(s);
				//assertNotNull(results.get(s));
				//MFAnalyser mf = new MFAnalyser(sc.getAtomContainer(i));
				//assertEquals(results.get(s),new Integer(mf.getAtomCount("C")));
				//System.out.println(FunctionalGroups.mapToString(sc.getAtomContainer(i)));
//				System.out.println(FunctionalGroups.hasGroupMarked(sc.getAtomContainer(i),q.getID()));
			}	

		}
	}
	
}
