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
package toxTree.ui.molecule;

import javax.swing.JPanel;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;

import toxTree.data.DataContainer;

/**
 * This is an abstract class to be used as structure entry (by SMILES and other means)
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-3
 */
public abstract class StructureEntryPanel extends JPanel {
	protected DataContainer dataContainer = null;
	
	/**
	 * 
	 */
	public StructureEntryPanel() {
		super();
	}


	/**
	 * @return Returns the atomContainer.
	 */
	public synchronized IAtomContainer getMolecule() {
		return dataContainer.getMolecule();
	}
	/**
	 * @param atomContainer The atomContainer to set.
	 */
	
	public synchronized void setMolecule(IAtomContainer atomContainer) {
    	if (!dataContainer.loadedFromFile()) 
    		dataContainer.setMolecule(atomContainer);
    }		
	
	/**
	 * @return Returns the toxData.
	 */
	public synchronized DataContainer getDataContainer() {
		return dataContainer;
	}
	/**
	 * @param toxData The toxData to set.
	 */
	public synchronized void setDataContainer(DataContainer toxData) {
		this.dataContainer = toxData;
	}
}
