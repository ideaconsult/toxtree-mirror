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

package toxtree.test.plugins.smartcyp;

import org.junit.Test;

import toxTree.exceptions.DecisionMethodException;
import toxtree.plugins.smartcyp.SMARTCYPPlugin;

public class TestView {

	public TestView() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void view() {
		try {
			SMARTCYPPlugin rules = new SMARTCYPPlugin();
			
			rules.getEditor().edit(null,rules);
		} catch (DecisionMethodException x) {
			
		}	
	}
	public static void main(String[] args) {
		TestView v = new TestView();
		v.view();
	}
	@Test
	public void test() {
		
	}
}


