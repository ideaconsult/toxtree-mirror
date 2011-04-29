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
package toxTree.apps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;

import toxtree.data.DataContainer;
import toxtree.data.DataModule;
import toxtree.data.DecisionMethodsDataModule;
import toxtree.ui.DataModulePanel;
import toxtree.ui.StatusBar;
import toxtree.ui.molecule.TopPanel;
import toxtree.ui.tree.molecule.CompoundPanel;

public abstract class CompoundMethodApplication extends AbstractApplication {
	//GUI
	protected JSplitPane splitPanel;
	protected JPanel mainPanel;
	protected CompoundPanel compoundPanel;
	protected DataModulePanel dataModulePanel;
	protected TopPanel strucEntryPanel;

	//cmd option
	protected File fileToOpen = null;
	public CompoundMethodApplication(String title,  Color bgColor, Color fColor) {
		super(title);
		mainFrame.getContentPane().add(createMenuBar(), BorderLayout.NORTH);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		compoundPanel = createCompoundPanel(dataModule.getDataContainer(), bgColor, fColor);
        if (((DecisionMethodsDataModule)dataModule).getTreeResult() != null)
            ((DecisionMethodsDataModule)dataModule).getTreeResult().addPropertyChangeListener(compoundPanel);        
		dataModulePanel = createDataModulePanel(dataModule);
		strucEntryPanel = new TopPanel();
		strucEntryPanel.setAutoscrolls(false);
		strucEntryPanel.setDataContainer(dataModule.getDataContainer());

		splitPanel = createSplitPanel(JSplitPane.HORIZONTAL_SPLIT);
		
		mainPanel.add(strucEntryPanel, BorderLayout.NORTH);
		mainPanel.add(splitPanel, BorderLayout.CENTER);

		// Status bar display
		JPanel sPanel = createStatusBar();
		
		mainFrame.getContentPane().add(sPanel, BorderLayout.SOUTH);
		// Add the panel to the window.
		mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		String t = getTitle();
		if ((t != null) && (!t.equals(""))) mainFrame.setTitle(t.toString());
		mainFrame.pack();
		mainFrame.setVisible(true);
		centerScreen();		
	}
	protected JSplitPane createSplitPanel(int splitDirection) {
		JSplitPane sp = new JSplitPane(splitDirection, compoundPanel,dataModulePanel);
		sp.setOneTouchExpandable(false);
		sp.setDividerLocation(300);
		return sp;
	}
	protected JPanel createStatusBar() {
		StatusBar sPanel = new StatusBar();
        if (((DecisionMethodsDataModule)dataModule).getTreeResult() != null)
            ((DecisionMethodsDataModule)dataModule).getTreeResult().addPropertyChangeListener(sPanel);        
		sPanel.setPreferredSize(new Dimension(300, 24));
		sPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		sPanel.setDataContainer(dataModule.getDataContainer());
		return sPanel;
	}

	protected abstract String getTitle();
	
	protected CompoundPanel createCompoundPanel(DataContainer dataContainer, Color bgColor, Color fColor) {
		CompoundPanel cp = new CompoundPanel(dataModule.getDataContainer(),bgColor,fColor);
		cp.setPreferredSize(new Dimension(300, 500));
		cp.setMinimumSize(new Dimension(100, 200));
		return cp;
	}
	protected abstract DataModulePanel createDataModulePanel(DataModule dataModule);

	@Override
	protected void parseCmdArgs(String[] args) {
		char option;
		int p = 0;
		while (p < args.length) {
			option = args[p].charAt(0);
			if (option != '-')
				continue;
			option = args[p].charAt(1);
			switch (option) {
			case 'f': {
				p++;
				if (p >= args.length)
					break;
				fileToOpen = new File(args[p]);
				if (!fileToOpen.exists()) {
					logger.error("File do not exists!\t", fileToOpen
							.getAbsolutePath());
					fileToOpen = null;
				}
				break;
			}
			}
			p++;
		}
	}


	@Override
	protected DataModule createDataModule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ImageIcon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
