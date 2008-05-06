/*
Copyright (C) 2005-2006  

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
package toxTree.ui.tree.rules;

import javax.swing.Icon;

import org.openscience.cdk.interfaces.IMolecule;

import toxTree.core.IDecisionRule;
import toxTree.ui.molecule.MoleculeEditAction;
import toxTree.ui.tree.actions.IRuleAction;




/**
 * Launches JChemPaint for a molecule associated with the rule
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-12
 */
public class RuleMoleculeEditAction extends MoleculeEditAction implements IRuleAction {
	protected IDecisionRule rule =null;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 2965976481724298257L;

	/**
	 * @param molecule
	 */
	public RuleMoleculeEditAction(IMolecule molecule) {
		super(molecule);
	}

	/**
	 * @param molecule
	 * @param arg0
	 */
	public RuleMoleculeEditAction(IMolecule molecule, String arg0) {
		super(molecule, arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param molecule
	 * @param arg0
	 * @param arg1
	 */
	public RuleMoleculeEditAction(IMolecule molecule, String arg0, Icon arg1) {
		super(molecule, arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public IDecisionRule getRule() {
		return rule;
	}

	public void setRule(IDecisionRule rule) {
		this.rule = rule;

	}

}
