/* Mopac7WriterTest.java
 * Author: Nina Jeliazkova
 * Date: 2006-4-8 
 * Revision: 0.1 
 * 
 * Copyright (C) 2005-2006  Ideaconsult Ltd.
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

package mutant.test.descriptors;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;

import junit.framework.TestCase;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.MDLWriter;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.result.DoubleArrayResult;
import org.openscience.cdk.smiles.SmilesParser;

import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import ambit2.mopac.DescriptorMopacShell;
import ambit2.mopac.Mopac7Reader;
import ambit2.mopac.Mopac7Writer;

/**
 * TODO add description
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> 2006-4-8
 */
public class Mopac7WriterTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(Mopac7WriterTest.class);
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
     * Constructor for Mopac7WriterTest.
     * @param arg0
     */
    public Mopac7WriterTest(String arg0) {
        super(arg0);
    }
    public void test() {
        try {
        	StringWriter w = new StringWriter();
            Mopac7Writer writer = new Mopac7Writer(w) {
            	@Override
            	public String getTitle() {
            		return "test";
            	}
            };
//                    new OutputStreamWriter(new FileOutputStream("helper/mopac.dat")));
            SmilesParser p = new SmilesParser(DefaultChemObjectBuilder.getInstance());
            IMolecule mol = p.parseSmiles("CCCCCc1cccc2cccc(c12)CCC");
            
            MolAnalyser.analyse(mol);
            
            StructureDiagramGenerator g = new StructureDiagramGenerator();
            g.setMolecule(mol,false);
            g.generateCoordinates();
            
            /*
            ModelBuilder3D mb3d=new ModelBuilder3D();
            mb3d.setTemplateHandler();
        	mb3d.setForceField("mm2");
			mb3d.setMolecule((org.openscience.cdk.Molecule)mol,false);
			mb3d.generate3DCoordinates();
			mol = mb3d.getMolecule();
			*/
            writer.write(mol);
            writer.close();
            
            String content = 
            	"PM3 NOINTER NOMM BONDS MULLIK PRECISE\r\n"+
            	"\r\n"+
            	"test\r\n"+
            	"C -5.1962 1 9 1 0 1 \r\n"+ 
            	"C -3.8971 1 8.25 1 0 1 \r\n"+ 
            	"C -3.8971 1 6.75 1 0 1 \r\n"+ 
            	"C -2.5981 1 6 1 0 1 \r\n"+ 
            	"C -2.5981 1 4.5 1 0 1 \r\n"+ 
            	"C -1.299 1 3.75 1 0 1 \r\n"+ 
            	"C 0 1 4.5 1 0 1 \r\n"+ 
            	"C 1.299 1 3.75 1 0 1 \r\n"+ 
            	"C 1.299 1 2.25 1 0 1 \r\n"+ 
            	"C 0 1 1.5 1 0 1 \r\n"+ 
            	"C 0 1 0 1 0 1 \r\n"+
            	"C -1.299 1 -0.75 1 0 1 \r\n"+ 
            	"C -2.5981 1 -0 1 0 1 \r\n"+
            	"C -2.5981 1 1.5 1 0 1 \r\n"+
            	"C -1.299 1 2.25 1 0 1 \r\n"+
            	"C -3.8971 1 2.25 1 0 1 \n"+
            	"C -5.1962 1 1.5 1 0 1 \r\n"+
            	"C -6.4952 1 2.25 1 0 1 \r\n"+
            	"H -5.1962 1 10.5 1 0 1 \r\n"+
            	"H -6.6734 1 9.2605 1 0 1 \r\n"+
            	"H -5.7092 1 7.5905 1 0 1 \r\n"+
            	"H -2.4199 1 7.9895 1 0 1 \r\n"+
            	"H -3.3841 1 9.6595 1 0 1 \r\n"+
            	"H -5.3743 1 7.0105 1 0 1 \r\n"+
            	"H -4.4101 1 5.3405 1 0 1 \r\n"+
            	"H -1.1209 1 5.7395 1 0 1 \r\n"+
            	"H -2.085 1 7.4095 1 0 1 \r\n"+
            	"H -4.0753 1 4.7605 1 0 1 \r\n"+
            	"H -3.1111 1 3.0905 1 0 1 \r\n"+
            	"H 0 1 6 1 0 1 \r\n"+
            	"H 2.5981 1 4.5 1 0 1 \r\n"+ 
            	"H 2.5981 1 1.5 1 0 1 \r\n"+
            	"H 1.299 1 -0.75 1 0 1 \r\n"+
            	"H -1.299 1 -2.25 1 0 1 \r\n"+            	
            	"H -3.8971 1 -0.75 1 0 1 \r\n"+
            	"H -2.9329 1 3.3991 1 0 1 \r\n"+
            	"H -4.8613 1 3.3991 1 0 1 \r\n"+
            	"H -6.1603 1 0.3509 1 0 1 \r\n"+
            	"H -4.232 1 0.3509 1 0 1 \r\n"+
            	"H -7.7942 1 1.5 1 0 1 \r\n"+
            	"H -5.531 1 3.3991 1 0 1 \r\n"+
            	"H -7.4594 1 3.3991 1 0 1 \r\n"+
            	"0 \r\n";
            	
            String actual = w.toString();
           // assertEquals(content.length(),actual.length());
            assertEquals(content,actual);
            
        } catch (Exception x) {
            x.printStackTrace();
            fail();
        }
    }
    public void testReader() throws Exception {
    		InputStream in = new FileInputStream(
    				"/data/mopac/ethylene.dat.out");
            Mopac7Reader r = new Mopac7Reader(in);
            IChemObject m = FunctionalGroups.createAtomContainer("C=C",true);
            m = r.read(m);
            Object e = m.getProperty("EIGENVALUES");
            assertNotNull(e);
            for (int i=0; i < Mopac7Reader.parameters.length;i++)
            	System.out.println(
            			Mopac7Reader.parameters[i]
            			                        + " = " +
            			((IMolecule) m).getProperty(Mopac7Reader.parameters[i])
            			);
            assertEquals(-10.552,
            		Double.parseDouble(m.getProperty(DescriptorMopacShell.EHOMO).toString()),
            		1E-3);
            assertEquals(1.438,
            		Double.parseDouble(m.getProperty(DescriptorMopacShell.ELUMO).toString()),
            		1E-3);            
    }
    public void xtestNCI() {
        try {
           
            IteratingMDLReader reader = new IteratingMDLReader(
                new FileInputStream("D:\\nina\\Databases\\nciopen_3D_fixed.sdf"),
                DefaultChemObjectBuilder.getInstance()
                );
            MDLWriter wriOK = new MDLWriter(new FileOutputStream(
                    "D:\\nina\\nciopen_3D_electronic_ok.sdf"));
            MDLWriter wriErr = new MDLWriter(new FileOutputStream(
            "D:\\nina\\nciopen_3D_electronic_err.sdf"));
            
            int n = 0;
            DescriptorMopacShell shell = new DescriptorMopacShell();
            while (reader.hasNext()) {
                Object o = reader.next();
                if (o instanceof IMolecule) {
                    n++;
                    if (n < 210) continue;
                    if (n > 219) break;
                    IMolecule m = (IMolecule) o;
                    //writer.write((org.openscience.cdk.interfaces.ChemObject)o);
                    
  
	                    DescriptorValue v = shell.calculate(m);
	                    assertNull(v.getException());
	                    DoubleArrayResult r = (DoubleArrayResult) v.getValue();
	                    String[] names = v.getNames();
	                    assertEquals(names.length,r.length());
	                    
	                    for (int g=0; g<names.length;g++) {
	                    	assertNotNull(r.get(g));
	                    	System.out.println(names[g] + "\t= "+r.get(g));
	                    } 	

                    /*
                    if ((m.getProperty(MopacShell.EHOMO) == null) ||
                    	m.getProperty(MopacShell.ELUMO) == null) {
                        wriErr.setSdFields(m.getProperties());
                        wriErr.writeMolecule(m);
                        System.out.println("HOMO/LUMO not calculated");
                    } else {
                        wriOK.setSdFields(m.getProperties());
                        wriOK.writeMolecule(m);
                        System.out.println("HOMO/LUMO calculated");
                    }
                    */

                }
                
                //System.out.println(n);
                //if (n > 1)
                
            }
            assertEquals(220,n);
            reader.close();
            wriOK.close();
            wriErr.close();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
