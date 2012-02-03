/*
Copyright Nina Jeliazkova (C) 2005-2011  
Contact: jeliazkova.nina@gmail.com

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

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.logging.TTLogger;
import toxTree.query.MolAnalyser;
import toxTree.query.QueryAtomContainers;
import verhaar.query.FunctionalGroups;

/**
 * Test case for {@link verhaar.query.FunctionalGroups}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-31
 */
public class FunctionalGroupstest  {
	protected static TTLogger logger = new TTLogger(FunctionalGroupstest.class);


	@Before
	public void setUp() throws Exception {
		TTLogger.configureLog4j(false);
	}

	@After
	public void tearDown() throws Exception {
		
	}

	public void groupTest(QueryAtomContainer query, String[] smiles, boolean[] answers) throws Exception {
		for (int i=0;i< smiles.length;i++) {
			IAtomContainer mol = FunctionalGroups.createAtomContainer(smiles[i]);
				MolAnalyser.analyse(mol);
				//System.err.println(FunctionalGroups.mapToString(mol));
				boolean b = FunctionalGroups.hasGroup(mol,query);
				
				Assert.assertEquals(answers[i],b);

		}
	}
	public void allowedGroupTest(QueryAtomContainers query, ArrayList ids, 
					String[] smiles, boolean[] answers) throws Exception  {
		for (int i=0;i< smiles.length;i++) {
			IAtomContainer mol = FunctionalGroups.createAtomContainer(smiles[i]);
				MolAnalyser.analyse(mol);
				FunctionalGroups.markCHn(mol);
				boolean b = FunctionalGroups.hasOnlyTheseGroups(mol,query,ids,false);
				
				Assert.assertEquals(answers[i],b);
				logger.debug(smiles[i],"\tOK");

		}
	}	
	@Test
	public void testHalogenAtBetaFromUnsaturation() throws Exception  {
		String[] halogens = {"Cl","F","Br","I"};
		QueryAtomContainer query = FunctionalGroups.halogenAtBetaFromUnsaturation(halogens);
		String[] smiles = {"C1=CC=CC=C1CCl","c1ccccc1Cl","c1ccccc1CCl","CC=CCF","CC=CF","CC#CCI"};
		boolean[] answers = {true,false,true,true,false,true};		
		groupTest(query,smiles,answers);
	}
	@Test
	public void testHalogen() throws Exception  {
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
	@Test
	public void testketone_a_b_unsaturated() throws Exception  {

		QueryAtomContainer query = FunctionalGroups.ketone_a_b_unsaturated();
		String[] smiles = {"CC(=O)C1=CC=CC=C1","c1ccccc1CCCC=CCCl"};
		boolean[] answers = {true,true};		
		groupTest(query,smiles,answers);
	}
	@Test
	public void testPhenol() throws Exception  {

		QueryAtomContainer query = FunctionalGroups.phenol();
		String[] smiles = {"c1ccccc1","C1=CC=CC=C1O","O=N(=O)c1cccc(O)c1"};
		boolean[] answers = {false,true,true};		
		groupTest(query,smiles,answers);
	}
	@Test
	public void testNitroPhenol() throws Exception  {
		QueryAtomContainer query = FunctionalGroups.phenol();
		IAtomContainer mol = FunctionalGroups.makeNitroPhenol();
			MolAnalyser.analyse(mol);
			Assert.assertTrue(FunctionalGroups.hasGroup(mol,query));

	}	
}

