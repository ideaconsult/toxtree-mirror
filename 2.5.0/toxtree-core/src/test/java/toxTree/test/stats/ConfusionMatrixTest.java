package toxTree.test.stats;

import org.junit.Test;

import toxTree.tree.stats.ConfusionMatrix;

public class ConfusionMatrixTest {

	@Test
	public void test() throws Exception {
		ConfusionMatrix<String, String> mx = new ConfusionMatrix<String, String>();
		mx.addEntry("expected1", "predicted1");
		mx.addEntry("expected1", "predicted1");
		mx.addEntry("expected1", "predicted3");
		mx.addEntry("expected1", "predicted5");
		
		mx.addEntry("expected2", "predicted2");
		mx.addEntry("expected2", "predicted2");
		mx.addEntry("expected2", "predicted2");
		mx.addEntry("expected2", "predicted4");
		mx.addEntry("expected2", "predicted3");
		
		mx.addEntry("expected3", "predicted1");
		mx.addEntry("expected3", "predicted5");
		mx.addEntry("expected3", "predicted5");
		mx.addEntry("expected3", "predicted3");
		mx.addEntry("expected3", "predicted3");
		mx.addEntry("expected3", "predicted4");
		
		mx.addEntry("expected4", "predicted4");
		mx.addEntry("expected4", "predicted4");
		mx.addEntry("expected4", "predicted4");
		mx.addEntry("expected4", "predicted4");
		mx.addEntry("expected4", "predicted4");
		
		System.out.println(mx);
		System.out.println(mx.printMatrix());
	}
}
