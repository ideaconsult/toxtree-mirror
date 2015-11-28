package mutant.test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;
import mutant.rules.SA27_gen;

import org.junit.Test;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.isomorphism.matchers.SymbolQueryAtom;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import ambit2.core.data.MoleculeTools;

public class AromaticityHackTest {
	protected static Logger logger = Logger.getLogger(AromaticityHackTest.class
			.getName());

	@Test
	public void test() throws Exception {

		QueryAtomContainer q = aromaticN();
		// "C1=CC=[N+]([O-])C=C1"
		IAtomContainer a = FunctionalGroups.createAtomContainer(
				"C=1C=CC=2[N+]=CC=CC=2(C=1)", true);
		MolAnalyser.analyse(a);
		//
		List map = FunctionalGroups.getBondMap(a, q, false);
		Assert.assertNotNull(map);
		Assert.assertNotNull(map);
		logger.log(Level.FINE,Integer.toString(map.size()));
		FunctionalGroups.markMaps(a, q, map);

		int aromatic = 0;
		for (int i = 0; i < a.getBondCount(); i++) {
			IBond b = a.getBond(i);
			if (b.getProperty(q.getID()) != null) {
				b.setFlag(CDKConstants.ISAROMATIC, true);
				b.removeProperty(q.getID());
				aromatic++;
				for (int j = 0; j < b.getAtomCount(); j++) {
					b.getAtom(j).setFlag(CDKConstants.ISAROMATIC, true);
					b.getAtom(j).removeProperty(q.getID());
				}

			}
		}
		Assert.assertEquals(6, aromatic);
		logger.log(Level.INFO, FunctionalGroups.mapToString(a).toString());

		SA27_gen rule = new SA27_gen();
		Assert.assertTrue(rule.verifyRule(a));

	}

	protected QueryAtomContainer aromaticN() {
		QueryAtomContainer q = new QueryAtomContainer(
				SilentChemObjectBuilder.getInstance());
		q.setID("Hack");
		IAtom n = MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),
				Elements.NITROGEN);
		n.setCharge(+1.0);
		SymbolQueryAtom qn = new SymbolQueryAtom(n);
		q.addAtom(qn);
		SymbolQueryAtom prev = qn;
		IBond.Order[] border = new IBond.Order[] {
				CDKConstants.BONDORDER_SINGLE, CDKConstants.BONDORDER_DOUBLE };
		for (int i = 0; i < 5; i++) {
			SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(
					SilentChemObjectBuilder.getInstance(), Elements.CARBON));
			q.addAtom(c);
			q.addBond(new OrderQueryBond(prev, c, border[i % 2], q.getBuilder()));
			prev = c;
		}
		q.addBond(new OrderQueryBond(prev, qn, border[5 % 2], q.getBuilder()));
		return q;
	}
}
