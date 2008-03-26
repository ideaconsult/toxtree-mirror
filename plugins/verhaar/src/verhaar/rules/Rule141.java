/*
Copyright Ideaconsult (C) 2005-2006  
Contact: nina@acad.bg

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


import org.openscience.cdk.interfaces.AtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.cramer.RuleIsOpenChain;
import verhaar.query.FunctionalGroups;

public class Rule141 extends RuleIsOpenChain {
	protected QueryAtomContainer x;
	protected static transient String[] halogens = {"Cl","Br","F","I"};
	/**
	 * 
	 */
	private static final long serialVersionUID = 6769690721536931384L;

	public Rule141() {
		super();
		id = "1.4.1";
		setTitle("Be acyclic compound NOT containing halogen at beta-positions from unsaturations (e.g. allylic / propargilyc halogens)");
		setExplanation("Be acyclic compound NOT containing halogen at beta-positions from unsaturations (e.g. allylic / propargilyc halogens)");
		examples[0] = "CCCCC=CC(Cl)CCC";
		examples[1] = "CCCCC=C(Cl)CCC";
		editable = false;
		x = FunctionalGroups.halogenAtBetaFromUnsaturation(halogens);
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleOpenChain#verifyRule(org.openscience.cdk.interfaces.AtomContainer)
	 */
	public boolean verifyRule(AtomContainer mol) throws DecisionMethodException {
		logger.info(toString());
		if (super.verifyRule(mol)) {  //acyclic
			return !FunctionalGroups.hasGroup(mol,x);
		} else return false; 
	}
	public boolean isImplemented() {
		return true;
	}

}
