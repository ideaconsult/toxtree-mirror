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
package toxtree.ui.tree;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;


/**
 * TODO add description
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-2
 */
public class ListTableModel extends AbstractTableModel implements Observer {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1099894331248616748L;
	protected List list = null;
	protected Class aClass = null;
	/**
	 * 
	 */

	public ListTableModel(List list) {
		super();
		setList(list);
	}
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
        if (list != null)
            return list.size();
        else return 0;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 1; //rule
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	/*
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			IDecisionRule rule = tr.getRule(rowIndex);
			switch (columnIndex) {
			case 0: return rule;
			case 1: return new Boolean(tr.getRuleResult(rowIndex).isResult());
			case 2: {
				IDecisionCategory category = tr.getCategory(rowIndex);
				if (category == null) return "";
				else return category;
			}			
			case 3: {
				org.openscience.cdk.interfaces.AtomContainer mol = tr.getMolecule(rowIndex);
				if (mol == null) return "";
				else return mol.getID();
			}
			default: return "NA";
			}
		} catch (DecisionResultException x) {
			return x.getMessage();
		}
	}
	*/
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			switch (columnIndex) {
			case 0: return list.get(rowIndex);
			default: return "NA";
			}
		}  catch (Exception x) {
			return "NA";
		}
	}
    public void setList(List data) {
    	if ((this.list != null) && (this.list instanceof Observable))
    		((Observable) this.list).deleteObserver(this);
    	
        this.list = data;
        
        if ((this.list != null) && (this.list instanceof Observable)) {
	    //    categoriesCopy = data.toArray();
	            ((Observable) this.list).addObserver(this);
        }
        
        fireTableStructureChanged();
    }
	

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {

		return "";
	}
	public void update(Observable arg0, Object arg1) {
		fireTableDataChanged();
		
	}
    public synchronized List getList() {
        return list;
    }
}
