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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectListener;

import toxTree.logging.TTLogger;
/**
 * An attempt to implement {@link org.openscience.cdk.interfaces.SetOfAtomContainers} interface 
 * as a {@link java.util.List} 
 * @author Nina Jeliazkova
 *
 */
public class ListOfAtomContainers extends ArrayList<IAtomContainer> implements	IAtomContainerSet {
	protected transient TTLogger logger = new TTLogger(ListOfAtomContainers.class);
	protected String id = "";
	protected Map properties = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7586214506838048913L;

	public ListOfAtomContainers() {
		super();
	}

	public ListOfAtomContainers(int arg0) {
		super(arg0);
	}

	public ListOfAtomContainers(Collection arg0) {
		super(arg0);
		
	}

	public void addAtomContainer(IAtomContainer atomContainer) {
		add(atomContainer);
	}

	public void removeAtomContainer(IAtomContainer atomContainer) {
		remove(atomContainer);
	}

	public void removeAllAtomContainers() {
		clear();
	}

	public void removeAtomContainer(int pos) {
		remove(pos);
	}


	public void setMultiplier(int position, Double multiplier) {
		// TODO Auto-generated method stub

	}

	public Double getMultiplier(int arg0) {
		return null;
	}
	public Double[] getMultipliers() {
		return null;
	}

	public boolean setMultipliers(Double[] newMultipliers) {
		return false;
	}

	public void addAtomContainer(IAtomContainer atomContainer, double multiplier) {
		addAtomContainer(atomContainer);
	}

	public void add(IAtomContainerSet atomContainerSet) {
		for (int i=0; i< atomContainerSet.getAtomContainerCount(); i++) 
			addAtomContainer(atomContainerSet.getAtomContainer(i));
	}
	public IAtomContainer[] getAtomContainers() {
		try {
			IAtomContainer[] m = new IAtomContainer[getAtomContainerCount()];
			for (int i=0; i<size();i++) 
				m[i] = (IAtomContainer) get(i);
			return m;
		} catch (Exception x) {
			logger.error(x);
			return null;
		}
	}

	public IAtomContainer getAtomContainer(int number) {
		return (IAtomContainer)get(number);
	}

	public Double getMultiplier(IAtomContainer arg0) {
		return 1.0;
	}

	public boolean setMultiplier(IAtomContainer arg0, Double arg1) {
		return false;
	}

	public int getAtomContainerCount() {
		return size();
	}

	public void addListener(IChemObjectListener col) {
		// TODO Auto-generated method stub

	}

	public int getListenerCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void removeListener(IChemObjectListener col) {
		// TODO Auto-generated method stub

	}

	public void notifyChanged() {
		// TODO Auto-generated method stub

	}



	public void setProperty(Object description, Object property) {
		properties.put(description,property);
	}

	public void removeProperty(Object description) {
		properties.remove(description);
	}

	public Object getProperty(Object description) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map getProperties() {
		return properties;
	}

	public String getID() {
		return id;
	}

	public void setID(String identifier) {
		this.id = identifier;
	}

	public void setFlag(int flag_type, boolean flag_value) {
		// TODO Auto-generated method stub

	}

	public boolean getFlag(int flag_type) {
		// TODO Auto-generated method stub
		return false;
	}
	public void setProperties(Map<Object, Object> properties) {
        this.properties = properties;
	    
	}


	public void setFlags(boolean[] flagsNew) {
		// TODO Auto-generated method stub

	}

	public boolean[] getFlags() {
		// TODO Auto-generated method stub
		return null;
	}

	public IChemObjectBuilder getBuilder() {

		return DefaultChemObjectBuilder.getInstance();
	}
	/* (non-Javadoc)
     * @see org.openscience.cdk.interfaces.IChemObject#setNotification(boolean)
     */
    public void setNotification(boolean arg0) {
        // TODO Auto-generated method stub

    }
    /* (non-Javadoc)
     * @see org.openscience.cdk.interfaces.IChemObject#getNotification()
     */
    public boolean getNotification() {
        // TODO Auto-generated method stub
        return false;
    }
    /* (non-Javadoc)
     * @see org.openscience.cdk.interfaces.IChemObject#notifyChanged(org.openscience.cdk.interfaces.IChemObjectChangeEvent)
     */
    public void notifyChanged(IChemObjectChangeEvent arg0) {
        // TODO Auto-generated method stub

    }
    public Iterable<IAtomContainer> atomContainers() {
    	return this;
    }
    public void sortAtomContainers(Comparator<IAtomContainer> comparator) {
    	Collections.sort(this,comparator);
    	
    }

	public void replaceAtomContainer(int position, IAtomContainer container) {
		set(position,container);
		
	}
}
