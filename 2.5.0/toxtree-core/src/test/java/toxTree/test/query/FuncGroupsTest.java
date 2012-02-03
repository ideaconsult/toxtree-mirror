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

*//**
 * <b>Filename</b> FuncGroupsTest.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>cellbox</b> 2005-8-8
 * <b>Project</b> toxTree
 */
package toxTree.test.query;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.openscience.cdk.Bond;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.Ring;
import org.openscience.cdk.RingSet;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.io.CDKSourceCodeWriter;
import org.openscience.cdk.io.IChemObjectWriter;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainerCreator;
import org.openscience.cdk.isomorphism.matchers.SymbolAndChargeQueryAtom;
import org.openscience.cdk.isomorphism.matchers.SymbolQueryAtom;
import org.openscience.cdk.isomorphism.matchers.smarts.AnyOrderQueryBond;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.LoggingTool;

import toxTree.exceptions.MolAnalyseException;
import toxTree.logging.TTLogger;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import ambit2.core.data.MoleculeTools;

/**
 * 
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-8
 */
public class FuncGroupsTest extends TestCase {
    protected SmilesParser gen = null;
    protected TTLogger logger;
    

    public static void main(String[] args) {
        junit.textui.TestRunner.run(FuncGroupsTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
	protected void setUp() throws Exception {
        super.setUp();
        
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
        //gen = null;
        //h = null;
    }

    /**
     * Constructor for FuncGroupsTest.
     * @param arg0
     */
    public FuncGroupsTest(String arg0) {
        super(arg0);
        gen = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        logger = new TTLogger(this);
        TTLogger.configureLog4j(true);
        LoggingTool.configureLog4j();
        
    }
    /*
    protected boolean querySalt(String smiles, QueryAtomContainer q) {
        try {
            Molecule mol = gen.
            return query(mol,q);
        } catch (InvalidSmilesException x ) {
            x.printStackTrace();
            return false;
        }
    }    
      */          
    protected boolean query(String smiles, QueryAtomContainer q) {
    	IAtomContainer mol = FunctionalGroups.createAtomContainer(smiles,true);
    	return (FunctionalGroups.hasGroup( mol, q) );
    }
    public void testmatchInherited() throws Exception {

        SymbolQueryAtom c1 = new SymbolQueryAtom(MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON));
        SymbolAndChargeQueryAtom c2 = new SymbolAndChargeQueryAtom(MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON));
    	IAtomContainer c = MoleculeFactory.makeAlkane(2); 
        
    	QueryAtomContainer query1 = new QueryAtomContainer();
        query1.addAtom(c1);
        query1.addAtom(c2);
        query1.addBond(new OrderQueryBond(c1,c2, CDKConstants.BONDORDER_SINGLE));
    	assertTrue(UniversalIsomorphismTester.isSubgraph(c,query1));
    	
    	QueryAtomContainer query = new QueryAtomContainer();
        query.addAtom(c1);
        query.addAtom(c2);
        query.addBond(new AnyOrderQueryBond(c1,c2, CDKConstants.BONDORDER_SINGLE));
    	assertTrue(UniversalIsomorphismTester.isSubgraph(c,query));
    	

    	
    }

    public void testhydrochlorideOfAmine() {
    	QueryAtomContainer q = FunctionalGroups.hydrochlorideOfAmine(1); //primary
    	IAtomContainer c = 
    		FunctionalGroups.createAtomContainer("[Cl-].[NH3+]C1CCCCC1");
    	try {
    		MolAnalyser.analyse(c);
    	} catch (MolAnalyseException x) {
    		fail();
    	}
    	assertTrue(FunctionalGroups.hasGroup(c,q));
    	
    	q = FunctionalGroups.hydrochlorideOfAmine3(); //tertiary
    	c = 
    		FunctionalGroups.createAtomContainer("oc1c=ccc(c1)=[N+](CC)CC.[Cl-]");
    	try {
    		MolAnalyser.analyse(c);
    	} catch (MolAnalyseException x) {
    		fail();
    	}
    	assertTrue(FunctionalGroups.hasGroup(c,q));    	
    }
    public void sulphateOfAmine() {
    	IAtomContainer c = phenazineMethosulphate();
    	QueryAtomContainer q = FunctionalGroups.sulphateOfAmine(0); //any amine 
    	try {
    	MolAnalyser.analyse(c);
    	} catch (MolAnalyseException x) {
    		fail();
    	}
    	assertTrue(FunctionalGroups.hasGroup(c,q));    	
    	
    }
    public void testSulphateOfPrimaryAmine() {
    	QueryAtomContainer q = FunctionalGroups.sulphateOfAmine(1); //primary
    	IAtomContainer c = FunctionalGroups.createAtomContainer("S(=O)(=O)([O-])O.[NH3+]C1CCCCC1");
    	try {
    	MolAnalyser.analyse(c);
    	} catch (MolAnalyseException x) {
    		fail();
    	}
    	assertTrue(FunctionalGroups.hasGroup(c,q));    	
    	
    }
    
    public void testSulphateOfTertiaryAmine() {
    	QueryAtomContainer q = FunctionalGroups.sulphateOfAmine(3); //tertiary
    	IAtomContainer c = FunctionalGroups.createAtomContainer("S(=O)(=O)([O-])O.[NH+](CCC)(CC)CCC1CCCCC1");
    	try {
    	MolAnalyser.analyse(c);
    	} catch (MolAnalyseException x) {
    		fail();
    	}
    	assertTrue(FunctionalGroups.hasGroup(c,q));    	
    	
    }    
    
    public void testAmine() {
        QueryAtomContainer q = FunctionalGroups.primaryAmine(false);
        assertTrue(query("CCCN",q));
        assertFalse(query("c1ccc(NC)cc1",q));        
        q = FunctionalGroups.secondaryAmine(false);
        assertTrue(query("CCCNCCC",q));        
        assertTrue(query("c1ccc(NC)cc1",q));
        q = FunctionalGroups.secondaryAmine(true);
        assertFalse(query("c1ccc(NC)cc1",q));
        q = FunctionalGroups.tertiaryAmine();
        assertTrue(query("CCCN(CC)CC",q));
        
    }

    
    public void testCyano() {
        QueryAtomContainer q = FunctionalGroups.cyano();
        assertTrue(query("CC#NCCC",q));
        assertFalse(query("CSCCCSC#N",q));
        assertFalse(query("c1ccc(NC)cc1",q));        
    }
    public void testNnitroso() {
        QueryAtomContainer q = FunctionalGroups.Nnitroso();
        assertTrue(query("CCN(CCC)N=O",q));
        assertFalse(query("CCNN=O",q));        
    }
    public void testDiazo() {
        QueryAtomContainer q = FunctionalGroups.diAzo();
        assertTrue(query("C=N#N",q));
        assertFalse(query("CCNN=O",q));        
    }
    public void testTriAzeno() {
        QueryAtomContainer q = FunctionalGroups.triAzeno();
        assertTrue(query("CCCN=NN",q));
        assertFalse(query("CCCN=NNCCCCCCC",q));        
    }
    public void testQuaternaryN1() {
        QueryAtomContainer q = FunctionalGroups.quaternaryNitrogen1(true);
        assertFalse(query("CCN(CC)(CC)OS(=O(=O)O)",q));
        assertTrue(query("[Cl-].[NH3+]C1CCCCC1",q));
        assertTrue(query("[Cl-].[NH3+]C1CCCCC1",q));        
        assertFalse(query("Cl.[H]N([H])([H])C1CCCCC1",q));
        
        q = FunctionalGroups.quaternaryNitrogen1(false);
        assertTrue(query("Cl.[H]N([H])([H])C1CCCCC1",q));
        assertTrue(query("[NH3+]C1CCCCC1",q));
        assertFalse(query("CNC",q));
        assertFalse(query("O=C(O[Na])C2=NN(=C(O)C2(N=NC1C=CC(CC1)S(=O)(=O)O[Na]))c3ccc(cc3)S(=O)(=O)O[Na]",q));
        
        
    }
    public void testQuaternaryN2() {
        QueryAtomContainer q = FunctionalGroups.quaternaryNitrogen2(true);
        assertFalse(query("CCN(CC)(CC)OS(=O(=O)O)",q));
        assertFalse(query("[Cl-].[NH3+]C1CCCCC1",q));
        assertTrue(query("CCC[NH+](=C)CCC",q));
        assertFalse(query("CN=C",q));
        
    }    
    public void testNitro() {
    	QueryAtomContainer q = FunctionalGroups.nitro1double();
    	assertTrue(query("NC(=O)C1=CC(=CC(=C1)[N+]([O-])=O)[N+]([O-])=O",q));
    	
    }
    public void testQNitrogenException() {
        QueryAtomContainer q = FunctionalGroups.quarternaryNitrogenException();
        assertTrue(query("C(CC)(CC)=[N+](C)CCC",q));
        assertTrue(query("C(CC)(CC)=[N+]([H])[H]",q));
        assertTrue(query("[N+](O)(C)=C(C)(C)",q));
    }
    public void testSaltOfCarboxylicAcid() {
        QueryAtomContainer q = FunctionalGroups.saltOfCarboxylicAcid(
                new String[]{"Na","K","Ca","Mg","Al"});
        assertTrue(query("CCCCC(=O)O[Na]",q));
        assertTrue(query("CC(CC(CC))CCC(=O)O[Ca]",q));
        assertTrue(query("c1ccccc1CCCCC(=O)O[Mg]",q));
        assertTrue(query("CC(CCCCCC)CCC(=O)O[Al]",q));
        
        //assertTrue(query("[Na+].OC[C@H](O)C1OC(=O)C(O)=C1[O-]",q));
        
        IMolecule mol = (IMolecule) FunctionalGroups.createAtomContainer("[Na]OC(=O)C1C=C(O)CC1",true);
        List list = FunctionalGroups.getBondMap(mol,q,false);
        FunctionalGroups.markMaps(mol,q,list);
        assertNotNull(list);
        //System.out.println(FunctionalGroups.mapToString(mol));        
                
    }
    
    public void testSulphonate() {
        QueryAtomContainer q = FunctionalGroups.sulphonate(
                new String[]{"Na","K","Ca"});
        assertTrue(query("CS(=O)(=O)[O-][Na+]",q));
        assertTrue(query("CS(=O)(=O)O[Ca]",q));
        
        IMolecule mol = (IMolecule) FunctionalGroups.createAtomContainer("O=S(=O)(O[Na])c1ccccc1",true);
        List list = FunctionalGroups.getBondMap(mol,q,false);
        FunctionalGroups.markMaps(mol,q,list);
        assertNotNull(list);
        
        
        IAtomContainer c = FunctionalGroups.createAtomContainer("[Na+].[O-]S(=O)(=O)NC1CCCCC1");
        try {
        //	FunctionalGroups.associateIonic(c);
        	MolAnalyser.analyse(c);
        	assertFalse(FunctionalGroups.hasGroup(c,q)); //this is a sulphamate
        } catch (MolAnalyseException x) {
			x.printStackTrace();
		}
       
    }    
    
    public void testSulphonate1() {
        QueryAtomContainer q = FunctionalGroups.sulphonate(null,false);

        assertTrue(query("O=S1(=O)([O-]1)CCCC",q));
        /*
        Molecule mol = (Molecule) FunctionalGroups.createAtomContainer("O=S1(=O)([O-]1)CCCC",true);

        List list = FunctionalGroups.getBondMap(mol,q,false);
        FunctionalGroups.markMaps(mol,q,list);
        assertNotNull(list);
        System.out.println(FunctionalGroups.mapToString(mol));
        */   
                
                
    }    
    public void testSulphate() {
    	QueryAtomContainer q = FunctionalGroups.sulphate(null);
    	assertTrue(query("[Na+].CCCCCCCCCCCCOCCOCCOCCOS([O-])(=O)=O",q));

    	IAtomContainer c = FunctionalGroups.createAtomContainer("C=CCC(=NOS(=O)(=O)[O-])SC1OC(CO)C(O)C(O)C1(O).[Na+].O");
        try {
        	MolAnalyser.analyse(c);
        	assertTrue(FunctionalGroups.hasGroup(c,q)); 
        } catch (MolAnalyseException x) {
			x.printStackTrace();
		}
        
        c = FunctionalGroups.createAtomContainer("C=CCC(=NOS(=O)(=O)[O-])SC1OC(CO)C(O)C(O)C1(O).[K+].O");
        assertNotNull(c); //cannot parse K for whatever reason
        try {
        	MolAnalyser.analyse(c);
        	assertTrue(FunctionalGroups.hasGroup(c,q)); 
        } catch (MolAnalyseException x) {
        	System.err.println("Perhaps can't parse smiles with potassium ( K )");
			x.printStackTrace();
		}        
    }
    public void testSulphamate() {
        QueryAtomContainer q = FunctionalGroups.sulphamate(
                new String[]{"Na","K","Ca"});
        assertTrue(query("O=S(=O)(O[Na])NC1CCCCC1",q));
        assertFalse(query("CS(=O)(=O)O[Na]",q));
        
        Molecule mol = (Molecule) FunctionalGroups.createAtomContainer("O=S(=O)(O[Na])NC1CCCCC1",true);
        List list = FunctionalGroups.getBondMap(mol,q,false);
        FunctionalGroups.markMaps(mol,q,list);
        assertNotNull(list);
        
        IAtomContainer c = FunctionalGroups.createAtomContainer("[Na+].[O-]S(=O)(=O)NC1CCCCC1");
        try {
        //	FunctionalGroups.associateIonic(c);
        	MolAnalyser.analyse(c);
        	assertTrue(FunctionalGroups.hasGroup(c,q)); //this is a sulphamate
        } catch (MolAnalyseException x) {
			x.printStackTrace();
		}

    }    
    public void testEster() {
        QueryAtomContainer q = FunctionalGroups.ester();
        assertTrue(query("CC(=O)OCCCCCC",q));
        assertTrue(query("O=C1OC(=O)CC1",q));        
        assertTrue(query("C1OC(=O)C=C1",q)); //crotonolactone        
    }
    
    public void testThioEster() {
        QueryAtomContainer q = FunctionalGroups.thioester();
        assertTrue(query("CCSC(=O)CCC",q));
        assertFalse(query("O=C1OC(=O)CC1",q));        
    }            
    public void testKetone() {
        QueryAtomContainer q = FunctionalGroups.ketone();
        assertTrue(query("CCCCCC(=O)CCCCCCCCCC",q));
        assertFalse(query("CCCCOC(=O)CCCCCCCCCC",q));
        assertFalse(query("C1OC(=O)C=C1",q));        
    }    
    public void testAlcohol() {
        QueryAtomContainer q = FunctionalGroups.alcohol(true);
        IAtomContainer mol = FunctionalGroups.createAtomContainer("CCCCCCO",true);
        //FunctionalGroups.markCHn(mol); //note markCH is necessary 
        assertTrue(FunctionalGroups.hasGroup( mol, q));
        
        mol = FunctionalGroups.createAtomContainer("CCCCOC(=O)CCCCCCCCCC",true);
        //FunctionalGroups.markCHn(mol); //note markCH is necessary
        assertFalse(FunctionalGroups.hasGroup( mol, q));
    }        
    public void testEther() throws Exception  {
        QueryAtomContainer q = FunctionalGroups.ether();
        IAtomContainer mol = FunctionalGroups.createAtomContainer("COC",true);
        assertTrue(FunctionalGroups.hasGroup( mol, q));
        
        mol = FunctionalGroups.createAtomContainer("CCCOCCCCOCOCCCC",true);
        assertTrue(FunctionalGroups.hasGroup( mol, q));
        
        mol = FunctionalGroups.createAtomContainer("CS(=O)(=O)OC",true);
        assertFalse(FunctionalGroups.hasGroup( mol, q));

        mol = FunctionalGroups.createAtomContainer("C1CCCOC1",true);
        assertTrue(FunctionalGroups.hasGroup( mol, q));

        mol = FunctionalGroups.createAtomContainer("CCCCCCCCCCCCCCCC(=O)O[C@]1(O[C@H](CO)[C@@H](O)[C@H](O)[C@H]1O)[C@@]2(CO)O[C@H](CO)[C@@H](O)[C@@H]2O",true);
        assertTrue(FunctionalGroups.hasGroup( mol, q));
        
        
    }
    
        
    public void testCarbonyl() {
        QueryAtomContainer q = FunctionalGroups.carbonyl();
        assertTrue(query("CCCCCC=O",q));
        assertTrue(query("CCCCOC(=O)CCCCCCCCCC",q));
    }
    public void testAldehyde() {
        QueryAtomContainer q = FunctionalGroups.aldehyde();
        assertTrue(query("CCCCCC=O",q));
        assertFalse(query("CCCCCC(=O)CCCCCCCCCC",q)); //ketone
        assertTrue(query("C=O",q)); //formaldehyde        
    }
    public void testCarboxylicAcid() {
        QueryAtomContainer q = FunctionalGroups.carboxylicAcid();
        assertTrue(query("C(=O)O",q));
        assertTrue(query("CCCC(=O)O",q));
        assertFalse(query("CCCC(=O)OC",q));        
        assertFalse(query("CCCCCC(=O)CCCCCCCCCC",q)); //ketone
    }
    public void testAcetal() {
        QueryAtomContainer q = FunctionalGroups.acetal();
        assertTrue(query("COCOC",q));
        assertTrue(query("CCCCOCOCCCC",q));
        assertTrue(query("C1CCCOCOCC1CC",q));        
        assertFalse(query("COCO",q));        
    }
    public void testSulphide() {
        QueryAtomContainer q = FunctionalGroups.sulphide();
        assertTrue(query("CSC",q));
        assertTrue(query("CCCSCCCCOCOCCCC",q));
        assertFalse(query("CS(=O)(=O)OC",q));        //have to check S valency =2
    }

    public void testMercaptan() {
        QueryAtomContainer q = FunctionalGroups.mercaptan();
        assertTrue(query("CCCS",q));
        assertTrue(query("c1cc(S)ccc1",q));
        assertFalse(query("CSC",q));
        assertFalse(query("CS(=O)(=O)OC",q));       
    }        

    public void testpolyoxyethylene() {
        QueryAtomContainer q = FunctionalGroups.polyoxyethylene(1);
        assertTrue(query("OCC",q));
        assertTrue(query("OCCOCCOCC",q));
        assertFalse(query("CS(=O)(=O)OC",q));        //have to check S valency =2
        q = FunctionalGroups.polyoxyethylene(4);
        assertFalse(query("OCC",q));
        assertTrue(query("OCCOCCOCCOCC",q));
    }
    public void testMethoxy() {
        QueryAtomContainer q = FunctionalGroups.methoxy();
        assertTrue(query("Oc1ccc(cc1(OC))CC=C",q));
    }
    
    public void testKetalAttachedToTerminalVinyl() {
        QueryAtomContainer q = FunctionalGroups.ketalAttachedToTerminalVinyl();
        assertTrue(query("CCC(OC)(OC)C=C",q));
        assertFalse(query("CC(OC)(OC)C=CC",q));        
    }
    
    public void testUniqueMap() {
        QueryAtomContainer q1 = FunctionalGroups.polyoxyethylene(1);
        QueryAtomContainer q2 = FunctionalGroups.polyoxyethylene(2);
        
        IMolecule mol = (IMolecule) FunctionalGroups.createAtomContainer("OCC",true);
        List list = FunctionalGroups.getUniqueBondMap(mol,q1,false);
        assertEquals(1,list.size());
        list = FunctionalGroups.getUniqueBondMap(mol,q2,false);
        assertNull(list);

        mol = (IMolecule) FunctionalGroups.createAtomContainer("CCOCCOCC",true);
        list = FunctionalGroups.getUniqueBondMap(mol,q2,false);
        assertEquals(1,list.size());
        
        list = FunctionalGroups.getUniqueBondMap(mol,q1,false);
        //FunctionalGroups.markMaps(mol,q1,list);
        //System.out.println(FunctionalGroups.mapToString(mol));          
        assertEquals(2,list.size());

        q2 = FunctionalGroups.polyoxyethylene(3);        
        
        mol = (IMolecule) FunctionalGroups.createAtomContainer("CCOCCOCCOCC",true);
        list = FunctionalGroups.getUniqueBondMap(mol,q2,false);
        assertEquals(1,list.size());
        
        mol = (IMolecule) FunctionalGroups.createAtomContainer("CCOCCOCCOCC",true);
        list = FunctionalGroups.getUniqueBondMap(mol,q1,false);
        //FunctionalGroups.markMaps(mol,q1,list);
        //System.out.println(FunctionalGroups.mapToString(mol));        
        assertEquals(3,list.size());
        
        q1 = FunctionalGroups.ester();
        mol = (IMolecule) FunctionalGroups.createAtomContainer("CCCCC(=O)OC(=O)CCCC",true);

        list = FunctionalGroups.getUniqueBondMap(mol,q1,false);
//        FunctionalGroups.markMaps(mol,q1,list);
        
        assertEquals(1,list.size());
    }    

	public void testGetSubgraphBondMaps() throws java.lang.Exception
	{
	    QueryAtomContainer q = FunctionalGroups.alcohol(false);
	    String[] ids = new String[5];
	    ids[0] = q.getID();
	    IAtomContainer mol = FunctionalGroups.createAtomContainer("CNC1=C(C=CC=C1)C(=O)OC",true);
	    
	    MolAnalyser.analyse((IMolecule)mol);
	    if (mol != null) {
	        List list = FunctionalGroups.getBondMap(mol,q,false);
	        q = FunctionalGroups.ketone();
	        list = FunctionalGroups.getBondMap(mol,q,false);
	        assertEquals(0,list.size());
	        ids[1] = q.getID();

	        q = FunctionalGroups.aldehyde();
	        list = FunctionalGroups.getBondMap(mol,q,false);
	        assertEquals(0,list.size());
	        ids[2] = q.getID();
	        
	        q = FunctionalGroups.carboxylicAcid();
	        list = FunctionalGroups.getBondMap(mol,q,false);
	        assertEquals(0,list.size());
	        ids[3] = q.getID();

	        q = FunctionalGroups.ester();
	        list = FunctionalGroups.getBondMap(mol,q,false);
	        assertEquals(1,list.size());
	        ids[4] = q.getID();	        
	        
	        int inRingAromatic = 0;
	        int atomsInEsterGroup = 0;
	        for (int i=0; i < mol.getAtomCount(); i++) {
	        	IAtom a = mol.getAtom(i);
	        	if (a.getFlag(CDKConstants.ISAROMATIC) && a.getFlag(CDKConstants.ISINRING))
	        		inRingAromatic++;
	        	if (a.getProperty(ids[4]) != null) atomsInEsterGroup++;
	        }
	        //System.out.println(FunctionalGroups.mapToString(mol));
	        assertEquals(6,inRingAromatic);
	        assertEquals(3,atomsInEsterGroup);
	        
	    }    
	}

	public void testRings() throws java.lang.Exception
	{
	     
	    IMolecule mol = (IMolecule) FunctionalGroups.createAtomContainer("CNC1=C(C=CC=C1)C(=O)OCCCC2CCCCC2",true);
	    //"CC(C)CCC1=C(C(O)=O)C2=C(C=C1C)C=C(C=C2)C(C)C");
	    		//"CNC1=C(C=CC=C1)C(=O)OC");
	    
	    MolAnalyser.analyse(mol);
	    
	    ArrayList ids = new ArrayList();
	    ids.add(FunctionalGroups.C);	    
	    ids.add(FunctionalGroups.CH);
	    ids.add(FunctionalGroups.CH2);
	    ids.add(FunctionalGroups.CH3);
	    FunctionalGroups.markCHn(mol);
	    QueryAtomContainer q;
	    List list;
	    
	    q = FunctionalGroups.ester();
	    ids.add(q.getID());
	    list = FunctionalGroups.getUniqueBondMap(mol,q,false);
	    assertEquals(1,list.size()); //one ester group
	    FunctionalGroups.markMaps(mol,q,list);
	    
	    q = FunctionalGroups.secondaryAmine(false);
	    ids.add(q.getID());	    
	    list = FunctionalGroups.getUniqueBondMap(mol,q,false);
	    assertEquals(1,list.size()); //one secondary amine
	    FunctionalGroups.markMaps(mol,q,list);
	    
	    assertTrue(FunctionalGroups.hasMarkedOnlyTheseGroups(mol,ids));
	    
	    //System.out.println(FunctionalGroups.mapToString(mol,ids).toString());
	    
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    assertNotNull(mf);
	    IRingSet rs = mf.getRingset();
	    int size = ((RingSet) rs).getAtomContainerCount();
	    assertNotNull(rs);
	    assertEquals(2,size);
	    for (int i=0; i < size; i++) {
	        Ring r = (Ring) rs.getAtomContainer(i);
	        logger.debug("Ring\t"+(i+1));

	        IAtomContainer mc = FunctionalGroups.cloneDiscardRingAtomAndBonds(mol,r);	        

			logger.debug("\tmol atoms\t"+mc.getAtomCount());
		    //assertEquals(mc.getAtomCount(),atoms-removedAtoms);
			IMoleculeSet  s = ConnectivityChecker.partitionIntoMolecules(mc);
			logger.debug("partitions\t"+s.getMoleculeCount());
			for (int k = 0; k < s.getMoleculeCount(); k++) {
			    logger.debug("Partition\t"+(k+1));
			    IMolecule m = s.getMolecule(k);
			    if (m!=null)
			        logger.debug(FunctionalGroups.mapToString(m).toString());
			    else    
			    	logger.debug(m);
			}	        
	    }

	}   
	protected IMolecule salt() {
	    IMolecule mol = MoleculeTools.newMolecule(DefaultChemObjectBuilder.getInstance());
	    IAtom a1 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
	    mol.addAtom(a1);
	    IAtom a2 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
	    mol.addAtom(a2);
	    IAtom a3 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
	    mol.addAtom(a3);
	    IAtom a4 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
	    mol.addAtom(a4);
	    IAtom a5 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.NITROGEN);
	    mol.addAtom(a5);
	    IAtom a6 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
	    mol.addAtom(a6);
	    IAtom a7 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
	    mol.addAtom(a7);
	    IAtom a8 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CHLORINE);
	    mol.addAtom(a8);
	    Bond b1 = new Bond(a2, a1, IBond.Order.SINGLE);
	    mol.addBond(b1);
	    Bond b2 = new Bond(a3, a2, IBond.Order.SINGLE);
	    mol.addBond(b2);
	    Bond b3 = new Bond(a4, a3, IBond.Order.SINGLE);
	    mol.addBond(b3);
	    Bond b4 = new Bond(a5, a4, IBond.Order.SINGLE);
	    mol.addBond(b4);
	    Bond b5 = new Bond(a6, a5, IBond.Order.SINGLE);
	    mol.addBond(b5);
	    Bond b6 = new Bond(a7, a5, IBond.Order.SINGLE);
	    mol.addBond(b6);	    
	    return mol;
	}
	public void testSalt() {
	    String smiles = "CCCC[N+](C)C.[Cl-]";
	    
	    IMolecule mol = (IMolecule) FunctionalGroups.createAtomContainer(smiles,false);
	    assertNotNull(mol);
	    IMoleculeSet  s = ConnectivityChecker.partitionIntoMolecules(mol);
	    assertEquals(2,s.getMoleculeCount());
	    StringWriter stringWriter = new StringWriter();
	    IChemObjectWriter writer = new CDKSourceCodeWriter(stringWriter);
	    SmilesGenerator sg = new SmilesGenerator();
	    try {
		    String newSmiles = sg.createSMILES(mol);
		    String newSmiles1 = sg.createSMILES(salt());
		    logger.debug(newSmiles);
		    logger.debug(newSmiles1);
		    writer.write((IMolecule)mol);
		    writer.close();
	    } catch (CDKException x) {
	        x.printStackTrace();
	    } catch (IOException x) {
	        x.printStackTrace();
	    }
	    //logger.debug(stringWriter.toString());	    
	}
	public void testQuery() {
		try {
			IAtomContainer a = gen.parseSmiles("C(=O)C(=O)");
			
			//assertEquals(a.getAtomCount(),6);
			//h.addExplicitHydrogensToSatisfyValency((IMolecule)a);
			//assertEquals(a.getAtomCount(),6);
			/*
			for (int i=0; i < a.getAtomCount(); i++)
				System.out.println(a.getAtomAt(i).getSymbol());
				*/
			QueryAtomContainer q = QueryAtomContainerCreator.createBasicQueryContainer(a);
			
			IMolecule mol = (IMolecule) FunctionalGroups.createAtomContainer("CCCCCC(=O)C(=O)CCCCCCCCCC",true);
			assertTrue(FunctionalGroups.hasGroup(mol,q));
		} catch (InvalidSmilesException x) {
			fail();
			/*
		} catch (IOException x) {
			fail();
		} catch (CDKException x) {
			fail();
		} catch (ClassNotFoundException x) {
			fail();
			*/
		}
	}
	public void testTerpene() {
		IMolecule mol = MoleculeFactory.makeAlphaPinene();
	    
        try {
            MolAnalyser.analyse(mol);
            assertTrue(FunctionalGroups.isCommonTerpene(mol));

        } catch (MolAnalyseException x) {
        	fail();
        }	        
		
		//mol = (IMolecule)FunctionalGroups.createAtomContainer("CC12(CCC(CC1)C2(C)(C))",true);
		//assertFalse(FunctionalGroups.isCommonTerpene(mol));
		
	}
	protected void associateIonic(IAtomContainer mol, int ionicBonds) throws CDKException {
		assertEquals(ionicBonds,FunctionalGroups.associateIonic(mol));
		//SetOfAtomContainers c = ConnectivityChecker.partitionIntoMolecules(mol);
		//assertEquals(1,c.getAtomContainerCount());
		//SmilesGenerator g = new SmilesGenerator();
		//System.out.println(g.createSMILES((IMolecule)mol));
	}
	public void testAssociateIonic() {
		try {
			IAtomContainer c =null;
			/*
			try {
				
			c =	 FunctionalGroups.createAtomContainer("[Na+].[Na+].CCN(CC1=CC(=CC=C1)S([O-])(=O)=O)C2=CC=C(C=C2)C(=C3//C=CC(\\C=C3)=[N+](//CC)CC4=CC=CC(=C4)S([O-])(=O)=O)\\C5=C(C=C(O)C=C5)S([O-])(=O)=O");
			associateIonic(c,2);
			} catch (CDKException x) {
				assertTrue(x.getMessage().startsWith("Can't find an ionic bond for atom(s)\t"));
			}

			c = FunctionalGroups.createAtomContainer("[Na+].[Na+].[Na+].OC1=C(N=NC2=CC=C(C=C2)S([O-])(=O)=O)C(=NN1C3=CC=C(C=C3)S([O-])(=O)=O)C([O-])=O");
			associateIonic(c,3);	


			c = FunctionalGroups.createAtomContainer("OC1=CC=C2C=C(C=CC2=C1N=NC3=CC=C(C=C3)S(=O)(=O)[O-])S(=O)(=O)[O-].[Na+].[Na+]");
			associateIonic(c,2);	
			*/
			c = FunctionalGroups.createAtomContainer("[Ca+2].[O-]C=O.[O-]C=O");			
			associateIonic(c,2);

		} catch (CDKException x) {
			x.printStackTrace();
			fail();
		}
		
	}
	public void testAssociateIonic1to1() {
		try {
			IAtomContainer c;
			c = FunctionalGroups.createAtomContainer("[Cl-].[NH3+]C1CCCCC1");
			associateIonic(c,1);
			
			c = FunctionalGroups.createAtomContainer("[Na+].[O-]S(=O)(=O)NC1CCCCC1");
			associateIonic(c,1);
			
	
			
		} catch (CDKException x) {
			x.printStackTrace();
			fail();
		}
	}
	public IMoleculeSet removeGroup(String smiles,QueryAtomContainer q) {
		IAtomContainer c = FunctionalGroups.createAtomContainer(smiles);
		try {
			MolAnalyser.analyse(c);
		} catch (MolAnalyseException x) {
			fail();
		}
		
		List map = FunctionalGroups.getBondMap(c,q,false);
		FunctionalGroups.markMaps(c,q,map);
		//System.out.println(FunctionalGroups.mapToString(c));
		assertNotNull(map);
		IMoleculeSet sc = FunctionalGroups.detachGroup(c,q);
		//System.out.println();
		/*
		if (sc != null) {
			SmilesGenerator g = new SmilesGenerator();
			for (int i=0;i<sc.getAtomContainerCount();i++)
				System.out.println(g.createSMILES((IMolecule)sc.getAtomContainer(i)));
		}
		*/
		return sc;
	}
	public void testBreakLactone() {
		QueryAtomContainer q = FunctionalGroups.lactoneBreakable();
		IAtomContainerSet c = null;
		c = removeGroup("O=C1CCCO1",q);
		assertEquals(1,c.getAtomContainerCount());
		//hydroxy acid
		IAtomContainer a = FunctionalGroups.createAtomContainer("O=C(O)CCCO",true);
		try {
			assertTrue(UniversalIsomorphismTester.isIsomorph(a,c.getAtomContainer(0)));
		} catch (CDKException x) {
			x.printStackTrace();
		}

		c = removeGroup("OCC(O)C1OC(=O)C(O)=C1(O)",q);
		assertEquals(1,c.getAtomContainerCount());
		

	}
    public void testLactone() {
        QueryAtomContainer q = FunctionalGroups.lactone(false);
        QueryAtomContainer q1 = FunctionalGroups.anhydride();        
        Object[][] answer = {
        		{"C1OC(=O)C=C1",new Boolean(true)},
        		{"O=C1OC(O)C(O)=C1(O)",new Boolean(true)},
        		{"O=C1CCC(=O)O1",new Boolean(false)}
        			};
        	

        for (int i=0;i<answer.length;i++) {
	        IAtomContainer c = FunctionalGroups.createAtomContainer(answer[i][0].toString());
	        try {
	        	MolAnalyser.analyse(c);
	        	assertEquals(FunctionalGroups.hasGroup(c,q),true);	        	
	        	assertEquals(FunctionalGroups.hasGroup(c,q1),!((Boolean)answer[i][1]).booleanValue());
	        	System.out.println(answer[i][0]+"\t"+q.getID()+"\t"+((Boolean)answer[i][1]).toString());
	        	
	        } catch (MolAnalyseException x) {
	        	x.printStackTrace();
	        	fail();
	        	
	        }
        }
    	
    }
    /**
     * DeduceBondTool loops forever
     * @throws Exception
     */
	public void testRemoveSulphonateGroup_big() throws Exception  {
		QueryAtomContainer q = FunctionalGroups.sulphonate(null,false);
		IAtomContainerSet c = null;
		c = removeGroup("[Na+].[Na+].CCN(CC1=CC(=CC=C1)S([O-])(=O)=O)C2=CC=C(C=C2)C(=C3//C=CC(\\C=C3)=[N+](//CC)CC4=CC=CC(=C4)S([O-])(=O)=O)\\C5=C(C=C(O)C=C5)S([O-])(=O)=O",q);
		assertEquals(4,c.getAtomContainerCount());
	}	
	public void testRemoveSulphonateGroup() throws Exception  {
		QueryAtomContainer q = FunctionalGroups.sulphonate(null,false);
		IAtomContainerSet c = null;
		c = removeGroup("[Na+].[O-]S(=O)(=O)CC1CCCCC1",q);
		assertEquals(2,c.getAtomContainerCount());
		c = removeGroup("[Na+].[O-]S(=O)(=O)CCCCS(=O)(=O)[O-].[Na+]",q);
		assertEquals(3,c.getAtomContainerCount());
	}
	public void testRemoveSulphateOfAmineGroup() {
		QueryAtomContainer q = FunctionalGroups.sulphateOfAmineBreakable();
		IAtomContainer a = phenazineMethosulphate();
		try {
			MolAnalyser.analyse(a);
		} catch (MolAnalyseException x) {
			fail();
		}
		IAtomContainerSet c = FunctionalGroups.detachGroup(a,q);
		assertNotNull(c);
		/*
		SmilesGenerator g = new SmilesGenerator();		
		System.out.println("Sulphate of amine");
		MFAnalyser mf = new MFAnalyser(a);
		a = mf.removeHydrogensPreserveMultiplyBonded();
		System.out.println(g.createSMILES((IMolecule)a));
		System.out.println();
		for (int i=0;i<c.getAtomContainerCount();i++)
			System.out.println(g.createSMILES((IMolecule)c.getAtomContainer(i)));
		*/
		assertEquals(2,c.getAtomContainerCount());
	}
	public void testRemoveSulphateGroup() {
		QueryAtomContainer q = FunctionalGroups.sulphate(null);
		IAtomContainerSet c = null;
		c = removeGroup("[Na+].CCCCCCCCCCCCOCCOCCOCCOS([O-])(=O)=O",q);
		assertEquals(2,c.getAtomContainerCount());
		/*
		c = removeGroup("[Na+].[O-]S(=O)(=O)CC1CCCCC1",q);
		assertEquals(2,c.getAtomContainerCount());
		c = removeGroup("[Na+].[O-]S(=O)(=O)CCCCS(=O)(=O)[O-].[Na+]",q);
		assertEquals(3,c.getAtomContainerCount());
		*/
	}
	
	public void testBreakSalt() {
		QueryAtomContainer q = FunctionalGroups.saltOfCarboxylicAcidBreakable(
				new String[] {"Na","K"});
		IAtomContainer salt = FunctionalGroups.createAtomContainer("CCCCC(=O)[O-].[Na+]");
		IAtomContainer acid = FunctionalGroups.createAtomContainer("CCCCC(=O)O",true);
		
		try {
			MolAnalyser.analyse(salt);
			//MolAnalyser.analyse(acid);			
		} catch (MolAnalyseException x) {
			fail();
		}
		IAtomContainerSet c = FunctionalGroups.detachGroup(salt,q);
		assertNotNull(c);

		//SmilesGenerator g = new SmilesGenerator();		
		assertEquals(2,c.getAtomContainerCount());
		//System.out.println(q.getID());
		//System.out.println(g.createSMILES((IMolecule)acid));				
		for (int i=0; i < c.getAtomContainerCount();i++) {
			IAtomContainer a = c.getAtomContainer(i);
			//MFAnalyser mf = new MFAnalyser(a);
			//a = mf.removeHydrogensPreserveMultiplyBonded();			
			//System.out.println(g.createSMILES((IMolecule)a));							
			if (FunctionalGroups.hasGroupMarked(a,q.getID()))
			//if (a.getAtomCount() > 3)
				try {
				assertTrue(UniversalIsomorphismTester.isIsomorph(acid,a));
				} catch (CDKException x) {
					x.printStackTrace();
					fail();
				}

		}
	}
	//
		
	public void testBreakHydrochlorideOfAmine() throws Exception  {
		QueryAtomContainer q = FunctionalGroups.hydrochlorideOfAmineBreakable();
		SmilesGenerator g = new SmilesGenerator(true);		
		
		String[] examples = {
				"[Cl-].[NH3+]C1CCCCC1",
				"[Cl-].[N+]C1CCCCC1",
				"[Cl-].[N+]([H])([H])([H])C1CCCCC1"
				};
		IAtomContainer amine = FunctionalGroups.createAtomContainer("NC1CCCCC1",true);
		
		for (int e=0; e < examples.length; e++) {
			IAtomContainer hydrochlorideAmine = FunctionalGroups.createAtomContainer(examples[e]);
			
			MolAnalyser.analyse(hydrochlorideAmine);
			IAtomContainerSet c = FunctionalGroups.detachGroup(hydrochlorideAmine,q);
			assertNotNull(c);
			
			assertEquals(2,c.getAtomContainerCount());
			//System.out.print(q.getID());
			System.out.println("Amine "+g.createSMILES((IMolecule)amine));
			
			for (int i=0; i < c.getAtomContainerCount();i++) {
				IAtomContainer a = c.getAtomContainer(i);
	
				System.out.println("Product "+g.createSMILES((IMolecule)a));							
				if (FunctionalGroups.hasGroupMarked(a,q.getID())) {
					assertEquals(amine.getAtomCount(),a.getAtomCount());
					assertTrue(UniversalIsomorphismTester.isIsomorph(amine,a));
				}	
	
			}
		}
	}
	
	public static IMolecule phenazineMethosulphate() 
	{
		  IMolecule mol = MoleculeTools.newMolecule(DefaultChemObjectBuilder.getInstance());
		  IAtom nq = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.NITROGEN);
		  nq.setFormalCharge(+1);
		  mol.addAtom(nq);
		  IAtom a2 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.SULFUR);
		  mol.addAtom(a2);
		  IAtom a3 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.NITROGEN);
		  mol.addAtom(a3);
		  IAtom a4 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a4);
		  IAtom a5 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a5);
		  IAtom a6 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a6);
		  IAtom a7 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a7);
		  IAtom o1 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.OXYGEN);
		  o1.setFormalCharge(-1);
		  mol.addAtom(o1);
		  IAtom a9 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.OXYGEN);
		  mol.addAtom(a9);
		  IAtom a10 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.OXYGEN);
		  mol.addAtom(a10);
		  IAtom a11 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.OXYGEN);
		  mol.addAtom(a11);
		  IAtom a12 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a12);
		  IAtom a13 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a13);
		  IAtom a14 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a14);
		  IAtom a15 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a15);
		  IAtom a16 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a16);
		  IAtom a17 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a17);
		  IAtom a18 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a18);
		  IAtom a19 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a19);
		  IAtom a20 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a20);
		  IAtom a21 = MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON);
		  mol.addAtom(a21);
		  Bond b1 = new Bond(a3, a7, IBond.Order.SINGLE);
		  mol.addBond(b1);
		  Bond b2 = new Bond(a4, nq, IBond.Order.SINGLE);
		  mol.addBond(b2);
		  Bond b3 = new Bond(a5, nq, IBond.Order.DOUBLE);
		  mol.addBond(b3);
		  Bond b4 = new Bond(a6, a5, IBond.Order.SINGLE);
		  mol.addBond(b4);
		  Bond b5 = new Bond(a7, a4, IBond.Order.DOUBLE);
		  mol.addBond(b5);
		  Bond b6 = new Bond(o1, a2, IBond.Order.SINGLE);
		  mol.addBond(b6);
		  Bond b7 = new Bond(a9, a2, IBond.Order.DOUBLE);
		  mol.addBond(b7);
		  Bond b8 = new Bond(a10, a2, IBond.Order.DOUBLE);
		  mol.addBond(b8);
		  Bond b9 = new Bond(a11, a2, IBond.Order.SINGLE);
		  mol.addBond(b9);
		  Bond b10 = new Bond(a12, nq, IBond.Order.SINGLE);
		  mol.addBond(b10);
		  Bond b11 = new Bond(a13, a4, IBond.Order.SINGLE);
		  mol.addBond(b11);
		  Bond b12 = new Bond(a14, a5, IBond.Order.SINGLE);
		  mol.addBond(b12);
		  Bond b13 = new Bond(a15, a6, IBond.Order.SINGLE);
		  mol.addBond(b13);
		  Bond b14 = new Bond(a16, a7, IBond.Order.SINGLE);
		  mol.addBond(b14);
		  Bond b15 = new Bond(a17, a11, IBond.Order.SINGLE);
		  mol.addBond(b15);
		  Bond b16 = new Bond(a18, a13, IBond.Order.DOUBLE);
		  mol.addBond(b16);
		  Bond b17 = new Bond(a19, a14, IBond.Order.DOUBLE);
		  mol.addBond(b17);
		  Bond b18 = new Bond(a20, a19, IBond.Order.SINGLE);
		  mol.addBond(b18);
		  Bond b19 = new Bond(a21, a16, IBond.Order.DOUBLE);
		  mol.addBond(b19);
		  Bond b20 = new Bond(a6, a3, IBond.Order.DOUBLE);
		  mol.addBond(b20);
		  Bond b21 = new Bond(a18, a21, IBond.Order.SINGLE);
		  mol.addBond(b21);
		  Bond b22 = new Bond(a15, a20, IBond.Order.DOUBLE);
		  mol.addBond(b22);
		  return mol;
		}
	

	
}

