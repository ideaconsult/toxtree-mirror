/*
Copyright Ideaconsult Ltd. (C) 2005-2014
Contact: www.ideaconsult.net

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
/**
 * ToxTree application
 * @author Nina Jeliazkova <br>
 * <b>Created</b> 2005-4-30
 */
package toxTree.apps;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import toxTree.core.Introspection;
import toxTree.tree.DecisionMethodsList;
import toxtree.data.DataModule;
import toxtree.data.ToxTreeModule;
import toxtree.ui.DataModulePanel;
import toxtree.ui.HazardPanel;

/**
 * ToxTreeApp is a full-featured and flexible user-friendly open source
 * application, which is able to estimate toxic hazard by applying a decision
 * tree approach. Currently it encodes the Cramer scheme (Cramer G. M., R. A.
 * Ford, R. L. Hall, Estimation of Toxic Hazard - A Decision Tree Approach, J.
 * Cosmet. Toxicol., Vol.16, pp. 255-276, Pergamon Press, 1978) and could be
 * applied to datasets from various compatible file types (MOL, MOL2, SDF, CML,
 * XYZ, PDB, HIN, CDX and SMI). User-defined molecular structures are also
 * supported - they could be entered by SMILES or by <a
 * href="http://jchempaint.sourceforge.net">JChemPaint</a> structure diagram
 * editor
 * 
 * 
 * TODO Prevent multiple instances:
 * http://www.rgagnon.com/javadetails/java-0288.html
 * 
 * @author Nina Jeliazkova <br>
 *         <b>Modified</b> 2008-03-17
 */
public class ToxTreeApp extends CompoundMethodApplication {
    protected File file4Batch = null;

    /**
	 *
	 */
    public ToxTreeApp() {
	super("Estimation of Toxic Hazard - a Decision Tree Approach ", Color.black, Color.WHITE);
	insecureConfig();
    }

    @Override
    protected String getTitle() {
	// Setting correct version
	Package p = Package.getPackage("toxTree.apps");
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
    protected DataModule createDataModule() {
	DecisionMethodsList methods = new DecisionMethodsList();
	try {
	    // methods.loadAllFromPlugins();
	    methods.loadPluginsByConfiguration();

	} catch (Exception x) {
	    logger.log(Level.SEVERE, x.getMessage(), x);
	}
	if (methods.size() == 0) {
	    logger.log(Level.WARNING, "No decision trees found! Have you installed the application correctly?");
	    logger.log(Level.INFO, "Loadind default plugins...");
	    String[] config = new String[] { "toxTree.tree.cramer.CramerRules", "cramer2.CramerRulesWithExtensions",
		    "toxtree.plugins.kroes.Kroes1Tree", "verhaar.VerhaarScheme",
		    "toxtree.plugins.verhaar2.VerhaarScheme2", "mutant.BB_CarcMutRules",
		    "toxtree.plugins.ames.AmesMutagenicityRules", // 6
		    "sicret.SicretRules", // 7
		    "eye.EyeIrritationRules", // 8
		    "toxtree.plugins.skinsensitisation.SkinSensitisationPlugin",// 10
		    "michaelacceptors.MichaelAcceptorRules", // 11
		    "com.molecularnetworks.start.BiodgeradationRules",// 12
		    "toxtree.plugins.smartcyp.SMARTCYPPlugin",// 13
		    "mic.MICRules",// 14
		    "toxtree.plugins.func.FuncRules",// 100
		    "toxtree.plugins.proteinbinding.ProteinBindingPlugin",// 100
		    "toxtree.plugins.dnabinding.DNABindingPlugin" };
	    for (String clazz : config)
		try {
		    logger.log(Level.INFO, "Loading " + clazz);
		    methods.add(Introspection.loadCreateObject(clazz));
		} catch (Exception x) {
		    logger.log(Level.WARNING, x.getMessage());
		}

	    // methods.add(Introspection.loadCreateObject("toxtree.plugins.moa.MOARules"));//100
	    // methods.add(Introspection.loadCreateObject("toxtree.plugins.search.CompoundLookup"));

	}
	return new ToxTreeModule(mainFrame, fileToOpen, methods);
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    protected static void createAndShowGUI() {
	// Set the look and feel.
	initLookAndFeel();

	// Make sure we have nice window decorations.
	JFrame.setDefaultLookAndFeelDecorated(true);

	new ToxTreeApp();

    }

    /**
     * Command line options: <br>
     * java -jar toxTree.jar -f input_file_for_normal_open <br>
     * java -jar toxTree.jar -b input_file_for_batch_processing
     * 
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
		    logger.severe("File do not exists!\t" + fileToOpen.getAbsolutePath());
		    fileToOpen = null;
		}
		break;
	    }
	    case 'b': {
		p++;
		if (p >= args.length)
		    break;
		file4Batch = new File(args[p]);
		if (!file4Batch.exists()) {
		    logger.severe("File do not exists!\t" + file4Batch.getAbsolutePath());
		    file4Batch = null;
		}
		break;
	    }
	    }
	    p++;
	}
    }

    @Override
    protected ImageIcon getIcon() {
	URL iconURL = ToxTreeApp.class.getClassLoader().getResource("toxTree/ui/tree/images/bird.gif");
	if (iconURL != null) {
	    return new ImageIcon(iconURL);
	    // mainFrame.setIconImage(toxTreeIcon.getImage());
	} else
	    return null;
    }

    @Override
    protected DataModulePanel createDataModulePanel(DataModule dataModule) {
	HazardPanel hPanel = new HazardPanel((ToxTreeModule) dataModule);
	hPanel.setPreferredSize(new Dimension(300, 500));
	return hPanel;
    }

    protected void insecureConfig() {
	// Create a trust manager that does not validate certificate chains
	TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return null;
	    }

	    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
	    }

	    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
	    }
	} };

	// Install the all-trusting trust manager
	try {
	    SSLContext sc = SSLContext.getInstance("SSL");
	    sc.init(null, trustAllCerts, new java.security.SecureRandom());
	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	} catch (Exception e) {
	}
	HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
	    public boolean verify(String string, SSLSession ssls) {
		return true;
	    }
	});
    }

}
