package toxtree.plugins.verhaar2.test.validate;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.stats.ConfusionMatrix;
import toxtree.plugins.verhaar2.VerhaarScheme2;
import verhaar.VerhaarScheme;

public class ConfusionMatrixTest {
	protected IDecisionMethod rules ;

	protected transient static Logger logger = Logger.getLogger(ConfusionMatrixTest.class.getName());

/**
<pre>
Expected	1.0	Predicted	Class 1 (narcosis or baseline toxicity)	[39]  <<<<<<<<
Expected	1.0	Predicted	Class 5 (Not possible to classify according to these rules)	[1]

Expected	2.0	Predicted	Class 1 (narcosis or baseline toxicity)	[2]
Expected	2.0	Predicted	Class 2 (less inert compounds)	[16]  <<<<<<<<
Expected	2.0	Predicted	Class 5 (Not possible to classify according to these rules)	[4]

Expected	3.0	Predicted	Class 1 (narcosis or baseline toxicity)	[5]
Expected	3.0	Predicted	Class 3 (unspecific reactivity)	[17] <<<<
Expected	3.0	Predicted	Class 4 (compounds and groups of compounds acting by a specific mechanism)	[5]
Expected	3.0	Predicted	Class 5 (Not possible to classify according to these rules)	[11]

Expected	4.0	Predicted	Class 1 (narcosis or baseline toxicity)	[3]
Expected	4.0	Predicted	Class 3 (unspecific reactivity)	[7]
Expected	4.0	Predicted	Class 4 (compounds and groups of compounds acting by a specific mechanism)	[13]  <<<<<<<
Expected	4.0	Predicted	Class 5 (Not possible to classify according to these rules)	[2]
</pre>
 */
	@Test
	public void testVerhaar2() throws Exception {
		 rules = new VerhaarScheme2();
		testFile(getClass().getClassLoader().getResource("toxtree/plugins/verhaar2/Verhaar2000.sdf").getFile(),
				new String[] {"Class","Class_additional"});
	}
	
	/**
	<pre>
Expected	1.0	Predicted	Class 1 (narcosis or baseline toxicity)	[39]  <<<<<<
Expected	1.0	Predicted	Class 5 (Not possible to classify according to these rules)	[1]

Expected	2.0	Predicted	Class 1 (narcosis or baseline toxicity)	[2]
Expected	2.0	Predicted	Class 2 (less inert compounds)	[15]  <<<
Expected	2.0	Predicted	Class 4 (compounds and groups of compounds acting by a specific mechanism)	[1]
Expected	2.0	Predicted	Class 5 (Not possible to classify according to these rules)	[4]

Expected	3.0	Predicted	Class 1 (narcosis or baseline toxicity)	[1]
Expected	3.0	Predicted	Class 3 (unspecific reactivity)	[18] <<<<<<<<
Expected	3.0	Predicted	Class 4 (compounds and groups of compounds acting by a specific mechanism)	[8]
Expected	3.0	Predicted	Class 5 (Not possible to classify according to these rules)	[11]

Expected	4.0	Predicted	Class 3 (unspecific reactivity)	[2]
Expected	4.0	Predicted	Class 4 (compounds and groups of compounds acting by a specific mechanism)	[21]  <<<<<<
Expected	4.0	Predicted	Class 5 (Not possible to classify according to these rules)	[2]
	</pre>
	 */	
	@Test
	public void testVerhaar2Reversed() throws Exception {
		 rules = new VerhaarScheme2(true);
		testFile(getClass().getClassLoader().getResource("toxtree/plugins/verhaar2/Verhaar2000.sdf").getFile(),
				new String[] {"Class","Class_additional"});
	}	
	
	@Test
	public void testVerhaar() throws Exception {
		 rules = new VerhaarScheme();
		testFile(getClass().getClassLoader().getResource("toxtree/plugins/verhaar2/Verhaar2000.sdf").getFile(),
				new String[] {"Class","Class_additional"});
	}
	
	@Test
	public void testVerhaar2Enoch2008() throws Exception {
		 rules = new VerhaarScheme2();
		testFile(getClass().getClassLoader().getResource("toxtree/plugins/verhaar2/enoch08.csv").getFile(),
				new String[] {"Expected Verhaar scheme","Verhaar_1_60","MOA"});
	}
	
	
	@Test
	public void testVerhaar2EPAFHM() throws Exception {
		 rules = new VerhaarScheme2();
		testFile(getClass().getClassLoader().getResource("toxtree/plugins/verhaar2/EPAFHM_v4b_617_15Feb2008.sdf").getFile(),
				new String[] {"MOA"});
	}	
	
	protected void testFile(String filename, String[] expectedColumns) throws Exception {
			System.out.println(filename);
			IteratingSDFReader reader = new IteratingSDFReader(
					new FileInputStream(new File(filename)),SilentChemObjectBuilder.getInstance());
			IDecisionResult result = rules.createDecisionResult();
			int ok = 0;
			int records = 0;
			int emptyMolecules = 0;
			int applyError = 0;
			
			ConfusionMatrix<String,String> cmatrix = new ConfusionMatrix<String, String>();
			Object[] expectedValues = new Object[expectedColumns.length];
			cmatrix.setExpectedTitle(expectedColumns[0]);
			cmatrix.setPredictedTitle(rules.getTitle());
			
			while (reader.hasNext()) {
				result.clear();				
				Object o = reader.next();
				if (o instanceof IAtomContainer) {
					IAtomContainer a = (IAtomContainer) o;

					if (a.getAtomCount()==0) emptyMolecules++;
					try {
						result.classify(a);
						for (int i=0; i < expectedColumns.length;i++) {
							expectedValues[i] = a.getProperty(expectedColumns[i]);
							if(expectedValues[i]==null) continue;
							if("".equals(expectedValues[i])) continue;
							cmatrix.addEntry(expectedValues[i].toString(),result.getCategory().getName());
						}
						ok++;
					} catch(DecisionResultException x) {
						//x.printStackTrace();
						applyError ++;
					}

				}
				records++;
			}
			System.out.println(cmatrix.toString());
			System.out.println(cmatrix.printMatrix());
			//logger.error(category);
			logger.severe("Processed\t"+records);
			logger.severe("Successfull\t"+ok);
			logger.severe("Empty\t"+emptyMolecules);
			logger.severe("Error when applying rules\t"+applyError);
	
			Assert.assertTrue(records > 0);
			Assert.assertEquals(records-applyError,ok);
			Assert.assertEquals(0,emptyMolecules);
			Assert.assertEquals(0,applyError);

	}	
}
