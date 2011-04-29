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

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.query.QueryAtomContainers;

/**
 * 
 * Table model for {@link QueryAtomContainers}.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class QueryAtomContainersModel extends ListTableModel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3277542698124116055L;

    public QueryAtomContainersModel(List list,IDecisionRule rule) {
        super(list);
        if (rule instanceof Observable) ((Observable) rule).addObserver(this);
    }
	public QueryAtomContainersModel(QueryAtomContainers list, IDecisionRule rule) {
		super(list);
		if (rule instanceof Observable) ((Observable) rule).addObserver(this);
	}	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0: {
			try {
			IAtomContainer a = (IAtomContainer)((QueryAtomContainers) list).get(rowIndex);
			return a.getID();
			} catch (Exception x) {
				return super.getValueAt(rowIndex,columnIndex);
			}
		}
		default: return "NA";
		}
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		fireTableDataChanged();
		
	}

}
