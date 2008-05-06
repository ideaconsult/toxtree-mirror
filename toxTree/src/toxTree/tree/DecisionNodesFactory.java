/*
Copyright (C) 2005-2006  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package toxTree.tree;

import java.io.Serializable;

import toxTree.core.IDecisionCategories;
import toxTree.exceptions.DecisionMethodException;

public class DecisionNodesFactory implements IDecisionNodesFactory, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1633489192067405069L;
	protected boolean multiLabel = false;
	public DecisionNodesFactory() {
		this(false);
	}
	public DecisionNodesFactory(boolean multiLabel) {
		setMultiLabel(multiLabel);
	}	
	public DecisionNodesList createNodesList() {
		if (multiLabel) return new MultiLabelDecisionNodesList();
		else return new DecisionNodesList();
	}
	public DecisionNodesList createNodesList(IDecisionCategories classes, 
			String[] customRules,
			int[][] customTransitions) throws DecisionMethodException  {
		if (multiLabel) 
			return new MultiLabelDecisionNodesList(classes,customRules,customTransitions);
		else 
			return new DecisionNodesList(classes,customRules,customTransitions);
	}
	public boolean isMultiLabel() {
		return multiLabel;
	}
	public void setMultiLabel(boolean multiLabel) {
		this.multiLabel = multiLabel;
	}
}


