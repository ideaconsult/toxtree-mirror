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

import javax.swing.AbstractAction;
import javax.swing.Icon;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.io.Tools;

/**
 * Edits a category
 * 
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class EditCategoryAction extends AbstractTreeAction  implements ICategoryAction {
	protected IDecisionCategory category = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2505268379464961009L;

	public EditCategoryAction(IDecisionMethod tree) {
		this(tree,"Edit category");
	}

	public EditCategoryAction(IDecisionMethod tree, String name) {
		this(tree, name,Tools.getImage("tag_blue_edit.png"));
	}

	public EditCategoryAction(IDecisionMethod tree, String name, Icon icon) {
		super(tree, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Modifies category details");	}

	public void actionPerformed(ActionEvent arg0) {
		if (category == null) return;
		
        category.getEditor().edit(getParentFrame(), category);
        /*
		Object selectedValue = JOptionPane.showInputDialog(parent, 
				"Enter category name", category.getName());
		category.setName(selectedValue.toString());
		*/

		
	}
	public IDecisionCategory getCategory() {
		return category;
	}
	public void setCategory(IDecisionCategory category) {
		this.category = category;
		
	}

}
