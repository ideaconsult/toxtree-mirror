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
package toxTree.test.query;

import java.util.List;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.query.FunctionalGroups;
import toxTree.query.SimpleReactions;

/**
 * A test for SimpleReactions class
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-18
 */
public class SimpleReactionsTest {
 
	protected static Logger logger = Logger.getLogger(SimpleReactionsTest.class.getName());

	protected SimpleReactions sr = null;
	@Before
	public void setUp() throws Exception {
		sr = new SimpleReactions();
		Assert.assertNotNull(sr);
	}
	@Test
	public void testSimpleReactions() {
		
	}
	@Test
	public void testLoadHydrolysisReaction() throws Exception{
			for (int i=0; i < SimpleReactions.getHydrolysisReactionCount(); i++) {
				IReaction hr = sr.getHydrolysisReaction(i);
				Assert.assertNotNull(hr);
				Assert.assertEquals(2,hr.getReactantCount());
				Assert.assertEquals(2,hr.getProductCount());
			}

	}
	@Test
	public void testLoadMetabolicReaction() throws Exception {
			
			for (int i=0; i < SimpleReactions.getMetabolicReactionCount(); i++) {
				IReaction mr = sr.getMetabolicReaction(i);
				Assert.assertNotNull(mr);
				Assert.assertEquals(1,mr.getReactantCount());
				Assert.assertEquals(2,mr.getProductCount());
			}			
			
	
	}
	@Test
	public void testGetHydrolysisReactionCount()  {
		Assert.assertEquals(6,SimpleReactions.getHydrolysisReactionCount());
	}
	@Test
	public void testGetMetabolicReactionCount() {
		Assert.assertEquals(4,SimpleReactions.getMetabolicReactionCount());
	}
	protected IAtomContainerSet processHydrolysis(IAtomContainer mol,int index) throws Exception  {

			IReaction hr = sr.getHydrolysisReaction(index);
			Assert.assertNotNull(hr);
			Assert.assertEquals(2,hr.getReactantCount());
			Assert.assertEquals(2,hr.getProductCount());
			IAtomContainerSet sc = 
				SimpleReactions.process(mol,hr);
			
			if (sc != null) {
				verifyProducts(sc,hr);
				//TODO problem with CML Writer !!!!!!!!!!!!!!!!!!!!
				/*
				Reaction r = new Reaction();
				r.addReactant((IMolecule)mol);
				for (int p=0; p< sc.getAtomContainerCount(); p++)
					r.addProduct((IMolecule)sc.getAtomContainer(p));
		        StringWriter writer = new StringWriter();
		        CMLWriter cmlWriter = new CMLWriter(writer);
		        try {
		            cmlWriter.write(r);
		        } catch (Exception exception) {
		        	exception.printStackTrace();
		            fail();
		        }
		        System.out.println(writer.toString());
		        */
			}
			
			return sc;
	

	}
	@Test
	public void testHydrolysis0() throws Exception {
			IAtomContainer mol =MoleculeFactory.makeAlkane(5);
			Assert.assertNull(processHydrolysis(mol,0));
			mol = FunctionalGroups.acrylicAcid();
			Assert.assertNull(processHydrolysis(mol,0));
			mol = FunctionalGroups.createAtomContainer("CCCC(=O)OCCCCCCC");
			IAtomContainerSet results = processHydrolysis(mol,0);
			Assert.assertNotNull(results);
			Assert.assertEquals(2,results.getAtomContainerCount());
			

	}
	@Test
	public void testHydrolysis2() throws Exception {
		int index = 2;
		IAtomContainer mol =MoleculeFactory.makeAlkane(5);

			Assert.assertNull(processHydrolysis(mol,index));
			mol = FunctionalGroups.acrylicAcid();
			Assert.assertNull(processHydrolysis(mol,index));
			//CCCC(=S)OCCCCCCC
			mol = FunctionalGroups.createAtomContainer("O(C(CCC)=S)CCCCCCC");
			IAtomContainerSet results = processHydrolysis(mol,index);
			Assert.assertNotNull(results);
			Assert.assertEquals(2,results.getAtomContainerCount());
	}
	@Test
	public void testHydrolysis1() throws Exception {
		int index = 1;
		IAtomContainer mol =MoleculeFactory.makeAlkane(5);

			Assert.assertNull(processHydrolysis(mol,index));
			mol = FunctionalGroups.acrylicAcid();
			Assert.assertNull(processHydrolysis(mol,index));
			//CCCC(=S)OCCCCCCC
			mol = FunctionalGroups.createAtomContainer("O=C(CCC)SCCCCCCC");
			IAtomContainerSet results = processHydrolysis(mol,index);
			Assert.assertNotNull(results);
			Assert.assertEquals(2,results.getAtomContainerCount());
	}
	@Test
	public void testHydrolysis3() throws Exception {
		int index = 3;
		IAtomContainer mol =MoleculeFactory.makeAlkane(5);
			Assert.assertNull(processHydrolysis(mol,index));
			mol = FunctionalGroups.acrylicAcid();
			Assert.assertNull(processHydrolysis(mol,index));
			//CCCC(=S)OCCCCCCC
			mol = FunctionalGroups.createAtomContainer("S=C(CCC)SCCCCCCC");
			IAtomContainerSet results = processHydrolysis(mol,index);
			Assert.assertNotNull(results);
			Assert.assertEquals(2,results.getAtomContainerCount());

	}
	@Test
	public void testHydrolysis4() throws Exception{
		int index = 4;
		IAtomContainer mol =MoleculeFactory.makeAlkane(5);

			Assert.assertNull(processHydrolysis(mol,index));
			mol = FunctionalGroups.acrylicAcid();
			Assert.assertNull(processHydrolysis(mol,index));
			//CCCC(=S)OCCCCCCC
			//tuk triabwa da se kusat wsicki edinicni wryzki, ne samo edna
			mol = FunctionalGroups.createAtomContainer("O=P(OCC)(OCC)OCCC");
			IAtomContainerSet results = processHydrolysis(mol,index);
			Assert.assertNotNull(results);
			Assert.assertEquals(2,results.getAtomContainerCount());
			/*
			mol = FunctionalGroups.createAtomContainer("O=P(O)(OCC)OCC");
			results = processHydrolysis(mol,index);
			assertNotNull(results);
			*/

	}	
	protected void printProducts(IAtomContainerSet products,
			IReaction reaction) throws Exception {
		return;
		/*
		
		SmilesGenerator g = new SmilesGenerator();
			logger.debug("");
			logger.debug(reaction.getID());
			logger.debug(g.createSMILES((Reaction) reaction));
			logger.debug("Products");
			if (products == null) return;
			for (int p=0; p< products.getAtomContainerCount(); p++)
				System.out.println((p+1)+".\n"+
					FunctionalGroups.mapToString((IMolecule)products.getAtomContainer(p)));
				logger.debug((p+1)+".\t"+
					g.createSMILES((IMolecule)products.getAtomContainer(p)));
				*/
		
	}
	protected int verifyProducts(IAtomContainerSet products,
			IReaction reaction) throws Exception {
		int match = 0;
		if (products == null) return 0;
		printProducts(products,reaction);
		for (int p=0; p< products.getAtomContainerCount(); p++) 
			for (int i=0; i< reaction.getProducts().getAtomContainerCount(); i++) {
				IAtomContainer product = reaction.getProducts().getAtomContainer(i);
				QueryAtomContainer q = SimpleReactions.createQueryContainer(product);
				q.setID(reaction.getID());
				List list = UniversalIsomorphismTester.getSubgraphMaps(
						products.getAtomContainer(p),q);
				if ((list != null) && (list.size()>0)) {
					match ++;
					logger.finer("Product "+(p+1)+ " match");
				}
			}

		return match;
	}
	protected IAtomContainerSet processMetabolic(AtomContainer mol,int index) throws Exception  {
			IReaction hr = sr.getMetabolicReaction(index);
			Assert.assertNotNull(hr);
			Assert.assertEquals(1,hr.getReactantCount());
			Assert.assertEquals(2,hr.getProductCount());
			IAtomContainerSet sc = SimpleReactions.process(mol,hr);
			
			if (sc != null) {
				verifyProducts(sc,hr);
			}
			
			return sc;

	}
	@Test
	public void testcanMetabolize() throws Exception {
		logger.finer("testcanMetabolize");
		Object[][] answers = {
				{"CCCCCCCCCC",new Boolean(false)},
				{"CN(=NC(C)C(C)C(C)(C)C)c1ccc(c(c1)C)C",new Boolean(true)},
				{"C(C(C=C(C)C)=[N+](CC(C)C)C(C)C)=C(C)C",new Boolean(true)},
				{"C(C)(=C(C)CN(CC1CC1)C(C)CC(C)C(C)C)C(C)CC",new Boolean(true)},
				//{"C(=C(C(C)C)C(C)C)(CC)CC(C)C",new Boolean(true)},
				//{"O=C1c4cc(ccc4(NC1=C2Nc3ccc(cc3(C2(=O)))S(=O)(=O)O[Na]))S(=O)(=O)O[Na]",new Boolean(true)}
				

		};
		SimpleReactions sr = new SimpleReactions();
		
			
				IMolecule mol;
				
				int success = 0;
				
				for (int r=0; r < answers.length; r++) { 
					
					mol = (IMolecule) FunctionalGroups.createAtomContainer((String)answers[r][0],true);
					IAtomContainerSet results = sr.canMetabolize(mol,true);
					boolean canM = results != null;
					if (((Boolean) answers[r][1]).booleanValue()) {
						if (canM) {
							success++;
							logger.info("Reactant\t"+ (String)answers[r][0]+"\tOK");
						} else
							logger.severe("Reactant\t" + (String)answers[r][0]+"\tERROR");
					} else {
						if (canM) {
							logger.severe("Reactant\t" + (String)answers[r][0]+"\tERROR");
						} else {
							success++;
							logger.finer("Reactant\t" +(String)answers[r][0]+"\tOK");
						}

					}
				}	
				Assert.assertEquals(success,answers.length);
		
	}		
	@Test
	public void testMetabolic() throws Exception {
		logger.finer("testMetabolic");
		Object[][] answers = {
				{"CCCCCCCCCC",new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false)},
				{"CN(=NC(C)C(C)C(C)(C)C)c1ccc(c(c1)C)C",new Boolean(true),new Boolean(false),new Boolean(false),new Boolean(false)},
				{"C(C(C=C(C)C)=[N+](CC(C)C)C(C)C)=C(C)C",new Boolean(false),new Boolean(true),new Boolean(false),new Boolean(false)},
				{"C(=C(C)C(C)CC)CN(CC1CC1)C(C)CC(C)C(C)C",new Boolean(false),new Boolean(false),new Boolean(true),new Boolean(false)},
				{"C(=C(C(C)C)C(C)C)(CC)CC(C)C",new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(true)},
				{"O=C1c4cc(ccc4(NC1=C2Nc3ccc(cc3(C2(=O)))S(=O)(=O)O[Na]))S(=O)(=O)O[Na]",new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(true)}

		};
	
			for (int index = 0; index < SimpleReactions.getMetabolicReactionCount(); index++) {
				IAtomContainer mol;
				IReaction mr = sr.getMetabolicReaction(index);
				
				logger.finer(mr.getID());
				for (int r=0; r < answers.length; r++) { 
	
					mol = FunctionalGroups.createAtomContainer((String)answers[r][0]);
					IAtomContainerSet results = SimpleReactions.process(mol,mr);
					if (((Boolean) answers[r][index+1]).booleanValue()) {
						verifyProducts(results,mr);
						logger.finer(Integer.toString(r+1)+"\tReactant\t" + (String)answers[r][0]);
						Assert.assertNotNull(results)	;						
						Assert.assertEquals(2,results.getAtomContainerCount());
					} else {
						Assert.assertNull(results);
					}
				}	
	
			}

		
	}
	@Test
	public void testMultipleReactions() throws Exception  {
		logger.finer("MultipleReactions");
		IMolecule Gr3 = (IMolecule) FunctionalGroups.createAtomContainer(
				"CCN(Cc1cccc(c1)S(=O)(=O)O[Na])c2cccc(c2)C(=C3C=CC(C=C3)=[N+](CC)Cc4cccc(c4)S(=O)(=O)O[Na])c5ccc(O)cc5S6(=O)(=O)([O-]6)"
				,true);
		SimpleReactions sr = new SimpleReactions();

			IAtomContainerSet results = sr.canMetabolize(Gr3,true);
			Assert.assertNotNull(results);
			Assert.assertEquals(3,results.getAtomContainerCount());
	}
	
}
