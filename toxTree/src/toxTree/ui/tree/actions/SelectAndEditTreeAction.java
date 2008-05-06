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

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Icon;

import toxTree.core.IDecisionMethod;
import toxTree.core.Introspection;
import toxTree.data.DecisionMethodsDataModule;
import toxTree.ui.tree.ListTableModel;
import toxTree.ui.tree.SelectListDialog;
import toxTree.ui.tree.images.ImageTools;

/**
 * Selects available tree types, makes a copy if it is read-only and launches a decision tree editor
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class SelectAndEditTreeAction extends EditDecisionMethodAction {
    protected ActionMap treeActions;
	public static final String _aEditMethod = "Select from list";    
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -8584820332812646355L;

	/**
	 * @param module
	 */
	public SelectAndEditTreeAction(DecisionMethodsDataModule module) {
		this(module,_aEditMethod);
	}

	/**
	 * @param module
	 * @param name
	 */
	public SelectAndEditTreeAction(DecisionMethodsDataModule module, String name) {
		this(module, name,ImageTools.getImage("plugin_edit.png"));
		
	}

	/**
	 * @param module
	 * @param name
	 * @param icon
	 */
	public SelectAndEditTreeAction(DecisionMethodsDataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Selects a tree from a list of available trees and launches a decision tree editor");		
		prepareActions();
	}
	protected void prepareActions() {
        treeActions = new ActionMap();
        //treeNodeActions.put("New",new SelectNewTreeAction(this,"New tree"));
        treeActions.put("Open tree",new LoadTreeAction((DecisionMethodsDataModule)module,"Load from file"));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
    	Object selectedObject = SelectListDialog.selectFromList(
    			module.getActions().getFrame(),
    			"Select a tree to edit",
    			"Available decision trees",
    			new ListTableModel(((DecisionMethodsDataModule)module).getMethods()),null);
    	if ((selectedObject != null) && (selectedObject instanceof IDecisionMethod)) {
    		IDecisionMethod tree = (IDecisionMethod) selectedObject;
    		if (!tree.isEditable()) {
    			Object o = Introspection.loadCreateObject(tree.getClass().getName());
    			if (o != null) {
    				tree = (IDecisionMethod) o;
    				tree.setTitle("Copy of "+tree.getTitle());
    				tree.setModified(true);
    				//module.getMethods().addDecisionMethod(tree);
    			}
    			else tree =null;
    		} else tree.setModified(false);
    		
    		if (tree !=null)
    			launchEditor(tree);
    	}
		

	}

}
