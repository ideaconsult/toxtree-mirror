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
package toxTree.ui.tree.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;

import toxTree.core.IDecisionMethod;
import toxTree.data.DecisionMethodsDataModule;
import toxTree.data.ToxTreeModule;
import toxTree.ui.actions.DataModuleAction;
import toxTree.ui.tree.ListTableModel;
import toxTree.ui.tree.SelectListDialog;
import toxTree.ui.tree.images.ImageTools;

/**
 * 
 * Selects a tree from available decision trees
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class SelectTreeAction extends DataModuleAction {
	private static final long serialVersionUID = -3751562922607916214L;
	public static final String _aSelectMethod = "Select a decision tree";

	public SelectTreeAction(DecisionMethodsDataModule module) {
		this(module,_aSelectMethod);
	}

	public SelectTreeAction(DecisionMethodsDataModule module, String name) {
		this(module, name,ImageTools.getImage("plugin.png"));
	}

	public SelectTreeAction(DecisionMethodsDataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(Action.SHORT_DESCRIPTION,"Select a new decision tree from the available ones.");

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
			ActionMap actions = new ActionMap();
			actions.put("Open",new LoadTreeAction((DecisionMethodsDataModule)module,"Load from file"));
			Object o = SelectListDialog.selectFromList(
					module.getActions().getFrame(),
					"Select a tree",
					"Available decision trees",
					new ListTableModel(((DecisionMethodsDataModule) module).getMethods()),actions);
			addTree((IDecisionMethod)o);

	}
	public void addTree(IDecisionMethod tree) {
            if (tree != null) {
                ((ToxTreeModule) module).setRules(tree);
            }        
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
