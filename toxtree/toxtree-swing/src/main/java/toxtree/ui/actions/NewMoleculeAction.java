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
import javax.swing.JOptionPane;

import toxTree.io.Tools;
import toxtree.data.DataContainer;

/**
 * Creates new empty molecule
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class NewMoleculeAction extends DataContainerAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1751471309451329195L;

	/**
	 * @param module
	 */
	public NewMoleculeAction(DataContainer dataContainer) {
		this(dataContainer,"New molecule");
	}

	/**
	 * @param module
	 * @param name
	 */
	public NewMoleculeAction(DataContainer dataContainer, String name) {
		this(dataContainer, name,Tools.getImage("page_white.png"));
	}

	/**
	 * @param module
	 * @param name
	 * @param icon
	 */
	public NewMoleculeAction(DataContainer dataContainer, String name, Icon icon) {
		super(dataContainer, name, icon);
        putValue(AbstractAction.SHORT_DESCRIPTION,"Creates an empty compound");
        putValue(AbstractAction.MNEMONIC_KEY,new Integer(KeyEvent.VK_N));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
    	if (dataContainer.loadedFromFile()) { 
    		int n = JOptionPane.showConfirmDialog(
    			    null,
    			    "This will clear the list of molecules loaded from file",
    			    "",
    			    JOptionPane.OK_CANCEL_OPTION);
    		switch (n) {
    		case JOptionPane.OK_OPTION: { dataContainer.newMolecule(); break; }
    		case JOptionPane.CANCEL_OPTION: { break; }            		
    		}
    	} else dataContainer.newMolecule();


	}

}
