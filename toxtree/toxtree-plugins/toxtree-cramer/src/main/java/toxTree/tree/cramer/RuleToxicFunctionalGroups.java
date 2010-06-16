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
 * Filename: RuleToxicFunctionalGroups.java
 */
package toxTree.tree.cramer;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.tree.AbstractRuleHilightHits;

/**
 * Rule 2 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleToxicFunctionalGroups extends AbstractRuleHilightHits {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 351259209047271614L;

    /**
	 * Constructor
	 * 
	 */
	public RuleToxicFunctionalGroups() {
		super();
		title = "Contains functional groups associated with enhanced toxicity";
		explanation.append("Does the substance contain any of the following functional groups: an aliphatic secondary amine or a salt thereof, cyano, N-nitroso, diazo, triazeno or quaternary nitrogen, except in any of the following forms: >C=N+R2, >C=N+H2 or the hydrochloride or sulphate salt of a primary or tertiary amine?");
		id = "2";
		examples[0] = "C=CCN=C=S";
		examples[1] = "C1=C(N(=O)=O)C=CC=C1";
		editable = false;
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		return verifyRule(mol,null);
	}
	public boolean verifyRule(IAtomContainer  mol,IAtomContainer selected) throws DecisionMethodException {
		logger.info(toString());
//		try {
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf ==null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    
	    	//[C;R0]-;!@[N;R0;H1]-;!@[C;R0]
		    QueryAtomContainer q = FunctionalGroups.secondaryAmine(true);
		    
			if (mf.isAliphatic() && FunctionalGroups.hasGroup(mol, q,selected)) return true;
			//[N+](=O)([O-])([!#1])
		    if (FunctionalGroups.hasGroup( mol, FunctionalGroups.nitro1double(),selected)) return true;	
		    //N(=O)(=O)([!#1])
		    if (FunctionalGroups.hasGroup( mol, FunctionalGroups.nitro2double(),selected)) return true;
		    //C(#N)([C,#1])
		    if (FunctionalGroups.hasGroup( mol, FunctionalGroups.cyano(),selected)) return true;
		    //O=NN([!#1])([!#1])
		    if (FunctionalGroups.hasGroup( mol, FunctionalGroups.Nnitroso(),selected)) return true;
		    // N#N
		    if (FunctionalGroups.hasGroup(mol, FunctionalGroups.diAzo(),selected)) return true;
		    //[#6][#7]=[#7][#7;H2]
		    if (FunctionalGroups.hasGroup( mol, FunctionalGroups.triAzeno(),selected)) return true;

		    boolean toxicN = true;
		    //quaternary nitrogen with exception
		    
		    /*
		     * N[*][*][*][*]
		     * [N+][*][*][*][*]
		     * [N+][*][*][=*]
		     */
		    boolean quaternaryNitrogen = 
		    		FunctionalGroups.hasGroup( mol, FunctionalGroups.quaternaryNitrogen1(true),selected) ||
		    		FunctionalGroups.hasGroup( mol, FunctionalGroups.quaternaryNitrogen1(false),selected) ||
		    		FunctionalGroups.hasGroup( mol, FunctionalGroups.quaternaryNitrogen2(true),selected) ||
		    		FunctionalGroups.hasGroup( mol, FunctionalGroups.quaternaryNitrogen2(false),selected);
		    		
		    if (quaternaryNitrogen) { 
		    	//exception 1
		    	// N([*])([*])(=[!$(C[!#1]([!#1]))])
		    	if (FunctionalGroups.hasGroup( mol, FunctionalGroups.quarternaryNitrogenException(),selected)) 
		    		toxicN = false;
		        //hydrochloride or sulphate salt of primary or tertiary amine
		    	
			    for (int i=1; i <=3; i+=2) {
			    	q = FunctionalGroups.sulphateOfAmine(i);
			    	if (FunctionalGroups.hasGroup( mol, q,selected)) toxicN = false;		    	
			    	q = FunctionalGroups.hydrochlorideOfAmine(i);
			    	if (FunctionalGroups.hasGroup( mol, q,selected)) toxicN = false;
			    }		    	
			    return toxicN;
		    } else toxicN = false;
		    
/*		    
	    } catch (CDKException x) {
	        throw new DecisionMethodException(x);
	    }
*/
	    return false;
 
	}	
    @Override
	public boolean isImplemented() {
        return true;
    }
    	
}
