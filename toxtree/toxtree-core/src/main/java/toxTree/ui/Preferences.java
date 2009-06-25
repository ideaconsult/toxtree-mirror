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

package toxTree.ui;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Preferences {
	protected static PropertyChangeSupport propertyChangeSupport;
	public static String SHOW_AROMATICITY="showAromaticity";
	public static String GENERATE2D="generate2D";
	public static String DEFAULT_DIR="defaultDir";
	public static String SMILESPARSER="smilesParser";
    public static String STOP_AT_UNKNOWNATOMTYPES="atomtypes.stop";
    public static String MOPAC_DIR="mopac.dir";
    public static String MOPAC_EXE="mopac.exe";
    public static String MENGINE_WIN="mengine.win";
    public static String MENGINE_LINUX="mengine.linux";
    public static String OPENBABEL_WIN="obabel.win";
    public static String OPENBABEL_LINUX="obabel.linux";
    public static String SMI2SDF_WIN="smi2sdf.win";
    public static String SMI2SDF_LINUX="smi2sdf.linux";
    public static String SMILES_FIELD="SMILES";
    public static String SMILES_GEN="smi2sdf.smiles.generate";
    
    
    
	protected final static String filename="toxtree.pref";
	protected static Properties props = null;

	public static Object[][] default_values = {
		//{tag, name, default value, class, hint, hidden}
		{DEFAULT_DIR,"Default directory","",String.class,"Remembers the directory of the last opened/saved file",false},		
		{SHOW_AROMATICITY,"Show circle in an aromatic ring","true",Boolean.class,"Toggles displaying aromatic rings",false},
		{GENERATE2D,"Generate 2d coordinates if none exist","true",Boolean.class,"Generate 2D coordinates of the structures, entered as SMILES",false},
        {SMILESPARSER,"Use Openbabel SMILES parser","true",Boolean.class,"Toggles usage of Openbabel vs. CDK SMILES parser. Openbabel available at http://openbabel.org/",false},
        {STOP_AT_UNKNOWNATOMTYPES,"Stop at unknown atom types","false",Boolean.class,"Report an error if an unknown atom type is encountered.",false},
        
        {OPENBABEL_WIN,"Path to OpenBabel (Windows)","helper/openbabel/win/babel.exe",String.class,"Where to find Openbabel in Windows",false},
        {OPENBABEL_LINUX,"Path to OpenBabel (Linux)","helper/openbabel/linux/babel",String.class,"Where to find OpenBabel in Linux",false},
		{MOPAC_DIR,"MOPAC directory","helper",String.class,"Directory where MOPAC resides",false},
        {MOPAC_EXE,"MOPAC executable","MOPAC_7.1.exe",String.class,"Name of the MOPAC executable. Used to calculate electronic descriptors as eHOMO/eLUMO, required by some plugins. OpenMopac available at http://openmopac.net/",false},
        {MENGINE_WIN,"mengine (3D builder - Windows)","helper/smi23d/win/mengine.exe",String.class,"MMFF94 force field by mengine (http://www.chembiogrid.org/cheminfo/smi23d/). Structures without 3D coordinates are submitted to mengine before running MOPAC.",false},
        {MENGINE_LINUX,"mengine (3D builder - Linux)","helper/smi23d/linux/mengine",String.class,"MMFF94 force field by mengine (http://www.chembiogrid.org/cheminfo/smi23d/). Structures without 3D coordinates are submitted to mengine before running MOPAC.",false},
        {SMI2SDF_WIN,"smi2sdf (Windows) (used by mengine)","helper/smi23d/win/smi2sdf.exe",String.class,"Generates rough 3D structure. First step before mengine http://www.chembiogrid.org/cheminfo/smi23d/)",false},
        {SMI2SDF_LINUX,"smi2sdf (Linux) (used by mengine)","helper/smi23d/linux/smi2sdf",String.class,"Generates rough 3D structure. First step before mengine (http://www.chembiogrid.org/cheminfo/smi23d/)",false},
        {SMILES_FIELD,"Name of the field containing SMILES","SMILES",String.class,"Name of the field, containing SMILES in SDF,CSV,TXT files.",true},
        {SMILES_GEN,"Generate the smiles submitted to smi2sdf","true",Boolean.class,"Generate the smiles , submitted to smi2sdf, or use the one in the file",true}

	};
	
	
	protected static Properties getDefault() {
		Properties p = new Properties();
		for (int i=0; i < default_values.length; i++) {
			p.setProperty(default_values[i][0].toString(),default_values[i][2].toString());			
		}
		return p;
	}
	protected static Properties loadProperties() throws IOException {
		Properties p = new Properties();
		InputStream in = new FileInputStream(new File(filename));
		p.load(in);
		in.close();
		return p;
	}
	public static Properties getProperties() {
		if (props == null)  
			try {
				props = loadProperties();
				if (props.size()==0)
					props = getDefault();
				propertyChangeSupport = new PropertyChangeSupport(props);
			} catch (Exception x) {
				props = getDefault();
				propertyChangeSupport = new PropertyChangeSupport(props);
			}
		return props;
	}
	/*
	@Override
	protected void finalize() throws Throwable {
		saveProperties("Toxtree");
		super.finalize();
	}
	*/
	public static void saveProperties(String comments) throws IOException {
		if (props == null) return;
		OutputStream out = new FileOutputStream(new File(filename));
		props.store(out,comments);
		out.close();
	}
	public static void setProperty(String key, String value) {
		Properties p = getProperties();
		String oldValue = p.getProperty(key);
		p.put(key, value);
		propertyChangeSupport.firePropertyChange(key, oldValue, value);
	}
	public static String getProperty(String key) {
		Properties p = getProperties();
        Object o = p.get(key);
        if (o==null) {
            Properties ps = getDefault();
            o = ps.get(key);
            if (o != null) setProperty(key, o.toString());
            else setProperty(key, "");
        }
        return o.toString();
	}
	public static PropertyChangeSupport getPropertyChangeSupport() {
		if (propertyChangeSupport == null)
			getProperties();
		return propertyChangeSupport;
	}
	public static void setPropertyChangeSupport(
			PropertyChangeSupport propertyChangeSupport) {
		Preferences.propertyChangeSupport = propertyChangeSupport;
	}	
}

