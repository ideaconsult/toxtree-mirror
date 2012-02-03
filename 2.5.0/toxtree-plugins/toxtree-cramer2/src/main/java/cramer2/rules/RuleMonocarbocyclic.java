/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*//**
 * Created on 2005-5-2

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree.rules
 * Filename: RuleMonocarbocyclic.java
 */
package cramer2.rules;


import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleRingAllowedSubstituents;

/**
 * Rule 24 of the Cramer scheme (see {@link cramer2.CramerRulesExtendedExtended})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleMonocarbocyclic extends RuleRingAllowedSubstituents {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -625423309210659129L;

    /**
	 * Constructor
	 * 
	 */
	public RuleMonocarbocyclic() {
		super();
		addSubstructure(FunctionalGroups.alcohol(true));
		addSubstructure(FunctionalGroups.aldehyde());
		addSubstructure(FunctionalGroups.acyclic_acetal());
		addSubstructure(FunctionalGroups.sidechain_ketone());
		addSubstructure(FunctionalGroups.carboxylicAcid());
		addSubstructure(FunctionalGroups.ester());
		addSubstructure(FunctionalGroups.sulphonate(
				new String[] {"Na","K","Ca"}));
		addSubstructure(FunctionalGroups.sulphonate(null,false));
		addSubstructure(FunctionalGroups.sulphamate(
				new String[] {"Na","K","Ca"}));
		addSubstructure(FunctionalGroups.sulphamate(null));
		
		id = "24";
		title = "Monocarbocyclic with simple substituents";
		explanation.append("<html>");
		explanation.append("Is the substance monocarbocyclic (excluding cyclopropane or cyclobutane and their derivatives) with ring or <i>aliphatic</i> (A) side chains,");
		explanation.append("unsubstituted or containing only alcohol, aldehyde, side-chain ketone, acid, ester, or Na, K or Ca sulphonate or sulphamate, or acyclic acetal or ketal?");
		explanation.append("/<html>");
		examples[0] = "O=C1CCCCC1";
		examples[1] = "CC(=O)C1CCCCC1";
		editable = false;
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleRingOtherThanAllowedSubstituents#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return true;
	}
	@Override
	protected IRingSet hasRingsToProcess(IAtomContainer  mol) throws DecisionMethodException {
		logger.info(toString());
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    
	    IRingSet rings = mf.getRingset();
	    if (rings == null) return null;
	    if (rings.getAtomContainerCount() > 1) {
	    	logger.info("Not monocarbocyclic, >1 rings found",rings.getAtomContainerCount());
	    	return null; //monocarbocyclic
	    }
	    IRing r = (IRing) rings.getAtomContainer(0);
	    if (r.getAtomCount() < 5 ) return null;
	    Object o = r.getProperty(MolAnalyser.HETEROCYCLIC);
	    if ((o!=null) && ((Boolean) o).booleanValue()) {
	    	logger.info("Heteroring found");
	    	return null;
	    }
		else {
			logger.info("Monocarbocyclic ring found");
			return rings;
		}
	    
	    
	}	
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleRingAllowedSubstituents#verifyRule(org.openscience.cdk.AtomContainer)
	 */
	@Override
	public boolean verifyRule(IAtomContainer molecule) throws DecisionMethodException {
		Object o = molecule.getProperty(MolFlags.PARENT);
		IAtomContainer mol = molecule;
		if ((o != null) && (molecule instanceof IAtomContainer)) {
			logger.debug("Parent compound found, will continue analyzing the parent");
			mol = (IAtomContainer) o;
		}
		return super.verifyRule(mol);
	}

}
