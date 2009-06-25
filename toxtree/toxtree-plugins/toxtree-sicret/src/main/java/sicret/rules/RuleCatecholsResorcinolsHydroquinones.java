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

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.rules.RuleAnySubstructure;

/**
 * Catechols, Resorcinols Hydroquinones.<br>
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleCatecholsResorcinolsHydroquinones extends RuleAnySubstructure {
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 0;

    /**
	 * Constructor
	 * 
	 */
	public RuleCatecholsResorcinolsHydroquinones() {
		//TODO fix sterically hindered condition (example NO fails)
		super();

		addSubstructure(FunctionalGroups.createAtomContainer("OC1=CC(O)=CC=C1",false));	
		addSubstructure(FunctionalGroups.createAtomContainer("OC1=C(O)C=CC=C1",false));
		addSubstructure(FunctionalGroups.createAtomContainer("OC1=CC=C(O)C=C1",false));
		
		id = "43";
		title = "CatecholsResorcinolsHydroquinones";
		
		examples[0] = "OC1=CC=C(N)C=C";
		examples[1] = "OC1=C(O)C=CC=C1";	
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
