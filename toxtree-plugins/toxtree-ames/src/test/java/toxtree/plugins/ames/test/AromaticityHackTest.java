/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

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

package toxtree.plugins.ames.test;

import java.util.List;

import junit.framework.TestCase;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.isomorphism.matchers.SymbolQueryAtom;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxtree.plugins.ames.rules.SA27_gen;
import ambit2.core.data.MoleculeTools;

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
			
			SA27_gen rule = new SA27_gen();
			assertTrue(rule.verifyRule(a));
		} catch (Exception x ) {
			x.printStackTrace();
			fail(x.getMessage());
		}

	}
	protected QueryAtomContainer aromaticN() {
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		QueryAtomContainer q = new QueryAtomContainer(builder);
		q.setID("Hack");
		IAtom n = MoleculeTools.newAtom(builder,Elements.NITROGEN);
		n.setCharge(+1.0);
		SymbolQueryAtom qn = new SymbolQueryAtom(n);
		q.addAtom(qn);
		SymbolQueryAtom prev = qn;
		IBond.Order[] border = new IBond.Order[] {CDKConstants.BONDORDER_SINGLE,CDKConstants.BONDORDER_DOUBLE}; 
		for (int i=0; i < 5; i++) {
			SymbolQueryAtom c = new SymbolQueryAtom(MoleculeTools.newAtom(builder,Elements.CARBON));
			q.addAtom(c);
			q.addBond(
					new OrderQueryBond(prev,c,border[i % 2],builder)
					);
			prev = c;
		}
		q.addBond(
				new OrderQueryBond(prev,qn,border[5 % 2],builder)
				);		
		return q;
	}
}
