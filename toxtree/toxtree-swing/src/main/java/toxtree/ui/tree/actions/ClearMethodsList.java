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

import javax.swing.AbstractAction;
import javax.swing.Icon;

import toxTree.io.Tools;
import toxtree.data.DecisionMethodsDataModule;
import toxtree.ui.actions.DataModuleAction;

public class ClearMethodsList extends DataModuleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6753371259514111318L;

	public ClearMethodsList(DecisionMethodsDataModule module) {
		this(module,"Clear forest");

	}

	public ClearMethodsList(DecisionMethodsDataModule module, String name) {
		this(module, name,Tools.getImage("cross.png"));
	}

	public ClearMethodsList(DecisionMethodsDataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION, "Clears list of decision trees");
	}

	@Override
	public void run() throws Exception {
		((DecisionMethodsDataModule)module).getMethods().clear();

	}

}


