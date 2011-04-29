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
package toxTree.apps.toxForest;

import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

import toxTree.apps.CompoundMethodApplication;
import toxTree.core.Introspection;
import toxTree.tree.BatchDecisionResultsList;
import toxTree.tree.DecisionResultsList;
import toxTree.ui.EditorFactory;
import toxtree.data.DataContainer;
import toxtree.data.DataModule;
import toxtree.ui.DataModulePanel;
import toxtree.ui.editors.SwingEditorFactory;
import toxtree.ui.tree.molecule.CompoundPanel;

/**
 * Toxforest application - multiple decision trees.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 16, 2006
 */
public class ToxForestApp extends CompoundMethodApplication {

	public ToxForestApp() {
		super("ToxForest",new Color(239,243,255),new Color(32,89,201));
		EditorFactory.setInstance(new SwingEditorFactory());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		cmdArgs = args;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	protected static void createAndShowGUI() {
		 initLookAndFeel();
		 JFrame.setDefaultLookAndFeelDecorated(true);
		 new ToxForestApp();
	}	
	@Override
	protected void parseCmdArgs(String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected DataModule createDataModule() {
		DecisionResultsList methods = new BatchDecisionResultsList();
		try {
			//methods.addDecisionMethod(new DbSearchTree());
			//methods.loadFromPlugins("verhaar.VerhaarScheme");
			//methods.loadAllFromPlugins();
			//methods.addDecisionMethod(new SMARTSTree());
			//methods.loadFromPlugins("toxTree.tree.demo.SMARTSTree");
			
			//methods.loadAllFromPlugins();
			Introspection.configureURLLoader(getClass().getClassLoader());
			methods.loadFromPlugins("toxTree.tree.demo.SMARTSTree");
			//methods.addDecisionMethod(new SicretRules());

			//methods.addDecisionMethod(new CramerRules());
				//methods.loadAllFromPlugins();
		} catch (Exception x) {
			x.printStackTrace();
			logger.error(x);
		}		
		return new ToxForestDataModule(mainFrame,null,methods);
	}
    @Override
	protected ImageIcon getIcon() {
        URL iconURL = ToxForestApp.class.getClassLoader().getResource("toxTree/ui/tree/images/bird.gif");
        if (iconURL != null) {
            return new ImageIcon(iconURL);
        } else return null;     
    }


    @Override
	protected String getTitle() {
        // Setting correct version
        Package p = Package.getPackage("toxTree.apps.toxForest");
        StringBuffer t = new StringBuffer();
        if (p.getImplementationTitle() != null) {
            t.append(p.getImplementationTitle());

        }
        if (p.getImplementationVersion() != null) {
            t.append(" v");
            t.append(p.getImplementationVersion());
        }

        return t.toString();

    }
	@Override
	protected CompoundPanel createCompoundPanel(DataContainer dataContainer, Color bgColor, Color fColor) {
		ToxForestCompoundPanel cp = new ToxForestCompoundPanel(dataContainer, bgColor, fColor);
		cp.setPreferredSize(new Dimension(300, 300));
		cp.setMinimumSize(new Dimension(200, 100));
		return cp;
	}
	@Override
	protected DataModulePanel createDataModulePanel(DataModule dataModule) {

		ToxForestDataModulePanel dp =  new ToxForestDataModulePanel((ToxForestDataModule)dataModule);
		dp.setPreferredSize(new Dimension(600, 300));
		return dp;
	}
	@Override
	protected JSplitPane createSplitPanel(int splitDirection) {
		JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, compoundPanel,dataModulePanel);
		sp.setPreferredSize(new Dimension(600, 600));		
		sp.setOneTouchExpandable(false);
		sp.setDividerLocation(300);
		return sp;
	}	
}
