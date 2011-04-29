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
import toxtree.ui.tree.SelectListDialog;

/**
 * Shows a list of available categories and adds the selected one to the list of categories.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-9
 */
public class SelectCategoryFromListAction extends AbstractTreeAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1555737259542981434L;

	/**
	 * @param tree
	 */
	public SelectCategoryFromListAction(IDecisionMethod tree) {
		this(tree,"New category");
	}

	/**
	 * @param tree
	 * @param name
	 */
	public SelectCategoryFromListAction(IDecisionMethod tree, String name) {
		this(tree, name,Tools.getImage("tag_blue.png"));
	}

	/**
	 * @param tree
	 * @param name
	 * @param icon
	 */
	public SelectCategoryFromListAction(IDecisionMethod tree, String name, Icon icon) {
		super(tree, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Selects new category from the list and adds it to the categories used in the tree.");

	}

	public void actionPerformed(ActionEvent e) {
        /*
		Object o = getValue(AbstractTreeAction.PARENTKEY);
		Component parent = null;
		if ((o!=null) && (o  instanceof Component)) parent = (Component) o;
		else parent = null;
        */
		Object o = SelectListDialog.selectNewCategory(getParentFrame(),
				this.getClass().getClassLoader());
		if ((o != null) && (o instanceof IDecisionCategory)) {
			tree.getCategories().addCategory((IDecisionCategory)o);
			}
	}

}
