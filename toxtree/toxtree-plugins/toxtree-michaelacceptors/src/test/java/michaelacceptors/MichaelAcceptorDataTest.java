package michaelacceptors;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;
import michaelacceptors.categories.CategoryMichaelAcceptor;

import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRuleList;

public class MichaelAcceptorDataTest {

	protected MichaelAcceptorRules cr = null;
	static protected Logger logger = Logger
			.getLogger(MichaelAcceptorDataTest.class.getName());

	@Before
	public void setup() throws Exception {
		cr = new MichaelAcceptorRules();
		cr.setResiduesIDVisible(false);
	}

	@Test
	public void testHasUnreachableRules() {
		IDecisionRuleList unvisited = cr.hasUnreachableRules();
		if ((unvisited == null) || (unvisited.size() == 0))
			Assert.assertTrue(true);
		else {
			Assert.fail(String.format("Unvisited rules: %d", unvisited));
		}

	}

	@Test
	public void testPredictions() throws Exception {
		IDecisionResult r = cr.createDecisionResult();
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"toxtree/michaelacceptors/LLNA_3D.sdf");
		IIteratingChemObjectReader reader = new IteratingSDFReader(in,
				SilentChemObjectBuilder.getInstance());
		int tp = 0;
		int tn = 0;
		int fp = 0;
		int fn = 0;
		CategoryMichaelAcceptor ma = new CategoryMichaelAcceptor();
		while (reader.hasNext()) {
			Object o = reader.next();
			Assert.assertTrue(o instanceof IAtomContainer);
			IAtomContainer mol = (IAtomContainer) o;
			r.classify(mol);
			r.assignResult(mol);

			if ("MA".equals(mol.getProperty("ReactionDomain"))) {
				if (ma.toString().equals(
						mol.getProperty(r.getResultPropertyNames()[0])
								.toString())) {
					tp++;

					logger.log(Level.FINE, String.format(
							"%s\t\"%s\"\t%s\t%s\t\"%s\"", mol
									.getProperty("CasRN"), mol
									.getProperty("Names"), mol
									.getProperty("SMILES"), mol
									.getProperty("ReactionDomain"), mol
									.getProperty(r.getResultPropertyNames()[0])
									.toString(), r.explain(false)));
				} else {

					fn++;
				}
			} else if (ma.toString().equals(
					mol.getProperty(r.getResultPropertyNames()[0]).toString())) {
				fp++;
				logger.log(Level.FINE, String.format(
						"%s\t\"%s\"\t%s\t%s\t\"%s\"", mol.getProperty("CasRN"),
						mol.getProperty("Names"), mol.getProperty("SMILES"),
						mol.getProperty("ReactionDomain"),
						mol.getProperty(r.getResultPropertyNames()[0])
								.toString(), r.explain(false)));

			} else {
				tn++;

			}
		}
		logger.log(Level.FINE,
				String.format("TP=%d\tTN=%d\tFP=%d\tFN=%d", tp, tn, fp, fn));
		reader.close();
	}

}
