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
 * Tri And Tetra Halogenated Benzenes.<br>
 * Substructure  <ul>
 * <li>ClC1=CC(Cl)C(Cl)C(Cl)C1
 * <li>ClC1C=CC(Cl)C(Cl)C1(Cl)
 * <li>ClC1C=CCC(Cl)C1(Cl)
 * </ul>
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleTriAndTetraHalogenatedBenzenes extends RuleAnySubstructure {
	 private static final long serialVersionUID = 0;
	 public RuleTriAndTetraHalogenatedBenzenes() {
			//TODO fix sterically hindered condition (example NO fails)
			super();
			
			
			addSubstructure(FunctionalGroups.createAtomContainer("ClC1=CC(Cl)C(Cl)C(Cl)C1",false));
			addSubstructure(FunctionalGroups.createAtomContainer("ClC1C=CC(Cl)C(Cl)C1(Cl)",false));
			addSubstructure(FunctionalGroups.createAtomContainer("ClC1C=CCC(Cl)C1(Cl)",false));
			
			id = "70";
			title = "Tri And Tetra Halogenated Benzenes";
			
			examples[0] = "C1C=CCC(Cl)C1(Cl)";
			examples[1] = "ClC1C=CCC(Cl)C1(Cl)";
			editable = false;
		}
		/**
		 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
		 */
		public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
			logger.finer(toString());		
			return super.verifyRule(mol);
		}
		/* (non-Javadoc)
		 * @see toxTree.tree.AbstractRule#isImplemented()
		 */
		public boolean isImplemented() {
			return true;
		}
}
