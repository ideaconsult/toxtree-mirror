/*
Copyright Ideaconsult Ltd.(C) 2006  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package sicret.rules;


import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.RuleAnySubstructure;
import verhaar.query.FunctionalGroups;


/**
 * Carbamoyl Halide.<br>
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleCarbamoylHalide extends RuleAnySubstructure {
	public final static transient String MSG_18H="Acyclic group";
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 0;

    /**
	 * Constructor
	 * 
	 */
	public RuleCarbamoylHalide() {

		super();		
		QueryAtomContainer q = FunctionalGroups.createAutoQueryContainer("[*]N([H])C(=O)Cl");
		addSubstructure(q);
		QueryAtomContainer q1 = FunctionalGroups.createAutoQueryContainer("[*]N([H])C(=O)Br");
		addSubstructure(q1);
		QueryAtomContainer q2 = FunctionalGroups.createAutoQueryContainer("[*]N([H])C(=O)F");
		addSubstructure(q2);
		QueryAtomContainer q3 = FunctionalGroups.createAutoQueryContainer("[*]N([H])C(=O)I");
		addSubstructure(q3);
		
		id = "63";
		title = "CarbamoylHalide";
		examples[0] = "N([H])C(=O)";
		examples[1] = "N([H])C(=O)Cl";		
		editable = false;
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.info(toString());		
		return super.verifyRule(mol);
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	public boolean isImplemented() {
		return true;
	}
}

