/*
Copyright Nina Jeliazkova (C) 2005-2006  
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
package verhaar.test;

import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.exceptions.DecisionMethodException;
import verhaar.VerhaarScheme;

public class VerhaarSchemeTest extends RulesTestCase {

	public static void main(String[] args) {
	}

	public VerhaarSchemeTest() {
		super();
		try {
			rules = new VerhaarScheme();
		} catch (DecisionMethodException x) {
			fail();
		}
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testReflection() throws Exception  {
		VerhaarScheme vs = (VerhaarScheme) Class.forName("verhaar.VerhaarScheme").newInstance();
		vs.calculate(MoleculeFactory.makeBenzene());
	}
	public void testVerhaar() {
		VerhaarScheme rulesNew = (VerhaarScheme)objectRoundTrip(rules,"VerhaarScheme");
		rules = rulesNew;
		tryImplementedRules();
	}
	/**
	public void testPrintVerhaar() {
		try {
			System.out.println(new VerhaarScheme().getRules());
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	*/
}
