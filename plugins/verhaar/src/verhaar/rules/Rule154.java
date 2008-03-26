/*
Copyright Nina Jeliazkova (C) 2005-2006  
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
import toxTree.query.FunctionalGroups;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;

public class Rule154 extends RuleOnlyAllowedSubstructures {
	QueryAtomContainer ketone_a_b_unsaturated = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -453162542432372824L;

	public Rule154() {
		super();
		id = "1.5.4";
		setTitle("Be ketones, but not alpha-, beta unsaturated ketones (e.g. 1-butenone or acetophenone)");
		examples[0] = "CC(=O)C1=CC=CC=C1"; //acetophenone
		examples[1] = "O=C(C)CC";
		addSubstructure(FunctionalGroups.ketone());
		ids.add(FunctionalGroups.C);
		ids.add(FunctionalGroups.CH);
		ids.add(FunctionalGroups.CH2);
		ids.add(FunctionalGroups.CH3);		
		ketone_a_b_unsaturated = verhaar.query.FunctionalGroups.ketone_a_b_unsaturated();
		editable = false;
	}
	
	public boolean verifyRule(AtomContainer mol) throws DecisionMethodException {
		//TODO check for a b unsaturated
		if (super.verifyRule(mol)) {
			if (FunctionalGroups.hasGroup(mol,ketone_a_b_unsaturated)) {
				logger.debug("Ketone a,b-unsaturated\tYES");
				return false;
			} else return true;
		} else {
			logger.debug("Ketone\tNO");
			return false;
		}
	}	

}
