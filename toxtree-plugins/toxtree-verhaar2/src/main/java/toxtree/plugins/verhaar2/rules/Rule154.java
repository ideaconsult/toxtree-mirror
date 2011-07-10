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
package toxtree.plugins.verhaar2.rules;


import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;
import verhaar.query.FunctionalGroups;

/**
 * 
 * Ketones, but not alpha-, beta unsaturated ketones (e.g. 1-butenone or acetophenone).
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
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
		ketone_a_b_unsaturated =  FunctionalGroups.ketone_a_b_unsaturated();
		editable = false;
	}
	
	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol, null);
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {
		//TODO check for a b unsaturated
		if (super.verifyRule(mol,selected)) {
			if (FunctionalGroups.hasGroup(mol,ketone_a_b_unsaturated,selected)) {
				logger.debug("Ketone a,b-unsaturated\tYES");
				return false;
			} else return true;
		} else {
			logger.debug("Ketone\tNO");
			return false;
		}
	}	

}
