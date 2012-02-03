/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/


package toxTree.tree.rules;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.io.iterator.IteratingSMILESReader;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.MolAnalyseException;
import toxTree.logging.TTLogger;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import ambit2.core.io.IteratingDelimitedFileReader;

public class LookupFile implements Serializable {
	protected File file;
	protected transient ArrayList<String> fileCache = null;
	protected boolean useCache = false; //will be true if fixed
	protected boolean checkAromaticity = true;
	protected transient FileInputStream fStream = null;
	protected transient TTLogger logger;
	public LookupFile(String filename) throws IOException {
		this(new File(filename));
	}
	public LookupFile(File file) throws IOException {
		String f = file.getPath().toLowerCase();
		if ((file.exists())  && 
			((f.endsWith(".sdf") || f.endsWith(".smi") || f.endsWith(".csv")))) {
			this.file = file;
		} else {
			this.file = null;
			throw new IOException("File does not exists or unsupported format\t"+file.getAbsoluteFile());
		}
	}
	public boolean find(IAtomContainer mol ) throws DecisionMethodException {
		try {
			return lookup(mol) != null;
		} catch (CDKException x) {
			throw new DecisionMethodException(x);
		}
	}
	public IAtomContainer lookup(IAtomContainer mol ) throws DecisionMethodException, CDKException {
		if (file == null) return null;
		
		if (logger == null) setLogger(new TTLogger(getClass()));
		String molFormula = MolecularFormulaManipulator.getString(MolecularFormulaManipulator.getMolecularFormula(mol));
		
		if (useCache && (fileCache != null) && (fileCache.indexOf(molFormula) == -1)) {
			logger.info("Using cached information of file\t",file.getAbsoluteFile(),"\tCompound\t",molFormula,"\tNOT FOUND");
			return null;  
		}		
		IIteratingChemObjectReader reader;
		String f = file.getPath().toLowerCase();
		fStream = null;
		boolean FOUND = false;
		try {
			fStream = new FileInputStream(file);
			if (f.endsWith(".sdf")) reader = new IteratingMDLReader(fStream,DefaultChemObjectBuilder.getInstance());
			else if (f.endsWith(".csv")) reader = new IteratingDelimitedFileReader(fStream);
			else if (f.endsWith(".smi")) reader = new IteratingSMILESReader(fStream);
			else {
				fStream.close(); file = null;
				throw new DecisionMethodException("Unsupported format\t"+file);
			}
			
			int r = 0;
			if (fileCache == null) {
				fileCache = new ArrayList<String>();
			}
			IAtomContainer mol_found = null;
			while (reader.hasNext()) {
				String formula = "";
				r++;
				Object o = reader.next();
				
				if ((r-1) < fileCache.size()) {
					formula = fileCache.get(r-1);
					/*if cached formula of this record does not match our formula,
					 * skip analysis of this record and try the next
					 */
					if (useCache && !molFormula.equals(formula))  
						continue;
				} else fileCache.add(formula);
				
				if (o instanceof IAtomContainer) {

					IAtomContainer m = (IAtomContainer) o;
					try {
						MolAnalyser.analyse(m);
						if (formula.equals("")) 
							fileCache.set(r-1,MolecularFormulaManipulator.getString(MolecularFormulaManipulator.getMolecularFormula(m)));
						
						/*
						m.setFlag(CDKConstants.ISAROMATIC,HueckelAromaticityDetector.detectAromaticity(m,true));			
						hadder.addExplicitHydrogensToSatisfyValency(m);
						*/
						if (m.getAtomCount() != mol.getAtomCount()) {
							continue;
						}
						if (m.getBondCount() != mol.getBondCount()) {
							continue;
						}

						if (checkAromaticity && (m.getFlag(CDKConstants.ISAROMATIC) != mol.getFlag(CDKConstants.ISAROMATIC))) {
							logger.debug("Aromaticity does not match");
							continue;							
						}
					
					} catch (MolAnalyseException x) {
						//hmm
						logger.error(x);
					}					
					if (FunctionalGroups.isSubstance(m,mol)) {
						logger.info("Found");
						mol_found = m;
						FOUND = true;
						
						if (useCache) {
							reader.close();
							return m;
						}
						//else read the entire file
					}
				}
			}
			if (!FOUND) logger.info("Not found");
			reader.close();
			useCache = fileCache.size() > 0;
		
			if (FOUND) return mol_found;
			else return null;

		} catch (IOException x) {
			logger.error(x.getMessage());
			throw new DecisionMethodException(x);
		}

	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		if (!this.file.equals(file)) {
			if (fileCache != null) fileCache.clear();
			fileCache = null;
		}
		this.file = file;
	}
	public boolean isUseCache() {
		return useCache;
	}
	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
	public TTLogger getLogger() {
		return logger;
	}
	public void setLogger(TTLogger logger) {
		this.logger = logger;
	}
	public boolean isCheckAromaticity() {
		return checkAromaticity;
	}
	public void setCheckAromaticity(boolean checkAromaticity) {
		this.checkAromaticity = checkAromaticity;
	}
}


