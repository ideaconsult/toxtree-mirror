/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package toxTree.io.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ProgressMonitorInputStream;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.io.IChemObjectWriter;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;
import org.openscience.cdk.io.iterator.IteratingSMILESReader;

import toxTree.io.MDLWriter;
import ambit2.core.io.DelimitedFileFormat;
import ambit2.core.io.DelimitedFileWriter;
import ambit2.core.io.IteratingDelimitedFileReader;
import ambit2.core.io.MyIteratingMDLReader;


/**
 * Implements batch processing of chemical files
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-4
 */
public class ChemObjectBatchProcessing extends BatchProcessing  {
	protected transient IIteratingChemObjectReader reader;
	protected transient IChemObjectWriter writer;
	protected transient IChemObject chemObject;

	public transient static final String[] extensions = {".sdf",".csv",".smi",".txt"};
	public transient static final String[] extensions_descr = {"SDF files (*.sdf)",
			"Comma delimited (*.csv)","SMILES (*.smi)","Text files (*.txt)"};
	protected transient static int SDF_INDEX = 0;
	protected transient static int CSV_INDEX = 1;
	protected transient static int SMI_INDEX = 2;
	protected transient static int TXT_INDEX = 3;
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -7112745841569991346L;

	/**
	 * 
	 */
	public ChemObjectBatchProcessing() {
		super();
	}

	/**
	 * @param input Input file name
	 * @param output Output file name
	 * @throws BatchProcessingException
	 */
	public ChemObjectBatchProcessing(String input, String output)
			throws BatchProcessingException {
		super(input, output);

	}
	/**
	 * 
	 * @param input Input File
	 * @param output Output File
	 * @throws BatchProcessingException
	 */
	public ChemObjectBatchProcessing(File input, File output) throws BatchProcessingException {
		super(input, output);
	}
	protected org.openscience.cdk.io.iterator.IIteratingChemObjectReader getReader(FileState file,boolean monitor) throws BatchProcessingException {
		try {
			String fname = file.filename.toLowerCase(); 
			if (fname.endsWith(extensions[SDF_INDEX]))
				if (monitor)
					return new MyIteratingMDLReader(new ProgressMonitorInputStream(
                                null,
                                "Reading " + file.getFile().getName(),
                                new FileInputStream(file.getFile())),DefaultChemObjectBuilder.getInstance());
				else
					return new MyIteratingMDLReader(new FileInputStream(file.getFile()),DefaultChemObjectBuilder.getInstance());
			else if (fname.endsWith(extensions[SMI_INDEX])) 
				return new IteratingSMILESReader(new FileInputStream(file.getFile()));
			else if (fname.endsWith(extensions[CSV_INDEX]))
				return new IteratingDelimitedFileReader(new FileInputStream(file.getFile()));
			else if (fname.endsWith(extensions[TXT_INDEX]))
				return new IteratingDelimitedFileReader(new FileInputStream(file.getFile()),
						new DelimitedFileFormat(' ','"'));			
			else throw new BatchProcessingException(MSG_UNSUPPORTEDFORMAT+file.filename,this);
		} catch (FileNotFoundException x) {
			logger.error(x);
			throw new BatchProcessingException(x,this);
		}
	}
	/**
	 * Opens the input file as set by constructor and creates {@link IteratingChemObjectReader}
	 */
	@Override
	public void openInputFile() throws BatchProcessingException {
		super.openInputFile();
		reader = null;
		try {
			logger.info(MSG_OPEN,inputFile.filename);
			reader = getReader(inputFile,false);
			if ((reader != null) && (inputFile.currentRecord>0)) {
				logger.info("Have to skip\t",Long.toString(inputFile.currentRecord),"\trecords");
				int count = 0;
				while (reader.hasNext() && (count < inputFile.currentRecord)) {
					reader.next();
					count++;
				}
				if (count == inputFile.currentRecord)
					logger.info(Long.toString(inputFile.currentRecord),"\trecords skipped.");
				else { 
					/*
					logger.error("Input file is expected to have at least\t",Long.toString(inputFile.currentRecord),
							"\trecords, but only \t",Long.toString(count),"\t have been found!");
							*/
					throw new BatchProcessingException("Input file "+ inputFile.getFilename()+
							"is expected to have at least\t"+Long.toString(inputFile.currentRecord)+
							"\trecords, but only \t"+Long.toString(count)+"\t have been found!",this);
				}
			}
		} catch (Exception x) {
				logger.error(x);
				throw new BatchProcessingException(x);
		}
	}
	/**
	 * Creates {@link ChemObjectWriter}
	 */
	@Override
	public void createOutputFile() throws BatchProcessingException {
		super.createOutputFile();
		String fname = outputFile.filename.toLowerCase();
		boolean append = outputFile.currentRecord>0;
		try {
			if (fname.endsWith(extensions[SDF_INDEX])) {
				writer = new MDLWriter(new FileOutputStream(outputFile.getFile(),append));
				((MDLWriter) writer).dontWriteAromatic();
			} else if (fname.endsWith(extensions[CSV_INDEX])) 
				writer = new DelimitedFileWriter(new FileOutputStream(outputFile.getFile(),append));
			else if ((fname.endsWith(extensions[TXT_INDEX]))) 
				writer = new DelimitedFileWriter(new FileOutputStream(outputFile.getFile(),append),
						new DelimitedFileFormat(' ','"'));
			else throw new BatchProcessingException(MSG_UNSUPPORTEDFORMAT+outputFile.filename,this);
		} catch (Exception x) {
			logger.error(MSG_ERRORSAVE,outputFile.filename);
			throw new BatchProcessingException(x);
	}			
			if ((writer != null) && append) {
				/*TODO
				int count = 0;
				try {
					IteratingChemObjectReader r = getReader(outputFile,true);
				
					while (r.hasNext()) {
						r.next();
						count ++;
					}
					r.close();
				} catch (Exception x) {
					logger.error(MSG_ERRORCOUNTING,outputFile.filename);
					throw new BatchProcessingException(MSG_ERRORCOUNTING+outputFile.filename,this);
				}				
				if (count == outputFile.getCurrentRecord())
						logger.info("Verifying if the file has indeed \t",Long.toString(outputFile.currentRecord),"\trecords\tYES");
				else {
						logger.info("Verifying if the file has indeed \t",Long.toString(outputFile.currentRecord),"\trecords\tNO");
						throw new BatchProcessingException("Output file "+
								outputFile.getFilename() +
								" is expected to have exactly \t"+Long.toString(inputFile.currentRecord)+
								"\t records, but \t"+Long.toString(count)+"\t records have been found!",this);
	
				}
			*/

			}

	}
	/* (non-Javadoc)
	 * @see toxTree.io.BatchProcessing#start()
	 */
	@Override
	public void start() throws BatchProcessingException {
		super.start();
	}
	/* (non-Javadoc)
	 * @see toxTree.io.IBatchProcessing#closeInputFile()
	 */
	public void closeInputFile() throws BatchProcessingException {
		try {
			if (reader != null)
				reader.close(); 
			reader = null;
		} catch (IOException x) {
			throw new BatchProcessingException(x,this);
		}
	}
	/* (non-Javadoc)
	 * @see toxTree.io.IBatchProcessing#closeOutputFile()
	 */
	public void closeOutputFile() throws BatchProcessingException {
		try {
			if (writer != null)
				writer.close(); 
			writer = null;
		} catch (IOException x) {
			throw new BatchProcessingException(x,this);
		}
	}
	/* (non-Javadoc)
	 * @see toxTree.io.IBatchProcessing#processRecord()
	 */
	public void processRecord() throws BatchProcessingException {
		//do something with chemObject 

	}

	/* (non-Javadoc)
	 * @see toxTree.io.IBatchProcessing#readRecord()
	 */
	public void readRecord() throws BatchProcessingException {
		chemObject = (IChemObject) reader.next();
	}	

	/* (non-Javadoc)
	 * @see toxTree.io.IBatchProcessing#writeRecord()
	 */
	public void writeRecord() throws BatchProcessingException {
		try {
			writer.write(chemObject);
		} catch (CDKException x) {
			throw new BatchProcessingException("Error on writing record\t",x,this);
		}

	}

	/* (non-Javadoc)
	 * @see toxTree.io.IBatchProcessing#hasNextRecord()
	 */
	public boolean hasNextRecord() {
		return reader.hasNext();
	}
	
}
