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

*/
package toxTree.tree.rules;


import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.smiles.SmilesGenerator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;

/**
 * A rule to analyze ring substituents
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-17
 */
public abstract class RuleRingOtherThanAllowedSubstituents extends RuleRingSubstituents {


	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8494351305392210977L;

	/**
	 * 
	 */
	public RuleRingOtherThanAllowedSubstituents() {
		super();

	}

	/**
	 * return true if other than listed substructures are found in a ring substituent
	 * @see toxTree.core.IDecisionRule#verifyRule(IAtomContainer)
	 */
	@Override
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.info(toString());
		IRingSet rs = hasRingsToProcess(mol);
		if (rs == null) return false;
		
	    FunctionalGroups.markCHn(mol);	    
	    //if entire structure has only allowed groups, return false (can't have other groups as substituents)
	    //if (FunctionalGroups.hasOnlyTheseGroups(mol,query,ids)) return false; yes, but some other conditions may fail ...
	    FunctionalGroups.hasOnlyTheseGroups(mol,query,ids,true); //just using it to mark groups
	    //otherwise some of the forbidden groups can be in-ring, so substituents can be fine	
	    //iterating over all rings in the ringset
	    IRing r =null;
	    //logger.debug("Rings\t",rs.size());
	    for (int i=0; i < rs.getAtomContainerCount(); i++) {
	        r = (IRing) rs.getAtomContainer(i);
	        if (!analyze(r)) continue;
	        logger.debug("Ring\t",(i+1));
	        
	        //new atomcontainer with ring atoms/bonds deleted
	        IAtomContainer mc = FunctionalGroups.cloneDiscardRingAtomAndBonds(mol,r);	        
			
		    SmilesGenerator gen = new SmilesGenerator(true);
		    
		    IMoleculeSet  s = ConnectivityChecker.partitionIntoMolecules(mc);
			logger.debug("Substituents\t",s.getMoleculeCount());
			for (int k = 0; k < s.getMoleculeCount(); k++) {
				IMolecule m = s.getMolecule(k);
			    if (m!=null) {
				    if ((m.getAtomCount() == 1) && (m.getAtom(0).getSymbol().equals("H"))) continue;
				    if (logger.isDebugEnabled()) {
				    	logger.debug("Ring substituent\t",(k+1));
				    	logger.debug(gen.createSMILES(m));
				    }
				    if (!substituentIsAllowed(m,null)) {
				    	logger.debug(ERR_PRECONDITION_FAILED);
				    	return true;				    	
				    }
	
				    logger.debug(FunctionalGroups.mapToString(m));
				    if (!FunctionalGroups.hasMarkedOnlyTheseGroups(m,ids)) {
				    	logger.debug(allowedSubstituents());
				    	logger.debug(CONDITION_FAILED);
				    	logger.debug(FunctionalGroups.mapToString(m).toString());
				    	return true;
				    }
			    }
			}	        
	    }
	    return false; //no forbidden substructures found
		
	}
	/*
	public boolean verifyRule(Molecule mol) throws DecisionMethodException {
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) throw new DecisionMethodException("Structure should be preprocessed!");
	    RingSet rs = mf.getRingset();
	    if (rs == null) return false; //acyclic structure
	    

	    FunctionalGroups.markCHn(mol);	    
	    //iterating over all rings in the ringset
	    for (int i=0; i < rs.size(); i++) {
	        Ring r = (Ring) rs.get(i);
	        logger.debug("Ring\t",(i+1));
	        
	        //new atomcontainer with ring atoms/bonds deleted
	        AtomContainer mc = FunctionalGroups.cloneDiscardRing(mol,r);	        
			logger.debug("\tmol atoms\t",mc.getAtomCount());
		    
			SetOfMolecules  s = ConnectivityChecker.partitionIntoMolecules(mc);
			logger.debug("partitions\t",s.getMoleculeCount());
			for (int k = 0; k < s.getMoleculeCount(); k++) {
			    Molecule m = s.getMolecule(k);
			    if (m!=null) {
				    if ((m.getAtomCount() == 1) && (m.getAtomAt(0).getSymbol().equals("H"))) continue;
				    logger.debug("Partition\t",(k+1));				    
				    if (!FunctionalGroups.hasOnlyTheseGroups(m,query,ids)) {
				    	logger.debug(ids.toString());
				    	logger.debug("Not allowed substructure found\n");
				    	logger.debug(FunctionalGroups.mapToString(m).toString());
				    	return true;
				    }
			    }
			}	        
	    }
	    return false; //no forbidden substructures found
		
	}
	*/
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleSubstructures#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
	
		return true;
	}


}
