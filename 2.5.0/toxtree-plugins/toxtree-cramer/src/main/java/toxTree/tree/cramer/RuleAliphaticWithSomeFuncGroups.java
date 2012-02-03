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
package toxTree.tree.cramer;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleSubstructures;

/**
 * Rule 20 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleAliphaticWithSomeFuncGroups extends RuleSubstructures {
	protected static int carbonate_id = 0;	
	protected static int carboxylicacid_id = 1;
	protected static int ester_id = 2;
	protected static int aldehyde_id = 3;	
	protected static int alcohol_id = 4;


	protected static int ketone_id = 5;
	protected static int acetal_id = 6;
	protected static int thioester_id = 7;
	protected static int sulphide_id = 8;
	protected static int mercaptan_id = 9;
	protected static int aminePrimary_id = 10;
	protected static int amineTertiary_id = 11;
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4236594225792068881L;
    /**
	 * Constructor
	 * 
	 */
	public RuleAliphaticWithSomeFuncGroups() {
		super();
		addSubstructure(FunctionalGroups.carbonate());		
		addSubstructure(FunctionalGroups.carboxylicAcid());
		addSubstructure(FunctionalGroups.ester());
		addSubstructure(FunctionalGroups.aldehyde());
		addSubstructure(FunctionalGroups.alcohol(false));
		
		addSubstructure(FunctionalGroups.ketone());
		addSubstructure(FunctionalGroups.acetal());
		addSubstructure(FunctionalGroups.thioester());
		addSubstructure(FunctionalGroups.sulphide());
		addSubstructure(FunctionalGroups.mercaptan());
		addSubstructure(FunctionalGroups.primaryAmine(false));
		addSubstructure(FunctionalGroups.tertiaryAmine());
		
		id = "20";
		title = "Aliphatic with some functional groups (see explanation)";
		explanation.append("<html>Is the structure a linear or <i>simply branched</i> (I) <i>aliphatic</i> (A) compound, containing any one or combination of only the following <i>functional groups</i> (E): <UL>");
		explanation.append("<LI>(a)four or less, each, of alcohol, aldehyde, carboxylic acid or esters and/or");
		explanation.append("<LI>(b)one each of one or more of the following: acetal, either ketone or ketal but not both, mercaptan, sulphide (mono- or poly-), thioester, polyoxyethylene [(-OCH2CH2-)x with x no greather than 4], or ");
		explanation.append("primary or tertiary amine </UL>");
		explanation.append("this question should be answered YES if the structure contains one or any possible combination of alcoholic, aldehydic or carboxylic acid or ester groups, provided there are no more than four of any one kind.");
		explanation.append("It should be answered YES if the structure contains in addition to, or instead of, those just listed, any assortment of no more than one each of the following:");
		explanation.append("acetal, either ketone or ketal but not both, mercaptan, mono- or polysulphide, thioester, polyoxyethylene, primary or tertiary amine. <p>");
		explanation.append("Answer the question NO if the structure contains more than four of any of the first set of groups, more than one of the second set, or any substituent not listed.");
		explanation.append("</html>");
		examples[0] = "CC(=O)CCC(C)=O";
		examples[1] = "CC(=O)C(O)=O";
		editable = false;
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		return verifyRule(mol,null);
	}
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {
	
		logger.info(toString());
	    //should be set via MolAnalyser
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    if (mf.isAliphatic()) {
		    ArrayList ids = new ArrayList();
		    FunctionalGroups.preProcess(mol);
		    
		    for (int id=0; id < ketone_id; id++) {
			    IAtomContainer q = getSubstructure(id);
		        List list = FunctionalGroups.getUniqueBondMap(mol,q,true);
			    ids.add(q.getID());
		        if ((list != null) && (list.size() > 4)) {
		        	logger.debug("More than 4 groups\t",q.getID());
		        	return false;
		        }
		        FunctionalGroups.markMaps(mol,q,list);		    	
		    }
		   
	        int ak = 0;
	        for (int id=ketone_id; id <= amineTertiary_id; id++) {
			    IAtomContainer q = getSubstructure(id);
		        List list = FunctionalGroups.getUniqueBondMap(mol,q,true);
		        if (list != null) {
		        	if (list.size() > 0) {
			        	logger.debug("Found group\t",q.getID());
		        		if (list.size() > 1) {
				        	logger.debug("More than 1 group\t",q.getID());	
				        	return false;
		        		}
		        	}
			        FunctionalGroups.markMaps(mol,q,list);	        
				    ids.add(q.getID());
			        ak += list.size();
		        }
	        }
	        
	        for (int i=4; i > 0; i--) {
			    QueryAtomContainer q = FunctionalGroups.polyoxyethylene(i);
	        	logger.debug(q.getID() , Integer.toString(i));
		        List list = FunctionalGroups.getUniqueBondMap(mol,q,true);
		        if (list != null) {
			        	logger.debug("Found " , Integer.toString(list.size()));
					    ids.add(q.getID());
				    	if (list.size() > 4) {
				    		return false;
				    	}
					    FunctionalGroups.markMaps(mol,q,list);
					    
		        }
			 }
		    
	        
		    ids.add(FunctionalGroups.C);	    
		    ids.add(FunctionalGroups.CH);
		    ids.add(FunctionalGroups.CH2);
		    ids.add(FunctionalGroups.CH3);
		    FunctionalGroups.markCHn(mol);
		    
		    boolean ok = FunctionalGroups.hasMarkedOnlyTheseGroups(mol,ids,selected,null);
		    ids.clear();
		    ids = null;
		    return ok;
	        
		} else 
	        return false;
	}
	/* (non-Javadoc)
     * @see toxTree.tree.AbstractRule#isImplemented()
     */
    @Override
	public boolean isImplemented() {
        return true;
    }	
}
