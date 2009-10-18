/*
Copyright (C) 2005-2006  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package mutant.rules;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IReaction;

import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.ReactionException;
import toxTree.query.MolFlags;
import toxTree.query.SimpleReactions;
import toxTree.tree.rules.RuleVerifyAlertsCounter;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;

public class RuleAromaticDiazo extends RuleSMARTSubstructureCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7210404986049938157L;

	protected static String aromatic_diazo="Aromatic diazo";
	protected transient SimpleReactions sr;
	
	public RuleAromaticDiazo() {
		try {
			setContainsAllSubstructures(true);
        	addSubstructure(aromatic_diazo,"a[N]=[N]a");
        	
			setID("aN=Na");
			setTitle(aromatic_diazo);
			setExplanation(aromatic_diazo);
			
			examples[0] = "NC1CCCCC1";
			examples[1] = "C1=CC=C(C=C1)N=NC2=CC=CC=C2"; //CNc1ccc(cc1(C))N=Nc2ccccc2";	
			sr = new SimpleReactions();
			
			
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
	
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		if (super.verifyRule(mol)) {
			logger.info(toString());
		    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		    if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
		    
			try {
			    IReaction r = sr.getMetabolicReaction(0);
			    IAtomContainerSet sc = SimpleReactions.process(mol,r);
			    if ((sc != null) && (sc.getAtomContainerCount()>0)) {
			    	Object o = mol.getProperty(RuleVerifyAlertsCounter.ALERTSCounter);
			    	if (o != null)
			    		for (int i=0; i < sc.getAtomContainerCount();i++)
			    			sc.getAtomContainer(i).setProperty(RuleVerifyAlertsCounter.ALERTSCounter, o);
			    	mf.setResidues(sc);
			    }	
			} catch (ReactionException x) {
				throw new DecisionMethodException(x);
			} catch (CDKException x) {
				throw new DecisionMethodException(x);
			}
			return true;
		} else return false;
	}	
}




