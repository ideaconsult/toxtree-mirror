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
 * Filename: RuleLactoneOrCyclicDiester.java
 */
package cramer2.rules;

import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleSubstructures;

/**
 * Rule 8 of the Cramer scheme (see {@link cramer2.CramerRulesExtendedExtended})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleLactoneOrCyclicDiester extends RuleSubstructures {
	protected int index_lactone;
	protected QueryAtomContainer anhydride;
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6435882912202895280L;

    /**
	 * Constructor
	 * 
	 */
	public RuleLactoneOrCyclicDiester() {
		super();
	
		addSubstructure(FunctionalGroups.lactone(false));
		anhydride = FunctionalGroups.anhydride();
		//TODO FunctionalGroups.cyclicdiester
		id = "8";
		title="Lactone or cyclic diester";
		explanation.append("<html>Is it a lactone or cyclic diester?<p>This question separates the lactones and cyclic diesters from other heterocyclic compounds.</html>");
		examples[0] = "CC1=C(O)C(=O)C=CO1";
		examples[1] = "CC(=O)C1C(=O)OC(C)=CC1=O";
		editable = false;
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleAnySubstructure#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
	
		return true;

	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)} 
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		//TODO verify if it is 5 or 6 membered ring
		logger.info(toString());
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) throw new DecisionMethodException("Structure should be preprocessed!");
		
		//if (FunctionalGroups.isCyclicDiester(mol,mf.getRingset())) return true;
		List list = FunctionalGroups.getBondMap(mol,getSubstructure(index_lactone),false); 
		if ((list != null) && (list.size() > 0)) {//lactone found
			
			if (FunctionalGroups.hasGroup(mol,anhydride)) {
				return false;
			}
			
			FunctionalGroups.markMaps(mol,getSubstructure(index_lactone),list);
			
			IRingSet rings = mf.getRingset();

			for (int i=0; i < rings.getAtomContainerCount(); i++) {
				IRing ring = (IRing) rings.getAtomContainer(i);
				if ((ring.getAtomCount() > 3)  /*&& (ring.getAtomCount() < 7) */) {

					//can be lactone ring
					for (int j=0; j < ring.getBondCount(); j++) {
						if (ring.getBond(j).getProperty(FunctionalGroups.LACTONE) != null) {
							logger.debug("ring found.");
							for (int a=0; a < ring.getAtomCount(); a++) {
								if (ring.getAtom(a).getSymbol().equals("C")) continue;
								if (ring.getAtom(a).getSymbol().equals("O") &&
			        				(ring.getAtom(a).getProperty(FunctionalGroups.LACTONE) != null)) continue;									
	        					logger.debug("Heteroring\tYES");
	        					return false;
							}							
							logger.debug("Lactone \tYES");
							return true;
						}
						
					}
				}
			}
			logger.debug("5/6 membered lactone ring NOT found.");
			return false;
		} else {
			logger.debug("Lactone\tNO");
			return false;
		}
	}
}
