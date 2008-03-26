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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.result.DoubleResult;
import org.openscience.cdk.qsar.result.IntegerResult;

import toxTree.query.MolFlags;

/**
 * Used to display properties of a {@link org.openscience.cdk.interfaces.Molecule} obtained by 
 * getProperties()
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-3
 */
public class AtomContainerPropertyTableModel extends AbstractTableModel {
	protected IAtomContainer ac;
	protected ArrayList names;
	//protected ArrayList values;
	private String[] columnNames;
    protected static NumberFormat nf = NumberFormat.getInstance();
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -2692032007126882924L;

	/**
	 * 
	 */
	public AtomContainerPropertyTableModel() {
		super();
		names = new ArrayList();
		//values = new ArrayList();
        columnNames = new String[2];
        columnNames[0] = "Name";
        columnNames[1] = "Value";
        nf.setMinimumFractionDigits(4);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 2;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {

		return names.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
        if ( rowIndex >= names.size() || columnIndex >= columnNames.length ) {
            return null;
        }
        
        if (columnIndex == 0) {
            return names.get(rowIndex);
        } else if (columnIndex == 1) {
            Object o = ac.getProperty(names.get(rowIndex));
            if (o == null) return "";
            else if (o instanceof Number) { 
            	if (o.equals(Double.NaN)) return o.toString();
            	else return nf.format((Number)o);
            } else if (o instanceof DoubleResult) {
            	if (((DoubleResult)o).doubleValue() == Double.NaN) return o.toString();
            	else return nf.format(((DoubleResult)o).doubleValue());
            } else return o.toString();
        } else {
            return null;
        }
    }
    private void cleanTable() {
        names.clear();
        fireTableStructureChanged();
    }
    public void setAtomContainer(IAtomContainer object) {
        cleanTable();
        this.ac = object;
        if (object == null) return; 
        Map properties = object.getProperties();
        if (properties == null) return;
        Iterator iter = properties.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            if (key instanceof String) {
                String keyName = (String)key;
                Object value = properties.get(keyName);
                if (accepts(keyName,value)) {
	                names.add(keyName);                
	                //values.add(value.toString());
                }
            }  
        }
        Collections.sort(names);
        fireTableStructureChanged();        
    }    
    public boolean accepts(Object key, Object value) {
    	if (key.equals(CDKConstants.ALL_RINGS)) return false;
    	else if (key.equals(CDKConstants.SMALLEST_RINGS)) return false;
    	else if (key.equals(MolFlags.MOLFLAGS)) return false;
    	else return true;
    }


}
