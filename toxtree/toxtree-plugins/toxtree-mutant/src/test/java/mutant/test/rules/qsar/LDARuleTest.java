/*
Copyright (C) 2005-2008  

Contact: nina@acad.bg

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

package mutant.test.rules.qsar;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.IMolecularDescriptor;

import toxTree.exceptions.DecisionMethodException;
import toxTree.qsar.IDescriptorPreprocessor;
import toxTree.qsar.LinearDiscriminantRule;
import toxTree.qsar.LinearPreprocessor;
import toxTree.query.MolAnalyser;

public abstract class LDARuleTest extends TestCase {
	protected LinearDiscriminantRule ruleToTest;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ruleToTest = createRuleToTest();

	}

	public abstract LinearDiscriminantRule createRuleToTest() throws Exception;

	public void hasDescriptors(int number) throws Exception {
		List<IMolecularDescriptor> d = ruleToTest.getModel().getDescriptors();
		assertNotNull(d);
		assertEquals(number, d.size());
	}

	@Test
	public void testPreprocessor() throws Exception {
		IDescriptorPreprocessor p = ruleToTest.getModel().getPreprocessor();
		System.out.println(ruleToTest.getExplanation());
		System.out.println("Model");
		System.out.println(ruleToTest.getModel());
		if (p != null) {

			assertTrue(p instanceof LinearPreprocessor);
			LinearPreprocessor lp = (LinearPreprocessor) p;
			double[] values = new double[p.getDimension()];
			double[] original = new double[p.getDimension()];
			for (int i = 0; i < values.length; i++) {
				values[i] = i + 1;
				original[i] = values[i];
			}
			double[] newvalues = lp.process(values);

			assertEquals(newvalues.length, values.length);

			for (int i = 0; i < values.length; i++) {
				/*
				 * System.out.print(newvalues[i]); System.out.print("=");
				 * System.out.print(original[i]); System.out.print("*");
				 * System.out.print(lp.getScale()[i]); if
				 * (lp.getTranslation()[i]>=0) System.out.print("+");
				 * System.out.println(lp.getTranslation()[i]);
				 */
				assertEquals(lp.getScale()[i] * original[i] + lp.getTranslation()[i], newvalues[i]);
			}

		}
	}

	protected void verifyExample(boolean answer) throws DecisionMethodException {
		IAtomContainer m = ruleToTest.getExampleMolecule(answer);
		try {
			/*
			 * HydrogenAdder ha = new HydrogenAdder();
			 * ha.addExplicitHydrogensToSatisfyValency(m);
			 */
			MolAnalyser.analyse(m);
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
		assertEquals(answer, ruleToTest.verifyRule(m));
	}

	@Test
	public void testExampleNo() throws DecisionMethodException {
		verifyExample(false);
	}

	@Test
	public void testExampleYes() throws DecisionMethodException {
		verifyExample(true);
	}

}
