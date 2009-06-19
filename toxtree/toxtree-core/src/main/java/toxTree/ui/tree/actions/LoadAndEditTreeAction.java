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
import java.io.File;
import java.io.FileInputStream;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import toxTree.core.IDecisionMethod;
import toxTree.core.Introspection;
import toxTree.data.DataModule;
import toxTree.data.ToxTreeActions;
import toxTree.io.MolFileFilter;
import toxTree.ui.tree.images.ImageTools;

/**
 * Loads a decision tree from a file and launches a decision tree editor
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class LoadAndEditTreeAction extends EditDecisionMethodAction {
	public static final String _aEditMethod = "Load from file";
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -8466744301351947086L;

	/**
	 * @param module
	 */
	public LoadAndEditTreeAction(DataModule module) {
		this(module,_aEditMethod);
	}

	/**
	 * @param module
	 * @param name
	 */
	public LoadAndEditTreeAction(DataModule module, String name) {
		this(module, name,ImageTools.getImage("folder.png"));
	}

	/**
	 * @param module
	 * @param name
	 * @param icon
	 */
	public LoadAndEditTreeAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Loads a tree from a file and launches a decision tree editor");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
        File file = ToxTreeActions.selectFile(null, MolFileFilter.toxTree_ext , MolFileFilter.toxTree_ext_descr ,true);
        if (file == null) return;
        try {
        	FileInputStream in = new FileInputStream(file);
        	IDecisionMethod tree = null;
        	if (file.getAbsolutePath().toLowerCase().endsWith(".tml")) {
        		tree = Introspection.loadRulesXML(in,file.getAbsolutePath());
        	} else if (file.getAbsolutePath().toLowerCase().endsWith(".tree")) {
	            tree = Introspection.loadRules(in,file.getAbsolutePath());
        	} else throw new Exception("Unsupported format");

            if (tree !=null) {
    			launchEditor(tree);
            }
            try {
                in.close();
            } catch (Exception x) {
            	//x.printStackTrace();
        		JOptionPane.showMessageDialog(null,
        				x.getMessage(),
        			    "Error when loading rules from",						
        			    JOptionPane.ERROR_MESSAGE);
            }
                
    	} catch (Exception x) {
    		JOptionPane.showMessageDialog(null,
    				x.getMessage(),
    			    "Error when loading rules from",						
    			    JOptionPane.ERROR_MESSAGE);                
    	}



	}

}
