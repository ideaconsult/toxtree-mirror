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
import toxTree.tree.rules.DefaultAlertCounter;
import toxTree.tree.rules.IAlertCounter;
import toxTree.tree.rules.RuleRingAllowedSubstituents;
import verhaar.query.FunctionalGroups;

/**
 * 
 * Monocyclic compounds that are unsubstituted or substituted with acyclic structures containing only C&H or complying with rule {@link Rule141}.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule143 extends RuleRingAllowedSubstituents  implements IAlertCounter {
	protected IAlertCounter alertsCounter;
	protected QueryAtomContainer x ;
	protected String[] halogens = {"Cl","F","Br","I"}; 
	/**
	 * 
	 */
	private static final long serialVersionUID = 4746595221282769930L;

	public Rule143() {
		super();
		alertsCounter = new DefaultAlertCounter();
		id = "1.4.3";
		setTitle("Be monocyclic compounds that are unsubstituted or substituted with acyclic structures containing only C&H or complying with rule 1.4.1");
		explanation.append(
				"Note that compounds containing benzylic halogens do NOT comply with rule 1.4.1, and thus cannot be considered narcotic chemicals"
				);
		examples[0] = "c1ccccc1CCCC=CCCl";
		examples[1] = "c1ccccc1CCCCCCl"; 
		editable = false;
		x =  FunctionalGroups.halogenAtBetaFromUnsaturation(halogens);
		ids.add(FunctionalGroups.C);
		ids.add(FunctionalGroups.CH);
		ids.add(FunctionalGroups.CH2);
		ids.add(FunctionalGroups.CH3);
		addSubstructure( FunctionalGroups.halogen(halogens));
	}
	
	protected IRingSet hasRingsToProcess(IAtomContainer  mol) throws DecisionMethodException {
		
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    FunctionalGroups.markCHn(mol);
	    IRingSet rings = mf.getRingset();
	    if (rings == null) return null;
	    if (rings.getAtomContainerCount() > 1) {
	    	logger.info("Monocyclic\tNO\t",rings.getAtomContainerCount());
	    	return null; //monocyclic
	    } else {
	    	logger.info("Monocyclic\tYES");
	    	return rings;
	    }
	    
	}	
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleRingAllowedSubstituents#substituentIsAllowed(org.openscience.cdk.interfaces.AtomContainer, int[])
	 */
	public boolean substituentIsAllowed(IAtomContainer a, int[] place)
			throws DecisionMethodException {
		// TODO Auto-generated method stub
		return super.substituentIsAllowed(a, place);
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleRingAllowedSubstituents#verifyRule(org.openscience.cdk.interfaces.AtomContainer)
	 */
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		logger.info(toString());
		if (FunctionalGroups.hasGroup(mol,x)) {
			logger.info("Do not comply with rule 1.4.1");
			return false;
		} else {
			FunctionalGroups.markCHn(mol);
			if (super.verifyRule(mol)) {
				incrementCounter(mol);
				return true;
			} else return false;
		}
		
	}
	public boolean isImplemented() {
		return true;
	}
	@Override
	public void incrementCounter(IAtomContainer mol) {
		alertsCounter.incrementCounter(mol);
		
	}
	@Override
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append(alertsCounter.getImplementationDetails());
		return b.toString();
	}
}
