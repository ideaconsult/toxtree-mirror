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
package toxTree.tree.rules;

import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;

/**
 * A rule to analyze ring substituents
 * 
 * @author Nina Jeliazkova <b>Modified</b> 2005-8-17
 */
public class RuleRingAllowedSubstituents extends RuleRingSubstituents {
	private static final long serialVersionUID = -7904081573533596701L;

	public RuleRingAllowedSubstituents() throws Exception {
		super();
	}

	@Override
	public boolean isImplemented() {
		return true;
	}

	@Override
	public boolean substituentIsAllowed(IAtomContainer a, int[] place)
			throws DecisionMethodException {
		return true;
	}

	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol, null);
	}

	/**
	 * returns true if only allowed substituents are found
	 */
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		logger.finer(toString());
		IRingSet rs = hasRingsToProcess(mol);
		if (rs == null)
			return false;

		FunctionalGroups.markCHn(mol);
		// if entire structure has only allowed groups, return true (can't have
		// other groups as substituents)
		if (FunctionalGroups
				.hasOnlyTheseGroups(mol, query, ids, true, selected)) {
			logger.finer("The entire structure consists only of allowed groups");
			return true;
		}
		// otherwise some of the forbidden groups can be in-ring, so
		// substituents can be fine
		// iterating over all rings in the ringset
		IRing r = null;
		for (int i = 0; i < rs.getAtomContainerCount(); i++) {
			r = (IRing) rs.getAtomContainer(i);
			if (!analyze(r))
				continue;
			logger.finer("Ring\t" + (i + 1));

			// new atomcontainer with ring atoms/bonds deleted
			IAtomContainer mc = FunctionalGroups.cloneDiscardRingAtomAndBonds(
					mol, r);
			logger.fine("\tmol atoms\t" + mc.getAtomCount());

			IAtomContainerSet s = ConnectivityChecker
					.partitionIntoMolecules(mc);
			logger.fine("partitions\t" + s.getAtomContainerCount());
			for (int k = 0; k < s.getAtomContainerCount(); k++) {
				IAtomContainer m = s.getAtomContainer(k);
				if (m != null) {
					if ((m.getAtomCount() == 1)
							&& (m.getAtom(0).getSymbol().equals("H")))
						continue;
					logger.finer("Partition\t" + (k + 1));
					if (!substituentIsAllowed(m, null)) {
						logger.finer(ERR_PRECONDITION_FAILED);
						return false;
					}
					if (!FunctionalGroups.hasMarkedOnlyTheseGroups(m, ids,
							selected, null)) {
						logger.finer(allowedSubstituents());
						logger.finer(CONDITION_FAILED);
						// logger.debug(FunctionalGroups.mapToString(m).toString());
						return false;
					}
				}
			}
		}
		return true; // no forbidden substructures found

	}

}
