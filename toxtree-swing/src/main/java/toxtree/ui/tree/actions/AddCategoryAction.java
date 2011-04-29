/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

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
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import toxTree.core.IDecisionCategory;
import toxTree.io.Tools;
import toxtree.ui.tree.SelectListDialog;

/**
 * A descendant of {@link AbstractAction}
 * Used in {@link toxTree.ui}
 * Launches a dialog with a list of all available categories {@link toxTree.core.IDecisionCategory}
 * and adds the selected one to the list 
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-9
 */
public class AddCategoryAction extends AbstractAction {
	List list;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -8925342733654963005L;

	/**
	 * @param list should be {@link toxTree.core.IDecisionCategories}
	 */
	public AddCategoryAction(List list) {
		this(list,"Add category");
	}

	/**
	 * @param list of categories {@link toxTree.core.IDecisionCategories}
	 * @param name Action name
	 */
	public AddCategoryAction(List list, String name) {
		this(list,name,Tools.getImage("tag_blue_add.png"));
		
	}

	/**
	 * 
	 * @param list
	 * @param name
	 * @param icon
	 */
	public AddCategoryAction(List list, String name, Icon icon) {
		super( name, icon);
		this.list = list;
		putValue(AbstractAction.SHORT_DESCRIPTION,"Adds a new category.");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = getValue(AbstractTreeAction.PARENTKEY);
		Component parent = null;
		if ((o!=null) && (o  instanceof Component)) parent = (Component) o;
		o = SelectListDialog.selectNewCategory(parent,
				this.getClass().getClassLoader());
		if ((o != null) && (o instanceof IDecisionCategory)) {
				list.add((IDecisionCategory)o);
			}

	}

}
