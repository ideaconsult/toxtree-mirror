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

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionRule;


public class MultiLabelDecisionNode extends DecisionNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9026738433152349465L;
	
	/**
	 * Constructor
	 * 
	 */
	public MultiLabelDecisionNode() {
		super();
	}
	/**
	 * Constructs a decision node with rule, null next nodes.
	 * @param rule
	 */
	public MultiLabelDecisionNode(IDecisionRule rule) {
		this();
		setRule(rule);
	}
	public void setBranch(boolean answer, IDecisionRule node) {
		if (node instanceof DecisionNode) {
			int index = 0;
			if (answer) index = 1; else index = 0;
			nodes[index] = (DecisionNode)node; 
		}
		//else setRule(node);
		setChanged();
		notifyObservers();		
	}		
	public void setCategory(boolean answer, IDecisionCategory category) {
		if (answer) {
			if (category != null) {
				categories[1] = category;
			}
		} else {
			if (category != null) {
				categories[0] = category;
			}
		}
		setChanged();
		notifyObservers();		
	}
}


