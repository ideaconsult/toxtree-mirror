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
 * Filename: RuleSomeBenzeneDerivatives.java
 */
package toxTree.tree.cramer;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleRingAllowedSubstituents;


/**
 * Rule 6 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleSomeBenzeneDerivatives extends RuleRingAllowedSubstituents {
	public static final transient String MSG_ALKOXYPARACH="Alkoxy group para to hydrocarbon chain susbtituent\t";
	public static final transient String MSG_BENZENESUBSTITUENT = "Benzene substituent found:\t";
	protected int index_hydroxy1 = 0;
	//protected int index_hydroxyEsterSubstituted = 1;
	protected int index_hydroxy = 1;
	protected int index_ester = 2;
	protected int index_alkoxy = 3;
	protected int index_hydrocarbon = 4;
	protected ArrayList chIDs, hydroxyIDs, alkoxyIDs;
	protected int[] places = {-1,-1,-1,-1,-1,-1}; 
	protected QueryAtomContainer ring6 = null;
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7287247707766324579L;

    /**
	 * Constructor
	 * 
	 */
	public RuleSomeBenzeneDerivatives() {
		super();
		setAnalyzeAromatic(true);
		setAnalyzeOnlyRingsWithFlagSet(true);
		chIDs = new ArrayList();
		chIDs.add(FunctionalGroups.CH);chIDs.add(FunctionalGroups.CH2);chIDs.add(FunctionalGroups.CH3);
		
		hydroxyIDs = new ArrayList();
		hydroxyIDs.add(FunctionalGroups.HYDROXY1);
		//hydroxyIDs.add(FunctionalGroups.HYDROXYESTERSUBSTITED);
		hydroxyIDs.add(FunctionalGroups.ALCOHOL);
		hydroxyIDs.add(FunctionalGroups.ESTER);
		hydroxyIDs.add(FunctionalGroups.CH);
		hydroxyIDs.add(FunctionalGroups.CH2);
		hydroxyIDs.add(FunctionalGroups.CH3);
		
		alkoxyIDs = new ArrayList();alkoxyIDs.add(FunctionalGroups.ETHER);
		alkoxyIDs.add(FunctionalGroups.CH);alkoxyIDs.add(FunctionalGroups.CH2);alkoxyIDs.add(FunctionalGroups.CH3);
		
		ring6 = FunctionalGroups.ring(6);

		addSubstructure(FunctionalGroups.hydroxy1());
		//addSubstructure(FunctionalGroups.hydroxyEsterSubstituted());
		addSubstructure(FunctionalGroups.alcohol(false));
		addSubstructure(FunctionalGroups.ester());
		addSubstructure(FunctionalGroups.alkoxy()); //alkoxy
		id = "6";
		title = "Benzene derivative with certain substituents";
		explanation.append("<HTML>Is the substance a benzene derivative bearing substituents consisting only of <UL>");
		explanation.append("<LI>(a)hydrocarbon chains or 1'hydroxy or hydroxy ester-substituted hydrocarbon chains and");
		explanation.append("<LI>(b)one or more alkoxy groups, one of which must be para to the hydrocarbon chain in (a)?");
		explanation.append("</UL>this places in class III safrole, myristicin and related substances.</html>");
		examples[0] = "COC1=CC(CC=C)=CC=C1O";
		examples[1] = "C=CCC1=CC=C2OCOC2=C1";
		editable = false;
	}
	@Override
	protected IRingSet hasRingsToProcess(IAtomContainer  mol) throws DecisionMethodException {
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    
	    IRingSet rings = mf.getRingset();
	    if (rings == null) {
	    	logger.fine("Acyclic structure");
	    	return null;
	    }
	    int benzene = 0;
	    for (int i=0; i < rings.getAtomContainerCount(); i++) {
		    IRing r = (IRing) rings.getAtomContainer(i);
		    if (r.getAtomCount() != 6 ) continue;
		    if (!r.getFlag(CDKConstants.ISAROMATIC)) continue;
		    Object o = r.getProperty(MolAnalyser.HETEROCYCLIC);
		    if ((o!=null) && ((Boolean) o).booleanValue()) continue;
		    benzene ++;
	    }
	    if (benzene == 1) {
	    	logger.fine("Benzene ring found.");
	    	return rings; 
	    } else {
	    	if (benzene > 1) logger.fine("Too many benzene rings found.");
	    	else logger.fine("No benzene rings found.");
	    	return null;
	    }
	}	
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleRingAllowedSubstituents#substituentIsAllowed(org.openscience.cdk.AtomContainer)
	 */
	@Override
	public boolean substituentIsAllowed(IAtomContainer  a, int[] place)
			throws DecisionMethodException {
		//(a)hydrocarbon chain
		//FunctionalGroups.markCHn(a);
		//logger.fine(FunctionalGroups.mapToString(a));
		if (FunctionalGroups.hasMarkedOnlyTheseGroups(a,chIDs)) {
			for (int j=0;j<place.length;j++)
				if (place[j] >=0) places[place[j]] = index_hydrocarbon;
			logger.fine(MSG_BENZENESUBSTITUENT+"hydrocarbon chain\tat place\t"+place);
			return true;
		} 
		QueryAtomContainer q = (QueryAtomContainer)getSubstructure(index_hydroxy1);
		List list = FunctionalGroups.getBondMap(a,q,true);
		if (list != null) {
			//FunctionalGroups.markMaps(a,q,list);
			if (FunctionalGroups.hasMarkedOnlyTheseGroups(a,hydroxyIDs)) {
				for (int j=0;j<place.length;j++)
					if (place[j] >=0) places[place[j]] = index_hydroxy1;
				logger.fine(MSG_BENZENESUBSTITUENT+"1'-hydroxy hydrocarbon chain");
				return true;
			}
		}//clear hydroxy mark ?
		/*
		q = (QueryAtomContainer)getSubstructure(index_hydroxyEsterSubstituted);
		list = FunctionalGroups.getBondMap(a,q,true);
		if (list != null) {
			//FunctionalGroups.markMaps(a,q,list);
			if (FunctionalGroups.hasMarkedOnlyTheseGroups(a,hydroxyIDs)) {
				for (int j=0;j<place.length;j++)
					if (place[j] >=0) places[place[j]] = index_hydroxyEsterSubstituted;
				logger.fine(MSG_BENZENESUBSTITUENT,"hydroxy ester substituted hydrocarbon chain");
				return true;
			}
		}
		*/
		q = (QueryAtomContainer)getSubstructure(index_hydroxy);
		list = FunctionalGroups.getUniqueBondMap(a,q,true);
		if (list != null) FunctionalGroups.markMaps(a,q,list);
		q = (QueryAtomContainer)getSubstructure(index_ester);
		list = FunctionalGroups.getUniqueBondMap(a,q,true);
		if (list != null) FunctionalGroups.markMaps(a,q,list);
		
		if (FunctionalGroups.hasMarkedOnlyTheseGroups(a,hydroxyIDs)) {
			for (int j=0;j<place.length;j++)
					if (place[j] >=0) places[place[j]] = index_ester;
			logger.fine(MSG_BENZENESUBSTITUENT+"hydroxy ester substituted hydrocarbon chain");
			return true;
		}		
		q = (QueryAtomContainer)getSubstructure(index_alkoxy);
		list = FunctionalGroups.getBondMap(a,q,true);
		if (list != null) {
			//FunctionalGroups.markMaps(a,q,list);
			if (FunctionalGroups.hasMarkedOnlyTheseGroups(a,alkoxyIDs)) {
				for (int j=0;j<place.length;j++)
					if (place[j] >=0) places[place[j]] = index_alkoxy;
					
				logger.fine(MSG_BENZENESUBSTITUENT+"alkoxy group at place\t"+place);
				return true;
			}
		}		
		logger.fine("Forbiden benzene substituent found");
		return false;
		
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleSubstructures#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return true;
	}
	@Override
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.info(toString());		
		IRingSet rs = hasRingsToProcess(mol);
		if (rs == null) return false;
		
		for (int i=0;i<6;i++) places[i] = -1;		
	    FunctionalGroups.markCHn(mol);	    
	    //if entire structure has only allowed groups, return true (can't have other groups as substituents)
	    FunctionalGroups.hasOnlyTheseGroups(mol,query,ids,false);
	   // logger.fine(FunctionalGroups.mapToString(mol));
	    //otherwise some of the forbidden groups can be in-ring, so substituents can be fine	
	    //iterating over all rings in the ringset
	    IRing r =null;
	    for (int i=0; i < rs.getAtomContainerCount(); i++) {
	        r = (IRing) rs.getAtomContainer(i);
	        if (!analyze(r)) continue;
	        logger.fine("Ring\t"+(i+1));
	        
	        //new atomcontainer with ring atoms/bonds deleted
	        IAtomContainer mc = FunctionalGroups.cloneDiscardRingAtomAndBonds(mol,r);	        
			logger.fine("\tmol atoms\t"+mc.getAtomCount());
		    
			FunctionalGroups.markAtomsInRing(mol,ring6);
			
			IMoleculeSet  s = ConnectivityChecker.partitionIntoMolecules(mc);
			//logger.fine("Ring substituents\t",s.getMoleculeCount());
			int substituents = 0;
			
			
			for (int k = 0; k < s.getMoleculeCount(); k++) {
				IMolecule m = s.getMolecule(k);
			    if (m!=null) {
				    if ((m.getAtomCount() == 1) && (m.getAtom(0).getSymbol().equals("H"))) continue;
				    
				    Object place = null;
				    int[] p = new int[m.getAtomCount()];	    				    
				    for (int j=0;j <m.getAtomCount();j++) {
				    	p[j] = -1;
				    	place = m.getAtom(j).getProperty(FunctionalGroups.RING_NUMBERING);
				    	if (place != null) {
				    		p[j] = ((Integer) place).intValue();
				    		logger.fine("Ring substituent at place\t"+p[j]);
				    	}
				    } 
				    	

				    
				    substituents++;
				    if (!substituentIsAllowed(m,p)) {
				    	logger.fine(ERR_PRECONDITION_FAILED);
				    	return false;
				    }
				    if (!FunctionalGroups.hasMarkedOnlyTheseGroups(m,ids)) {
				    	logger.fine(allowedSubstituents());
				    	logger.fine(CONDITION_FAILED);
				    	logger.fine(FunctionalGroups.mapToString(m).toString());
				    	return false;
				    }
			    }
			}
			
			if (substituents == 0) {
				logger.fine("Ring substituents\t"+MSG_NO);
				return false;
			}
	    }
	    
	    for (int i=0;i<6;i++)
	    	if (places[i] == index_alkoxy) {
	    		int para = (i+3) % 6;
	    		if (places[para] == index_hydrocarbon) {
	    			logger.fine(MSG_ALKOXYPARACH+"YES");
	    			return true;
	    		} else if (places[para] == index_ester) {
	    			logger.fine(MSG_ALKOXYPARACH+"YES");
	    			return true;
	    		}
	    	}
	    logger.fine(MSG_ALKOXYPARACH+"NO");
	    return false;
		
	}
	
	
}
