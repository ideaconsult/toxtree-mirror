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
*/
package toxtree.ui.tree.rules;

import java.util.List;

import toxTree.tree.DecisionNode;
import toxtree.ui.tree.ListTableModel;

/**
 * table model for {@link toxTree.tree.DecisionNodesList}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class DecisionNodesListTableModel extends ListTableModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -487377621045549030L;

	/**
	 * @param list
	 */
	public DecisionNodesListTableModel(List list) {
		super(list);
	}
	/* (non-Javadoc)
	 * @see toxTree.ui.tree.ListTableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 2;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DecisionNode node = (DecisionNode) list.get(rowIndex);
		switch (columnIndex) {
		case 0: return node;
		case 1: return new Boolean(node.isVisited());
		default: return "NA";
		}
	}
    @Override
	public String getColumnName(int col) {
    	switch (col) {
    	case 0: return "Rule";
    	case 1: return "Used in tree";
    	default : return "";
    	}
    }
    /* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
    @Override
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
