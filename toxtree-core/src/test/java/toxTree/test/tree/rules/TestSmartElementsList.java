/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
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


package toxTree.test.tree.rules;

import java.util.TreeSet;

import junit.framework.TestCase;
import toxTree.core.SmartElementsList;

public class TestSmartElementsList extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestSmartElementsList.class);
	}

	/*
	 * Test method for 'toxTree.core.SmartElementsList.contains(Object)'
	 */
	public void testContainsObject() {
		SmartElementsList list = new SmartElementsList();
		list.add("C");
		assertTrue(list.contains("C"));
		list.add("Cl");
		assertTrue(list.contains("Cl"));
		assertTrue(list.contains("X"));
	}
	public void testEquals() {
		SmartElementsList list = new SmartElementsList();
		list.add("C");
		list.add("X");
		SmartElementsList list1 = new SmartElementsList();
		list1.add("X");
		list1.add("C");
		assertEquals(list,list1);
		
		
	}

	/*
	 * Test method for 'toxTree.core.SmartElementsList.getHalogens()'
	 */
	public void testGetHalogens() {
		SmartElementsList list = new SmartElementsList();
		TreeSet<String> h = list.getHalogens();
		TreeSet<String> hal = new TreeSet<String>();
		hal.add("F");
		hal.add("Cl");
		hal.add("Br");
		hal.add("I");
		assertEquals(hal,h);
	}

	/*
	 * Test method for 'toxTree.core.SmartElementsList.setHalogens(String[])'
	 */
	public void testSetHalogens() {
		TreeSet<String> hal = new TreeSet<String>();
		hal.add("F");
		hal.add("Cl");
		hal.add("Br");
		SmartElementsList list = new SmartElementsList();
		list.add("X");
		list.setHalogens(hal);
		TreeSet<String> h = list.getHalogens();
		assertEquals(hal,h);
		assertTrue(list.contains("Cl"));
		assertFalse(list.contains("I"));
	}

}


