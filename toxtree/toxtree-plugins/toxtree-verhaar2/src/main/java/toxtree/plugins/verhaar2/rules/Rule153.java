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
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;
import verhaar.query.FunctionalGroups;

/**
 * 
 * Alcohols with aromatic moieties, but NOT phenols or benzylic alcohols.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule153 extends RuleOnlyAllowedSubstructures {
	QueryAtomContainer phenol = null;
	QueryAtomContainer benzylAlcohol = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7020871482125337347L;

	public Rule153() {
		super();
		id = "1.5.3";
		setTitle("Be alcohols with aromatic moieties, but NOT phenols or benzylic alcohols");
		addSubstructure(FunctionalGroups.alcohol(false));
		ids.add(FunctionalGroups.C);
		ids.add(FunctionalGroups.CH);
		ids.add(FunctionalGroups.CH2);
		ids.add(FunctionalGroups.CH3);
		phenol =  FunctionalGroups.phenol();
		benzylAlcohol =  FunctionalGroups.benzylAlcohol();
		examples[0] = "c1ccccc1O";  //benzyl alcohol: c1ccccc1CO
		examples[1] = "c1ccccc1CCCCO"; //towa dali e wiarno
		editable = false;
	}
	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol, null);
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {
		logger.info(toString());
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf ==null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    if (mf.isAromatic())  {
	    	logger.debug("Aromatic\tYES");
			if (super.verifyRule(mol,selected)) {
				if (FunctionalGroups.hasGroup(mol,phenol,selected)) {
					logger.debug("Phenol\tYES");
					return false;
				} else if (FunctionalGroups.hasGroup(mol,benzylAlcohol,selected)) {
					logger.debug("Benzylic alcohol\tYES");
					return false;
				} else return true;
			} else {
				logger.debug("Alcohol\tNO");
				return false;
			}
	    } else {
			logger.debug("Aliphatic\tNO");
	    	return false; 
	    }
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleOnlyAllowedSubstructures#isImplemented()
	 */
	public boolean isImplemented() {
		return true;
	}
}
