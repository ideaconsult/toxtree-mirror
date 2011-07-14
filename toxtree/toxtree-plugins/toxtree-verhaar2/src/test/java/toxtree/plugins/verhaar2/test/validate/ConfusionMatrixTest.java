package toxtree.plugins.verhaar2.test.validate;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionResultException;
import toxTree.logging.TTLogger;
import toxtree.plugins.verhaar2.VerhaarScheme2;
import verhaar.VerhaarScheme;
import ambit2.core.io.MyIteratingMDLReader;

public class ConfusionMatrixTest {
	protected IDecisionMethod rules ;
	protected static TTLogger logger = new TTLogger(ConfusionMatrixTest.class); 
	
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
		 rules = new VerhaarScheme2(false);
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
			MyIteratingMDLReader reader = new MyIteratingMDLReader(
					new FileInputStream(new File(filename)),NoNotificationChemObjectBuilder.getInstance());
			IDecisionResult result = rules.createDecisionResult();
			int ok = 0;
			int records = 0;
			int emptyMolecules = 0;
			int applyError = 0;
			
			ConfusionMatrix<String,String> cmatrix = new ConfusionMatrix<String, String>();
			Object[] expectedValues = new Object[expectedColumns.length];
			
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
			//logger.error(category);
			logger.error("Processed\t",records);
			logger.error("Successfull\t",ok);
			logger.error("Empty\t",emptyMolecules);
			logger.error("Error when applying rules\t",applyError);
			logger.error("");			
			Assert.assertTrue(records > 0);
			Assert.assertEquals(records-applyError,ok);
			Assert.assertEquals(0,emptyMolecules);
			Assert.assertEquals(0,applyError);

	}	
}

class ConfusionMatrix<A,B> {
	protected boolean dirty= false;
	List<CMEntry<A,B>> cmatrix = new ArrayList<CMEntry<A,B>>();
	public ConfusionMatrix() {
		super();
	}
	public void addEntry(A expected, B predicted) {
		CMEntry<A,B> cme = new CMEntry<A,B>(expected,predicted);
		int index = cmatrix.indexOf(cme);
		if (index<0) { cmatrix.add(cme); cme.increment();}
		else cmatrix.get(index).increment();
		dirty = true;
	}
	public void sort() {
		
	}
	
	@Override
	public String toString() {
		if (dirty ) Collections.sort(cmatrix);	
		StringBuilder b = new StringBuilder();
		Object x = null;
		for (CMEntry cm : cmatrix) {
			if ((x!=null) && !x.equals(cm.getExpected())) b.append("\n");
			b.append(cm.toString());
			b.append("\n");
			x = cm.getExpected();
		}
		return b.toString();
	}
}
class CMEntry<A,B> implements Comparable<CMEntry<A,B>>{
	A expected;
	B predicted;
	int count;
	
	public CMEntry(A expectedValue, B predictedValue) {
		setExpected(expectedValue);
		setPredicted(predictedValue);
		count = 0;
	}
	public A getExpected() {
		return expected;
	}
	protected void setExpected(A expected) {
		this.expected = expected;
	}
	public B getPredicted() {
		return predicted;
	}
	protected void setPredicted(B predicted) {
		this.predicted = predicted;
	}
	public int getFrequency() {
		return count;
	}
	public void increment() {
		this.count++;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CMEntry) {
			CMEntry o = (CMEntry)obj;
			return  getExpected().toString().equals(o.getExpected().toString()) &&
		 		getPredicted().toString().equals(o.getPredicted().toString());
		}
		return false;
	}
	@Override
	public int compareTo(CMEntry<A, B> o) {
		int ok = getExpected().toString().compareTo(o.getExpected().toString());
		if (ok==0) return getPredicted().toString().compareTo(o.getPredicted().toString());
		else return ok;
	}
	
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (null == expected ? 0 : expected.hashCode());
		hash = 31 * hash + (null == predicted ? 0 : predicted.hashCode());
		return hash;
	}
	@Override
	public String toString() {
		return String.format("Expected\t%s\tPredicted\t%s\t[%d]",getExpected(),getPredicted(),getFrequency() );
	}
}