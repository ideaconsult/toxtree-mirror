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

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import toxTree.data.DataModule;
import toxTree.data.ToxTreeActions;
import toxTree.io.MolFileFilter;
import toxTree.ui.tree.images.ImageTools;

/**
 * Loads a file inti {@link toxTree.data.ToxTreeModule#getDataContainer()}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class OpenFileAction extends DataModuleAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -3383975975928183069L;

	/**
	 * @param module
	 */
	public OpenFileAction(DataModule module) {
		this(module,"Open");
	}

	/**
	 * @param module
	 * @param name
	 */
	public OpenFileAction(DataModule module, String name) {
		this(module, name,ImageTools.getImage("folder.png"));
	}

	/**
	 * @param module
	 * @param name
	 * @param icon
	 */
	public OpenFileAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
        putValue(AbstractAction.SHORT_DESCRIPTION,"Opens a file with chemical compounds");
        putValue(AbstractAction.MNEMONIC_KEY,new Integer(KeyEvent.VK_O));
		
	}

	
	@Override
	public void run() {
		//module.getActions().allActionsEnable(true);
        File file = ToxTreeActions.selectFile(module.getActions().getFrame(), 
        		MolFileFilter.supported_extensions,MolFileFilter.supported_exts_description
        		, true);
        if (file != null) {
        	//module.getActions().allActionsEnable(false);
        	
        	module.getDataContainer().openFile(file);
        	
        	//module.getActions().allActionsEnable(true);
        }

	}

}
