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
 * Created on 2005-5-3

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree.rules
 * Filename: RuleMonocycloalakoneEtc.java
 */
package toxTree.tree.cramer;



import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;

/**
 * Rule 26 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-3
 */
public class RuleMonocycloalkanoneEtc extends RuleOnlyAllowedSubstructures {
	protected static String[] metals = {"Na","K","Ca"};
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4450352445667633895L;

    /**
	 * Constructor
	 * 
	 */
	public RuleMonocycloalkanoneEtc() {
		super();
		addSubstructure(FunctionalGroups.alcohol(true));
		addSubstructure(FunctionalGroups.aldehyde());
		addSubstructure(FunctionalGroups.acyclic_acetal());
		addSubstructure(FunctionalGroups.sidechain_ketone());
		addSubstructure(FunctionalGroups.ketone_ring());		
		addSubstructure(FunctionalGroups.carboxylicAcid());
		addSubstructure(FunctionalGroups.ester());
		addSubstructure(FunctionalGroups.sulphonate(metals));
		addSubstructure(FunctionalGroups.sulphonate(metals,false));
		addSubstructure(FunctionalGroups.sulphamate(metals));
		
		id = "26";
		title = "Monocycloalkanone or a bicyclocompound";
		explanation.append("<html>");
		explanation.append("Does the structure contain no functional groups other than those listed in Q24 and is either a monocycloalkanone or a bicyclic compound with or without a ring ketone?");
		explanation.append("</html>");
		examples[0] = "NC1CCCCC1=O";
		examples[1] = "CC(=O)C1=CCCCC1=O";
		editable = false;
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	@Override
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		return verifyRule(mol,null);
	}
	@Override
	public boolean verifyRule(IAtomContainer  mol,IAtomContainer selector) throws DecisionMethodException {
		if (! super.verifyRule(mol,selector)) {
			logger.debug(ids.toString());
			logger.debug("Contains forbidden groups");
			
			return false; 
		}
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    IRingSet rings = mf.getRingset();
	    if ((rings == null) || (rings.getAtomContainerCount() ==0)) {
	    	logger.debug("Acyclic structure");
	    	return false;
	    }
	    if (rings.getAtomContainerCount() == 1) {
	    	//monocycloalkanone
	    	if (selector!=null) selectRings(rings, selector);
	    	if (mf.isHeterocyclic()) {
				logger.debug("Heterocyclic ring found");
	    		return false;
	    	}
	    	return ((FunctionalGroups.hasGroup(mol,FunctionalGroups.ketone_ring())));
	    } if (rings.getAtomContainerCount() ==2) {
	    	if (selector!=null) selectRings(rings, selector);
	    	logger.debug("Bicyclic structure");
	    	return true;
	    }
	    else return false;
	}

	protected void selectRings(IRingSet rings,IAtomContainer selected) {
		for (int i=0; i < rings.getAtomContainerCount();i++)
			selected.add(rings.getAtomContainer(i));
	}
}
