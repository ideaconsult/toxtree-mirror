/*
Copyright (C) 2005-2006  

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
*//**
 * <b>Filename</b> CategoriesTableModel.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-8
 * <b>Project</b> toxTree
 */
package toxtree.ui.tree.categories;

import java.util.Observable;
import java.util.Observer;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.tree.DefaultCategory;
import toxtree.ui.tree.ListTableModel;

/**
 * An {@link javax.swing.table.AbstractTableModel} descendant used in {@link toxtree.ui.tree.categories.CategoriesPanel} 
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-8
 */
public class CategoriesTableModel extends ListTableModel implements Observer  {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 5149481144969863275L;
    protected int columnsno =1;

    public CategoriesTableModel(IDecisionCategories data, int columns) {
        super(data);
        columnsno = columns;
    }
    
    /**
     * 
     */
    public CategoriesTableModel(IDecisionCategories data) {
        this(data,1);
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return columnsno;
    }



    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
    	IDecisionCategory c = (IDecisionCategory)list.get(rowIndex);
    	if (columnsno == 1) return "<html><b>" + c.toString() + "</b></html>";
    	else
    		switch (columnIndex) {
    		case 1: return c.getID();
    		case 0: return c.getName();
    		default: return c.getName();
    		}
        } catch (Exception x) {
            return x.getMessage();
        }
        //String s =  categoriesCopy[rowIndex].toString();
        //String t =  ((IDecisionCategory) categoriesCopy[rowIndex]).getThreshold();
        
    }
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(Observable o, Object arg) {
    	if (o == list)
    		fireTableStructureChanged();
    	else 
    		setList((IDecisionCategories)o);

    }
    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
	public String getColumnName(int column) {
    	switch (column) {
    	case 0: return "Name";
    	case 1: return "ID";
    	default: return "";
    	}
        
    }
    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    @Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    public IDecisionCategories getData() {
        return (IDecisionCategories)list;
    }

    public boolean isSelected(int row) {
    	
    	//return list.get(row).equals(((IDecisionCategories)list).getSelected());
    	return ((IDecisionCategories)list).get(row).isSelected();
        
    }
    
    public IDecisionCategory getCategory(int index) {
    	return ((IDecisionCategories)list).getCategory(new DefaultCategory("",(index+1)));
    }
    
}

