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


import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import verhaar.query.FunctionalGroups;

/**
 * 
 * Halogenated compounds that comply with rule {@link Rule15} but not alpha-, beta- halogen substituted compounds.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule171 extends Rule151 {
	QueryAtomContainer halogenAtAlphaUnsaturated = null;
	QueryAtomContainer halogenAtBetaUnsaturated = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5540666661281282664L;

	public Rule171() {
		super();
		addSubstructure(verhaar.query.FunctionalGroups.halogen());
		id = "1.7.1";
		setTitle("Are halogenated compounds that comply with rule Q.1.5 but not alpha-, beta- halogen substituted compounds");
		explanation = new StringBuffer();
		explanation.append("N.B.: It may be possible that some compounds, that are known to be more toxic than baseline ");
		explanation.append("toxicity, do classify as class 1 compounds. If this be the case, DO NOT treat these compounds as");
		explanation.append("baseline toxicants. An example of this would be lindane, or 2-hexachlorocyclohexane, which is much more toxic than the other");
		explanation.append(" hexaehlorocyclohexanes");
		examples[1] = "O(C)CC(C)Cl";
		examples[0] = "N(C)CC(C)Cl";
		editable = false;
		
		String[] h = new String[]{"Cl","F","Br","I"};
		halogenAtAlphaUnsaturated = FunctionalGroups.halogenAtAlphaFromUnsaturation(h);
		halogenAtBetaUnsaturated = FunctionalGroups.halogenAtBetaFromUnsaturation(h);
		
	}

	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol, null);
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {

		if (super.verifyRule(mol,selected)) 
			if ((FunctionalGroups.hasGroup(mol,halogenAtAlphaUnsaturated,selected)) || 
				(FunctionalGroups.hasGroup(mol,halogenAtBetaUnsaturated,selected))) {
				logger.debug("alpha-, beta- halogen substituted compounds");
				return false;
			} else return true;
		else return false;
	}
}
