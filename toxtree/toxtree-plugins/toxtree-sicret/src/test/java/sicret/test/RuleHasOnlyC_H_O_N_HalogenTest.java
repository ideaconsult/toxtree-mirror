/*
Copyright Ideaconsult Ltd.(C) 2006 - 2015
Contact: jelaizkova.nina@gmail.com

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

package sicret.test;

import junit.framework.Assert;

import org.junit.Test;

import sicret.rules.RuleHasOnlyC_H_O_N_Halogen;
import toxTree.query.FunctionalGroups;

public class RuleHasOnlyC_H_O_N_HalogenTest {

	@Test
	public void testVerifyRule() throws Exception {
		RuleHasOnlyC_H_O_N_Halogen rule = new RuleHasOnlyC_H_O_N_Halogen();

		boolean result = rule.verifyRule(FunctionalGroups.createAtomContainer(
				"ClC(Cl)(Cl)C", false));
		Assert.assertFalse(result);
		Assert.assertFalse(rule.verifyRule(FunctionalGroups
				.createAtomContainer("ClC(Cl)(Cl)CC(=O)CCC(N)CP", false)));
		Assert.assertTrue(rule.verifyRule(rule.getExampleMolecule(true)));
		Assert.assertFalse(rule.verifyRule(rule.getExampleMolecule(false)));

	}

}
