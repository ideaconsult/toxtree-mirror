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

package toxtree.plugins.smartcyp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.data.CategoryFilter;
import toxTree.exceptions.DMethodNotAssigned;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.DecisionNode;
import toxTree.tree.ProgressStatus;
import toxTree.tree.RuleResult;
import toxTree.tree.TreeResult;
import toxTree.tree.rules.IAlertCounter;
import dk.smartcyp.core.MoleculeKU.SMARTCYP_PROPERTY;
import dk.smartcyp.core.SMARTSData;
import dk.smartcyp.smirks.SMARTCYPReaction;



public class SMARTCypTreeResult extends TreeResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2708973197593705185L;
	/**
	 * 
	 */

	public final static String FORMAT = "SMARTCyp.Rank%s%d.%s";

	public void assignResult(IAtomContainer mol) throws DecisionResultException {
		if (mol == null) return;
		IDecisionCategories c = decisionMethod.getCategories();
		for (int i=0; i < c.size();i++) {
			String q=c.get(i).getID()==4?">=":"";
			Object result = Answers.toString(Answers.NO);
			IAtomContainer molProcessed = ((RuleResult)ruleResults.get(1)).getMolecule();
			if (getAssignedCategories().indexOf(c.get(i))>-1) {
				result = getAtomScoresOfRank(molProcessed,c.get(i).getID(),null); //Answers.toString(Answers.YES);
				mol.setProperty(c.get(i).toString(),result);
				
				result = getAtomScoresOfRank(molProcessed,c.get(i).getID(),SMARTCYP_PROPERTY.Score);
				mol.setProperty(String.format(FORMAT,q,c.get(i).getID(),SMARTCYP_PROPERTY.Score),result==null?Double.NaN:result);
				result = getAtomScoresOfRank(molProcessed,c.get(i).getID(),SMARTCYP_PROPERTY.Energy);
				mol.setProperty(String.format(FORMAT,q,c.get(i).getID(),SMARTCYP_PROPERTY.Energy),result==null?Double.NaN:result);
				
				result = getAtomScoresOfRank(molProcessed,c.get(i).getID(),SMARTCYP_PROPERTY.Reaction);
				mol.setProperty(String.format(FORMAT,q,c.get(i).getID(),SMARTCYP_PROPERTY.Reaction),result==null?"N/A":result);
				
				result = getAtomScoresOfRank(molProcessed,c.get(i).getID(),SMARTCYP_PROPERTY.SMIRKS);
				mol.setProperty(String.format(FORMAT,q,c.get(i).getID(),SMARTCYP_PROPERTY.SMIRKS),result==null?"N/A":result);
				
				result = getAtomScoresOfRank(molProcessed,c.get(i).getID(),SMARTCYP_PROPERTY.Accessibility); 
				mol.setProperty(String.format(FORMAT,q,c.get(i).getID(),SMARTCYP_PROPERTY.Accessibility),result==null?Double.NaN:result);
			} else {
				mol.setProperty(c.get(i).toString(),"");
				mol.setProperty(String.format(FORMAT,q,c.get(i).getID(),SMARTCYP_PROPERTY.Score),Double.NaN);
				mol.setProperty(String.format(FORMAT,q,c.get(i).getID(),SMARTCYP_PROPERTY.Energy),Double.NaN);
				mol.setProperty(String.format(FORMAT,q,c.get(i).getID(),SMARTCYP_PROPERTY.Accessibility),Double.NaN);
				
			}
		}
     	
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, status);        

        
        
	}
	protected Object getAtomScoresOfRank(IAtomContainer moleculeKU,Integer rank,SMARTCYP_PROPERTY property) {
           
           List atoms = new ArrayList();

   			// Iterate Atoms
            	
  			for(int atomIndex = 0; atomIndex < moleculeKU.getAtomCount()  ; atomIndex++ ){

    				IAtom currentAtom = (IAtom) moleculeKU.getAtom(atomIndex);

    				if (SMARTCYP_PROPERTY.Ranking.getNumber(currentAtom) == null) continue; 
    				int atomRank = SMARTCYP_PROPERTY.Ranking.getNumber(currentAtom).intValue();
    				if (rank==4) 
    					if (atomRank<rank) continue; 
    					else {}
    				else if (atomRank != rank) continue; 
    				// Match atom symbol
    				String currentAtomType = currentAtom.getSymbol();
    				if(currentAtomType.equals("C") || currentAtomType.equals("N") || currentAtomType.equals("P") || currentAtomType.equals("S")) {
    					Object value = null;
    					if (property ==null)
    						value = String.format("%s%s",currentAtom.getSymbol(),currentAtom.getID());
    					else
    						switch (property) {
    						case Reaction: {
    							SMARTSData data = property.getData(currentAtom);
        						if (data!=null) { 
        							if (atoms.indexOf(data.getReaction()) == -1)
        								value = data.getReaction().toString();         							
        						}
    							break;    							
    						}
    						case SMIRKS: {
    							SMARTSData data = property.getData(currentAtom);
        						if (data!=null) { 
        							if (atoms.indexOf(data.getReaction()) == -1)
        								value = data.getReaction().getSMIRKS();         							
        						}
    							break;    							
    						}    						
    						case Energy : {
    							SMARTSData data = property.getData(currentAtom);
        						if (data!=null) { 
        							if (atoms.indexOf(data.getEnergy()) == -1)
        								value = property.getData(currentAtom).getEnergy();
        						}
    							break;
    						}
    						default: {
        						if (property.getNumber(currentAtom)!=null) 
        							if (atoms.indexOf(property.getNumber(currentAtom)) == -1)
        								value = property.getNumber(currentAtom);
    						}
    						}
    						if ((value != null) && atoms.indexOf(value)<0)
    							atoms.add(value);

    					/*
    					mol.setProperty(String.format("%s_%s_%s",currentAtom.getSymbol(),currentAtom.getID(),SMARTCYP_PROPERTY.Ranking),
    							SMARTCYP_PROPERTY.Ranking.get(currentAtom));
    					
    					if (SMARTCYP_PROPERTY.Score.get(currentAtom)!=null)
    					mol.setProperty(String.format("%s_%s_%s",currentAtom.getSymbol(),currentAtom.getID(),SMARTCYP_PROPERTY.Score),
    							SMARTCYP_PROPERTY.Score.get(currentAtom));   
    					
    					if (SMARTCYP_PROPERTY.Energy.get(currentAtom)!=null)
    					mol.setProperty(String.format("%s_%s_%s",currentAtom.getSymbol(),currentAtom.getID(),SMARTCYP_PROPERTY.Energy),
    							SMARTCYP_PROPERTY.Energy.get(currentAtom));   
    					
    					mol.setProperty(String.format("%s_%s_%s",currentAtom.getSymbol(),currentAtom.getID(),SMARTCYP_PROPERTY.Accessibility),
    							SMARTCYP_PROPERTY.Accessibility.get(currentAtom));   
    							*/             					
   				}
   			}
			
			if (atoms.size()>1) {
				Collections.sort(atoms);
				StringBuffer b = new StringBuffer();
				String delimiter = "";
				for (Object atom:atoms) {b.append(delimiter); b.append(atom); delimiter=",";}
		           return b.toString();	
			} else if (atoms.size()==1) return atoms.get(0);
			else return null;
			

	}	
	@Override
	public String[] getResultPropertyNames() {
		IDecisionCategories c = decisionMethod.getCategories();
		
		SMARTCYP_PROPERTY[] p = new SMARTCYP_PROPERTY[] {SMARTCYP_PROPERTY.Score,SMARTCYP_PROPERTY.Energy,SMARTCYP_PROPERTY.Reaction,SMARTCYP_PROPERTY.SMIRKS,SMARTCYP_PROPERTY.Accessibility}; 
		String[] names = new String[c.size()*(p.length+1)];
		for (int i=0; i < c.size();i++) 
			names[i] = c.get(i).toString(); //atoms per rank

		int n = c.size();
		for (int j=0; j < p.length;j++) //scores per rank
			for (int i=0; i < c.size();i++) { 
				names[n] = String.format(FORMAT,"",c.get(i).getID(),p[j]);
				n++;
			}	
			//names[i] = c.get(i).toString();

		return names;
	}
	
	@Override
	protected boolean acceptCategory(IDecisionCategory category) {
		return category != null;
	}
	protected ArrayList<IAtomContainer> getAllAssignedMolecules() {
        ArrayList<IAtomContainer> residues = new ArrayList<IAtomContainer>();
        return residues;
    }

/*
	protected void assignAtomScores(IDecisionRule rule, IAtomContainer moleculeKU,IAtomContainer mol) {
		if (mol != null) {
            
            Object suffix = "";


            if (rule != null) {
    			// Iterate Atoms
            	
    			for(int atomIndex = 0; atomIndex < moleculeKU.getAtomCount()  ; atomIndex++ ){

    				IAtom currentAtom = (IAtom) moleculeKU.getAtom(atomIndex);

    				// Match atom symbol
    				String currentAtomType = currentAtom.getSymbol();
    				if(currentAtomType.equals("C") || currentAtomType.equals("N") || currentAtomType.equals("P") || currentAtomType.equals("S")) {
					
    					mol.setProperty(String.format("%s_%s_%s",currentAtom.getSymbol(),currentAtom.getID(),SMARTCYP_PROPERTY.Ranking),
    							SMARTCYP_PROPERTY.Ranking.get(currentAtom));
    					
    					if (SMARTCYP_PROPERTY.Score.get(currentAtom)!=null)
    					mol.setProperty(String.format("%s_%s_%s",currentAtom.getSymbol(),currentAtom.getID(),SMARTCYP_PROPERTY.Score),
    							SMARTCYP_PROPERTY.Score.get(currentAtom));   
    					
    					if (SMARTCYP_PROPERTY.Energy.get(currentAtom)!=null)
    					mol.setProperty(String.format("%s_%s_%s",currentAtom.getSymbol(),currentAtom.getID(),SMARTCYP_PROPERTY.Energy),
    							SMARTCYP_PROPERTY.Energy.get(currentAtom));   
    					
    					mol.setProperty(String.format("%s_%s_%s",currentAtom.getSymbol(),currentAtom.getID(),SMARTCYP_PROPERTY.Accessibility),
    							SMARTCYP_PROPERTY.Accessibility.get(currentAtom));                					
    				}
    			}
			
    			}

            }
	}
*/
	
	
	public Hashtable<String,String> getExplanation(IAtomContainer mol) throws DecisionResultException {
		Hashtable<String,String> b = new  Hashtable<String,String>();
        ArrayList<IAtomContainer> residues = getAllAssignedMolecules();
        IAtomContainer originalMol = getOriginalMolecule();
		try {
		    if (status.isEstimated()) {
		    	for (int i=0;i < ruleResults.size();i++) {
		    		RuleResult r = ((RuleResult)ruleResults.get(i));
		    		if (r.isSilent()) continue;

		    		if ((r.getCategory() == null) 
		    			|| (r.getRule() instanceof IAlertCounter)
		    			|| (
		    			(r.getRule() instanceof DecisionNode) && (((DecisionNode)r.getRule()).getRule() instanceof IAlertCounter)
		    			)
		    			) { //not a leaf node or an alert
		    			if (r.isResult()) {
		    				Object result = getAtomScoresOfRank(((RuleResult)ruleResults.get(0)).getMolecule(),r.getCategory().getID(),null);
		    				b.put(r.getRule().getID(),result==null?Answers.toString(Answers.YES):result.toString()); 
		    			} else 
		    				b.put(r.getRule().getID(),Answers.toString(Answers.NO));
		    		}
		    		//Use this to get descriptor values 
                    
		    		//if (r.isResult())
		    			//assignAtomScores(r.getRule(),r.getMolecule(),mol);
		    		}
		    	
		    } else if (status.isEstimating()) {
		        b.put(ProgressStatus._eResultIsEstimating,"YES");
		        
		    }   else if (status.isError()) {
		        b.put(ProgressStatus._eError,status.getErrMessage());
		    }

            residues.clear();
		//} catch (DecisionMethodException x) {
			//throw new DecisionResultException(x);
		} catch (NullPointerException x) {
			throw new DMethodNotAssigned(ProgressStatus._eMethodNotAssigned);
		}
		return b;
	}

	@Override
    public List<CategoryFilter> getFilters() {
    	//no reasonable way of grouping these - perhaps by reactions?
    	ArrayList<CategoryFilter> l = new ArrayList<CategoryFilter>();
    	IDecisionCategories c = decisionMethod.getCategories();
		for (int i=0; i < c.size();i++) {
			String q=c.get(i).getID()==4?">=":"";
			String property = String.format(FORMAT,q,c.get(i).getID(),SMARTCYP_PROPERTY.Reaction);
	    	for (SMARTCYPReaction reaction : SMARTCYPReaction.values()) 
				try { 
		    		l.add(new CategoryFilter(property,reaction.toString()));
		    	} catch (Exception x) {
		    		logger.error(x);
		    	}
		}	

    	return l;
    }	
}


/**
 * workaround due to problem with XML serialization with Enums
 * @author Nina Jeliazkova nina@acad.bg
 *
 */


class Answers {
	public static int NO=0;
	public static int YES=1;
	protected Answers() {
		
	}
	public static String toString(int answer) {
		switch (answer) {
		case 0: return "NO";
		case 1: return "YES";
		default: return "";
		}
	}
}