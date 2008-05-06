/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
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


package toxTree.ui.tree.actions;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionMethod;
import toxTree.ui.tree.images.ImageTools;

public class VerifyUnusedCategories extends AbstractTreeAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5183327943193841464L;

	public VerifyUnusedCategories(IDecisionMethod tree) {
		this(tree,"Remove unused categories");
	}

	public VerifyUnusedCategories(IDecisionMethod tree, String name) {
		this(tree, name,ImageTools.getImage("tag_blue_delete.png"));
	}

	public VerifyUnusedCategories(IDecisionMethod tree, String name, Icon icon) {
		super(tree, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION, "Verifies if unused categories exists and remove them after user confirmation");
	}

	public void actionPerformed(ActionEvent arg0) {
		if (tree != null) {
			IDecisionCategories unused = tree.hasUnusedCategories();
			if (unused != null)
	       	 	if (JOptionPane.showConfirmDialog(getParentFrame(),
	           	 		"The decision tree has unused categories. \nDelete unused categories?","Please confirm",JOptionPane.YES_NO_OPTION)
	            		==JOptionPane.YES_OPTION) {
	           	 		IDecisionCategories categories = tree.getCategories();
	           	 		for (int i=0;i<unused.size();i++) categories.remove(unused.get(i));
	           	 		
	           	 		//((Observable)rules).setChanged();
	           	 		if (categories instanceof Observable) {
	           	 			
	           	 			((Observable)categories).notifyObservers();
	           	 		}	
	           	 	}
	       	 	else {}
			else JOptionPane.showMessageDialog(getParentFrame(), "No unused categories");
		}
	}

}


