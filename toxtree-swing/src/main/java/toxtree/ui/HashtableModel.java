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
package toxtree.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.table.AbstractTableModel;

/**
 * {@link AbstractTableModel} for {@link Hashtable}.
 * @author Nina Jeliazkova
 *
 */
public class HashtableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7658905911850280047L;
	protected String[] columnNames = {"ID","SMARTS",};
	protected Hashtable table;
	protected ArrayList keys;
	protected boolean[] enabled;
    boolean includeTranslated = true;
	
    public HashtableModel(Hashtable table) {
        this(table,true);
    }
	public HashtableModel(Hashtable table, boolean includeTranslated) {
		super();
		keys = new ArrayList();
		enabled = null;
        this.includeTranslated = includeTranslated;
		setTable(table);
		
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return keys.size();
	}

	public Object getValueAt(int row, int col) {
	    Object key = keys.get(row);
	    if (key == null) return "NA";
        if (includeTranslated)
    		switch (col) {
    		case 0: return key;
    		case 1: { Object o = table.get(key); 
    			if (o==null) return "NA"; else return o; }
    		default: return "";
    		}
        else
            switch (col) {
            case 1: { Object o = table.get(key); 
                if (o==null) return "NA"; else return o; }
            case 0: return new Boolean(enabled[row]);
            default: return "";
            }

		
	}
	/* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
     */
    @Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (includeTranslated)
            switch (columnIndex) {
            case 1: {
                Object key = keys.get(rowIndex);
                Object o = table.get(key);
                table.put(key, aValue);
                break;
            }
            default:
            }
       else
           switch (columnIndex) {
           case 0: {
            enabled[rowIndex] = ((Boolean) aValue).booleanValue();
            break;
           }
           };
    }
	/* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    @Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (includeTranslated)
            return columnIndex ==1;
        else
            return columnIndex ==0;
    }

	public Hashtable getTable() {
		if (enabled != null)
			for (int i=0; i < enabled.length; i++) 
				if (!enabled[i]) {
					table.remove(keys.get(i));
				}
		setTable(table);
		return table;
	}

	public void setTable(Hashtable table) {
	    this.table = table;
	    
		keys.clear();
		if (table != null) {
			Enumeration e = table.keys();
			while (e.hasMoreElements()) {
				Object key = e.nextElement();
				if (accept(key))
				keys.add(key);
			}	
			enabled = new boolean[keys.size()];
			for (int i=0; i < enabled.length; i++) enabled[i] = true;
		}
		Collections.sort(keys);
		fireTableStructureChanged();
	}
	@Override
	public String getColumnName(int arg0) {
		return columnNames[arg0];
	}
	@Override
	public Class getColumnClass(int columnIndex) {
        if (!includeTranslated && (columnIndex == 0)) return Boolean.class;
		else
		return super.getColumnClass(columnIndex);
	}
	protected boolean accept(Object key) {
		return true;
	}
}
