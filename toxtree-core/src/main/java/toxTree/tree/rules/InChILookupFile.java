package toxTree.tree.rules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.inchi.InChIGenerator;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolAnalyser;
import ambit2.core.io.FileInputState;
import ambit2.core.processors.structure.InchiProcessor;

/**
 * In-memory InChI lookup. Should be fine for few hundreds compounds found in bodymol & foodmol.
 * @author nina
 *
 */
public class InChILookupFile implements ILookupFile{
	protected File file;
	protected InchiProcessor inchiProcessor;
	protected List<String> inchis = new ArrayList<String>();
	
	public InChILookupFile(File file) throws IOException , CDKException {
		setFile(file);
		inchiProcessor = new InchiProcessor();
	}
	@Override
	public boolean find(IAtomContainer mol) throws DecisionMethodException {
		try {
			if (inchis.size()==0) {
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						String.format("Loading %s",file.getAbsoluteFile()));
				long now = System.currentTimeMillis();
				if (file.getName().endsWith(".inchi")) {
					readFileInchi();
				} else
					readFile();
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						String.format("%s read in %sms",file.getAbsoluteFile(),System.currentTimeMillis()-now));
			}
			IAtomContainer newMol = (IAtomContainer) mol.clone();
			for (IBond bond : newMol.bonds()) bond.setFlag(CDKConstants.ISAROMATIC,false);
			//newMol = AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(newMol);
			InChIGenerator gen = inchiProcessor.process(newMol);
			String inchi = gen.getInchi();
			return inchis.indexOf(inchi)>=0;
		} catch (Exception x) { throw new DecisionMethodException(x);}
	}
	
	public void readFileInchi() throws Exception {
		 BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(file));
	        String line = fileReader.readLine();
	        while (line != null) {
	        	inchis.add(line);
	            line = fileReader.readLine();
	        }
		} finally {
			try {fileReader.close(); } catch (Exception x) {}
		}
	}
	public void readFile() throws Exception {
		FileInputStream stream = null;
		IIteratingChemObjectReader<IAtomContainer> reader = null;

		try {
			stream = new FileInputStream(file);
			reader = FileInputState.getReader(stream, file.getName());
			while (reader.hasNext()) {
				IAtomContainer ac = reader.next();
				AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(ac);
				MolAnalyser.analyse(ac);
				//InChI doesn't like aromatic bonds
				for (IBond bond : ac.bonds()) bond.setFlag(CDKConstants.ISAROMATIC,false);
				//ac = AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(ac);
				InChIGenerator gen = inchiProcessor.process(ac);
				String inchi = gen.getInchi();
				if (!inchis.contains(inchi)) inchis.add(inchi);
			}
		} catch (Exception x) {
			throw x;
		} finally {
			try {if (reader!=null) reader.close();} catch (Exception x) {}
			try {if (stream !=null) stream.close();} catch (Exception x) {}
		}
	}
	@Override
	public boolean isEnabled() {
		return (inchis.size()>0) || (file !=null && file.exists());
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public void setFile(File file) throws IOException {
		if (!file.exists()) throw new FileNotFoundException(file.getAbsolutePath());
		this.file = file;
		inchis.clear();
		this.file = file;
		
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (String inchi:inchis) {
			b.append(inchi);
			b.append("\r\n");
		}
		return b.toString();
	}
	
	@Override
	public int size() {
		return inchis.size();
	}
	
}
