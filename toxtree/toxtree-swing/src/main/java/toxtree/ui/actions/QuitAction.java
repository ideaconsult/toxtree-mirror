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
package toxtree.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import toxTree.io.Tools;
import toxtree.data.DataModule;
import ambit2.base.config.Preferences;

/**
 * Quit {@link toxTree.apps.ToxTreeApp}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class QuitAction extends DataModuleAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 607922353225947542L;

	/**
	 * @param module
	 */
	public QuitAction(DataModule module) {
		this(module,"Exit");
	}

	/**
	 * @param module
	 * @param name
	 */
	public QuitAction(DataModule module, String name) {
		this(module, name,Tools.getImage("door_out.png"));
	}

	/**
	 * @param module
	 * @param name
	 * @param icon
	 */
	public QuitAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.MNEMONIC_KEY,new Integer(KeyEvent.VK_Q));
		putValue(AbstractAction.SHORT_DESCRIPTION,"Exits");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Preferences.saveProperties(module.toString());
		} catch (Exception x) {
			x.printStackTrace();
		}
		 	Runtime.getRuntime().runFinalization();						 
   		 	Runtime.getRuntime().exit(0);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
