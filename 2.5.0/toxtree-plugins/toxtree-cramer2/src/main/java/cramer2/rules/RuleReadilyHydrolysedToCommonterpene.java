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
 * Filename: RuleReadilyHydrolisedToCommonterpene.java
 */
package cramer2.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleReadilyHydrolised;

/**
 * Rule 17 of the Cramer scheme (see {@link cramer2.CramerRulesExtendedExtended})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleReadilyHydrolysedToCommonterpene extends RuleReadilyHydrolised {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6559369329324069745L;

    /**
	 * Constructor
	 * 
	 */
	public RuleReadilyHydrolysedToCommonterpene() {
		super();
		id = "17";
		title = "Readily hydrolysed to a common terpene";
		explanation.append("<html>Is the substance <i>readily hydrolysed</I> (H) to a <i>common terpene</i> (D),");
		explanation.append("-alcohol, -aldehyde or -carboxylic acid?<p>");
		explanation.append("if the answer is YES, treat the hydrolysed residues separately and proceed to Q.18 for the terpene moiety and to Q19 for any non-terpenoid moiety).<p>");
		explanation.append("Since there may be substances that are hydrolysed to two or more residues, one of which is terpene, treat the residues separately from Q18 onward to conclusion.</html>");
		examples[0] = "CCC(=O)\\C=C\\C1C=CCCC1(C)C";
		examples[1] = "COC(OC)C1=CC=C(C=C1)C(C)C";
		editable = false;
	}
	@Override
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		if (!super.verifyRule(mol))  {
			logger.debug("not readily hydrolysed");
			return false; //not readily hydrolysed at all
		}
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    IAtomContainerSet sc = mf.getHydrolysisProducts();
	    //sc is not null since we passed the inherited method
	    for (int i=0; i<sc.getAtomContainerCount();i++) {
	    	if (FunctionalGroups.isCommonTerpene(sc.getAtomContainer(i))) {
	    		mf.setResidues(sc);
	    		return true;
	    	}
	    	logger.debug(new Integer((i+1))," Residue not a common terpene");
	    }
	    sc = null;
	    return false;
	}

}
