/*
Copyright Ideaconsult Ltd.(C) 2006  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/

package toxtree.plugins.lewis.test;

import toxTree.core.Introspection;
import toxtree.plugins.lewis.LewisTree;
import toxtree.ui.tree.TreeFrame;

public class TestView {

	public TestView() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void view() {
			Introspection.setLoader(getClass().getClassLoader());		
			LewisTree rules = new LewisTree();
			TreeFrame f = new TreeFrame(rules);
            f.pack();
            f.setVisible(true);
			//rules.getEditor().edit(null,rules);

	}
	public static void main(String[] args) {
		
		TestView v = new TestView();
		v.view();
	}
}


