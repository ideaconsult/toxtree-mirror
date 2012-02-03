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
 * Filename: RuleHeterocyclicComplexSubstituents.java
 */
package toxTree.tree.cramer;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.rules.RuleRingOtherThanAllowedSubstituents;

/**
 * Rule 11 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleHeterocyclicComplexSubstituents extends RuleRingOtherThanAllowedSubstituents {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 954868399687751242L;
    /**
	 * Constructor
	 * 
	 */
	public RuleHeterocyclicComplexSubstituents() {
		super();
		setAnalyzeHeterocyclic(true);
		setAnalyzeOnlyRingsWithFlagSet(true);
		addSubstructure(FunctionalGroups.alcohol(false));
		addSubstructure(FunctionalGroups.aldehyde());
		addSubstructure(FunctionalGroups.acetal());
		addSubstructure(FunctionalGroups.ketone());
		addSubstructure(FunctionalGroups.carboxylicAcid());
		addSubstructure(FunctionalGroups.ester());
		addSubstructure(FunctionalGroups.mercaptan());
		addSubstructure(FunctionalGroups.sulphide());
		addSubstructure(FunctionalGroups.methylether());
		addSubstructure(FunctionalGroups.hydroxy_ring());
		//TODO  single rings with ....
		//addSubstructure();
		id = "11";
		title = "Has a heterocyclic ring with complex substituents.";
		explanation.append("<html>Disregarding only the heteroatoms on any one ring, does that heterocyclic ring contain or bear substituents other than ");
		explanation.append("<i>simply branched</i> (I) hydrocarbonds (including bridged chains and monocyclic aryl or alkyl structures),");
		explanation.append("alkyl alcohols, aldehydes,acetals,ketones,ketals,acids,esters (including cyclic esters other than lactones),");
		explanation.append("mercaptans, sulphides, methyl ethers, hydroxy or single rings (hetero or aryl) with no substituents other than those just listed?");
		explanation.append("<p>Questions 11-15 separate out various categories of heteroaromatic substances. Under 11, set aside and do not consider the atom(s), usually O,N and S, making the ring heterocyclic.");
		explanation.append("If there is more than one hetero ring, regard each ring separately, with the remainder of the structure as substituents of that hetero ring.");
		explanation.append("Other than the heterocyclic atoms, does the ring carry anything besides the simple groups listed? <p>If so, the answer is YES,");
		explanation.append("and the next question 33. If not, then classify further by Q12 et seq. Bridged chain derivatives may be represented by structures like the bicyclic ether 1,4 cineole");
		explanation.append(" while monocyclic aryl derivatives may be represented by compounds like benzaldehyde propylene glycol acetal or 3-phenyl-2-furancarboxaldehyde.");
		explanation.append("</html>");
		examples[0] = "CC(C)C12CCC(C)(CC1)COC2";
		examples[1] = "CSC(=O)C1=CC=CC=N1";
		editable = false;
	}
	/* (non-Javadoc)
     * @see toxTree.tree.AbstractRule#isImplemented()
     */
    @Override
	public boolean isImplemented() {
        return true;
    }
    @Override
    public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
    		throws DecisionMethodException {
    	if (super.verifyRule(mol,selected)) {
    		//verify if unmarked atoms are ring heteroatoms
    		/*
    		boolean hasUnmarkedAtom = false;
    		for (int i=0;i < mol.getAtomCount();i++) {
    			Atom a = mol.getAtomAt(i);
    			if (a.getSymbol().equals("H")) continue;
    			if (a.getSymbol().equals("C")) continue;
    			boolean marked = false;
    			for (int j=0;j<ids.size();j++) 
    				if (a.getProperty(ids.get(j)) != null) marked = true;
    			if (!marked) 
    				if (a.getFlag(CDKConstants.ISINRING)) marked = true;
    				else {
    					logger.debug("An atom ",a.getSymbol()," doesn't belong to allowed groups and is not a heteroring atom");
    					hasUnmarkedAtom = true;
    				}
    			
    		}
    		return hasUnmarkedAtom;
    		*/
    		return true;	
    	} else return false;
    }
    @Override
	public boolean substituentIsAllowed(IAtomContainer a, int[] place) throws DecisionMethodException {
    	return true;
    }
}
