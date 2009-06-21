/*
Copyright (C) 2005-2007  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package toxTree.ui.tree.actions;

import javax.swing.Action;
import javax.swing.Icon;

import toxTree.core.IDecisionInteractive;
import toxTree.core.IDecisionMethod;
import toxTree.data.DataModule;
import toxTree.data.ToxTreeModule;
import toxTree.io.Tools;
import toxTree.ui.actions.DataModuleAction;

public class TreeOptionsAction extends DataModuleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4763457462786443549L;

	public TreeOptionsAction(DataModule module) {
		this(module,"Decision tree options");
	}

	public TreeOptionsAction(DataModule module, String name) {
		this(module, name,Tools.getImage("cog.png"));
	}

	public TreeOptionsAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(Action.SHORT_DESCRIPTION,"Allows to set several options, specific to the selected decision tree.");

	}

	@Override
	public void run() {
		IDecisionMethod m = ((ToxTreeModule) module).getRules();
		boolean interactive = true;
		if (m instanceof IDecisionInteractive) {
			interactive = ((IDecisionInteractive)m).getInteractive();
			((IDecisionInteractive)m).setInteractive(true);
		}
		((ToxTreeModule) module).getRules().setParameters(null);
		if (m instanceof IDecisionInteractive) {
			((IDecisionInteractive)m).setInteractive(interactive);
		}
	}

}


