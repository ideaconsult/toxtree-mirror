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
package toxTree.data;

import java.awt.Component;
import java.io.File;
import java.util.TreeMap;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import toxTree.io.MolFileFilter;
import toxTree.ui.Preferences;
import toxTree.ui.actions.DataModuleAction;

public class ActionList extends ActionMap {
	private static final long serialVersionUID = 5579378925985803990L;
	public static final String _TYPEID = "TYPE";
	public static final String _MNEMONICID = "MNEMONIC";
	public static final String _CHILDREN = "CHILDREN";
	protected int no = 0;
    protected JFrame frame = null;

	private static String cDir = null;    

	public ActionList() {
		super();
	}
	public ActionList(JFrame mainFrame) {
		this.frame = mainFrame;
	}
    public Action addAction(Action a, String type, String mnemonic) {
        if (a instanceof DataModuleAction) {
            ((DataModuleAction)a).setFrame(frame);
        }
        a.putValue(_TYPEID,type);
        a.putValue(_MNEMONICID,mnemonic);
        put(new Integer(no),a);
        no++;
        return a;
    }
    public Action addAction(Action a, ActionList children, String type, String mnemonic) {
    	Action a1 = addAction(a,type,mnemonic);
    	a1.putValue(_CHILDREN,children);
        return a1;
    }
    public static JMenu createSubmenu(Action a) {
    	Object o = a.getValue(_CHILDREN);
    	if ((o != null) && (o instanceof ActionList)) {
    		ActionList actions = (ActionList) o;
            Object[] k = actions.keys();
    		JMenu m = new JMenu(a.getValue(Action.NAME).toString());            
        	for (int i=0; i < k.length; i++)    {   
        		m.add(actions.get(k[i]));
        	}
        	return m;
    	} else return null;
    }
    public void createMenu(JMenuBar menuBar) {
        JMenu menu;
        TreeMap submenus = new TreeMap();
        Object subMenuTitle;
        String mnemonic;
        char m[] = {'F'};
        Action a;
        Object[] k = keys();
    	for (int i=0; i < k.length; i++)    {   
            a = get(k[i]);
            subMenuTitle = a.getValue(_TYPEID);
            mnemonic = (String) a.getValue(_MNEMONICID);
            if (submenus.containsKey(subMenuTitle)) {
                menu = (JMenu) submenus.get(subMenuTitle);
            } else {
                menu = new JMenu((String) subMenuTitle);
                submenus.put(subMenuTitle,menu);
                menuBar.add(menu);
            }
            mnemonic.getChars(0,1,m,0);
            menu.setMnemonic(m[0]);
            JMenu actionSubMenu = ActionList.createSubmenu(a);
            if (actionSubMenu != null) menu.add(actionSubMenu);
			else menu.add(a);
        }
        
    }
    public void allActionsEnable(boolean enable) {
    	Object[] k = keys();
    	for (int i=0; i < k.length; i++) get(k[i]).setEnabled(enable);
    }
    public void enable(boolean enable,String type) {
    	Object[] k = keys();
    	if (type.equals(""))
    		for (int i=0; i < k.length; i++) get(k[i]).setEnabled(enable);
    	else
    		for (int i=0; i < k.length; i++) {
    			Action a = get(k[i]);
    			if (a.getValue(_TYPEID).equals(type))
    				get(k[i]).setEnabled(enable);
    		}	
    }
    
	public synchronized JFrame getFrame() {
		return frame;
	}

	/**
	 * Invokes OpenFile or SaveFile dialog
	 * @param frame {@link java.awt.Frame} a frame to be used  as a parent for the file dialog
	 * @param open if true invokes OpenFile dialog, otherwise invokes SaveFile
	 * @return the selected {@link java.io.File}
	 */
	public static File selectFile(Component frame, 
	        String[] ext,String[] extDescription, boolean open) {
		JFileChooser fc = new JFileChooser();
		for (int i=0; i < ext.length; i++)
			fc.addChoosableFileFilter(new MolFileFilter(ext[i],extDescription[i]));
		fc.setFileFilter(fc.getChoosableFileFilters()[1]);
		
		try {
		    fc.setCurrentDirectory(new File(getCurrentDirectory()));
		} catch (Exception x) {
		    fc.setCurrentDirectory(null);		    
		}
		
        int returnVal;
        if (open) returnVal = fc.showOpenDialog(frame);
		else  returnVal =  fc.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            FileFilter ff = fc.getFileFilter();
            if (ff instanceof MolFileFilter)
            	file = changeExtension(file,(MolFileFilter)ff);
            
//   		 Get the file typeValue name
//    	    String fileTypeName = fc.getTypeDescription(file);
            if (file != null) setCurrentDirectory(file.getPath());
            fc = null;
            
            return file;
        } 
        fc = null;
        return null;
	}
	
	public static File changeExtension(File file, MolFileFilter ff) {
	    //String suffix = null;
	    String fname= file.getAbsolutePath(); 
	    int i = fname.lastIndexOf('.');

	    
	    if(i > 0 && i < fname.length() - 1)
	    	if (fname.substring(i).toLowerCase().equals(ff.getExtension())) return file;
	    
	    return new File(fname+ff.getExtension());
	}
	
    public void showMessage(String caption, String message) {
		JOptionPane.showMessageDialog(frame,
				caption,						
			    message,
			    JOptionPane.INFORMATION_MESSAGE);        
    }
    public static void showMsg(String caption, String message) {
		JOptionPane.showMessageDialog(null,
				caption,						
			    message,
			    JOptionPane.INFORMATION_MESSAGE);        
    }
	public static String getCurrentDirectory() {
		if (cDir == null)
		cDir = Preferences.getProperty(Preferences.DEFAULT_DIR).toString();
		return cDir;
	}
	public static void setCurrentDirectory(String currentDirectory) {
		cDir = currentDirectory;
		if (currentDirectory != null)
		Preferences.setProperty(Preferences.DEFAULT_DIR,currentDirectory);
	}
	
}
