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
import javax.swing.Icon;

import toxTree.data.DataModule;
import toxTree.data.ToxTreeModule;
import toxTree.io.Tools;
import toxTree.ui.actions.DataModuleAction;

/**
 * Views a {@link toxTree.core.IDecisionMethod}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class ViewTreeAction extends DataModuleAction {
	public static final String _aViewMethod = "View decision tree";
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -6269437325466855082L;

	/**
	 * @param module
	 */
	public ViewTreeAction(DataModule module) {
		this(module,_aViewMethod);
	}

	/**
	 * @param module
	 * @param name
	 */
	public ViewTreeAction(DataModule module, String name) {
		this(module, name,Tools.getImage("tree.png"));
	}

	/**
	 * @param module
	 * @param name
	 * @param icon
	 */
	public ViewTreeAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(Action.SHORT_DESCRIPTION,"Visualize the selected decision tree.");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		((ToxTreeModule) module).viewMethod(! ((ToxTreeModule) module).getTreeResult().isEstimating());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
