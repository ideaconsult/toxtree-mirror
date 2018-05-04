/*
Copyright (C) 2005-2015  

Contact: jeliazkova.nina@gmail.com

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
 */

package toxtree.tree.cramer3.rules.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;

import toxTree.core.IDecisionRule;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import ambit2.core.io.FileInputState;

public abstract class AbstractRuleTest {
	protected IDecisionRule rule2test = null;
	static final String loggingProperties = "logging.properties";
	protected static Logger logger_test;
	static {
		init();

	}

	@Before
	public void setUp() throws Exception {
		rule2test = createRule();
	}

	@After
	public void tearDown() throws Exception {
		rule2test = null;
	}

	protected static void init() {

		logger_test = Logger.getLogger(AbstractRuleTest.class.getName());
		InputStream in = null;
		try {
			URL url = AbstractRuleTest.class.getClassLoader().getResource(
					loggingProperties);
			System.setProperty("java.util.logging.config.file", url.getFile());
			in = new FileInputStream(new File(url.getFile()));
			Assert.assertNotNull(in);
			LogManager.getLogManager().readConfiguration(in);
			logger_test.log(
					Level.FINE,
					String.format("Logging configuration loaded from %s",
							url.getFile()));

		} catch (Exception x) {
			System.err
					.println("logging configuration failed " + x.getMessage());
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception x) {
			}
		}
	}

	protected abstract IDecisionRule createRule() throws Exception;

	/**
	 * 
	 * @param smiles_and_answer
	 *            {{String smiles, Boolean answer},...}
	 * @throws Exception
	 */
	public void ruleTest(Object[][] smiles_and_answer) throws Exception {

		int success = 0;
		for (int i = 0; i < smiles_and_answer.length; i++) {
			IAtomContainer mol = FunctionalGroups
					.createAtomContainer(smiles_and_answer[i][0].toString());
			if (mol != null) {
				MolAnalyser.analyse(mol);
				Boolean b = (Boolean) smiles_and_answer[i][1];

				boolean r = rule2test.verifyRule(mol);

				try {
					verifyResult(smiles_and_answer[i][0].toString(), mol, r);
				} catch (Exception x) {
					logger_test.warning(x.getMessage());
				}

				if (b.booleanValue() == r) {
					success++;
					logger_test.finer(smiles_and_answer[i][0] + "\tOK");
				} else
					logger_test.severe(smiles_and_answer[i][0].toString());
			} else
				throw new Exception("Null molecule " + smiles_and_answer[i][0]);

		}
		Assert.assertEquals(smiles_and_answer.length, success);
	}

	protected void verifyResult(String smiles, IAtomContainer mol,
			boolean result) throws Exception {

	}

	@Test
	public void test() throws Exception {
		// Assert.fail("Test not implemented");
	}

	@Test
	public void testExampleYes() throws Exception {
		IAtomContainer a = rule2test.getExampleMolecule(true);
		MolAnalyser.analyse(a);
		Assert.assertTrue(rule2test.verifyRule(a));
	}

	@Test
	public void testExampleNo() throws Exception {
		IAtomContainer a = rule2test.getExampleMolecule(false);
		MolAnalyser.analyse(a);
		Assert.assertFalse(rule2test.verifyRule(a));
	}

	@Test
	public void testMultiExampleYes() throws Exception {
		testMultiExample(true);
	}

	@Test
	public void testMultiExampleNo() throws Exception {
		testMultiExample(false);
	}

	protected void testMultiExample(boolean expected) throws Exception {
		URL url = this
				.getClass()
				.getClassLoader()
				.getResource(
						String.format(
								"toxtree/tree/cramer3/test/%s/%s.csv",
								rule2test
										.getClass()
										.getName()
										.replace("toxtree.tree.cramer3.rules.",
												""), expected ? "YES" : "NO"));
		Assert.assertNotNull(url);
		Assert.assertTrue(new File(url.getFile()).exists());
		IIteratingChemObjectReader<IAtomContainer> reader = null;
		logger_test.log(Level.FINE, String.format("Reading %s", url.getFile()));
		reader = FileInputState.getReader(new FileInputStream(url.getFile()),
				".csv");
		int records = 0;
		int errors = 0;

		try {
			while (reader.hasNext()) {
				IAtomContainer mol = reader.next();
				String ruletag = mol.getProperty("Rule");
				// commented out
				if (ruletag != null && ruletag.startsWith("#"))
					continue;
				if (mol.getAtomCount() > 0) {
					MolAnalyser.analyse(mol);
					logger_test.finer("--");
					boolean result = rule2test.verifyRule(mol);
					if (result ^ expected) {
						errors++;
						logger_test
								.log(Level.WARNING,
										String.format(
												"ERROR: %s\tExpected %s obtained %s `%s` SMILES %s",
												rule2test.getClass().getName(),
												expected, result,
												mol.getProperty("FG"),
												mol.getProperty("SMILES")));
					}
				}
				records++;
			}
		} catch (Exception x) {
			throw x;
		} finally {
			try {
				reader.close();
			} catch (Exception x) {
			}
		}
		Assert.assertTrue("No records in file " + url.getFile(), records > 0);
		Assert.assertEquals(records, records - errors);
	}

	public void test(String[] smiles, Boolean expected) throws Exception {
		Object[][] answer = new Object[smiles.length][2];
		for (int i = 0; i < smiles.length; i++) {
			answer[i][0] = smiles[i];
			answer[i][1] = expected;
		}

		ruleTest(answer);
	}
}
