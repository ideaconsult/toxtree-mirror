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

import toxTree.core.ToxTreePackageEntries;
import toxTree.core.ToxTreePackageEntry;

/**
 * Table model for displaying {@link ToxTreePackageEntries}. 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class ToxTreePackageEntryModel extends ListTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2126165671338667947L;


	public ToxTreePackageEntryModel(ToxTreePackageEntries list) {
		super(list);
	}	
	@Override
	public int getColumnCount() {
		return 2;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ToxTreePackageEntry e = (ToxTreePackageEntry) list.get(rowIndex);
		switch (columnIndex) {
		case 0: return e.getObjectTitle();
		case 2: return e;
		default :  return e.getPackageName();
		}
	}
	/*
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0: return "Rule";
		case 1: return "Package";
		default: return "";
		}
	}
	*/
	
}
