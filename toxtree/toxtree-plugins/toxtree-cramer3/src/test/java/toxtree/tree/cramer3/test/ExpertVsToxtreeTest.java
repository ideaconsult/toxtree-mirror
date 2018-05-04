package toxtree.tree.cramer3.test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;

import toxTree.core.IDecisionResult;
import toxTree.query.MolAnalyser;
import toxTree.tree.stats.ConfusionMatrix;
import toxtree.tree.cramer3.RevisedCramerDecisionTree;
import ambit2.core.io.DelimitedFileWriter;
import ambit2.core.io.FileInputState;

import com.google.common.base.Strings;

public class ExpertVsToxtreeTest {
	@Test
	public void test() throws Exception {
		URL url = ExpertVsToxtreeTest.class
				.getClassLoader()
				.getResource(
						"toxtree/tree/cramer3/test/expert/20151222_Expert_vs_TT3v1771.xlsx");
		Assert.assertNotNull(url);
		File file = new File(url.getFile());
		Assert.assertTrue(file.exists());

		RevisedCramerDecisionTree cdt = new RevisedCramerDecisionTree();
		IDecisionResult result = cdt.createDecisionResult();
		result.setDecisionMethod(cdt);
		testFile(result, file);

	}

	public void testFile(IDecisionResult tree, File file) throws Exception {
		String[] headers = new String[] { "FEMA ", "SMILES", "CAS",
				"PRINCIPALNAME", "Original Cramer Class", "JECFA Grp",
				"Class TT3.1771", "Path TT3.1771",
				"REVISED DECISION TREE EXPERT JUDGEMENT CLASS",
				"REVISED DECISION TREE EXPERT JUDGEMENT PATH" };
		
		Map commonPrefix = new TreeMap<String,Integer>();
		int errors = 0;
		int records = 0;
		int predictionError = 0;
		int correctPredictions = 0;
		int correctPaths = 0;
		int pathsError = 0;
		IIteratingChemObjectReader<IAtomContainer> reader = null;
		DelimitedFileWriter writer = new DelimitedFileWriter(new FileOutputStream(new File(file.getAbsolutePath()+"_result.csv")));
		
		ConfusionMatrix<String, String> cmatrix = new ConfusionMatrix<String, String>();
		cmatrix.setExpectedTitle("Experts");
		cmatrix.setPredictedTitle("Toxtree");
		
		try {
			reader = FileInputState.getReader(file);
			IAtomContainer mol = null;
			while (reader.hasNext()) {
				records++;
				mol = reader.next();
				Assert.assertNotNull(mol);
				if (mol.getAtomCount() == 0) {

					System.err.println("Empty molecule "
							+ mol.getProperties().toString());
					errors++;
					continue;
				}
				Iterator<Entry<Object, Object>> i = mol.getProperties()
						.entrySet().iterator();
				String expectedClass = null;
				String expectedPath = null;
				while (i.hasNext()) {
					Entry<Object, Object> p = i.next();
					if (p.getValue() == null)
						continue;
					String tag = p.getKey().toString();
					// ((Property)p.getKey()).getName();
					if (headers[headers.length - 1].equals(tag))
						expectedPath = p.getValue().toString().trim();
					else if (headers[headers.length - 2].equals(tag))
						expectedClass = p.getValue().toString().trim();
				}
				if ("".equals(expectedClass)) continue;
				Assert.assertNotNull(mol.getProperties().toString(),
						expectedClass);
				Assert.assertNotNull(mol.getProperties().toString(),
						expectedPath);
				try {
					MolAnalyser.analyse(mol);
					tree.classify(mol);
					
					tree.assignResult(mol);
					
					String predictedClass = tree.getCategory().getID() == 1 ? "I"
							: (tree.getCategory().getID() == 2) ? "II" : (tree
									.getCategory().getID() == 3) ? "III"
									: "UNKNOWN";
					String predictedPath = tree.explain(false).toString();

					cmatrix.addEntry(expectedClass, predictedClass);
					//ignore Q2
					predictedPath = predictedPath.replace("2N,","").replace("2Y,","");
					expectedPath = expectedPath.replace("2N,","").replace("2Y,","");
					
					if (predictedClass.equals(expectedClass)) {
						correctPredictions++;
					} else {
						System.out.println(String.format(
								"%d.\tPredicted %s\tExpected %s\t%s", records,
								predictedClass, expectedClass,
								mol.getProperty("SMILES")));
						predictionError++;
					}
					if (predictedPath.equals(expectedPath)) {
						correctPaths++;
						mol.setProperty("prefix", "");
					} else {
						pathsError++;
						String cpp = Strings.commonPrefix(expectedPath, predictedPath);
						String[] cp = cpp.split(",");
						Object c = commonPrefix.get(cp[cp.length-1]);
						if (c==null) commonPrefix.put(cp[cp.length-1], 1);
						else commonPrefix.put(cp[cp.length-1],  ((Integer)c).intValue()+1);
						mol.setProperty("prefix", cp[cp.length-1]);
					}	
					
					mol.setProperty("predictedPath", predictedPath);
					
				} catch (Exception x) {
					System.out.println(String.format(
							"%d.\tPredicted %s\tExpected %s\t%s", records,
							x.getMessage(), expectedClass,
							mol.getProperty("SMILES")));
				} finally {
					writer.writeMolecule(mol);
				}
			}
		} catch (Exception x) {
			x.printStackTrace();
			throw x;
		} finally {
			if (reader != null)
				reader.close();
			if (writer!=null) writer.close();
			System.out.println(String.format("Read: %s\tErrors on read: %s\tPrediction errors: %s\tPath errors: %s",records, errors, predictionError, pathsError));
			System.out.println(cmatrix);
			Iterator i = commonPrefix.entrySet().iterator();
			while (i.hasNext()) {
				System.out.println(i.next());	
			}
			
			Assert.assertEquals(2284, records);
			Assert.assertEquals(33, errors);
			Assert.assertEquals(0, predictionError);
			Assert.assertEquals(0, pathsError);
		}

	}
}
