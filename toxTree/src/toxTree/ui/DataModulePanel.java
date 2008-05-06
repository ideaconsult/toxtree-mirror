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
package toxTree.ui;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import toxTree.data.ActionList;
import toxTree.data.DataModule;

public abstract  class DataModulePanel extends JPanel  implements Observer {
	protected DataModule dataModule;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7992562763847145598L;

	public DataModulePanel(DataModule dataModule) {
		super();
		setDataModule(dataModule);
		addWidgets(dataModule.getActions());
	}

	public DataModulePanel(DataModule dataModule,boolean arg0) {
		super(arg0);
		setDataModule(dataModule);		
		addWidgets(dataModule.getActions());		
	}

	public DataModulePanel(DataModule dataModule,LayoutManager arg0) {
		super(arg0);
		setDataModule(dataModule);
		addWidgets(dataModule.getActions());		
	}

	public DataModulePanel(DataModule dataModule,LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		setDataModule(dataModule);
		addWidgets(dataModule.getActions());		
	}
	public void setDataModule(DataModule dataModule) {
		this.dataModule = dataModule;
		if (dataModule instanceof Observable) // Connect the View to the Model 
		    ((Observable) dataModule).addObserver(this);		
	}

	public Dimension getPeferredSize() {
		return new Dimension(256,64*4+20);
	}
	protected abstract void addWidgets(ActionList actions);
		

}
