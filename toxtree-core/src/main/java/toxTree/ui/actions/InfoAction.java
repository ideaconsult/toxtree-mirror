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
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import toxTree.core.IDecisionRule;
import toxTree.data.ToxTreeModule;
import toxTree.tree.cramer.RuleCommonComponentOfFood;
import toxTree.tree.cramer.RuleNormalBodyConstituent;
import toxTree.ui.tree.images.ImageTools;

/**
 * Provides inforation about files, etc.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class InfoAction extends DataModuleAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 5062923445126819919L;

	/**
	 * @param module
	 */
	public InfoAction(ToxTreeModule module) {
		this(module,"Files info");
	}

	/**
	 * @param module
	 * @param name
	 */
	public InfoAction(ToxTreeModule module, String name) {
		this(module, name,ImageTools.getImage("information.png"));
	}

	/**
	 * @param module
	 * @param name
	 * @param icon
	 */
	public InfoAction(ToxTreeModule module, String name, Icon icon) {
		super(module, name, icon);
        putValue(AbstractAction.SHORT_DESCRIPTION,"Current directory info");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
        File dir1 = new File (".");
        StringBuffer b = new StringBuffer();
        try {
        	String cdir = dir1.getCanonicalPath();
        	b.append("Current directory : ");
        	b.append(cdir) ;
        	b.append('\n');
        	b.append("Plugins directory : ");
        	b.append(cdir+ "\\ext") ;
        	b.append('\n');
        	b.append('\n');
        	
        	IDecisionRule r = new RuleNormalBodyConstituent();
        	if (r != null) {
	        	b.append("File with '" + r.getTitle() + "' compounds: ");
	        	File f = ((RuleNormalBodyConstituent)r).getFile(); 
	        	if ((f!=null) && f.exists())	b.append(f.getAbsolutePath());
	        	else b.append(" NOT FOUND!");	        	
	        	b.append('\n');
        	}
        	
        	r = new RuleCommonComponentOfFood();
        	if (r != null) {
	        	b.append("File with '" + r.getTitle() + "' compounds: ");
	        	File f = ((RuleCommonComponentOfFood)r).getFile(); 
	        	if ((f!=null) && f.exists())	b.append(f.getAbsolutePath());
	        	else b.append(" NOT FOUND!");
	        	b.append('\n');
        	}        	
        	
        	
        } catch (Exception x) {
        	b.append(x.getMessage());
        }
        JOptionPane.showMessageDialog(module.getActions().getFrame(),
        		b.toString(),
        		"Files information",JOptionPane.INFORMATION_MESSAGE);


	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
