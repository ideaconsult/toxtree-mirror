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
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;
import verhaar.query.FunctionalGroups;

/**
 * 
 * Linear ethers or monocyclic mono ethers, but not epoxides or peroxides.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
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
		epoxide =  FunctionalGroups.epoxide();
		peroxide =  FunctionalGroups.peroxide();
		
		examples[0] = "C(C)CCC(COOC)C";  //epoxide C1OC1 , peroxide  X-O-O-X
		examples[1] = "C(C)CCC(COC)C";
		editable = false;
	}
	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		return verifyRule(mol,null);
	}
	@Override
	public boolean verifyRule(IAtomContainer mol,IAtomContainer selected) throws DecisionMethodException {
		FunctionalGroups.markCHn(mol);
		if (super.verifyRule(mol,selected)) {
		    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		    if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
		    IRingSet rings = mf.getRingset();
		    if (rings != null) 
		    	if (rings.getAtomContainerCount() >1) {
			    	logger.info("Monocyclic\tNO");
			    	return false;
		    	} else {
		    		logger.info("Monocyclic\tYES");
		    		logger.info("Verify if mono-ether\tNOT IMPLEMENTED");
		    		
		    		if (FunctionalGroups.hasGroup(mol,epoxide,selected)) {
		    			logger.info("Epoxide\tYES");
		    			return false;
		    		}
		    	}
    		if (FunctionalGroups.hasGroup(mol,peroxide,selected)) {
    			logger.info("Peroxide\tYES");
    			return false;
    		} else return true;
		    
		} else return false;
	}
	public boolean isImplemented() {
		return true;
	}
}
