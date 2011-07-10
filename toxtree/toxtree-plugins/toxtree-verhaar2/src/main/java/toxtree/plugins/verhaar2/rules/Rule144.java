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

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;

/**
 * Polycyclic compounds that are unsubstituted or substituted with acyclic structures containing only C&H or complying with {@link Rule141}.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule144 extends Rule143 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2147762024464054397L;

	public Rule144() {
		super();
		id = "1.4.4";
		setTitle("Be polycyclic compounds that are unsubstituted or substituted with acyclic structures containing only C&H or complying with rule 1.4.1");
		explanation = new StringBuffer();
		explanation.append("Note that compounds containing benzylic halogens do NOT comply with rule 1.4.1, and thus cannot be considered ");
		explanation.append("narcotic chemicals. Note also that many of these polycyclic compounds, besides working as narcotics in acute toxicity experiments,");
		explanation.append("have chronic toxicities based on specific modes of action");
		examples[0] = "c1cc(cc(C=CCCl)c1c2cc(ccc2C)C(C)C)C";
		examples[1] = "c1cc(cc(C=CCl)c1c2cc(ccc2C)C(C)C)C";
		
		editable = false;
	}
	public boolean isImplemented() {
		return true;
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleRingSubstituents#hasRingsToProcess(org.openscience.cdk.interfaces.AtomContainer)
	 */
	protected IRingSet hasRingsToProcess(IAtomContainer mol)
			throws DecisionMethodException {
	
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    
	    IRingSet rings = mf.getRingset();
	    if (rings == null) {
	    	logger.info("Polycyclic\tNO");
	    	return null;
	    } else if (rings.getAtomContainerCount() > 1) {
	    	logger.info("Polycyclic\tYES\t",rings.getAtomContainerCount());
	    	return rings; //monocarbocyclic
	    } else {
	    	logger.info("Polycyclic\tNO");
	    	return null;
	    }

	}

}
