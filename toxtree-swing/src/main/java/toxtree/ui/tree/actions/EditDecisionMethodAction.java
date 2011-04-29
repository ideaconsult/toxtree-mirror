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

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionMethodEditor;
import toxTree.exceptions.DecisionMethodException;
import toxtree.data.DataModule;
import toxtree.data.DecisionMethodsDataModule;
import toxtree.ui.actions.DataModuleAction;

public class EditDecisionMethodAction extends DataModuleAction {
	IDecisionMethodEditor editor;

	/**
	 * 
	 */
	private static final long serialVersionUID = -783463983411442353L;

	public EditDecisionMethodAction(DataModule module) {
		this(module,"Method options");
	}

	public EditDecisionMethodAction(DataModule module, String name) {
		this(module, name,null);
	}

	public EditDecisionMethodAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
		editor = null;
		putValue(AbstractAction.SHORT_DESCRIPTION,"Launches decision tree visualization/editor");	
	}
	public void launchEditor(final IDecisionMethod method) {
		if (method == null) return;
    	editor = method.getEditor();
    	if (editor instanceof Window) {
    		((Window)editor).addWindowListener(new WindowAdapter() {
        		@Override
				public void windowClosing(WindowEvent arg0) {
        			//boolean isModified = ((EditTreeFrame)arg0.getWindow()).isModified();
                    boolean isModified = method.isModified();
        			boolean doClose =  !isModified || (isModified &&
        	        	 (JOptionPane.showConfirmDialog(getFrame(),"The decision tree is not saved. \nAre you sure to exit without saving the decision tree?","Please confirm",JOptionPane.YES_NO_OPTION)
        	        		==JOptionPane.YES_OPTION));
        			if (doClose) {
        				((Window)editor).setVisible(false);
        				((Window)editor).dispose();
	        			editor = null;
        	        }
        		}
        	});
    	}
    	try {
    		//editor.edit(getFrame(),((DecisionMethodsDataModule) module).getRules());
    		editor.edit(getFrame(),method);
		} catch (DecisionMethodException x) {
			x.printStackTrace();
		}
    	
	}
	@Override
	public void run() {
		if (module instanceof DecisionMethodsDataModule) { 
			IDecisionMethod method = ((DecisionMethodsDataModule)module).getSelectedRules();
				launchEditor(method);
		}		
	}
}
