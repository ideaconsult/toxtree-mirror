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

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import toxTree.core.IDecisionMethod;
import toxTree.io.Tools;
import toxtree.data.DecisionMethodsDataModule;
import toxtree.ui.actions.DataModuleAction;
import toxtree.ui.tree.SelectListDialog;

/**
 * Uses {@link toxtree.ui.tree.SelectListDialog#selectNewTree(Component, ClassLoader)} to present a list of classes that 
 * implement {@link toxTree.core.IDecisionMethod} . If the user selects a tree, it is instantiated and added to 
 * the list of decision trees {@link toxTree.core.IDecisionMethodsList} in 
 * {@link toxtree.data.ToxTreeModule}
 * @author Nina Jeliazkova
 *
 */
public class SelectNewTreeAction extends DataModuleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5669451306467326673L;

	public SelectNewTreeAction(DecisionMethodsDataModule module) {
		this(module,"Select tree");
	}

	public SelectNewTreeAction(DecisionMethodsDataModule module, String name) {
		this(module, name,Tools.getImage("plugin.png"));

	}

	public SelectNewTreeAction(DecisionMethodsDataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Selects a tree from existing ones and adds it to the list");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
   			Object o = SelectListDialog.selectNewTree(
   					module.getActions().getFrame(),
   					this.getClass().getClassLoader());
   			if ((o != null) && (o instanceof IDecisionMethod)) 
                ((DecisionMethodsDataModule) module).getMethods().addDecisionMethod((IDecisionMethod)o);
	}
	@Override
	public void runAction() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
