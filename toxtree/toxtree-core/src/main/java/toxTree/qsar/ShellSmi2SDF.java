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
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.MFAnalyser;

import toxTree.core.Introspection;
import toxTree.exceptions.ShellException;
import toxTree.ui.Preferences;

public class ShellSmi2SDF extends ShellSDFoutput<IMolecule> implements PropertyChangeListener {
    protected boolean dropHydrogens = true;
    
	protected transient SmilesGenerator gen = new SmilesGenerator();
	public ShellSmi2SDF() throws ShellException {
		super();
	}
	@Override
	protected void initialize() throws ShellException {
		super.initialize();
        /*
		addExecutable(CommandShell.os_WINDOWS, "helper/smi23d/win/smi2sdf.exe");
        addExecutable(CommandShell.os_LINUX, "helper/smi23d/linux/smi2sdf");
        */
        addExecutable(CommandShell.os_WINDOWS, Introspection.getToxTreeRoot()+Preferences.getProperty(Preferences.SMI2SDF_WIN));        
        addExecutable(CommandShell.os_LINUX, Introspection.getToxTreeRoot()+Preferences.getProperty(Preferences.SMI2SDF_LINUX));            
		setInputFile("mol.smi");
		setOutputFile("rough.sdf");		
		setReadOutput(false);
        Preferences.getPropertyChangeSupport().addPropertyChangeListener(Preferences.SMI2SDF_LINUX, this);
        Preferences.getPropertyChangeSupport().addPropertyChangeListener(Preferences.SMI2SDF_WIN, this);
        Preferences.getPropertyChangeSupport().addPropertyChangeListener(Preferences.SMILES_FIELD, this);
        Preferences.getPropertyChangeSupport().addPropertyChangeListener(Preferences.SMILES_GEN, this);        
    }
    @Override
    protected void finalize() throws Throwable {
        Preferences.getPropertyChangeSupport().removePropertyChangeListener(Preferences.SMI2SDF_LINUX, this);
        Preferences.getPropertyChangeSupport().removePropertyChangeListener(Preferences.SMI2SDF_WIN, this);
        Preferences.getPropertyChangeSupport().removePropertyChangeListener(Preferences.SMILES_FIELD, this);
        Preferences.getPropertyChangeSupport().removePropertyChangeListener(Preferences.SMILES_GEN, this);        
        super.finalize();
    }
	protected List<String> prepareInput(String path, IMolecule mol) throws ShellException {
		try {
			Object smiles = mol.getProperty(Preferences.getProperty(Preferences.SMILES_FIELD)); 
			if (isGenerateSmiles() || (smiles == null)) {
                logger.debug("Generate smiles\t");
                IMolecule c = mol;
                if (dropHydrogens) {
                    c = (IMolecule) mol.clone();
                    MFAnalyser mf = new MFAnalyser(c);
                    c = (IMolecule) mf.removeHydrogensPreserveMultiplyBonded();
                }
                
			    smiles = gen.createSMILES(c);
            } else logger.debug("Use smiles from file\t");
            logger.debug(smiles);			
			FileWriter writer = new FileWriter(path + File.separator + getInputFile());
			writer.write(smiles.toString());
            /*
			Object title = mol.getProperty(CDKConstants.TITLE);
			if (title != null) {
				writer.write('\t');
				writer.write(title.toString());
			}
            */
			writer.write('\n');
			writer.flush();
			writer.close();
			
			List<String> list = new ArrayList<String>();
			list.add("-o");
			list.add(getOutputFile());
			list.add("-p");
			list.add("mmxconst.prm");
			list.add(getInputFile());
			return list;
		} catch (Exception x) {
			throw new ShellException(this,x);
		}
	}
	@Override
	public String toString() {
		return "smi2sdf";
	}
    protected boolean exitCodeOK(int exitVal) {
    	return exitVal != 0;
    }	
	@Override
	protected IMolecule transform(IMolecule mol) {
		return mol;
	}
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            addExecutableWin(Preferences.getProperty(Preferences.SMI2SDF_WIN));
            addExecutableLinux(Preferences.getProperty(Preferences.SMI2SDF_LINUX));
        } catch (Exception x) {
            logger.error(x);
        }
        
    }
    public synchronized boolean isGenerateSmiles() {
        return  Preferences.getProperty(Preferences.SMILES_GEN).equals("true");
    }
    public synchronized void setGenerateSmiles(boolean generateSmiles) {
        if (generateSmiles)
        Preferences.setProperty(Preferences.SMILES_GEN,"true");
        else
            Preferences.setProperty(Preferences.SMILES_GEN,"false");
    }
    public synchronized boolean isDropHydrogens() {
        return dropHydrogens;
    }
    public synchronized void setDropHydrogens(boolean dropHydrogens) {
        this.dropHydrogens = dropHydrogens;
    }        
}


