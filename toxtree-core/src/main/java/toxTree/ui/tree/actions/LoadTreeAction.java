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
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionMethodsList;
import toxTree.core.Introspection;
import toxTree.data.DecisionMethodsDataModule;
import toxTree.data.ToxTreeActions;
import toxTree.exceptions.IntrospectionException;
import toxTree.io.MolFileFilter;
import toxTree.io.Tools;
import toxTree.ui.actions.DataModuleAction;

public class LoadTreeAction extends DataModuleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3106015502941829943L;

	public LoadTreeAction(DecisionMethodsDataModule module) {
		this(module,"Load from file");
	}

	public LoadTreeAction(DecisionMethodsDataModule module, String name) {
		this(module, name,Tools.getImage("folder.png"));
	}

	public LoadTreeAction(DecisionMethodsDataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Loads a tree from user selected file (*.tml | *.tree)");
	}
	/*
    public void loadRules(File file,InputStream stream) {
		try {
		    ObjectInputStream in = new ObjectInputStream(stream);
		    IDecisionMethod tree  = (IDecisionMethod) in.readObject();
		    tree.setModified(false);
		    if (tree != null)  {
		    	tree.setTitle(file.getAbsolutePath());
			    module.getMethods().add(tree);
		    }
    		JOptionPane.showMessageDialog(null,
    				"info",
				    "Rules loaded ",						
				    JOptionPane.INFORMATION_MESSAGE);        			
		} catch (Exception e) {
			e.printStackTrace();
			
		JOptionPane.showMessageDialog(null,
				e.getMessage(),
			    "Error when loading rules from",						
			    JOptionPane.INFORMATION_MESSAGE);
		}
   }    	
    */

	@Override
	public void actionPerformed(ActionEvent arg0) {
        File file = ToxTreeActions.selectFile(null,  MolFileFilter.toxTree_ext , MolFileFilter.toxTree_ext_descr ,true);
        if (file == null) return;
        try {
        	FileInputStream in = new FileInputStream(file);
        	if (file.getAbsolutePath().toLowerCase().endsWith(".tml")) {
        		IDecisionMethod tree = Introspection.loadRulesXML(in,file.getAbsolutePath());
        		if (tree != null)
        			((DecisionMethodsDataModule) module).getMethods().addDecisionMethod(tree);
        		else throw new IntrospectionException("Decision tree not loaded!");
        	} else if (file.getAbsolutePath().toLowerCase().endsWith(".tree")) {
	            IDecisionMethod tree = Introspection.loadRules(in,file.getAbsolutePath());
	            if (tree != null)
	            	((DecisionMethodsDataModule) module).getMethods().addDecisionMethod(tree);
	            else throw new IntrospectionException("Decision tree not loaded!");
        	} else if (file.getAbsolutePath().toLowerCase().endsWith(".fml")) {
	            IDecisionMethodsList trees = Introspection.loadForestXML(in);
	            if (trees != null)
		            for (int i=0; i < trees.size(); i++)
		            ((DecisionMethodsDataModule) module).getMethods().addDecisionMethod(trees.getMethod(i));
	            else throw new IntrospectionException("Forest not loaded!");
        	} else if (file.getAbsolutePath().toLowerCase().endsWith(".forest")) {
	            IDecisionMethodsList trees = Introspection.loadForest(in);
	            if (trees != null)
		            for (int i=0; i < trees.size(); i++)
		            ((DecisionMethodsDataModule) module).getMethods().addDecisionMethod(trees.getMethod(i));
	            else throw new IntrospectionException("Forest not loaded!");
        	}

            //loadRules(file,in);
            try {
                in.close();
            } catch (IOException x) {
        		JOptionPane.showMessageDialog(module.getActions().getFrame(),
        				x.getMessage(),
        			    "Error when loading rules",						
        			    JOptionPane.INFORMATION_MESSAGE);
            }
    	} catch (FileNotFoundException x) {
    		JOptionPane.showMessageDialog(module.getActions().getFrame(),
    				x.getMessage(),
    			    "Error when loading rules",						
    			    JOptionPane.INFORMATION_MESSAGE);
    	} catch (IOException x) {
    		JOptionPane.showMessageDialog(module.getActions().getFrame(),
    				x.getMessage(),
    			    "Error when loading rules",						
    			    JOptionPane.INFORMATION_MESSAGE);       		
    	} catch (IntrospectionException x) {
    		JOptionPane.showMessageDialog(module.getActions().getFrame(),
    				x.getMessage(),
    			    "Error creating rules",						
    			    JOptionPane.INFORMATION_MESSAGE);                
    	}


	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
