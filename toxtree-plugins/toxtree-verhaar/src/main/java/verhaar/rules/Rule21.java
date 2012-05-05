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
package verhaar.rules;


import java.util.ArrayList;

import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.DefaultAlertCounter;
import toxTree.tree.rules.IAlertCounter;
import verhaar.query.FunctionalGroups;

/**
 * 
 * Non- or weakly acidic phenols.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule21 extends RuleRingMainStrucSubstituents implements IAlertCounter {
	protected IAlertCounter alertsCounter;
	protected transient int nitroGroupsCount = 0;
	protected transient int halogensCount = 0;
	protected transient String[] halogens = {"Cl","Br","F"};
	protected ArrayList nitroIDs;
	protected ArrayList phenolIDs;
	protected ArrayList halogenIDs;
	protected int maxHalogens = 3;
	protected int maxNitroGroups = 1; 
	/**
	 * 
	 */
	private static final long serialVersionUID = -7909075887636611371L;

	public Rule21() {
		super();
		alertsCounter = new DefaultAlertCounter();
		id = "2.1";
		setTitle("Be non- or weakly acidic phenols");
		explanation.append("Be non- or weakly acidic phenols; <p>i.e. phenols with one nitro substituent, and / or one to three chlorine substituents, and/or alkyl substituents");
		examples[0] = "Clc1cc(O)c(Cl)c(Cl)c1(Cl)"; //4 Cl
		examples[1] = "O=[N+]([O-])c1cccc(O)c1"; //1 nitro
		addSubstructure(FunctionalGroups.nitro1double());
		addSubstructure(FunctionalGroups.nitro2double());
		addSubstructure( FunctionalGroups.halogen(getHalogens()));
		if (mainStructure != null)
			addSubstructure(mainStructure);
		
		ids.add(FunctionalGroups.C);
		ids.add(FunctionalGroups.CH);
		ids.add(FunctionalGroups.CH2);
		ids.add(FunctionalGroups.CH3);
		
		
		phenolIDs = new ArrayList(); if (mainStructure != null) phenolIDs.add(mainStructure.getID());
		nitroIDs = new ArrayList(); nitroIDs.add(getSubstructure(0).getID()); nitroIDs.add(getSubstructure(1).getID());
		halogenIDs = new ArrayList(); halogenIDs.add(getSubstructure(2).getID());
		
		editable = false;
		
	}
	protected String[] getHalogens() {
		 return new String[]{"Cl","Br","F"};
	}
	protected QueryAtomContainer createMainStructure() {
		return  FunctionalGroups.phenol();
	}
	//TODO check for one nitro and up to 3 Cl
	
	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (verifyRule(mol,null)) {
			incrementCounter(mol);
			return true;	
		} else return false;
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {

		logger.info(toString());
		if ((mainStructure != null) && (!FunctionalGroups.hasGroup(mol,mainStructure,selected))) {
			logger.debug(mainStructure.getID(),"\tNO");
			return false;
		}
		nitroGroupsCount = 0;
		halogensCount = 0;		
		IRingSet rs = hasRingsToProcess(mol);
		if (rs == null) return false;
		
	    FunctionalGroups.markCHn(mol);	    
	    //if entire structure has only allowed groups, return true (can't have other groups as substituents)
	    if (FunctionalGroups.hasOnlyTheseGroups(mol,query,ids,true)) {
	    	logger.debug("The entire structure consists only of allowed groups");
	    	//return true; 
	    }
	    //otherwise some of the forbidden groups can be in-ring, so substituents can be fine	
	    //iterating over all rings in the ringset
	    IRing r =null;
	    for (int i=0; i < rs.getAtomContainerCount(); i++) {
	        r = (IRing) rs.getAtomContainer(i);
	        if (!analyze(r)) continue;
	        logger.debug("Ring\t",(i+1));
	        
	        //new atomcontainer with ring atoms/bonds deleted
	        IAtomContainer mc = FunctionalGroups.cloneDiscardRingAtomAndBonds(mol,r);	        
			logger.debug("\tmol atoms\t",mc.getAtomCount());
		    
			IAtomContainerSet  s = ConnectivityChecker.partitionIntoMolecules(mc);
			logger.debug("partitions\t",s.getAtomContainerCount());
			for (int k = 0; k < s.getAtomContainerCount(); k++) {
				IAtomContainer m = s.getAtomContainer(k);
			    if (m!=null) {
				    if ((m.getAtomCount() == 1) && (m.getAtom(0).getSymbol().equals("H"))) continue;
				    logger.debug("Partition\t",(k+1));
				    if (!substituentIsAllowed(m,null)) {
				    	logger.debug(ERR_PRECONDITION_FAILED);
				    	return false;
				    }
				    if (!FunctionalGroups.hasMarkedOnlyTheseGroups(m,ids)) {
				    	logger.debug(allowedSubstituents());
				    	logger.debug(CONDITION_FAILED);
				    	logger.debug(FunctionalGroups.mapToString(m).toString());
				    	return false;
				    }
			    }
			}	        
	    }
	    //throw new DRuleNotImplemented("TODO check for one nitro and up to 3 halogens");
	    return true;
		
	}
	public boolean substituentIsAllowed(IAtomContainer a, int[] place) throws DecisionMethodException {
		if (FunctionalGroups.hasMarkedOnlyTheseGroups(a,phenolIDs)) return true;
		else if (FunctionalGroups.hasMarkedOnlyTheseGroups(a,nitroIDs)) {
			logger.debug("Nitro substituent\tYES");
			nitroGroupsCount++;
			return nitroGroupsCount <= maxNitroGroups;
		} if (FunctionalGroups.hasMarkedOnlyTheseGroups(a,halogenIDs)) {
			halogensCount++;
			logger.debug("Halogen substituents\t","YES");
			if (halogensCount > maxHalogens) {
				logger.debug("Too many halogen substituents\t",Integer.toString(halogensCount));
				return false;
			} else return true;
		} else {
			for (int i=0;i<a.getAtomCount();i++)
				if (a.getAtom(i).getSymbol().equals("C")) continue;
				else if (a.getAtom(i).getSymbol().equals("H")) continue;
				else {
					logger.debug("Forbidden group found\t",a.getAtom(i).getSymbol());
					return false;
				}
			logger.debug("Alkyl substituent\tYES");
			return true;
		}
			
	}

	public boolean isImplemented() {
		return true;
	}
	public int getMaxHalogens() {
		return maxHalogens;
	}
	public void setMaxHalogens(int maxHalogens) {
		this.maxHalogens = maxHalogens;
	}
	public int getMaxNitroGroups() {
		return maxNitroGroups;
	}
	public void setMaxNitroGroups(int maxNitroGroups) {
		this.maxNitroGroups = maxNitroGroups;
	}	
	@Override
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append(alertsCounter.getImplementationDetails());

		return b.toString();
	}
	@Override
	public void incrementCounter(IAtomContainer mol) {
		alertsCounter.incrementCounter(mol);
		
	}

}
