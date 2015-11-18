/*
Copyright Nina Jeliazkova (C) 2005-2011  
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
package toxtree.plugins.verhaar2.test;

import java.util.logging.Level;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.core.IDecisionRule;
import toxTree.tree.DecisionNode;
import toxTree.tree.rules.IAlertCounter;
import toxTree.tree.rules.RuleInitAlertCounter;
import toxtree.plugins.verhaar2.VerhaarScheme2;

public class VerhaarSchemeTest extends RulesTestCase {

	public VerhaarSchemeTest() throws Exception {
		super();
		rules = new VerhaarScheme2();

	}

	@Test
	public void testReflection() throws Exception {
		VerhaarScheme2 vs = (VerhaarScheme2) Class.forName(
				"toxtree.plugins.verhaar2.VerhaarScheme2").newInstance();
		vs.calculate(MoleculeFactory.makeBenzene());
	}

	@Test
	public void testVerhaar() throws Exception {
		VerhaarScheme2 rulesNew = (VerhaarScheme2) objectRoundTrip(rules,
				"VerhaarScheme");
		rules = rulesNew;
		tryImplementedRules();
	}

	/**
	 * public void testPrintVerhaar() { try { System.out.println(new
	 * VerhaarScheme().getRules()); } catch (Exception x) { x.printStackTrace();
	 * } }
	 */
	@Test
	public void testIsCounter() throws Exception {

		int nr = rules.getNumberOfRules();
		int ne = 0;
		for (int i = 0; i < nr; i++) {
			IDecisionRule rule = rules.getRule(i);
			try {
				IDecisionRule irule = ((DecisionNode) rule).getRule();
				if (irule instanceof IAlertCounter) {
					// ok
				} else {
					if (irule instanceof RuleInitAlertCounter)
						continue;
					ne++;
					logger.log(Level.FINE, rule.toString());
				}
			} catch (Exception x) {
				logger.log(Level.SEVERE, rule.toString() + x.getMessage());
				ne++;
			}

		}
		logger.log(Level.INFO, "Number of rules available\t" + nr);
		logger.log(Level.INFO,
				"Number of rules not implementing IAlertCounter\t" + ne);
		Assert.assertEquals(0, ne);
	}
}
