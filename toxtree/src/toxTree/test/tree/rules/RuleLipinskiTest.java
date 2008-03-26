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
package toxTree.test.tree.rules;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;
import toxTree.tree.rules.RuleLipinski5;

public class RuleLipinskiTest extends TestCase {

	public static void main(String[] args) {
	}

	public RuleLipinskiTest(String arg0) {
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

	public void testSerialization() {
		RuleLipinski5 rule = new RuleLipinski5();
		try {
			ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("rule5.tmp"));
			o.writeObject(rule);
			o.close();
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("rule5.tmp"));
			RuleLipinski5 ruleNew = (RuleLipinski5) in.readObject();
			assertEquals(rule,ruleNew);
		} catch (IOException x) {
			x.printStackTrace();
			fail();
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
			fail();
		}

		
	}
}
