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
import org.openscience.cdk.interfaces.RingSet;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;

public class Rule151 extends RuleOnlyAllowedSubstructures {
	QueryAtomContainer epoxide = null;
	QueryAtomContainer peroxide = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5360285168848609301L;

	public Rule151() {
		super();
		id = "1.5.1";
		setTitle("Be linear ethers or monocyclic mono ethers, but not epoxides or peroxides");
		addSubstructure(FunctionalGroups.ether());
		ids.add(FunctionalGroups.C);
		ids.add(FunctionalGroups.CH);
		ids.add(FunctionalGroups.CH2);
		ids.add(FunctionalGroups.CH3);
		epoxide = verhaar.query.FunctionalGroups.epoxide();
		peroxide = verhaar.query.FunctionalGroups.peroxide();
		
		examples[0] = "C(C)CCC(COOC)C";  //epoxide C1OC1 , peroxide  X-O-O-X
		examples[1] = "C(C)CCC(COC)C";
		editable = false;
	}

	public boolean verifyRule(AtomContainer mol) throws DecisionMethodException {
		FunctionalGroups.markCHn(mol);
		if (super.verifyRule(mol)) {
		    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		    if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
		    RingSet rings = mf.getRingset();
		    if (rings != null) 
		    	if (rings.size() >1) {
			    	logger.info("Monocyclic\tNO");
			    	return false;
		    	} else {
		    		logger.info("Monocyclic\tYES");
		    		logger.info("Verify if mono-ether\tNOT IMPLEMENTED");
		    		
		    		if (FunctionalGroups.hasGroup(mol,epoxide)) {
		    			logger.info("Epoxide\tYES");
		    			return false;
		    		}
		    	}
    		if (FunctionalGroups.hasGroup(mol,peroxide)) {
    			logger.info("Peroxide\tYES");
    			return false;
    		} else return true;
		    
		} else return false;
	}
	public boolean isImplemented() {
		return true;
	}
}
