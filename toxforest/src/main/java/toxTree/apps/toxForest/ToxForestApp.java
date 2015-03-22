/*
Copyright Ideaconsult Ltd. (C) 2005-2015  
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
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

import toxTree.apps.CompoundMethodApplication;
import toxTree.apps.ToxtreeOptions;
import toxTree.core.Introspection;
import toxTree.tree.BatchDecisionResultsList;
import toxTree.tree.DecisionResultsList;
import toxtree.data.DataContainer;
import toxtree.data.DataModule;
import toxtree.ui.DataModulePanel;
import toxtree.ui.tree.molecule.CompoundPanel;

/**
 * Toxforest application - multiple decision trees.
 * 
 * @author Nina Jeliazkova nina@acad.bg <b>Modified</b> Mar 20, 2015
 */
public class ToxForestApp extends CompoundMethodApplication {

	public ToxForestApp() {
		super("ToxForest", new Color(239, 243, 255), new Color(32, 89, 201));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		options = new ToxtreeOptions();
		try {
			options.parse(args);
			if (options.isNoui()) {

			} else
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						createAndShowGUI();
					}
				});
		} catch (Exception x) {
			logger.severe(x.getMessage());
		}
	}

	protected static void createAndShowGUI() {
		initLookAndFeel();
		JFrame.setDefaultLookAndFeelDecorated(true);
		new ToxForestApp();
	}

	@Override
	protected DataModule createDataModule() {
		DecisionResultsList methods = new BatchDecisionResultsList();

		try {
			Introspection.configureURLLoader(getClass().getClassLoader());

		} catch (Exception x) {
			logger.log(Level.SEVERE, x.getLocalizedMessage(), x);
		}
		if (methods.size() == 0) {
			logger.warning("No decision trees found! Have you installed the application correctly? Loading default plugins ...");
			try {
				methods.add(Introspection
						.loadCreateObject("toxTree.tree.cramer.CramerRules"));
			} catch (Exception e) {
			}
			try {
				methods.add(Introspection
						.loadCreateObject("cramer2.CramerRulesWithExtensions"));
			} catch (Exception e) {
			}
			try {
				methods.add(Introspection
						.loadCreateObject("toxtree.plugins.kroes.Kroes1Tree"));
			} catch (Exception e) {
			}
			try {
				methods.add(Introspection
						.loadCreateObject("sicret.SicretRules"));
			} catch (Exception e) {
			}
			try {
				methods.add(Introspection
						.loadCreateObject("mutant.BB_CarcMutRules"));
			} catch (Exception e) {
			}
			try {
				methods.add(Introspection
						.loadCreateObject("toxtree.plugins.smartcyp.SMARTCYPPlugin"));
			} catch (Exception e) {
			}
			try {
				methods.add(Introspection
						.loadCreateObject("toxtree.plugins.skinsensitisation.SkinSensitisationPlugin"));
			} catch (Exception e) {
			}
			try {
				methods.add(Introspection
						.loadCreateObject("com.molecularnetworks.start.BiodgeradationRules"));
			} catch (Exception e) {
			}
			try {
				methods.add(Introspection
						.loadCreateObject("verhaar.VerhaarScheme"));
			} catch (Exception e) {
			}
			try {
				methods.add(Introspection
						.loadCreateObject("toxtree.plugins.verhaar2.VerhaarScheme2"));
			} catch (Exception e) {
			}
			try {
				methods.add(Introspection
						.loadCreateObject("toxtree.plugins.func.FuncRules"));
			} catch (Exception e) {
			}
			// try
			// {methods.add(methods.add(Introspection.loadCreateObject("toxTree.tree.demo.SMARTSTree")));
			// } catch (Exception e) {}
		}
		return new ToxForestDataModule(mainFrame, null, methods);
	}

	@Override
	protected ImageIcon getIcon() {
		URL iconURL = ToxForestApp.class.getClassLoader().getResource(
				"toxTree/ui/tree/images/bird.gif");
		if (iconURL != null) {
			return new ImageIcon(iconURL);
		} else
			return null;
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
	protected CompoundPanel createCompoundPanel(DataContainer dataContainer,
			Color bgColor, Color fColor) {
		ToxForestCompoundPanel cp = new ToxForestCompoundPanel(dataContainer,
				bgColor, fColor);
		cp.setPreferredSize(new Dimension(300, 300));
		cp.setMinimumSize(new Dimension(200, 100));
		return cp;
	}

	@Override
	protected DataModulePanel createDataModulePanel(DataModule dataModule) {

		ToxForestDataModulePanel dp = new ToxForestDataModulePanel(
				(ToxForestDataModule) dataModule);
		dp.setPreferredSize(new Dimension(600, 300));
		return dp;
	}

	@Override
	protected JSplitPane createSplitPanel(int splitDirection) {
		JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				compoundPanel, dataModulePanel);
		sp.setPreferredSize(new Dimension(600, 600));
		sp.setOneTouchExpandable(false);
		sp.setDividerLocation(300);
		return sp;
	}
}
