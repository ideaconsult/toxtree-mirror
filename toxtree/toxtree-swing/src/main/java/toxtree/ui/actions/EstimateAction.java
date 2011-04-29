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

import javax.swing.AbstractAction;
import javax.swing.Icon;

import toxTree.io.Tools;
import toxtree.data.DataModule;
import toxtree.data.DecisionMethodsDataModule;

/**
 * Applies a decision tree to the current molecule
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class EstimateAction extends DataModuleAction {
	public static final String _aEstimate = "Estimate";
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -3493585373906087441L;

	/**
	 * @param module
	 */
	public EstimateAction(DataModule module) {
		this(module,_aEstimate);
	}

	/**
	 * @param module
	 * @param name
	 */
	public EstimateAction(DataModule module, String name) {
		this(module, name,Tools.getImage("control_play_blue.png"));
	}

	/**
	 * @param module
	 * @param name
	 * @param icon
	 */
	public EstimateAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Applies decision tree");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void run() {
    	//module.getActions().enable(false,ToxTreeActions._HazardAction);
        ((DecisionMethodsDataModule)module).classify();
    	//module.getActions().enable(true,ToxTreeActions._HazardAction);

	}

}
