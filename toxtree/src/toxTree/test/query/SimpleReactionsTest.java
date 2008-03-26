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

import junit.framework.TestCase;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.Reaction;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.exceptions.ReactionException;
import toxTree.logging.TTLogger;
import toxTree.query.FunctionalGroups;
import toxTree.query.SimpleReactions;

/**
 * A test for SimpleReactions class
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-18
 */
public class SimpleReactionsTest extends TestCase {
	public static TTLogger logger=new TTLogger(SimpleReactionsTest.class); 
	protected SimpleReactions sr = null;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		sr = new SimpleReactions();
		assertNotNull(sr);
	}
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		sr = null;
	}
	public void testSimpleReactions() {
		TTLogger.configureLog4j(false);
	}

	public void testLoadHydrolysisReaction() throws Exception{
			for (int i=0; i < SimpleReactions.getHydrolysisReactionCount(); i++) {
				IReaction hr = sr.getHydrolysisReaction(i);
				assertNotNull(hr);
				assertEquals(2,hr.getReactantCount());
				assertEquals(2,hr.getProductCount());
			}

	}

	public void testLoadMetabolicReaction() throws Exception {
			
			for (int i=0; i < SimpleReactions.getMetabolicReactionCount(); i++) {
				IReaction mr = sr.getMetabolicReaction(i);
				assertNotNull(mr);
				assertEquals(1,mr.getReactantCount());
				assertEquals(2,mr.getProductCount());
			}			
			
	
	}

	public void testGetHydrolysisReactionCount()  {
		assertEquals(6,SimpleReactions.getHydrolysisReactionCount());
	}

	public void testGetMetabolicReactionCount() {
		assertEquals(4,SimpleReactions.getMetabolicReactionCount());
	}
	protected IAtomContainerSet processHydrolysis(IAtomContainer mol,int index) throws Exception  {

			IReaction hr = sr.getHydrolysisReaction(index);
			assertNotNull(hr);
			assertEquals(2,hr.getReactantCount());
			assertEquals(2,hr.getProductCount());
			IAtomContainerSet sc = 
				SimpleReactions.process(mol,hr);
			
			if (sc != null) {
				verifyProducts(sc,hr);
				//TODO problem with CML Writer !!!!!!!!!!!!!!!!!!!!
				/*
				Reaction r = new Reaction();
				r.addReactant((Molecule)mol);
				for (int p=0; p< sc.getAtomContainerCount(); p++)
					r.addProduct((Molecule)sc.getAtomContainer(p));
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
	public void testHydrolysis0() throws Exception {
		IAtomContainer mol =MoleculeFactory.makeAlkane(5);
			assertNull(processHydrolysis(mol,0));
			mol = FunctionalGroups.acrylicAcid();
			assertNull(processHydrolysis(mol,0));
			mol = FunctionalGroups.createAtomContainer("CCCC(=O)OCCCCCCC");
			IAtomContainerSet results = processHydrolysis(mol,0);
			assertNotNull(results);
			assertEquals(2,results.getAtomContainerCount());
			

	}
	public void testHydrolysis2() throws Exception {
		int index = 2;
		IAtomContainer mol =MoleculeFactory.makeAlkane(5);

			assertNull(processHydrolysis(mol,index));
			mol = FunctionalGroups.acrylicAcid();
			assertNull(processHydrolysis(mol,index));
			//CCCC(=S)OCCCCCCC
			mol = FunctionalGroups.createAtomContainer("O(C(CCC)=S)CCCCCCC");
			IAtomContainerSet results = processHydrolysis(mol,index);
			assertNotNull(results);
			assertEquals(2,results.getAtomContainerCount());
	}
	public void testHydrolysis1() throws Exception {
		int index = 1;
		IAtomContainer mol =MoleculeFactory.makeAlkane(5);

			assertNull(processHydrolysis(mol,index));
			mol = FunctionalGroups.acrylicAcid();
			assertNull(processHydrolysis(mol,index));
			//CCCC(=S)OCCCCCCC
			mol = FunctionalGroups.createAtomContainer("O=C(CCC)SCCCCCCC");
			IAtomContainerSet results = processHydrolysis(mol,index);
			assertNotNull(results);
			assertEquals(2,results.getAtomContainerCount());
	}
	public void testHydrolysis3() throws Exception {
		int index = 3;
		IAtomContainer mol =MoleculeFactory.makeAlkane(5);
			assertNull(processHydrolysis(mol,index));
			mol = FunctionalGroups.acrylicAcid();
			assertNull(processHydrolysis(mol,index));
			//CCCC(=S)OCCCCCCC
			mol = FunctionalGroups.createAtomContainer("S=C(CCC)SCCCCCCC");
			IAtomContainerSet results = processHydrolysis(mol,index);
			assertNotNull(results);
			assertEquals(2,results.getAtomContainerCount());

	}
	public void testHydrolysis4() throws Exception{
		int index = 4;
		IAtomContainer mol =MoleculeFactory.makeAlkane(5);

			assertNull(processHydrolysis(mol,index));
			mol = FunctionalGroups.acrylicAcid();
			assertNull(processHydrolysis(mol,index));
			//CCCC(=S)OCCCCCCC
			//tuk triabwa da se kusat wsicki edinicni wryzki, ne samo edna
			mol = FunctionalGroups.createAtomContainer("O=P(OCC)(OCC)OCCC");
			IAtomContainerSet results = processHydrolysis(mol,index);
			assertNotNull(results);
			assertEquals(2,results.getAtomContainerCount());
			/*
			mol = FunctionalGroups.createAtomContainer("O=P(O)(OCC)OCC");
			results = processHydrolysis(mol,index);
			assertNotNull(results);
			*/

	}	
	protected void printProducts(IAtomContainerSet products,
			IReaction reaction) throws Exception {
		SmilesGenerator g = new SmilesGenerator();
			logger.debug("");
			logger.debug(reaction.getID());
			logger.debug(g.createSMILES((Reaction) reaction));
			logger.debug("Products");
			if (products == null) return;
			for (int p=0; p< products.getAtomContainerCount(); p++)
				/*System.out.println((p+1)+".\n"+
					FunctionalGroups.mapToString((Molecule)products.getAtomContainer(p)));*/
				logger.debug((p+1)+".\t"+
					g.createSMILES((Molecule)products.getAtomContainer(p)));
		
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
					logger.debug("Product "+(p+1)+ " match");
				}
			}

		return match;
	}
	protected IAtomContainerSet processMetabolic(AtomContainer mol,int index) throws Exception  {
			IReaction hr = sr.getMetabolicReaction(index);
			assertNotNull(hr);
			assertEquals(1,hr.getReactantCount());
			assertEquals(2,hr.getProductCount());
			IAtomContainerSet sc = SimpleReactions.process(mol,hr);
			
			if (sc != null) {
				verifyProducts(sc,hr);
			}
			
			return sc;

	}
	public void testcanMetabolize() throws Exception {
		logger.debug("testcanMetabolize");
		Object[][] answers = {
				{"CCCCCCCCCC",new Boolean(false)},
				{"CN(=NC(C)C(C)C(C)(C)C)c1ccc(c(c1)C)C",new Boolean(true)},
				{"C(C(C=C(C)C)=[N+](CC(C)C)C(C)C)=C(C)C",new Boolean(true)},
				{"C(C)(=C(C)CN(CC1CC1)C(C)CC(C)C(C)C)C(C)CC",new Boolean(true)},
				//{"C(=C(C(C)C)C(C)C)(CC)CC(C)C",new Boolean(true)},
				//{"O=C1c4cc(ccc4(NC1=C2Nc3ccc(cc3(C2(=O)))S(=O)(=O)O[Na]))S(=O)(=O)O[Na]",new Boolean(true)}
				

		};
		SimpleReactions sr = new SimpleReactions();
		
			
				Molecule mol;
				
				int success = 0;
				
				for (int r=0; r < answers.length; r++) { 
					
					mol = (Molecule) FunctionalGroups.createAtomContainer((String)answers[r][0],true);
					IAtomContainerSet results = sr.canMetabolize(mol,true);
					boolean canM = results != null;
					if (((Boolean) answers[r][1]).booleanValue()) {
						if (canM) {
							success++;
							logger.debug("Reactant\t" , (String)answers[r][0],"\tOK");
						} else
							logger.error("Reactant\t" , (String)answers[r][0],"\tERROR");
					} else {
						if (canM) {
							logger.error("Reactant\t" , (String)answers[r][0],"\tERROR");
						} else {
							success++;
							logger.debug("Reactant\t" , (String)answers[r][0],"\tOK");
						}

					}
				}	
				assertEquals(success,answers.length);
		
	}		
	
	public void testMetabolic() throws Exception {
		logger.debug("testMetabolic");
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
				
				logger.debug(mr.getID());
				for (int r=0; r < answers.length; r++) { 
	
					mol = FunctionalGroups.createAtomContainer((String)answers[r][0]);
					IAtomContainerSet results = SimpleReactions.process(mol,mr);
					if (((Boolean) answers[r][index+1]).booleanValue()) {
						verifyProducts(results,mr);
						logger.debug(Integer.toString(r+1),"\tReactant\t" + (String)answers[r][0]);
						assertNotNull(results)	;						
						assertEquals(2,results.getAtomContainerCount());
					} else {
						assertNull(results);
					}
				}	
	
			}

		
	}
	public void testMultipleReactions() throws Exception  {
		logger.debug("MultipleReactions");
		Molecule Gr3 = (Molecule) FunctionalGroups.createAtomContainer(
				"CCN(Cc1cccc(c1)S(=O)(=O)O[Na])c2cccc(c2)C(=C3C=CC(C=C3)=[N+](CC)Cc4cccc(c4)S(=O)(=O)O[Na])c5ccc(O)cc5S6(=O)(=O)([O-]6)"
				,true);
		SimpleReactions sr = new SimpleReactions();

			IAtomContainerSet results = sr.canMetabolize(Gr3,true);
			assertNotNull(results);
			assertEquals(3,results.getAtomContainerCount());
	}
	
}
