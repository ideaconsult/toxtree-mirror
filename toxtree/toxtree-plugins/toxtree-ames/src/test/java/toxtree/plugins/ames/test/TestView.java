/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

Contact: jeliazkova.nina@gmail.com

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

package toxtree.plugins.ames.test;

import toxTree.core.Introspection;
import toxTree.exceptions.DecisionMethodException;
import toxtree.plugins.ames.AmesMutagenicityRules;
import toxtree.ui.tree.TreeFrame;

public class TestView {

	public TestView() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void view() {
		try {
			Introspection.setLoader(getClass().getClassLoader());		
			AmesMutagenicityRules rules = new AmesMutagenicityRules();
			TreeFrame f = new TreeFrame(rules);
            f.pack();
            f.setVisible(true);
			//rules.getEditor().edit(null,rules);
		} catch (DecisionMethodException x) {
			
		}	
	}
	public static void main(String[] args) {
		
		TestView v = new TestView();
		v.view();
	}
}


