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
package toxTree.test.tree.cramer;

import java.io.File;
import java.io.FileInputStream;

import junit.framework.TestCase;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.logging.TTLogger;
import toxTree.query.FunctionalGroups;
import toxTree.tree.DefaultCategory;
import toxTree.tree.cramer.CramerRules;
import ambit2.core.io.IteratingDelimitedFileReader;

public class MunroTest extends TestCase {
	protected CramerRules rules;
	protected static TTLogger logger = new TTLogger(MunroTest.class); 
	public MunroTest(String arg0) {
		super(arg0);

		TTLogger.configureLog4j(true);
		try {
			rules = new CramerRules();
		} catch (DecisionMethodException x) {
			fail();
		}	
	}
	

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	protected void testFile(String filename, IDecisionCategory category) {
		logger.error("Should be\t",category);
		try {
			IteratingDelimitedFileReader reader = new IteratingDelimitedFileReader(
					new FileInputStream(new File(filename)));
			IDecisionResult result = rules.createDecisionResult();
			int ok = 0;
			int records = 0;
			int emptyMolecules = 0;
			int applyError = 0;
			
			QueryAtomContainer sulphonate = FunctionalGroups.sulphonate(null,false);
			
			while (reader.hasNext()) {
				result.clear();				
				Object o = reader.next();
				if (o instanceof IAtomContainer) {
					IAtomContainer a = (IAtomContainer) o;
					try {
						result.classify(a);
						if (!result.getCategory().equals(category)) {
							if ((category.getID() == 3) && (FunctionalGroups.hasGroup(a,sulphonate))) {
								ok++;
							} else
								logger.error("\""+a.getProperty("NAME"),"\"\t",result.getCategory(),"\t",result.explain(false));
						} else 
							ok++;

					} catch(DecisionResultException x) {
						//x.printStackTrace();
						applyError ++;
					}

				}
				records++;
			}
			logger.error(category);
			logger.error("Processed\t",records);
			logger.error("Successfull\t",ok);
			logger.error("Empty\t",emptyMolecules);
			logger.error("Error when applying rules\t",applyError);
			logger.error("");			
			assertTrue(records > 0);
			assertEquals(records-applyError,ok);
			assertEquals(emptyMolecules,0);
			assertEquals(applyError,0);
		} catch (Exception x) {
			x.printStackTrace();
			fail();
		}
	}
	public void testMunroClass1() {
		IDecisionCategory c = rules.getCategories().getCategory(new DefaultCategory("",1));
		testFile("toxTree/data/Munro/munro-1.csv",c);
	}
	public void testMunroClass2() {
		IDecisionCategory c = rules.getCategories().getCategory(new DefaultCategory("",2));
		testFile("toxTree/data/Munro/munro-2.csv",c);
	}	
	public void testMunroClass3() {
		IDecisionCategory c = rules.getCategories().getCategory(new DefaultCategory("",3));
		testFile("toxTree/data/Munro/munro-3.csv",c);
	}	
}