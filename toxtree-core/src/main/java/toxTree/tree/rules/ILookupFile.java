package toxTree.tree.rules;

import java.io.File;
import java.io.IOException;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;

public interface ILookupFile {
	public boolean find(IAtomContainer mol ) throws DecisionMethodException;
	public boolean isEnabled();
	public File getFile();
	public void setFile(File file)  throws IOException;
	public int size();
}
