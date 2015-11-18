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
/**
 * Created on 2005-5-2

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree.rules
 * Filename: RuleLactonesFusedOrUnsaturated.java
 */






import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.QueryAtomContainers;
import toxTree.tree.rules.RuleAnySubstructure;

/**
 * RuleLactonesFusedOrUnsaturated.
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleLactonesFusedOrUnsaturated extends RuleAnySubstructure {
	//protected transient QueryAtomContainer lactoneBreakable = null;
	protected transient QueryAtomContainer lactoneUnsaturated = null;
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4333357476318163143L;

    /**
	 * Constructor
	 * 
	 */
	public RuleLactonesFusedOrUnsaturated() throws Exception {
		super();
		lactoneUnsaturated = FunctionalGroups.lactone(true);
		id = "48";
		title = "Lactone, fused to another ring, or 5- or 6-membered a,b-unsaturated lactone?";
		explanation.append("<html>Is it a ");
		explanation.append(title);
		explanation.append("</html>");
		examples[0] = "C1CCC(O1)C2=CC=CC=C2";
		examples[1] = "O=C1OCCC1(=C)";
		editable = false;
	}
	@Override
	protected QueryAtomContainers initQuery() throws Exception {
		query = super.initQuery();
		addSubstructure(FunctionalGroups.lactone(false));
		return query;
	}	
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.finer(toString());
		return super.verifyRule(mol);
	}	
	public boolean isImplemented() {
		return true;
	}
}
