package mutant.test;

import java.util.List;

import junit.framework.TestCase;
import mutant.rules.SA27;

import org.openscience.cdk.Atom;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.isomorphism.matchers.SymbolQueryAtom;

import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;

public class AromaticityHackTest extends TestCase {
	
	public void test() {
		try {
			QueryAtomContainer q = aromaticN();
			//"C1=CC=[N+]([O-])C=C1"
			IAtomContainer a = FunctionalGroups.createAtomContainer("C=1C=CC=2[N+]=CC=CC=2(C=1)",true);
			MolAnalyser.analyse(a);
			//boolean aromatic = HueckelAromaticityDetector.detectAromaticity(a);
			//assertTrue(aromatic);
			List map = FunctionalGroups.getBondMap(a,q,false);
			assertNotNull(map);
			assertNotNull(map);
			System.out.println(map.size());
			FunctionalGroups.markMaps(a,q,map);

			int aromatic =0;
			for (int i=0; i < a.getBondCount();i++) {
				IBond b = a.getBond(i);
				if (b.getProperty(q.getID()) != null) {
					b.setFlag(CDKConstants.ISAROMATIC, true);
					b.removeProperty(q.getID());
					aromatic++;
					for (int j=0; j < b.getAtomCount();j++) {
						b.getAtom(j).setFlag(CDKConstants.ISAROMATIC, true);
						b.getAtom(j).removeProperty(q.getID());
					}	

				}
			}
			assertEquals(6,aromatic);
			System.out.println(FunctionalGroups.mapToString(a));
			
			SA27 rule = new SA27();
			assertTrue(rule.verifyRule(a));
		} catch (Exception x ) {
			x.printStackTrace();
			fail(x.getMessage());
		}

	}
	protected QueryAtomContainer aromaticN() {
		QueryAtomContainer q = new QueryAtomContainer();
		q.setID("Hack");
		IAtom n = DefaultChemObjectBuilder.getInstance().newAtom(Elements.NITROGEN);
		n.setCharge(+1.0);
		SymbolQueryAtom qn = new SymbolQueryAtom(n);
		q.addAtom(qn);
		SymbolQueryAtom prev = qn;
		IBond.Order[] border = new IBond.Order[] {CDKConstants.BONDORDER_SINGLE,CDKConstants.BONDORDER_DOUBLE}; 
		for (int i=0; i < 5; i++) {
			SymbolQueryAtom c = new SymbolQueryAtom(DefaultChemObjectBuilder.getInstance().newAtom(Elements.CARBON));
			q.addAtom(c);
			q.addBond(
					new OrderQueryBond(prev,c,border[i % 2])
					);
			prev = c;
		}
		q.addBond(
				new OrderQueryBond(prev,qn,border[5 % 2])
				);		
		return q;
	}
}
