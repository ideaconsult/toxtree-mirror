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
package toxTree.tree.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.AbstractRule;

/**
 * Verifies if the molecule is aromatic by applying the Hueckel rule
 * 
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-2
 */
public class RuleAromatic extends AbstractRule {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1159424698300767741L;

	/**
	 * 
	 */
	public RuleAromatic() {
		super();
		id = "[Aromatic]";
		title = "Aromatic";
		explanation.append("<html>Is the substance aromatic");
		examples[0] = "C1CCCCC1";
		examples[1] = "c1ccccc1";
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.info(getID());
	    //should be set via MolAnalyser
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf ==null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    return mf.isAromatic();
	}
	/* (non-Javadoc)
     * @see toxTree.tree.AbstractRule#isImplemented()
     */
    @Override
	public boolean isImplemented() {
        return true;
    }	

}
