/* MengineCrashTest.java
 * Author: Nina Jeliazkova
 * Date: Mar 18, 2008 
 * Revision: 0.1 
 * 
 * Copyright (C) 2005-2008  Nina Jeliazkova
 * 
 * Contact: nina@acad.bg
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 */

package toxTree.test.io;

import java.io.FileInputStream;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.logging.TTLogger;
import toxTree.qsar.ShellMengine;
import toxTree.qsar.ShellSmi2SDF;
import toxTree.query.MolAnalyser;
import toxTree.ui.Preferences;

import junit.framework.TestCase;

public class MengineCrashTest extends TestCase {
    protected ShellSmi2SDF smi2sdf;
    protected ShellMengine mengine;
    
    
    protected void setUp() throws Exception {
        super.setUp();
        smi2sdf = new ShellSmi2SDF();
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(false);
        mengine = new ShellMengine();   
        TTLogger.configureLog4j(true);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    public void test1() throws  Exception {
        IMolecule a = getChemical("E:/ChemDatabases/EINECS/tmp/problem-001.sdf");
        IMolecule b = getChemical("E:/ChemDatabases/EINECS/tmp/problem-001-chemidplus.sdf");        
        isIsomorph(a,b);   
    }
    public void test1_1() throws  Exception {
        IMolecule a = getChemical("toxTree/data/mengine/problem-001.sdf");
        IMolecule b = getChemical("toxTree/data/mengine/smi2sdf_generated.sdf");        
        isIsomorph(a,b);   
    }    
    public void test2() throws  Exception {
        IMolecule a = getChemical("toxTree/data/mengine/problem-002.sdf");
        IMolecule b = getChemical("toxTree/data/mengine/problem-002-chemidplus.sdf");        
        isIsomorph(a,b);   
    }       
    public void test3() throws  Exception {
        IMolecule a = getChemical("toxTree/data/mengine/problem-003.sdf");
        IMolecule b = getChemical("toxTree/data/mengine/problem-003-chemidplus.sdf");        
        isIsomorph(a,b);   
    }   
    public void test4() throws  Exception {
        IMolecule a = getChemical("toxTree/data/mengine/problem-004.sdf");
        IMolecule b = getChemical("toxTree/data/mengine/problem-004-chemidplus.sdf");        
        isIsomorph(a,b);   
    }       
    public void isIsomorph(IMolecule a,IMolecule b) throws  Exception {
        System.out.println(a.getAtomCount());
        System.out.println(b.getAtomCount());        
        SmilesGenerator g = new SmilesGenerator();
        MolAnalyser.analyse(a);
        MolAnalyser.analyse(b);
        System.out.println(a.getAtomCount());
        System.out.println(b.getAtomCount());
        assertTrue(UniversalIsomorphismTester.isIsomorph(a,b));

        
        String s1= g.createSMILES(a);
        String s2= g.createSMILES(b);
        
        SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IMolecule a1 = p.parseSmiles(s1);
        IMolecule b1 = p.parseSmiles(s2);
        assertTrue(UniversalIsomorphismTester.isIsomorph(a1,b1));
        assertTrue(UniversalIsomorphismTester.isIsomorph(a,a1));
        assertTrue(UniversalIsomorphismTester.isIsomorph(b,b1));
        
        MolAnalyser.analyse(a);
        MolAnalyser.analyse(b);
        assertTrue(UniversalIsomorphismTester.isIsomorph(a,b));


    }
    public void testCrash1() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(false);        
        IMolecule a = getChemical("toxTree/data/mengine/problem-001.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    }    
    public void testCrash2() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(false);        
        IMolecule a = getChemical("toxTree/data/mengine/problem-002.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    }
    public void testCrash3() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(false);        
        IMolecule a = getChemical("toxTree/data/mengine/problem-003.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    }
    public void testCrash4() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(false);        
        IMolecule a = getChemical("toxTree/data/mengine/problem-004.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    }   
    public void testCrash5() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(false);
        IMolecule a = getChemical("toxTree/data/mengine/problem-005.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    } 
    public void testCrash6() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(false);
        IMolecule a = getChemical("toxTree/data/mengine/problem-006.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    } 
    public void testCrash7() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(false);
        IMolecule a = getChemical("toxTree/data/mengine/problem-007.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    }     
    public void testCrashNoH1() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(true);
        IMolecule a = getChemical("toxTree/data/mengine/problem-001.sdf");
        assertEquals(0,goCrash(a,"SMILES"));
    }  
    public void testCrashNoH2() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(true);
        IMolecule a = getChemical("toxTree/data/mengine/problem-002.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    }
    public void testCrashNoH3() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(true);
        IMolecule a = getChemical("toxTree/data/mengine/problem-003.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    }
    public void testCrashNoH4() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(true);
        IMolecule a = getChemical("toxTree/data/mengine/problem-004.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    }
    public void testCrashNoH5() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(true);
        IMolecule a = getChemical("toxTree/data/mengine/problem-005.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    }
    public void testCrashNoH6() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(true);
        IMolecule a = getChemical("toxTree/data/mengine/problem-006.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    }
    public void testCrashNoH7() throws  Exception {
        smi2sdf.setGenerateSmiles(true);
        smi2sdf.setDropHydrogens(true);
        IMolecule a = getChemical("toxTree/data/mengine/problem-007.sdf");
        assertEquals(0,goCrash(a,"GENERATED_SMILES"));
    }         
    public int goCrash(IMolecule a, String smilesfield) throws  Exception {
        MolAnalyser.analyse(a);
        Preferences.setProperty(Preferences.SMILES_FIELD, smilesfield);
        smi2sdf.setOutputFile("test.sdf");
        smi2sdf.runShell(a);
        mengine.setInputFile("test.sdf");
        mengine.setOutputFile("good.sdf");
        IMolecule newmol = mengine.runShell(a);
        return mengine.getExitCode();
    }    
    
    public IMolecule getChemical(String file) throws Exception {
        IMolecule a = null;
        IIteratingChemObjectReader reader = new IteratingMDLReader(new FileInputStream(file),DefaultChemObjectBuilder.getInstance());
        while (reader.hasNext()) {
            Object o = reader.next();
            if (o instanceof IMolecule) {
                a = (IMolecule) o;
                break;
            }
        }
        reader.close();
        return a;
    }
}
