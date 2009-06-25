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

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import toxTree.data.DataModule;
import toxTree.logging.TTLogger;
import ambit2.base.config.Preferences;

public abstract class AbstractApplication {
	// Specify the look and feel to use. Valid values:
	// null (use the default), "Metal", "System", "Motif", "GTK+"
	final static String LOOKANDFEEL = "System";
	
	protected static String[] cmdArgs;
	protected static TTLogger logger = new TTLogger(ToxTreeApp.class);

	protected DataModule dataModule = null;
	protected JFrame mainFrame;
	
	
	public AbstractApplication(String title) {
		super();
		// Create and set up the window.
		TTLogger.configureLog4j(true);
		parseCmdArgs(cmdArgs);

		mainFrame = new JFrame(title);
				
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener( new WindowAdapter() {
     	   @Override
			public void windowClosing( WindowEvent e ){
     		   exit();
     	}
     	} 
       ); 				
		mainFrame.setSize(new Dimension(500, 500));
		ImageIcon icon = getIcon();
		if (icon != null)
			mainFrame.setIconImage(icon.getImage());
		dataModule = createDataModule();
	}
	
	protected void exit() {
		try {
    		   	Preferences.saveProperties(getClass().getName());
    	} catch (Exception x) {
    			   x.printStackTrace();
    	}
    	mainFrame.setVisible(false);
    	mainFrame.dispose();
    	Runtime.getRuntime().runFinalization();						 
    	Runtime.getRuntime().exit(0);       		
	}
	abstract void parseCmdArgs(String[] args);
	protected static void initLookAndFeel() {
		String lookAndFeel = null;

		if (LOOKANDFEEL != null) {
			if (LOOKANDFEEL.equals("Metal")) {
				lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			} else if (LOOKANDFEEL.equals("System")) {
				lookAndFeel = UIManager.getSystemLookAndFeelClassName();
			} else if (LOOKANDFEEL.equals("Motif")) {
				lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
			} else if (LOOKANDFEEL.equals("GTK+")) { // new in 1.4.2
				lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
			} else {
				logger.error("Unexpected value of LOOKANDFEEL specified: ",
						LOOKANDFEEL);
				lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			}

			try {
				UIManager.setLookAndFeel(lookAndFeel);
			} catch (ClassNotFoundException e) {
				logger.error(
						"Couldn't find class for specified look and feel:",
						lookAndFeel);
				logger
						.error("Did you include the L&F library in the class path?");
				logger.error("Using the default look and feel.");
			} catch (UnsupportedLookAndFeelException e) {
				logger.error("Can't use the specified look and feel (",
						lookAndFeel, ") on this platform.");
				logger.error("Using the default look and feel.");
			} catch (Exception e) {
				logger.error("Couldn't get specified look and feel (",
						lookAndFeel, "), for some reason.");
				logger.error("Using the default look and feel.");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * initLookAndFeel();
	 * JFrame.setDefaultLookAndFeelDecorated(true);
	 * new ToxTreeApp();
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	protected static void createAndShowGUI() {
		 initLookAndFeel();
		 JFrame.setDefaultLookAndFeelDecorated(true);
		 //new AbstractApplication()
	}
	
	protected abstract DataModule createDataModule();
	
	protected abstract ImageIcon getIcon();
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
	
	protected void centerScreen() {
		Dimension dim = mainFrame.getToolkit().getScreenSize();
		Rectangle abounds = mainFrame.getBounds();
		mainFrame.setLocation((dim.width - abounds.width) / 2,
				(dim.height - abounds.height) / 2);
	}

	// Create an Edit menu to support cut/copy/paste.
	protected JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		dataModule.getActions().createMenu(menuBar);
		return menuBar;
	}
	
}
