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
 * Filename: RuleSimplyBranchedAlyphatic.java
 */
package toxTree.tree.cramer;


import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleCarbohydrate;

/**
 * Implementation of Cramer rule No.5
 * Simply branched aliphatic or a common carbohydrate
 * A descendant of {@link RuleCarbohydrate}
 * @author Nina Jeliazkova <br>
 * @version 0.5, 2005-10-1
 */
public class RuleSimplyBranchedAliphaticHydrocarbon extends RuleCarbohydrate {
	public static transient String MSG_SIMPLYBRANCHED = "Simply branched\t";
	public static transient String MSG_ALIPHATIC = "Aliphatic\t";

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4822713429051030194L;
    /**
	 * Constructor
	 * 
	 */
	public RuleSimplyBranchedAliphaticHydrocarbon() {
		super();
		id = "5";
		title = "Simply branched aliphatic hydrocarbon or a common carbohydrate";
		explanation.append("<html>Is it a simply branched (I)acyclic aliphatic (A) hydrocarbon or a common carbohydrate?<p>This drops out the generally inocuous hydrocarbons and carbohydrates.</html>");
		examples[0] = "CC#CC(C)(C)C";
		examples[1] = "OC1COC(O)C(O)C1(O)"; //xylose
		editable = false;
	}
	/**
	 * 	 {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	@Override
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {

	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf ==null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);	    
		
	    IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(mol);
		int c = MolecularFormulaManipulator.getElementCount(formula,formula.getBuilder().newElement("C"));
		int h = MolecularFormulaManipulator.getElementCount(formula,formula.getBuilder().newElement("H"));

	    if ((mol.getAtomCount() == (c+h))) {  //hydrocarbon
	    	logger.info("Hydrocarbon ",MSG_YES);
		    if (mf.isAliphatic()) {
		    	logger.info(MSG_ALIPHATIC,MSG_YES);
		    	logger.warn(MSG_SIMPLYBRANCHED,"Not implemented");
		    	return !mf.isAcetylenic();
		    } else {
		    	logger.debug(MSG_ALIPHATIC,MSG_NO);
		    	return false;
		    } 
	    } else return super.verifyRule(mol);
	}
	/* (non-Javadoc)
     * @see toxTree.tree.AbstractRule#isImplemented()
     */
    @Override
	public boolean isImplemented() {
        return true;
    }
    
   

}
