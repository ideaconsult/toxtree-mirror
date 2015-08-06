/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

Contact: jeliazkova.nina@gmail.com

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
package toxtree.ui.molecule;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.result.DoubleResult;

import toxTree.query.MolFlags;

/**
 * Used to display properties of a {@link org.openscience.cdk.interfaces.Molecule} obtained by 
 * getProperties()
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-3
 */
public class AtomContainerPropertyTableModel extends AbstractTableModel {
	protected IAtomContainer ac;
	protected List<String> names = new ArrayList<String>();
	private String[] columnNames = new String[] {"Name","Value"};
    protected static NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -2692032007126882924L;

	/**
	 * 
	 */
	public AtomContainerPropertyTableModel() {
		super();
        nf.setMinimumFractionDigits(4);
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {

		return names==null?0:names.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
        if (( rowIndex >= names.size()) || (columnIndex >= columnNames.length )) {
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
        Map<Object,Object> properties = object.getProperties();
        if (properties == null) return;
        Iterator<Object> iter = properties.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            String keyName = key.toString();
            Object value = properties.get(key);
            if (accepts(keyName,value)) {
            	names.add(keyName);                
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
