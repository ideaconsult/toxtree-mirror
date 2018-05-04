package toxtree.tree.cramer3.test;

import java.util.logging.Level;

import javax.swing.JFrame;

import toxTree.apps.ToxTreeApp;
import toxTree.apps.ToxtreeOptions;
import toxTree.core.Introspection;
import toxTree.tree.DecisionMethodsList;
import toxtree.data.DataModule;
import toxtree.data.ToxTreeModule;

public class ToxTreeTest extends ToxTreeApp {

	public static void main(String[] args) {

		options = new ToxtreeOptions();
		try {
		options.parse(args);
		} catch (Exception x) {
			x.printStackTrace();
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	protected static void createAndShowGUI() {
		// Set the look and feel.
		initLookAndFeel();

		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		new ToxTreeTest();

	}

	@Override
	protected DataModule createDataModule() {
		DecisionMethodsList methods = new DecisionMethodsList();

		logger.log(Level.WARNING, "No decision trees found! Have you installed the application correctly?");
		logger.log(Level.INFO, "Loadind default plugins...");
		String[] config = new String[] { "toxtree.tree.cramer3.RevisedCramerDecisionTree" };
		for (String clazz : config)
			try {
				logger.log(Level.INFO, "Loading " + clazz);
				methods.add(Introspection.loadCreateObject(clazz));
			} catch (Exception x) {
				logger.log(Level.WARNING, x.getMessage());
			}

		return new ToxTreeModule(mainFrame, fileToOpen, methods);
	}
}
