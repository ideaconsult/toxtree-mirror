/*
Copyright Ideaconsult Ltd. (C) & Curios-IT (C) 2005-2008
Contact: nina@acad.bg, kazius@Curios-IT.com

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
 * Filename: RuleReHydrolysedNoPO4.java
 */
package cramer2.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.ReactionException;
import toxTree.query.MolFlags;
import toxTree.query.SimpleReactions;
import toxTree.tree.rules.RuleReadilyHydrolised;
import ambit2.core.data.MoleculeTools;
/**
 * cleaves PO4's from compounds, and analyses the remaining fragments
 * <b>Modified</b> Dec, 2008
 */
public class RuleReadilyHydrolysedPO4 extends RuleReadilyHydrolised {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 0;

    /**
	 * Constructor
	 *
	 */
	public RuleReadilyHydrolysedPO4() {
		super();
		id = "41";
		title = "Removes phosphates";
		explanation.append("<html>All phosphate groups that can occur in natural compounds are hydrolysed and removed.</html>");
		examples[0] = "COPOC";
		examples[1] = "COP(=O)(O)OC";
		editable = false;
        logger.finer("41 CHOPPING");
	}

	@Override
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    IAtomContainerSet sc = null;
        IAtomContainerSet scfinal = null;
		SimpleReactions sr = new SimpleReactions();
		try {
			sc = sr.hydrolizesPO4(mol);
		} catch (ReactionException x) {
			throw new DecisionMethodException(x);
		}
        //MFAnalyser mfatemp = null;
        if (sc == null) {//sc can be null if the Q40 SMARTS matches, but not the reaction pattern.
            logger.finer("no chopping to perform");
            return false;
        }
        for (int i=0;i<sc.getAtomContainerCount();i++) {
            IAtomContainer iaci = sc.getAtomContainer(i);
    	    IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(iaci);
    	    
    	    if (
    	    		!MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"C")) &&
    	    		!MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"N")) &&
    	    		!MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"S"))     	    		
    	    		) {
                continue;
            }
    	    iaci.setID(MolecularFormulaManipulator.getHillString(formula));
            mf.addResidue(iaci);
            
        }
	    return false;// always false
	}

}
