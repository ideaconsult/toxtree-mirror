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

import java.awt.Component;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import toxTree.core.IDecisionMethodsList;
import toxTree.core.Introspection;
import toxTree.io.MolFileFilter;
import toxTree.io.Tools;
import toxtree.data.DecisionMethodsDataModule;
import toxtree.data.ToxTreeActions;
import toxtree.ui.actions.DataModuleAction;

public class SaveMethodsListAction extends DataModuleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8025763574647379327L;

	public SaveMethodsListAction(DecisionMethodsDataModule module) {
		this(module,"Save forest");
	}

	public SaveMethodsListAction(DecisionMethodsDataModule module, String name) {
		this(module, name,Tools.getImage("disk_multiple.png"));
	}

	public SaveMethodsListAction(DecisionMethodsDataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Saves the set of decision trees to a file");

	}

	@Override
	public void run() {
		Component parent = null;
		Object o = getValue(AbstractTreeAction.PARENTKEY);
		if ((o!=null) && (o  instanceof Component)) parent = (Component) o;
		else parent = null;		
		
		IDecisionMethodsList methods = ((DecisionMethodsDataModule)module).getMethods();
		for (int i=0; i < methods.size(); i++) {
			VerifyUnreachableRulesAction a  = new VerifyUnreachableRulesAction(methods.getMethod(i));
			a.actionPerformed(null);
		}
		
		
        File file = ToxTreeActions.selectFile(parent, MolFileFilter.toxForest_ext , MolFileFilter.toxForest_ext_descr ,false);
        /*
        if (!file.getName().endsWith(MolFileFilter.toxForest_ext[0])) {
        	file = new File(file.getAbsolutePath()+MolFileFilter.toxForest_ext[0]);
        }
        */
        if (file == null) return;
        try {
            
            if (file.exists() && (JOptionPane.showConfirmDialog(parent,
                    "File "+file.getAbsolutePath() + " already exists.\nOverwrite?",
                    "Please confirm",JOptionPane.OK_CANCEL_OPTION) 
                    == JOptionPane.CANCEL_OPTION))
                return;
            
            if (file.getAbsolutePath().toLowerCase().endsWith(".fml")) {
                FileOutputStream os = new FileOutputStream(file);
                Thread.currentThread().setContextClassLoader(Introspection.getLoader());
                XMLEncoder encoder = new XMLEncoder(os);
                encoder.writeObject(methods);
                encoder.close();    
            } else { 
                if (!file.getName().endsWith(MolFileFilter.toxForest_ext[1])) 
                    file = new File(file.getAbsolutePath()+MolFileFilter.toxForest_ext[1]);
               
                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
                stream.writeObject(methods);
                stream.close();
            }
        } catch (IOException x) {
            	JOptionPane.showMessageDialog(parent,"Error on saving rules",x.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

	}

}


