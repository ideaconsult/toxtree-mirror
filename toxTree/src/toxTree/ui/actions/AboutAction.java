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
package toxTree.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import toxTree.data.DataModule;
import toxTree.ui.tree.images.ImageTools;

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
		this(module,"About",ImageTools.getImage("information.png"));
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
	public void run() {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		StringBuffer b = new StringBuffer();

    	Package self = Package.getPackage(packageName);
    	b.append("<html>Title  : <u>");
    	b.append(self.getImplementationTitle());
    	b.append("</u></html>\n");
    	
    	b.append("<html>Vendor : <b>");
    	b.append(self.getImplementationVendor());
    	b.append("</b></html>\n");
    	b.append("<html>Version:  <b>");
    	b.append(self.getImplementationVersion());
        b.append("</b></html>");
        //cramer rules implementation
        if (packageName.equals("toxTree.apps")) {
        	b.append("\n\nBuilt-in decision tree:\n");
        	self = Package.getPackage("toxTree.tree.cramer");
        	String s = self.getSpecificationTitle();
        	if (s == null) s = "Cramer scheme as in \"Cramer G. M., R. A. Ford, R. L. Hall, Estimation of Toxic Hazard - A Decision Tree Approach,J. Cosmet. Toxicol.,Vol.16, pp. 255-276, Pergamon Press, 1978\"";
        	while (s.length() > 0) {
        		if (s.length() > 70) {
        			b.append(s.substring(0,70));
        			b.append("\n");
        			s = s.substring(70);
        		}  else {
        			b.append(s);
        			break;
        		}
        	}
            b.append("\nVersion: ");
            b.append(self.getImplementationVersion());
        }
    	

    	b.append("\n\n<html><a href='mailto:nina@acad.bg'>mailto:nina@acad.bg</a>\n");
    	/*
    	JPanel p = new JPanel(new BorderLayout());
    	
    	JEditorPane text = new JEditorPane("text/html","");
    	
    	text.setText(b.toString());
    	text.setBackground(Color.lightGray);
		text.setBorder(BorderFactory.createLoweredBevelBorder());
		text.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(text,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		text.setPreferredSize(new Dimension(200,200));		
    	p.add(scrollPane,BorderLayout.CENTER);
*/
    	ImageIcon toxTreeIcon = null;
		try {
			toxTreeIcon = ImageTools.getImage("bird.gif");
        } catch (Exception x) {
            toxTreeIcon = null;
        }
			
    	JOptionPane.showMessageDialog(module.getActions().getFrame(), 
    			b.toString(),"About",JOptionPane.INFORMATION_MESSAGE,toxTreeIcon);
        

 	}

    public synchronized String getPackageName() {
        return packageName;
    }

    public synchronized void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
