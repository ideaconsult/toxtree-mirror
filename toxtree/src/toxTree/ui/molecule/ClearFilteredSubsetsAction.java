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


package toxTree.ui.molecule;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import toxTree.data.DataModule;
import toxTree.data.DecisionMethodsDataModule;
import toxTree.exceptions.FilterException;
import toxTree.ui.actions.DataModuleAction;
import toxTree.ui.tree.images.ImageTools;

public class ClearFilteredSubsetsAction extends DataModuleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8665214187795337299L;

	public ClearFilteredSubsetsAction(DataModule module) {
		this(module,"Clear subsets");
	}

	public ClearFilteredSubsetsAction(DataModule module, String name) {
		this(module, name,ImageTools.getImage("cross.png"));
	}

	public ClearFilteredSubsetsAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Clears subsets");
	}

	@Override
	public void run() {
		module.getActions().allActionsEnable(false);
		try {
			((DecisionMethodsDataModule)module).clearFilters();
		} catch (FilterException x) {
			JOptionPane.showMessageDialog(getFrame(),x.getMessage());
		}
		module.getActions().allActionsEnable(true);
	}

}


