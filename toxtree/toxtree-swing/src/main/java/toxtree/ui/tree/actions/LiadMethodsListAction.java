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
import java.io.File;
import java.io.FileInputStream;

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

public class LiadMethodsListAction extends DataModuleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -795354390405401992L;


	public LiadMethodsListAction(DecisionMethodsDataModule module) {
		this(module,"Load decision forest");
	}

	public LiadMethodsListAction(DecisionMethodsDataModule module, String name) {
		this(module, name,Tools.getImage("open.gif"));
	}

	public LiadMethodsListAction(DecisionMethodsDataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Loads set of decision trees from (*.forest) file");
	}
	

	@Override
	public void run() {
		Component parent = null;
		Object o = getValue(AbstractTreeAction.PARENTKEY);
		if ((o!=null) && (o  instanceof Component)) parent = (Component) o;
		else parent = null;	
		IDecisionMethodsList methods = ((DecisionMethodsDataModule)module).getMethods();
		methods.clear();
		File file = ToxTreeActions.selectFile(parent, MolFileFilter.toxForest_ext , MolFileFilter.toxForest_ext_descr ,true);
        if (file == null) return;
        try {
        	IDecisionMethodsList list = null;
        	FileInputStream in = new FileInputStream(file);
        	if (file.getAbsolutePath().toLowerCase().endsWith(".fml")) {
        		list = Introspection.loadForestXML(in);
        	} else {        	
	        	list = Introspection.loadForest(in);
        	}	
	        if (list != null) {
	        		methods.clear();
	        		for (int i=0; i < list.size();i++) {
	        			methods.addDecisionMethod(list.getMethod(i));
	        		}	
	        		list.clear();
	        		list = null;
	        }
	        	
            in.close();
       } catch (Exception x) {
    	   x.printStackTrace();
            	JOptionPane.showMessageDialog(parent,"Error on loading rules",x.getMessage(), JOptionPane.ERROR_MESSAGE);
       }
	}

}


