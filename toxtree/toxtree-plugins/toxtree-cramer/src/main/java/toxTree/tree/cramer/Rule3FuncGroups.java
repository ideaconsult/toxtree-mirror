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

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.AbstractRule;

/**
 * Rule 21 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class Rule3FuncGroups extends AbstractRule {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 300792171250153478L;

    /**
	 * Constructor
	 * 
	 */
	public Rule3FuncGroups() {
		super();
		id = "21";
		title = "3 or more different functional groups";
		explanation.append("<html>Does the structure contain >= 3 different types of functional groups (exclude methoxy and consider acids and esters as one functional type)?<p>");
		explanation.append("<I>Aliphatic</i> (A) compounds containing three or more different functional groups (excluding methoxy) are too complex to permit satisfactory prediction of toxicity. they should go therefore, into class III.");
		explanation.append("however, we do not wish to put into class III polyesters and similar substances, so these and the methoxy compounds get passed along to Q18.</html>");
		examples[0] = "CCCCOC(=O)CC(CC(=O)OCCCC)OC(C)=O";
		examples[1] = "[H]C(N)(CCSCC)C(O)=O";
		editable = false;
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return true;
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.info(toString());
		try {
			return FunctionalGroups.hasManyDifferentFunctionalGroups(mol,3);
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}

}
