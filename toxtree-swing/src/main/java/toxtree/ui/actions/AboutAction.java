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

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import toxTree.io.Tools;
import toxtree.data.DataModule;

/**
 * Launches About dialog
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class AboutAction extends DataModuleAction {
    protected String packageName = "toxTree.apps";
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5008831961271497399L;

	/**
	 * @param module
	 */
	public AboutAction(DataModule module) {
		this(module,"About",Tools.getImage("information.png"));
	}

	/**
	 * @param module
	 * @param name
	 */
	public AboutAction(DataModule module, String name) {
		this(module, name,null);
	}

	/**
	 * @param module
	 * @param name
	 * @param icon
	 */
	public AboutAction(DataModule module, String name, Icon icon) {
		super(module, name, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION,"Version info");
	}
	@Override
	public void runAction() throws Exception {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		StringBuffer b = new StringBuffer();

		b.append("<html>");
		
		b.append("<table width='100%'>");
    	Package self = Package.getPackage(packageName);
    	b.append(String.format("<tr><th>Title</th><td>%s</td></tr>",self.getImplementationTitle()));
    	b.append(String.format("<tr><th>Vendor</th><td>%s</td></tr>",self.getImplementationVendor()));
    	b.append(String.format("<tr><th>Version</th><td>%s</td></tr>",self.getImplementationVersion()));
        b.append("</table>");
        //cramer rules implementation
        if (packageName.equals("toxTree.apps")) {
        	
        	self = Package.getPackage("toxTree.tree.cramer");
        	if (self != null) {
        		b.append("<h5>Built-in decision tree:\n");
	        	String s = self.getSpecificationTitle();
	        	if (s == null) s = "Cramer scheme as in \"Cramer G. M., R. A. Ford, R. L. Hall, Estimation of Toxic Hazard - A Decision Tree Approach,J. Cosmet. Toxicol.,Vol.16, pp. 255-276, Pergamon Press, 1978\"";
	        	b.append(s);
	            b.append("<br>Version: ");
	            b.append(self.getImplementationVersion());
        	}

        }
        b.append("<br>");
        b.append("<table width='100%'>");

        b.append(String.format("<tr><th>WWW</th><td>%s</td></tr>","<a href='http://toxtree.sf.net'>http://toxtree.sourceforge.net</a>"));
        
        b.append(String.format("<tr><th>Support:</th><td>%s</td></tr>","<a a href='http://toxtree.sourceforge.net/issue-tracking.html'>Issue tracking</a></a>"));
        b.append(String.format("<tr><th></th><td>%s</td></tr>","<a href='http://www.ideaconsult.net'>http://www.ideaconsult.net</a>"));
        b.append(String.format("<tr><th></th><td>%s</td></tr>","<a href='http://ihcp.jrc.ec.europa.eu/our_labs/computational_toxicology/qsar_tools/toxtree'>JRC Computational Toxicology</a>"));
        b.append(String.format("<tr><th></th><td>%s</td></tr>","<a href='mailto:jeliazkova.nina@gmail.com'>e-mail</a>"));

        b.append("</table>");
    	
    	b.append("</html>");

        
    	ImageIcon toxTreeIcon = null;
		try {
			toxTreeIcon = Tools.getImage("bird.gif");
        } catch (Exception x) {
            toxTreeIcon = null;
        }
		JEditorPane label = new JEditorPane("text/html",b.toString());
		label.setBorder(BorderFactory.createEtchedBorder());
		label.setPreferredSize(new Dimension(400,300));
		label.setOpaque(false);
		label.setEditable(false);
		
		label.addHyperlinkListener(new HyperlinkListener() {
		    public void hyperlinkUpdate(HyperlinkEvent e) {
		        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		        	try { 
		        		if(Desktop.isDesktopSupported()) {
		        		    Desktop.getDesktop().browse(e.getURL().toURI());
		        		} else 
		        			Tools.openURL(e.getURL().toString());
		        	} catch (Exception x) {
		        		JOptionPane.showMessageDialog(module.getActions().getFrame(), x.getMessage());
		        	}
		        }
		    }
		});
		
    	JOptionPane.showMessageDialog(module.getActions().getFrame(),label,"About",JOptionPane.INFORMATION_MESSAGE,toxTreeIcon);
        

 	}

    public synchronized String getPackageName() {
        return packageName;
    }

    public synchronized void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
