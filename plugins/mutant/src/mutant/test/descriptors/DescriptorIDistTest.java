package mutant.test.descriptors;

import mutant.descriptors.DescriptorIDist;

import org.openscience.cdk.qsar.IMolecularDescriptor;

public class DescriptorIDistTest extends DescriptorsTest {
	@Override
	protected void setUp() throws Exception {
	    super.setUp();
		addPropertiesToTest("Idist_expected", "Idist");
	}
    /*
	public void testCalculate() throws Exception{

		Integer found = new Integer(1);
		Integer notfound = new Integer(0);
		Object[][] test = new Object[][] {
				//{"NC=6C=CC2C(=Cc1cc(ccc12)C5C3CC4CC5CC(C3)C4)C=6",found},
				{"Nc1ccc(cc1)c2cc(cc(c2)C(F)(F)F)C(F)(F)F",found},
				
				{"Nc1ccc(cc1)c2ccc(CCCC)cc2",found},
				{"Nc1ccc(cc1)c2ccc(cc2)C(C)(C)C",found},
				{"Nc1ccc(cc1)c2ccc(cc2)C(F)(F)F",found},
				{"C1=CC(=CC(=C1)C(F)(F)F)C2=CC=C(C=C2)N",found},
				
				{"Oc1ccc2C=C3CC(N)C=CC3(c2(c1))",notfound},

				{"c1cc(ccc1c2ccc(cc2)",notfound},
				{"c1cc(ccc1OCc2ccc(cc2)",notfound},
                {"Nc1ccc(cc1)c2ccc(cc2)CCC",notfound}
		};
			for (int i=0; i < test.length;i++) {
				IAtomContainer ac = FunctionalGroups.createAtomContainer(test[i][0].toString(), true);
				HueckelAromaticityDetector.detectAromaticity(ac);
				
				DescriptorValue v = (DescriptorValue) descriptor.calculate(ac);
				assertEquals(1,v.getNames().length);
				assertEquals("Idist",v.getNames()[0]);
				
				IntegerResult r = (IntegerResult) v.getValue();
				System.out.println(test[i][0]+"\tIdist="+r.intValue());
				assertEquals(
						((Integer)test[i][1]).intValue(),
						r.intValue());

				
			}
	}
    */
/*
	public void test() throws Exception {
   		InputStream in = new FileInputStream("plugins/mutant/data/aromatic_amines/ammTA100.csv");
   		IteratingDelimitedFileReader reader = new IteratingDelimitedFileReader(in, new DelimitedFileFormat(',','"'));
   		boolean ok = true;
   		while (reader.hasNext()) {
   			Object o = reader.next();
   			if (o instanceof IAtomContainer) {
   				MolAnalyser.analyse((IAtomContainer)o);
   				String smiles = ((IAtomContainer)o).getProperty("SMILES").toString();
   				int idist = Integer.parseInt(((IAtomContainer)o).getProperty("Idist").toString());
   				
   				DescriptorValue v = (DescriptorValue) descriptor.calculate((IAtomContainer)o);

   				if (idist != ((IntegerResult) v.getValue()).intValue()) {
   	   				System.out.print(((IAtomContainer)o).getProperty("StructureID").toString());
   	   				System.out.print("\t");
   	   				System.out.print(smiles);   					
   					System.out.print("\tExpected\t");
   					System.out.print(idist);
   					System.out.print("\tCalculated\t");
   					System.out.print(((IntegerResult) v.getValue()).toString());
   					System.out.println("\tBAD");
   					ok = false;
   				}	
   			}
   		}
   		reader.close();
   		assertTrue(ok);
	}
*/    
    @Override
    protected IMolecularDescriptor createDescriptorToTest() throws Exception {
        return new DescriptorIDist();
    }
    public String getResultsFile() {
        return "aromatic_amines/ammTA100_IDIST.csv";      
    }

    @Override
    public String getSourceFile() {
        return "aromatic_amines/ammTA100.csv";

    }

    @Override
    public String getStructureID() {
        return "CAS Number";
    }
    
	public void testAdamant() throws Exception {
		 
        Object[][] smiles = new Object[][] {
        		
         {"NC=1C=CC3=C(C=1)CC=2C=C(C=CC=23)C6C4CC5CC(C4)CC6(C5)","Idist",new Integer(1)},


         };
        calculate(smiles);
	}  	    
}
