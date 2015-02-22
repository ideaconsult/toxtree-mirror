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

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;

/**
 * A rule to analyze ring substituents
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-17
 */
public abstract class RuleRingSubstituents extends RuleRings {
	public static String ERR_PRECONDITION_FAILED = "Forbidden ring substituent found (precondition failed)";
	public static String CONDITION_FAILED = "Forbidden substructure found in a ring substituent";
	public static String CYCLIC_SUBSTITUENT = "Ring substituent bearing another ring!";
	public static String LONGCHAIN_SUBSTITUENT = "Ring substituent with > 5 carbon atoms!\t - ";
	

	protected boolean[] flags = null;
	protected boolean analyzeOnlyRingsWithFlagSet = false;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8095271692686086087L;

	/**
	 * 
	 */
	public RuleRingSubstituents() {
		super();

	    flags = new boolean[CDKConstants.MAX_FLAG_INDEX+1];
	    clearFlags();
	}

	protected boolean analyze(IRing r) {
		
		if (analyzeOnlyRingsWithFlagSet) {
			if (flags[flags.length-1]) {
				Object o = r.getProperty(MolAnalyser.HETEROCYCLIC);
				if (o==null) return false;
				if (((Boolean) o).booleanValue()) return true;
			}
			boolean[] ringFlags = r.getFlags();
			for (int i=0; i<(flags.length-1); i++)
				if (i>=ringFlags.length) break;
				else if (ringFlags[i] && flags[i]) return true;
							
			return false;

		} else return true;
	}	

	/**
	 *returns true if the substituent is allowed and false if it is forbidden 
	 *to be implemented by the inheriting class
	 */
	public abstract boolean substituentIsAllowed(IAtomContainer  a, int[] place) throws DecisionMethodException;
	
	public void clearFlags() {
		for (int i=0; i<flags.length; i++) flags[i] = false;
	}
	public void setAnalyzeHeterocyclic(boolean flag) {
		flags[CDKConstants.MAX_FLAG_INDEX] = flag;
	}
	public void setAnalyzeAromatic(boolean flag) {
		flags[CDKConstants.ISAROMATIC] = flag;
	}		
	/**
	 * @return Returns the analyzeOnlyRingsWithFlagSet.
	 */
	public boolean analyzeOnlyRingsWithFlagSet() {
		return analyzeOnlyRingsWithFlagSet;
	}
	/**
	 * @param analyzeOnlyRingsWithFlagSet The analyzeOnlyRingsWithFlagSet to set.
	 */
	public void setAnalyzeOnlyRingsWithFlagSet(
			boolean analyzeOnlyRingsWithFlagSet) {
		this.analyzeOnlyRingsWithFlagSet = analyzeOnlyRingsWithFlagSet;
	}
	public String allowedSubstituents() {
		return "Allowed substituents:\t" + ids.toString();
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		return verifyRule(mol,null);
	};
	public boolean verifyRule(IAtomContainer  mol, IAtomContainer selected) throws DecisionMethodException {
		logger.fine(toString());
		IRingSet rs = hasRingsToProcess(mol);
		if (rs == null) return false;
		
	    IRing r =null;
	    for (int i=0; i < rs.getAtomContainerCount(); i++) {
	        r = (IRing) rs.getAtomContainer(i);
	        if (!analyze(r)) continue;
	        logger.finer("Ring\t"+(i+1));
	        
	        //new atomcontainer with ring atoms/bonds deleted
	        IAtomContainer mc = FunctionalGroups.cloneDiscardRingAtomAndBonds(mol,r);	        
			logger.finer("\tmol atoms\t"+mc.getAtomCount());
		    
			IAtomContainerSet  s = ConnectivityChecker.partitionIntoMolecules(mc);
			//logger.debug("partitions\t",s.getMoleculeCount());
			for (int k = 0; k < s.getAtomContainerCount(); k++) {
				IAtomContainer m = s.getAtomContainer(k);
			    if (m!=null) {
				    if ((m.getAtomCount() == 1) && (m.getAtom(0).getSymbol().equals("H"))) continue;
				    logger.finer("Ring substituent\t"+(k+1));
				    if (!substituentIsAllowed(m,null)) {
				    	logger.finer(ERR_PRECONDITION_FAILED);
				    	return false;
				    }
			    }
			}	        
	    }
	    return true;
		
	}
	
}
