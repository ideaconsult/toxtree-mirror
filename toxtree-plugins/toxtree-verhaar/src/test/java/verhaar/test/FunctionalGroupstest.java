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

import java.util.ArrayList;

import junit.framework.TestCase;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.MolAnalyseException;
import toxTree.logging.TTLogger;
import toxTree.query.MolAnalyser;
import toxTree.query.QueryAtomContainers;
import verhaar.query.FunctionalGroups;

/**
 * Test case for {@link verhaar.query.FunctionalGroups}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-31
 */
public class FunctionalGroupstest extends TestCase {
	protected static TTLogger logger ;
	public static void main(String[] args) {
		junit.textui.TestRunner.run(FunctionalGroupstest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for FunctionalGroupstest.
	 * @param arg0
	 */
	public FunctionalGroupstest(String arg0) {
		super(arg0);
		logger = new TTLogger(this.getClass());
		TTLogger.configureLog4j(false);
	}

	public void groupTest(QueryAtomContainer query, String[] smiles, boolean[] answers) {
		for (int i=0;i< smiles.length;i++) {
			IAtomContainer mol = FunctionalGroups.createAtomContainer(smiles[i]);
			try {
				MolAnalyser.analyse(mol);
				//System.err.println(FunctionalGroups.mapToString(mol));
				boolean b = FunctionalGroups.hasGroup(mol,query);
				
				assertEquals(answers[i],b);
			} catch (MolAnalyseException x) {
				logger.error(x);
				fail();
			}
		}
	}
	public void allowedGroupTest(QueryAtomContainers query, ArrayList ids, 
					String[] smiles, boolean[] answers) {
		for (int i=0;i< smiles.length;i++) {
			IAtomContainer mol = FunctionalGroups.createAtomContainer(smiles[i]);
			try {
				MolAnalyser.analyse(mol);
				FunctionalGroups.markCHn(mol);
				boolean b = FunctionalGroups.hasOnlyTheseGroups(mol,query,ids,false);
				
				assertEquals(answers[i],b);
				logger.debug(smiles[i],"\tOK");
			} catch (MolAnalyseException x) {
				logger.error(x);
				fail();
			}
		}
	}	
	
	public void testHalogenAtBetaFromUnsaturation() {
		String[] halogens = {"Cl","F","Br","I"};
		QueryAtomContainer query = FunctionalGroups.halogenAtBetaFromUnsaturation(halogens);
		String[] smiles = {"C1=CC=CC=C1CCl","c1ccccc1Cl","c1ccccc1CCl","CC=CCF","CC=CF","CC#CCI"};
		boolean[] answers = {true,false,true,true,false,true};		
		groupTest(query,smiles,answers);
	}
	public void testHalogen() {
		String[] halogens = {"Cl","F","Br","I"};
		QueryAtomContainer query = FunctionalGroups.halogen(halogens);
		QueryAtomContainers q = new QueryAtomContainers();
		q.add(query);
		ArrayList ids = new ArrayList();
		ids.add(query.getID());
		ids.add(FunctionalGroups.C);
		ids.add(FunctionalGroups.CH);
		ids.add(FunctionalGroups.CH2);
		ids.add(FunctionalGroups.CH3);
		String[] smiles = {"c1(CCCCCCCC)ccccc1CCCl"};
		boolean[] answers = {true};		
		allowedGroupTest(q,ids,smiles,answers);
	}
	
	public void testketone_a_b_unsaturated() {

		QueryAtomContainer query = FunctionalGroups.ketone_a_b_unsaturated();
		String[] smiles = {"CC(=O)C1=CC=CC=C1","c1ccccc1CCCC=CCCl"};
		boolean[] answers = {true,true};		
		groupTest(query,smiles,answers);
	}

	public void testPhenol() {

		QueryAtomContainer query = FunctionalGroups.phenol();
		String[] smiles = {"c1ccccc1","C1=CC=CC=C1O","O=N(=O)c1cccc(O)c1"};
		boolean[] answers = {false,true,true};		
		groupTest(query,smiles,answers);
	}
	public void testNitroPhenol() {
		QueryAtomContainer query = FunctionalGroups.phenol();
		IAtomContainer mol = FunctionalGroups.makeNitroPhenol();
		try {
			MolAnalyser.analyse(mol);
			assertTrue(FunctionalGroups.hasGroup(mol,query));
		} catch (Exception x) {
			x.printStackTrace();
			fail();
		}
	}	
}

