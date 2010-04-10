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
package toxTree.cramer;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionResultException;
import toxTree.logging.TTLogger;
import toxTree.query.FunctionalGroups;
import toxTree.tree.DefaultCategory;
import toxTree.tree.cramer.CramerRules;
import ambit2.core.io.IteratingDelimitedFileReader;

public class MunroTest {
	protected CramerRules rules;
	protected static TTLogger logger = new TTLogger(MunroTest.class); 

	

	@Before
	public void setUp() throws Exception {
		TTLogger.configureLog4j(true);
		rules = new CramerRules();
	}


	protected void testFile(String filename, IDecisionCategory category) throws Exception {
		logger.error("Should be\t",category);
		
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
			Assert.assertTrue(records > 0);
			Assert.assertEquals(records-applyError,ok);
			Assert.assertEquals(emptyMolecules,0);
			Assert.assertEquals(applyError,0);

	}
	@Test
	public void testMunroClass1() throws Exception {
		IDecisionCategory c = rules.getCategories().getCategory(new DefaultCategory("",1));
		URL url = getClass().getClassLoader().getResource("Munro/munro-1.csv");
		testFile(url.getFile(),c);
	}
	@Test
	public void testMunroClass2() throws Exception {
		IDecisionCategory c = rules.getCategories().getCategory(new DefaultCategory("",2));
		URL url = getClass().getClassLoader().getResource("Munro/munro-2.csv");
		testFile(url.getFile(),c);
	}	
	@Test
	public void testMunroClass3() throws Exception {
		IDecisionCategory c = rules.getCategories().getCategory(new DefaultCategory("",3));
		URL url = getClass().getClassLoader().getResource("Munro/munro-3.csv");
		testFile(url.getFile(),c);
	}	
}
