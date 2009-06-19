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
package toxTree.test;

import toxTree.core.SmartElementsList;
import junit.framework.TestCase;

public class SmartElementsListTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(SmartElementsListTest.class);
	}

	/*
	 * Test method for 'toxTree.core.SmartElementsList.contains(Object)'
	 * verify if the test is still relevant
	 */
	public void testContainsObject() {
		SmartElementsList list = new SmartElementsList();
		list.add("C");
		list.add("H");
		list.add("Cl");
		assertTrue(list.contains("C"));
		assertTrue(list.contains("H"));
		assertTrue(list.contains("Cl"));
		
		SmartElementsList query = new SmartElementsList();
		query.add("C");
		assertTrue(list.containsAll(query));
		query.add("Cl");		
		
		assertTrue(list.containsAll(query));
		query.add("H");
		
		assertTrue(list.containsAll(query));
		assertTrue(query.containsAll(list));
		assertEquals(list,query);
		
		query.add("N");
		assertNotSame(list,query);
		
		System.out.println(query);
	}

	/*
	 * Test method for 'toxTree.core.SmartElementsList.add(String)'
	 */
	public void testAddString() {

	}

}


