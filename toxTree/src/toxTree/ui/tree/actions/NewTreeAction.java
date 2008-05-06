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
import javax.swing.Icon;

import toxTree.core.IDecisionMethod;
import toxTree.data.DecisionMethodsDataModule;
import toxTree.tree.UserDefinedTree;
import toxTree.ui.tree.images.ImageTools;

/**
 * Creates new {@link UserDefinedTree} and launches {@link toxTree.ui.tree.EditTreeFrame} 
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-22
 */
public class NewTreeAction extends EditDecisionMethodAction {
	public static final String _aNewMethod = "New decision tree";    
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 5669451306467326673L;

	/**
	 * @param module
	 */
	public NewTreeAction(DecisionMethodsDataModule module) {
		this(module,_aNewMethod);
	}

	/**
	 * @param module
	 * @param name
	 */
	public NewTreeAction(DecisionMethodsDataModule module, String name) {
		this(module, name,ImageTools.getImage("page_white.png"));
	}

	/**
	 * @param module
	 * @param name
	 * @param icon
	 */
	public NewTreeAction(DecisionMethodsDataModule module, String name, Icon icon) {
		super(module, name, icon);
		editor = null;
		putValue(AbstractAction.SHORT_DESCRIPTION,"Creates new empty tree and launches decision tree editor.");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		IDecisionMethod newtree = new UserDefinedTree();
        newtree.setTitle("New decision tree");
        newtree.setExplanation("Empty decision tree");
		newtree.setModified(true);
		launchEditor(newtree);
       ((DecisionMethodsDataModule) module).getMethods().addDecisionMethod(newtree);
		//TODO verify if it is saved and make current if user wants
		//module.getMethods().addDecisionMethod(newtree);
	}

}
