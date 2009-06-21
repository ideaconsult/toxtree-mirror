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
package toxTree.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import toxTree.data.DataModule;
import toxTree.data.DecisionMethodsDataModule;
import toxTree.data.ToxTreeActions;
import toxTree.io.Tools;

/**
 * Clears the result of the current compound
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class ClearResultAction extends DataModuleAction {
	public static final String _aClearResults = "Clear results";
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 5761451917137448393L;

	/**
	 * @param module
	 */
	public ClearResultAction(DataModule module) {
		this(module,_aClearResults);
	}

	/**
	 * @param module
	 * @param name
	 */
	public ClearResultAction(DataModule module, String name) {
		this(module, name,Tools.getImage("cross.png"));
	}

	/**
	 * @param module
	 * @param name
	 * @param icon
	 */
	public ClearResultAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
    	module.getActions().enable(false,ToxTreeActions._HazardAction);
    	((DecisionMethodsDataModule)module).clearResults();
    	module.getActions().enable(true,ToxTreeActions._HazardAction);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
