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
import toxTree.tree.rules.RuleRingAllowedSubstituents;

public class Rule143 extends RuleRingAllowedSubstituents {
	protected QueryAtomContainer x ;
	protected String[] halogens = {"Cl","F","Br","I"}; 
	/**
	 * 
	 */
	private static final long serialVersionUID = 4746595221282769930L;

	public Rule143() {
		super();
		id = "1.4.3";
		setTitle("Be monocyclic compounds that are unsubstituted or substituted with acyclic structures containing only C&H or complying with rule 1.4.1");
		explanation.append(
				"Note that compounds containing benzylic halogens do NOT comply with rule 1.4.1, and thus cannot be considered narcotic chemicals"
				);
		examples[0] = "c1ccccc1CCCC=CCCl";
		examples[1] = "c1ccccc1CCCCCCl"; 
		editable = false;
		x = verhaar.query.FunctionalGroups.halogenAtBetaFromUnsaturation(halogens);
		ids.add(FunctionalGroups.C);
		ids.add(FunctionalGroups.CH);
		ids.add(FunctionalGroups.CH2);
		ids.add(FunctionalGroups.CH3);
		addSubstructure(verhaar.query.FunctionalGroups.halogen(halogens));
	}
	
	protected RingSet hasRingsToProcess(org.openscience.cdk.interfaces.AtomContainer  mol) throws DecisionMethodException {
		
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    FunctionalGroups.markCHn(mol);
	    RingSet rings = mf.getRingset();
	    if (rings == null) return null;
	    if (rings.size() > 1) {
	    	logger.info("Monocyclic\tNO\t",rings.size());
	    	return null; //monocyclic
	    } else {
	    	logger.info("Monocyclic\tYES");
	    	return rings;
	    }
	    
	}	
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleRingAllowedSubstituents#substituentIsAllowed(org.openscience.cdk.interfaces.AtomContainer, int[])
	 */
	public boolean substituentIsAllowed(AtomContainer a, int[] place)
			throws DecisionMethodException {
		// TODO Auto-generated method stub
		return super.substituentIsAllowed(a, place);
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleRingAllowedSubstituents#verifyRule(org.openscience.cdk.interfaces.AtomContainer)
	 */
	public boolean verifyRule(AtomContainer mol) throws DecisionMethodException {
		logger.info(toString());
		if (FunctionalGroups.hasGroup(mol,x)) {
			logger.info("Do not comply with rule 1.4.1");
			return false;
		} else {
			FunctionalGroups.markCHn(mol);
			return super.verifyRule(mol);
		}
		
	}
	public boolean isImplemented() {
		return true;
	}

}
