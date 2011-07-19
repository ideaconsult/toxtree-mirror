package toxTree.tree.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Performance statistics , similar to confusion matrix
 * @author nina
 *
 * @param <A>
 * @param <B>
 */
public class ConfusionMatrix<A,B extends Comparable > {
	protected boolean dirty= false;
	protected List<CMEntry<A,B>> cmatrix = new ArrayList<CMEntry<A,B>>();
	protected String expectedTitle = "Expected";
	public String getExpectedTitle() {
		return expectedTitle;
	}
	public void setExpectedTitle(String expectedTitle) {
		this.expectedTitle = expectedTitle;
	}
	public String getPredictedTitle() {
		return predictedTitle;
	}
	public void setPredictedTitle(String predictedTitle) {
		this.predictedTitle = predictedTitle;
	}

	protected String predictedTitle = "Predicted";
	
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
		synchronized (cmatrix) {
			if (dirty ) Collections.sort(cmatrix);	
			dirty = false;			
		}
		StringBuilder b = new StringBuilder();
		Object x = null;
		for (CMEntry<A,B> cm : cmatrix) {
			if ((x!=null) && !x.equals(cm.getExpected())) b.append("\n");
			b.append(cm.toString());
			b.append("\n");
			x = cm.getExpected();
		}
		return b.toString();
	}
	
	public String printMatrix() {
		synchronized (cmatrix) {
			if (dirty ) Collections.sort(cmatrix);	
			dirty = false;			
		}
		StringBuilder b = new StringBuilder();
		Object x = null;
		List<B> columns = new ArrayList<B>();
		int prevIndex = 0;
		for (CMEntry<A,B> cm : cmatrix) {
			B column = cm.getPredicted();
			int c = columns.indexOf(column);
			if (c<0) { columns.add(column);c = columns.size()-1; }
		}
		Collections.sort(columns);
		b.append(String.format("%s\\%s",expectedTitle,predictedTitle));
		for (B column: columns)
			b.append(String.format(",%s",column));
		for (CMEntry<A,B> cm : cmatrix) {
			int c = columns.indexOf(cm.getPredicted());
			
			if ((x==null) || ((x!=null) && !x.equals(cm.getExpected()))) {
				b.append("\n");
				b.append(cm.getExpected());
				b.append(",");
				prevIndex = 0;
			}
			
			for (int i=prevIndex; i < c; i++) b.append(",");
			//b.append(String.format("[%d.%s]",c,cm.predicted));
			b.append(cm.getFrequency());

			x = cm.getExpected();
			prevIndex = c;
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