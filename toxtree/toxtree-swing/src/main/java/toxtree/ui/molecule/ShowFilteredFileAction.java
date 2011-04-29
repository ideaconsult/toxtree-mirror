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


package toxtree.ui.molecule;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import toxTree.exceptions.FilterException;
import toxTree.io.Tools;
import toxtree.data.DecisionMethodsDataModule;
import toxtree.ui.actions.DataModuleAction;

public class ShowFilteredFileAction extends DataModuleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3721636787386952400L;

	public ShowFilteredFileAction(DecisionMethodsDataModule module) {
		this(module,"Subsets");
	}

	public ShowFilteredFileAction(DecisionMethodsDataModule module, String name) {
		this(module, name,Tools.getImage("application_cascade.png"));
	}

	public ShowFilteredFileAction(DecisionMethodsDataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Select which subset of chemical compounds to display");
	}

	@Override
	public void run() {
		module.getActions().allActionsEnable(false);
		try {
			((DecisionMethodsDataModule)module).selectFilter(getFrame());
		} catch (FilterException x) {
			JOptionPane.showMessageDialog(getFrame(),x.getMessage());
		}
		module.getActions().allActionsEnable(true);

	}

}


