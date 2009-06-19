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

package toxTree.ui.tree.qsar;

import toxTree.qsar.LinearDiscriminantRule;
import toxTree.ui.tree.rules.RuleEditor;

public class RuleQSARModelEditor extends RuleEditor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -730521306532147560L;

	public RuleQSARModelEditor(LinearDiscriminantRule object) {
		super(object);
		setRule((LinearDiscriminantRule)object);
		/*
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Explanation",explanationPane);
		tabbedPane.addTab("QSAR",((LinearDiscriminantRule)object).getModel().getEditor().getVisualCompoment());
		add(tabbedPane,BorderLayout.CENTER);
		*/
	}
}


