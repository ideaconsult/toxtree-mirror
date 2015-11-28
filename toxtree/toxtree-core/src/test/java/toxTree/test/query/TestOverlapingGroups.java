/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

 */
package toxTree.test.query;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;

public class TestOverlapingGroups {

	public void doTest(String smiles) throws Exception {
		// 1N,2N,3N,5N,6N,7N,16N,17N,19Y,20Y,21N,18N
		QueryAtomContainer q1 = FunctionalGroups.ester();
		IAtomContainer mol = (IAtomContainer) FunctionalGroups
				.createAtomContainer(smiles);

		try {
			MolAnalyser.analyse(mol);
		} catch (Exception x) {
			throw x;
		}
		List list = FunctionalGroups.getUniqueBondMap(mol, q1, false);
		FunctionalGroups.markMaps(mol, q1, list);
		QueryAtomContainer q2 = FunctionalGroups.polyoxyethylene(1);
		list = FunctionalGroups.getUniqueBondMap(mol, q2, false);
		FunctionalGroups.markMaps(mol, q2, list);

		ArrayList ids = new ArrayList();
		ids.add(q1.getID());
		ids.add(q2.getID());

		ids.add(FunctionalGroups.C);
		ids.add(FunctionalGroups.CH);
		ids.add(FunctionalGroups.CH2);
		ids.add(FunctionalGroups.CH3);
		FunctionalGroups.markCHn(mol);

		boolean ok = FunctionalGroups.hasMarkedOnlyTheseGroups(mol, ids);
		Assert.assertTrue(ok);
	}

	@Test
	public void test1() throws Exception {
		doTest("COCCOC(=O)C=C");
	}

	@Test
	public void test2() throws Exception {
		doTest("C=CC(=O)OCCOC");
	}
}
