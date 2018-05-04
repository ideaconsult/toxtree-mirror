package toxtree.tree.cramer3.rules.test;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.smiles.SmilesGenerator;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import toxTree.tree.AbstractRule;
import toxtree.tree.cramer3.rules.RuleQ1;
import toxtree.tree.cramer3.rules.RuleQ16;

public class TestRuleQ16 extends AbstractRuleTest {

	@Override
	protected IDecisionRule createRule() throws Exception {
		RuleQ16 rule = new RuleQ16();
		LogManager.getLogManager().readConfiguration();
		Assert.assertTrue(Logger.getLogger(AbstractRule.class.getName())
				.isLoggable(Level.FINE));
		Assert.assertTrue(logger_test.isLoggable(Level.FINE));
		logger_test.log(Level.FINE, rule.getClass().getName());
		return rule;
	}

	/**
	 * Q16 should be YES for linear non-aromatic mercaptans when a second
	 * functional group from those listed earlier in Q16 is also presen
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMultiple() throws Exception {
		String[] smiles = { "CC(=O)CCCC(C)=O", "OCCS" };
		test(smiles, true);
	}

	/**
	 * simple aliphatic mercaptans and monosulfides (without other groups)
	 * should answer NO
	 * 
	 * /** Q16 should be YES for linear non-aromatic mercaptans when a second
	 * functional group from those listed earlier in Q16 is also present
	 * 
	 * @throws Exception
	 */

	@Test
	public void testSimpleAliphaticMercaptansandMonosulfides0()
			throws Exception {
		test(new String[] { 
				"SCC(C)C", 
				"SCCC(C)C", 
				"SCCCCCC" 
				}, false);
	}

	/**
	 * #46 Check the ketone SMARTS, it should match
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSimpleAliphaticMercaptansandMonosulfides2()
			throws Exception {
		test(new String[] {  "C(C(=O)C)CS" }, true);
		test(new String[] { "C(C(C)=O)S", }, true);
	}

	/**
	 * When there are 2 functional groups where one is a monosulfide and the
	 * other is listed an earlier line in Q16, the answer should be YES.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test2funcgroups_onemonosulfide() throws Exception {
		String[] smiles = new String[] { 
				"S(CCCN)C" 
				,"C(=O)CC(CCC)SC"
				,"S(C)CCC(=O)C(=O)O" 
				,"O=C(CCSC)C" 
				};

		test(smiles, true);
	}

	/**
	 * Other false negatives: "CC(=O)CCC(=O)OCc1ccccc1" can't be positive, it
	 * has an aromatic ring!
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPositiveHydrolysis() throws Exception {
		String[] smiles = new String[] { "CC(=O)CCC(=O)OCc1ccccc1" };
		RuleQ1 q1 = new RuleQ1();
		IAtomContainer mol = FunctionalGroups.createAtomContainer(smiles[0]);
		MolAnalyser.analyse(mol);
		boolean r = q1.verifyRule(mol);
		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		Assert.assertNotNull(mf);
		IAtomContainerSet products = mf.getHydrolysisProducts();
		Assert.assertNotNull(products);
		Assert.assertEquals(2, products.getAtomContainerCount());
		r = false;
		SmilesGenerator g = SmilesGenerator.unique();
		for (IAtomContainer product : products.atomContainers()) {
			MolAnalyser.analyse(product);
			String smi = g.create(product);
			boolean result = rule2test.verifyRule(product);
			r = r || result;
			System.out.println(String.format("%s\t%s",result,smi));
		}
		Assert.assertTrue(r);
	}

	@Test
	public void testPositive() throws Exception {
		String[] smiles = new String[] { "CC(=O)C(C)SC", "CC(=O)CC(C)SC",
				"CC(=O)CC(C)(S)CC", "O=C(CCCCC)C", "O=C(CCCC)CC" };
		test(smiles, true);
	}
	/**
	 * 16C The following indicates the idea behind this question. An mercaptan (SH group) is generally of concern unless there is another functional group present that influences the metabolism to an innocuous and thus lower class product.
	 * @throws Exception
	 */
	@Test
	public void testMercaptan() throws Exception {
		String[] smiles = new String[] { "OCCS" };
		test(smiles, true);
		smiles = new String[] { "SCC" };
		test(smiles, false);
	}

	@Test
	public void testMonoketones() throws Exception {
		String[] smiles = new String[] { "C=CC(=O)C\\C=C\\CC",
				"CC(=O)CCCCCCCC=C", "O=C(C=CC)C" };
		test(smiles, true);
	}

	@Test
	public void testAcetylenic() throws Exception {
		String[] smiles = new String[] { "O=C(OC)C#CCCCCCC" };
		test(smiles, false);
	}
	

	@Test
	public void testExcel_544_40_1() throws Exception {
		String[] smiles = new String[] { "S(CCCC)CCCC" };
		test(smiles, false);
	}

	@Test
	public void testSA()
			throws Exception {
		String[] smiles = new String[] { 
				"O=C(CCCCC)C",
				"O=C(CC(C)C)C",
				"O=C(C)CCCCCC=C",
				"CC(C)CC(=O)CO",
				"CC(CC)C(=O)C(=O)O",
				"CC(C)C(C)(C(C)C)C(=O)NC",
				"CC/C=C\\CC/C=C/C(=O)NCC",
				"CCCCCCCCC=CCCCCCCCCC(=O)C"
			};
		smiles = new String[] { "O=C(CCCCC)C"};
		test(smiles, true);
	}

	@Test
	public void test9CProduct()
			throws Exception {
		String[] smiles = new String[] { 
				"C(=O)(O)CCC(C)(O)CCC=CCC",
				"C(=O)(O[H])C(C(C(C([H])([H])[H])(O[H])C(C(C(=C(C(C([H])([H])[H])([H])[H])[H])[H])([H])[H])([H])[H])([H])[H])([H])[H]",
				"O=C(C(C(C(O[H])(C(C(C(=C(C(C([H])([H])[H])([H])[H])[H])[H])([H])[H])([H])[H])C([H])([H])[H])([H])[H])([H])[H])O[H]"
			};
		test(smiles, true);
	}
	
}
