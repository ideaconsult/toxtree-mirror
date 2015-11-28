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
package toxtree.test.io;

import java.io.File;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.tools.LoggingTool;

import toxTree.data.MoleculesIterator;
import toxtree.data.DecisionMethodData;


/**
 * TODO add description
 * @author Vedina
 * <b>Modified</b> 2005-8-12
 */
public class ToxtreeDataIOTest {
	protected DecisionMethodData data = null;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		data = new DecisionMethodData(null);
        //logger = new LoggingTool(this);
        LoggingTool.configureLog4j();		
	}

	/*
    public void testReadCML() {
        String filename = "data/Misc/SN1_reaction.cml";
       //String filename = "toxTree/test/3d.head.2an.cml";
        
        try {
            InputStream ins = new FileInputStream(filename);
        	
            CMLReader reader = new CMLReader(new InputStreamReader(ins));
            IChemObject f = new ChemFileCDO(new org.openscience.cdk.ChemFile());
            IChemObject chemFile = reader.read(f);

            // test the resulting ChemFile content
            assertNotNull(chemFile);
            System.out.println(chemFile.getClass().getName());
            assertTrue(chemFile instanceof IChemFile);
            assertEquals(1, ((IChemFile)chemFile).getChemSequenceCount());
            IChemSequence seq = ((IChemFile)chemFile).getChemSequence(0);
            assertNotNull(seq);
            assertEquals(seq.getChemModelCount(),1);
            IChemModel model = seq.getChemModel(0);
            assertNotNull(model);
            IMoleculeSet som = model.getMoleculeSet();
            assertNotNull(som);
            assertTrue(som.getMoleculeCount()>1);

            // test the molecule
            IMolecule mol = som.getMolecule(0);
            assertNotNull(mol);
   //         assertTrue(GeometryUtil.has3DCoordinates(mol));
        } catch (Exception e) {
        	e.printStackTrace();
            fail(e.toString());
        }
    }
    */
	@Test
	public void testOpenFile() throws Exception {
		MoleculesIterator m = new MoleculesIterator();
        m.openFile(new File("toxTree/data/Misc/emptyStructure.sdf"));
        Assert.assertEquals(2,m.getMoleculesCount());
	}

}
