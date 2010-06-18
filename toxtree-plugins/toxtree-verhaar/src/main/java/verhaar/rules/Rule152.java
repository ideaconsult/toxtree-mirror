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
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;

/**
 * 
 * Aliphatic alcohols but not allylic/propargylic alcohols.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule152 extends RuleOnlyAllowedSubstructures {
	QueryAtomContainer allyl = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8890813695847536800L;

	public Rule152() {
		super();
		id = "1.5.2";
		setTitle("Be aliphatic alcohols but not allylic/propargylic alcohols");
		addSubstructure(FunctionalGroups.alcohol(true));
		ids.add(FunctionalGroups.C);
		ids.add(FunctionalGroups.CH);
		ids.add(FunctionalGroups.CH2);
		ids.add(FunctionalGroups.CH3);		
		allyl = FunctionalGroups.createQuery("[H]C([H])=C([H])C([H])([H])","allyl");
		examples[0] = "C#CCO"; //propargyl alcohol ;  H2C=CH-CH2OH  - allyl alcohol   
		examples[1] = "CCCCC(O)CC";
		editable = false;
	}
	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol,null);
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {
		logger.info(toString());
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf ==null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    if (mf.isAliphatic())  {
	    	logger.debug("Aliphatic\tYES");
			if (super.verifyRule(mol,selected)) {
				if (mf.isAcetylenic()) {
					logger.debug("Propargylic alcohol\tYES");
					return false;
				} else if (FunctionalGroups.hasGroup(mol,allyl,selected)) {
					logger.debug("Allylic alcohol\tYES");
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
