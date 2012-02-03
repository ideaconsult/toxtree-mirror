/*
 Copyright (C) 2005-2007  

 Contact: nina@acad.bg

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public License
 as published by the Free Software Foundation; either version 2.1
 of the License, or (at your option) any later version.
 All we ask is that proper credit is given for our work, which includes
 - but is not limited to - adding the above copyright notice to the beginning
 of your source code files, and to any copyright notice that you may distribute
 with programs based on this work.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
 */

package mutant.test.descriptors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import junit.framework.TestCase;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.result.BooleanResult;
import org.openscience.cdk.qsar.result.DoubleArrayResult;
import org.openscience.cdk.qsar.result.IDescriptorResult;
import org.openscience.cdk.qsar.result.IntegerResult;

import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import ambit2.core.io.DelimitedFileWriter;
import ambit2.core.io.IteratingDelimitedFileReader;

public abstract class DescriptorsTest extends TestCase {
	protected IMolecularDescriptor descriptor;

	protected Hashtable<String, String> properties;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		descriptor = createDescriptorToTest();
		properties = new Hashtable<String, String>();
	}

	protected void addPropertiesToTest(String expected, String calculated) {
		properties.put(expected, calculated);
	}

	public void test() throws Exception {
		calculate(descriptor, getSourceFile(), getResultsFile(),
				getStructureID());
	}

	public abstract String getSourceFile();

	public abstract String getResultsFile();

	public abstract String getStructureID();

	protected void calculate(IMolecularDescriptor descriptor,
			String sourcefile, String resultsfile, String strucID)
			throws Exception {
		InputStream in_source = getClass().getClassLoader().getResourceAsStream(String.format("data/%s",sourcefile));

		OutputStream out_results = new FileOutputStream(File.createTempFile("results","sdf"));


		DelimitedFileWriter writer = new DelimitedFileWriter(out_results);
		
		IteratingDelimitedFileReader reader = new IteratingDelimitedFileReader(
				in_source);
		int errors = 0;
		while (reader.hasNext()) {
			Object o = reader.next();
			if (o instanceof IAtomContainer) {
    
			IAtomContainer mol = (IAtomContainer) o;
            try {                            
				MolAnalyser.analyse(mol);
				DescriptorValue dvalue = descriptor.calculate(mol);
				String[] names = dvalue.getNames();
				Object value = dvalue.getValue();
				if (value instanceof DoubleArrayResult) {
					if ((names != null)
							&& (names.length == ((DoubleArrayResult) value)
									.length()))
						for (int j = 0; j < names.length; j++) {
							mol.setProperty(names[j],
									((DoubleArrayResult) value).get(j));
						}
				} else if (value instanceof BooleanResult) {
                    if ((names != null)
                            && (names.length == 1))
                        if (((BooleanResult)value).booleanValue())
                            mol.setProperty(names[0],new Double(1));
                            else mol.setProperty(names[0],new Double(0));
                } else if (value instanceof IntegerResult) {
                    if ((names != null)
                            && (names.length == 1))
                      mol.setProperty(names[0],new Double(((IntegerResult)value).intValue()));
                }
				Enumeration<String> expected = properties.keys();
				boolean ok = true;
				
				while (expected.hasMoreElements()) {
					String expString = expected.nextElement();
					String calcString = properties.get(expString);

					Object expectedValue = mol.getProperty(expString);
					Number expNumber = Double.parseDouble(expectedValue
							.toString());
					assertNotNull(expNumber);

					Object calcValue = mol.getProperty(calcString);
					assertNotNull(calcValue);

					ok = ok && expNumber.equals(calcValue);
					if (!ok)
						System.err.println(mol.getProperty(strucID)
								+ " Expected " + expString + " = "
								+ expectedValue + " Calculated " + calcString
								+ " = " + calcValue);
					//assertTrue(ok);
				}
				if (!ok) {
					writer.write(mol);
					errors ++;
				}	
			} catch (Exception x) {
                System.err.println(mol.getProperty(strucID));                
                writer.write(mol);
				errors ++;
				x.printStackTrace();
				//fail(x.getMessage());
			}
            }            
		}
		in_source.close();
		out_results.close();
		assertEquals(0,errors);
	}

	protected abstract IMolecularDescriptor createDescriptorToTest()
			throws Exception;
	
    public void calculate(Object[][] smiles) throws Exception {    
		//fail("verify how substituted amine group should be treated (params of amine group + substituent, only substituent, largest substituent, average, etc.)");

        
        for (int i=0; i < smiles.length;i++) {
            System.out.println(smiles[i][0]);
            IAtomContainer a = FunctionalGroups.createAtomContainer(smiles[i][0].toString(), false);
            //AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(a);
            //CDKHueckelAromaticityDetector.detectAromaticity(a);

            MolAnalyser.analyse(a);
            for (int ii=0; ii< a.getAtomCount();ii++)
            	a.getAtom(ii).setID(Integer.toString(ii+1));
            /*
            for (int ii=0; ii< a.getAtomCount();ii++) {
            	IAtom aa = a.getAtom(ii);
            	
            	System.out.print(aa.getSymbol() + 
            			"\tneighbours="+ aa.getFormalNeighbourCount()+
            			"\thybridisation="+ aa.getHybridization()+
            			"\thcount="+ aa.getHydrogenCount()+
            			"\tID="+ aa.getID()+
            			"\tconnected=["+ a.getConnectedAtomsCount(aa) +"]\t"
            			);

            	List<IAtom> na = a.getConnectedAtomsList(aa);
            	for (int jj=0; jj < na.size();jj++) {
            		System.out.print(na.get(jj).getID());
            		System.out.print(',');
            	}
            	System.out.print("\tbonds\t");
            	List<IBond> nb = a.getConnectedBondsList(aa);
            	for (int jj=0; jj < nb.size();jj++) {
            		System.out.print(nb.get(jj).getOrder());
            		
            		if (nb.get(jj).getFlag(CDKConstants.ISAROMATIC))
            				System.out.print(" aromatic");
            		System.out.print(',');
            	}            	
            	System.out.println();
            	
            }
                        	*/
            DescriptorValue value = descriptor.calculate(a);
            IDescriptorResult r = value.getValue();
            if (r instanceof DoubleArrayResult) {

                for (int j=0; j < value.getNames().length;j++) {
                    //System.out.println(value.getNames()[j] + " = " + ((DoubleArrayResult)r).get(j));
                    a.setProperty(value.getNames()[j], ((DoubleArrayResult)r).get(j));
                }
                boolean ok = true;
                for (int j=1; j < smiles[i].length; j+=2) {
                    System.out.print(smiles[i][j]);
                    Object o = a.getProperty(smiles[i][j]);
                    
                    System.out.print("\tExpected\t");
                    System.out.print(smiles[i][j+1]);
                    System.out.print("\tCalculated\t");
                    System.out.println(o);
                    ok = ok && (smiles[i][j+1].equals(o));                    
                        
                }
                assertTrue(ok);
            } else if (r instanceof BooleanResult) {

                    for (int j=0; j < value.getNames().length;j++) {
                        //System.out.println(value.getNames()[j] + " = " + ((DoubleArrayResult)r).get(j));
                        a.setProperty(value.getNames()[j], ((BooleanResult)r).booleanValue());
                    }
                    boolean ok = true;
                    for (int j=1; j < smiles[i].length; j+=2) {
                        System.out.print(smiles[i][j]);
                        Object o = a.getProperty(smiles[i][j]);
                        
                        System.out.print("\tExpected\t");
                        System.out.print(smiles[i][j+1]);
                        System.out.print("\tCalculated\t");
                        System.out.println(o);
                        ok = ok && (smiles[i][j+1].equals(o));                    
                            
                    }
                    assertTrue(ok);
            } else if (r instanceof IntegerResult) {

                for (int j=0; j < value.getNames().length;j++) {
                    //System.out.println(value.getNames()[j] + " = " + ((DoubleArrayResult)r).get(j));
                    a.setProperty(value.getNames()[j], ((IntegerResult)r).intValue());
                }
                boolean ok = true;
                for (int j=1; j < smiles[i].length; j+=2) {
                    System.out.print(smiles[i][j]);
                    Object o = a.getProperty(smiles[i][j]);
                    
                    System.out.print("\tExpected\t");
                    System.out.print(smiles[i][j+1]);
                    System.out.print("\tCalculated\t");
                    System.out.println(o);
                    ok = ok && (smiles[i][j+1].equals(o));                    
                        
                }
                assertTrue(ok);
         }
        }
	}	
}
