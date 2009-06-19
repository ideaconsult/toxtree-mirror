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

import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;
import toxTree.core.Introspection;
import toxTree.exceptions.IntrospectionException;
import toxTree.tree.UserDefinedTree;
import toxTree.tree.cramer.Rule32;

public class IntrospectionTest extends TestCase {

	public static void main(String[] args) {
	}

	public IntrospectionTest(String arg0) {
		super(arg0);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test1() throws Exception {
		Introspection.listBaseTypes(Rule32.class,">");
		
		Class tree = UserDefinedTree.class;
		Package top = tree.getPackage();
		System.out.println(top.toString());
		Class[] c = top.getClass().getClasses();
		for (int i =0; i<c.length;i++)
			System.out.println(c[i]);
		try {
		ArrayList rules = Introspection.implementInterface(
				this.getClass().getClassLoader(),
				new File("toxTree/dist"),"toxTree.core.IDecisionRule");
		assertTrue(rules.size() > 0);	
		for (int i = 0; i < rules.size();i++) System.out.println(rules.get(i));
		
		ArrayList methods = Introspection.implementInterface(
				this.getClass().getClassLoader(),
				new File("toxTree/dist"),"toxTree.core.IDecisionMethod");		
		assertTrue(methods.size() > 0);	
		
		for (int i = 0; i < methods.size();i++) System.out.println(methods.get(i));
		} catch (IntrospectionException x) {
			x.printStackTrace();
			fail();
		}
	}
	public void testImplementsInterface() {
		Class clazz = Introspection.implementsInterface(toxTree.tree.cramer.Rule32.class,"toxTree.core.IDecisionRule");
		assertNotNull(clazz);
	}
	public void testImplementsInterfaceString() {
		try {
		Class clazz = Introspection.implementsInterface("toxTree.tree.cramer.Rule32","toxTree.core.IDecisionRule");
		assertNotNull(clazz);
		} catch (IntrospectionException x) {
			x.printStackTrace();
			fail();
		}
		
	}	
	public void testEnvironment() throws Exception {
	    String value = Introspection.getToxTreeRoot();
	    assertNotNull(value);
	    System.out.println(value);
	}
}
