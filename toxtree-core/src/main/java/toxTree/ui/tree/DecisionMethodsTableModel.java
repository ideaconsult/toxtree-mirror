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
package toxTree.ui.tree;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionMethodsList;
import toxTree.core.IDecisionResult;
import toxTree.tree.DecisionResultsList;

/**
 * Table model for {@link DecisionResultsList}.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class DecisionMethodsTableModel extends ListTableModel {
	protected String[] header = {"#","Delete","Method","Result","Details","Apply"};
    protected String[] tooltips = {"",
            "Click here to remove decision tree from the decision forest",
            "Click here to view/edit decision tree",
            "","",
            "Click here to apply decision tree"};
	/**
	 * 
	 */
	private static final long serialVersionUID = 8738193886962853436L;

	public DecisionMethodsTableModel(IDecisionMethodsList list) {
		super(list);

	}
	@Override
	public int getColumnCount() {
		return 6;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			//System.out.println(list.getClass().getName());
			IDecisionResult result = ((DecisionResultsList)list).getResult(rowIndex);
			IDecisionMethod method = result.getDecisionMethod();		
		
			switch (columnIndex) {
			case 0: return new Integer(rowIndex+1);
            case 1: return "<html><u>Delete</u></html>";
			case 2: return method;
			case 3: if (result.isEstimating()) return ""; return result.getCategory();
			case 4: if (result.isEstimating()) return ""; else return result.explain(false);
			case 5: return "<html><u>Run</u></html>";
			default: return "NA";
			}
		}  catch (Exception x) {
			x.printStackTrace();
			return "NA";
		}
	}	
	@Override
	public String getColumnName(int column) {
		return header[column];
	}
    public String getTooltip(int column) {
        return tooltips[column];
    }

}
