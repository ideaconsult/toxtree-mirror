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
import javax.swing.JOptionPane;

import toxTree.io.Tools;

/**
 * Help
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class HelpAction extends AbstractAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -8781252848699546773L;

	/**
	 * 
	 */
	public HelpAction() {
		this("Help");
	}

	/**
	 * @param name
	 */
	public HelpAction(String name) {
		this(name,Tools.getImage("help.png"));
	}

	/**
	 * @param name
	 * @param icon
	 */
	public HelpAction(String name, Icon icon) {
		super(name, icon);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		showHelp("toxTree");

	}
	public void showHelp(String id) 
	{
		JOptionPane.showMessageDialog(null,"User guide is available in the [installation directory]/doc","toxTree Help",JOptionPane.INFORMATION_MESSAGE);
		/*
		String h = "toxTree/config/help/" + id + ".hs"; 
		try {
		
        URL helpurl=HelpSet.findHelpSet(this.getClass().getClassLoader(),
        		h);
		    HelpSet hs = new HelpSet(null, helpurl);
		    HelpBroker hb = hs.createHelpBroker();
        hb.setDisplayed(true);
        hb.setSize(new Dimension(600,400));
        if(id!=null)
          hb.setCurrentID("top");
		} catch(Exception ee) {
	        System.out.println("HelpSet: "+ee.getMessage());
	        System.out.println("HelpSet: "+ h + " not found");
	      }
	      */
	}	

}
