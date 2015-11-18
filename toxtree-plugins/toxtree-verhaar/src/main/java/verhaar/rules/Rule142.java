/*
Copyright Nina Jeliazkova (C) 2005-2011  
Contact: jeliazkova.nina@gmail.com

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

 */
package verhaar.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.query.QueryAtomContainers;
import verhaar.query.FunctionalGroups;
import verhaar.rules.helper.RuleOnlyAllowedSubstructuresCounter;

/**
 * Monocyclic compounds substituted with halogens.
 * 
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com <b>Modified</b> July 12,
 *         2011
 */
public class Rule142 extends RuleOnlyAllowedSubstructuresCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8731063548318963732L;

	public Rule142() throws Exception {
		super();
		id = "1.4.2";
		setTitle("Be monocyclic compounds substituted with halogens");
		examples[1] = "c1ccccc1Cl";
		examples[0] = "c1ccc(cc1)c2ccc(cc2)Cl";
		editable = false;

		// TODO verify if only directly connected halogens are allowed
		/*
		 * ids.add(FunctionalGroups.C); ids.add(FunctionalGroups.CH);
		 * ids.add(FunctionalGroups.CH2); ids.add(FunctionalGroups.CH3);
		 */
	}

	@Override
	protected QueryAtomContainers initQuery() throws Exception {
		query = super.initQuery();
		String[] halogens = { "Cl", "Br", "F", "I" };
		for (int i = 0; i < halogens.length; i++)
			addSubstructure(FunctionalGroups.ringSubstituted(halogens[i]));
		addSubstructure(FunctionalGroups.ringSubstituted(null));
		return query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * toxTree.tree.rules.RuleRingSubstituents#hasRingsToProcess(org.openscience
	 * .cdk.interfaces.AtomContainer)
	 */
	protected IRingSet hasRingsToProcess(IAtomContainer mol)
			throws DecisionMethodException {
		// RingSet rings = super.hasRingsToProcess(mol);
		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		if (mf == null)
			throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);

		IRingSet rings = mf.getRingset();
		if ((rings == null)) {
			logger.fine("Acyclic structure");
			return null;
		} else if (rings.getAtomContainerCount() == 1) {
			logger.fine("Monocyclic\tYES");
			return rings;
		} else {
			logger.fine("More than one ring\t" + rings.getAtomContainerCount());
			return null;
		}
	}

	public boolean isImplemented() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * toxTree.tree.rules.RuleOnlyAllowedSubstructures#verifyRule(org.openscience
	 * .cdk.interfaces.AtomContainer)
	 */
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		logger.finer(toString());
		if (hasRingsToProcess(mol) != null)
			return super.verifyRule(mol);
		else
			return false;
	}

}
