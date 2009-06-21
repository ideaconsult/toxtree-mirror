/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/


package toxTree.ui.molecule;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.data.DataModule;
import toxTree.data.DecisionMethodsDataModule;
import toxTree.io.Tools;
import toxTree.ui.actions.DataModuleAction;

public class LookupCompoundAction extends DataModuleAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5916869751317470014L;

	public LookupCompoundAction(DataModule module) {
		this(module,"Find");
	}

	public LookupCompoundAction(DataModule module, String name) {
		this(module, name,Tools.getImage("find.png"));

	}

	public LookupCompoundAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Find compound by property value");
	}

	@Override
	public void run() {
    	module.getActions().allActionsEnable(false);
    	JComboBox box = new JComboBox();
    	box.addItemListener(new ItemListener() {
    		public void itemStateChanged(ItemEvent e) {
    			((JComponent)e.getSource()).setToolTipText("Find compound with " + e.getItem().toString() + " = ?");
    			
    		}
    	});
    	box.setToolTipText("Select or enter the name of the field");
    	box.setEditable(true);
    	
    	try {
    		IAtomContainer a = module.getDataContainer().getMolecule();
    		Iterator e = a.getProperties().keySet().iterator();
    		while (e.hasNext()) {
    			box.addItem(e.next().toString());
    		}
    	} catch (Exception x) {
    		box.addItem(CDKConstants.CASRN);	
    		box.addItem(CDKConstants.NAMES);

    	}
    	
    	String value = JOptionPane.showInputDialog(frame, box,"Find",JOptionPane.PLAIN_MESSAGE);
    	if ((value != null) && ((DecisionMethodsDataModule)module).lookup(box.getSelectedItem().toString(),value) < 0) 
    		JOptionPane.showMessageDialog(frame, value + " Not found","Message",JOptionPane.ERROR_MESSAGE);
    	module.getActions().allActionsEnable(true);

	}

}


