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
package toxtree.ui.tree.actions;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.core.IRuleSubstructures;
import toxTree.query.FunctionalGroups;
import toxTree.query.QueryAtomContainers;
import toxtree.ui.tree.QueryAtomContainersModel;
import toxtree.ui.tree.SelectListDialog;

/**
 * Adds a new substructure from a list of predefined substructures
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-22
 */
public class SubstructureFromListAction extends AbstractRuleAction {
	private static final long serialVersionUID = 3623439416208828042L;
	private static QueryAtomContainers funcGroupsList;

	/**
	 * @param rule
	 */
	public SubstructureFromListAction(IDecisionRule rule) {
		super(rule);
		initGroups();
	}

	/**
	 * @param rule
	 * @param arg0
	 */
	public SubstructureFromListAction(IDecisionRule rule, String arg0) {
		super(rule, arg0);
		initGroups();
	}

	/**
	 * @param rule
	 * @param arg0
	 * @param arg1
	 */
	public SubstructureFromListAction(IDecisionRule rule, String arg0, Icon arg1) {
		super(rule, arg0, arg1);
		initGroups();
	}
	protected void initGroups() {
		funcGroupsList = FunctionalGroups.getAllGroups();
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (rule instanceof IRuleSubstructures) {
			IRuleSubstructures r = (IRuleSubstructures) rule;
            /*
			Object o = getValue(AbstractTreeAction.PARENTKEY);
			Component parent = null;
			if ((o!=null) && (o  instanceof Component)) parent = (Component) o;
            */
			Object o = SelectListDialog.selectFromList(
					getParentFrame(),
					"Select functional group",
					"Functional groups", 
					new QueryAtomContainersModel(funcGroupsList,rule), null);
			if (o != null) {
				r.addSubstructure((IAtomContainer)o);
			}
		}
	}

}
