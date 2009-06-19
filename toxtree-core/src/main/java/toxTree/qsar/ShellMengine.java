/*
Copyright (C) 2005-2007  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package toxTree.qsar;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IMolecule;

import toxTree.core.Introspection;
import toxTree.exceptions.ShellException;
import toxTree.ui.Preferences;

public class ShellMengine extends ShellSDFoutput<IMolecule> {
	public ShellMengine() throws ShellException {
		super();
	}
	@Override
	protected void initialize() throws ShellException {
        /*
		addExecutable(CommandShell.os_WINDOWS, "helper/smi23d/win/mengine.exe");		
        addExecutable(CommandShell.os_LINUX, "helper/smi23d/linux/mengine");
        */
        addExecutable(CommandShell.os_WINDOWS, Introspection.getToxTreeRoot()+Preferences.getProperty(Preferences.MENGINE_WIN));        
        addExecutable(CommandShell.os_LINUX, Introspection.getToxTreeRoot()+Preferences.getProperty(Preferences.MENGINE_LINUX));        
		setInputFile("rough.sdf");
		setOutputFile("opt.sdf");
		setReadOutput(true);
        Preferences.getPropertyChangeSupport().addPropertyChangeListener(Preferences.MENGINE_LINUX, this);
        Preferences.getPropertyChangeSupport().addPropertyChangeListener(Preferences.MENGINE_WIN, this);
    }
    @Override
    protected void finalize() throws Throwable {
        Preferences.getPropertyChangeSupport().removePropertyChangeListener(Preferences.MENGINE_LINUX, this);
        Preferences.getPropertyChangeSupport().removePropertyChangeListener(Preferences.MENGINE_WIN, this);
        super.finalize();
    }
	protected List<String> prepareInput(String path, IMolecule mol) throws ShellException {
		List<String> list = new ArrayList<String>();
		list.add("-p");
		list.add("mmff94.prm");
		list.add("-c");
		list.add("mmxconst.prm");
		list.add("-o");
		list.add(outputFile);
		list.add(inputFile);
		return list;		
	}
	@Override
	public String toString() {
		return "mengine";
	}
	@Override
	protected IMolecule transform(IMolecule mol) {
		return mol;
	}

    public void propertyChange(PropertyChangeEvent evt) {
        try {
            addExecutableWin(Preferences.getProperty(Preferences.MENGINE_WIN));
            addExecutableLinux(Preferences.getProperty(Preferences.MENGINE_LINUX));
        } catch (Exception x) {
            logger.error(x);
        }
        
    }    
}


