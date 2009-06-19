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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.AccessController;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.TimeZone;

import sun.security.action.GetPropertyAction;
import toxTree.logging.TTLogger;

/**
 * Accessing & serializing Array: Throwing exception in jre6 while it works in jre5
 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6554519
 * An abstract class to support Batch processing with several fail safe options
 * 
 * @author Nina jeliazkova <b>Modified</b> 2005-9-4
 */
public abstract class BatchProcessing extends Observable implements
		 IBatchProcessing {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	protected int saveStateFrequency = 50;

	protected FileState inputFile = null;

	protected FileState outputFile = null;

	protected Date dateCreated = null;

	protected Date dateLastSaved = null;

	protected transient File configFile = null;

	protected transient String tmpDir = null;

	protected transient boolean deleteConfigOnSuccess = false;

	protected static transient String MSG_FILEDONOTEXISTS = "File do not exists!\t";

	protected static transient String MSG_CANTCREATEFILE = "Can't create file!\t";

	protected static transient String MSG_OPEN = "Opening input file\t";

	protected static transient String MSG_UNSUPPORTEDFORMAT = "UNSUPPORTED FORMAT\t";

	protected static transient String MSG_ERRORSAVE = "Error when writing file\t";

	protected static transient String MSG_ERRORCOUNTING = "Error when counting records in\t";

	protected static transient TTLogger logger = new TTLogger(
			BatchProcessing.class);

	protected static transient String[] statusMsg = {
			"Batch processing not started", "Batch running",
			"Batch processing finished", "Batch processing paused",
			"Batch processing aborted", "Batch processing not configured" };

	public final static transient int STATUS_NOTSTARTED = 0;

	public final static transient int STATUS_RUNNING = 1;

	public final static transient int STATUS_FINISHED = 2;

	public final static transient int STATUS_PAUSED = 3;

	public final static transient int STATUS_ABORTED = 4;

	public final static transient int STATUS_NOTINITIALIZED = 5;

	public int status = STATUS_NOTINITIALIZED;

	/**
	 * Creates empty BatchProcessing it should be configured prior to use
	 */
	public BatchProcessing() {
		super();
		setStatus(STATUS_NOTINITIALIZED);
		dateCreated = Calendar.getInstance(TimeZone.getDefault()).getTime();
		dateLastSaved = Calendar.getInstance(TimeZone.getDefault()).getTime();
	}

	/**
	 * Creates BatchProcessing given input and output file names
	 * 
	 * @param input
	 *            Input filename; should exists
	 * @param output
	 *            Results (output) filename ; deleted if exists
	 * @throws BatchProcessingException -
	 *             if input file do not exists - if output file can't be deleted
	 *             and a new one created; - on any IOException
	 */
	public BatchProcessing(String input, String output)
			throws BatchProcessingException {
		this();
		inputFile = new FileState(input);
		openInputFile();
		outputFile = new FileState(output);
		createOutputFile();
		setStatus(STATUS_NOTSTARTED);
	}

	/**
	 * Creates BatchProcessing given input and output file names
	 * 
	 * @param input
	 *            Input filename; should exists
	 * @param output
	 *            Results (output) filename ; deleted if exists
	 * @throws BatchProcessingException -
	 *             if input file do not exists - if output file can't be deleted
	 *             and a new one created; - on any IOException
	 */
	public BatchProcessing(File input, File output)
			throws BatchProcessingException {
		this();
		if (input == null)
			throw new BatchProcessingException("No input file!");
		inputFile = new FileState(input);
		openInputFile();
		if (output == null)
			throw new BatchProcessingException("No output file!");
		outputFile = new FileState(output);
		createOutputFile();
		setStatus(STATUS_NOTSTARTED);
	}

	public void openInputFile() throws BatchProcessingException {
		if (inputFile.getFile() == null)
			inputFile.setFile(inputFile.createFile());
		if (!inputFile.getFile().exists()) {
			logger.debug(MSG_FILEDONOTEXISTS, inputFile.getFilename());
			throw new BatchProcessingException(MSG_FILEDONOTEXISTS
					+ inputFile.getFilename());
		}
	}

	public void createOutputFile() throws BatchProcessingException {
		if (inputFile.filename.equals(outputFile.filename)) 
			throw new BatchProcessingException("Input and Output files are the same!");
		logger.debug("Creating output file\t", outputFile.filename);
		if (outputFile.getFile() == null)
			outputFile.setFile(outputFile.createFile());
		/*
		 * try { //logger.info("Creating output file\t",outputFile.filename); if
		 * (outputFile.getFile() == null)
		 * outputFile.setFile(outputFile.createFile());
		 * 
		 * if (!outputFile.getFile().createNewFile()) {
		 * outputFile.getFile().delete(); if
		 * (!outputFile.getFile().createNewFile()) { //again unsuccessful throw
		 * new BatchProcessingException(MSG_CANTCREATEFILE +
		 * outputFile.getFilename()); }
		 * 
		 *  } catch (IOException x) { logger.error(x); throw new
		 * BatchProcessingException(x); }
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.io.IBatchProcessing#start()
	 */
	public void start() throws BatchProcessingException {

		if (isStatus(STATUS_NOTSTARTED) || isStatus(STATUS_PAUSED)) {

			if (isStatus(STATUS_PAUSED))
				logger.info("Resume batch processing after pause");
			if (configFile == null)
				configFile = createConfigFile();

			setStatus(STATUS_RUNNING);
			long startRecord = 0;
			long record = 0;
			while (hasNextRecord() && (status == STATUS_RUNNING)) {

				readRecord();
				try {
					processRecord();
				} catch (BatchProcessingException x) {
					logger.error(x);
				}
				inputFile.setCurrentRecord(inputFile.getCurrentRecord() + 1);
				writeRecord();
				outputFile.setCurrentRecord(outputFile.getCurrentRecord() + 1);

				record++;
				if (((record - startRecord) % saveStateFrequency) == 0) {
					saveConfig();
					startRecord = 0;
				}
				setChanged();
				notifyObservers();				
			}
			if (status == STATUS_RUNNING) {
				setStatus(STATUS_FINISHED);
				close();
			} else if (status != STATUS_PAUSED)
				close();
			// save state finally
			saveConfig();

			if (deleteConfigOnSuccess && (status == STATUS_FINISHED)) {
				configFile.delete();
			}
		} else
			throw new BatchProcessingException(statusMsg[status]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.io.IBatchProcessing#close()
	 */
	public void close() throws BatchProcessingException {
		if ((status != STATUS_RUNNING) && (status != STATUS_PAUSED)) {
			closeInputFile();
			closeOutputFile();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.io.IBatchProcessing#pause()
	 */
	public void pause() throws BatchProcessingException {
		setStatus(STATUS_PAUSED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.io.IBatchProcessing#cancel()
	 */
	public void cancel() throws BatchProcessingException {
		setStatus(STATUS_ABORTED);
	}

	public void saveConfig() throws BatchProcessingException {
		if ((inputFile == null) || (outputFile == null))
			throw new BatchProcessingException("Empty input/output files!");
		dateLastSaved = Calendar.getInstance(TimeZone.getDefault()).getTime();
		FileOutputStream configStream = null;
		try {
			configStream = new FileOutputStream(configFile);
			saveConfig(configStream);
			configStream.close();
		} catch (FileNotFoundException x) {
			configStream = null;
			logger.error(x);
		} catch (IOException x) {
			configStream = null;
			logger.error(x);
		}
		setChanged();
		notifyObservers();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.io.IBatchProcessing#saveConfig()
	 */
	public void saveConfig(OutputStream out) throws BatchProcessingException {
		if (out == null)
			throw new BatchProcessingException("Can't save batch state!");
		try {
			logger.debug("Save state\t", configFile.toString());
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(this);
			os.close();
		} catch (IOException x) {
			throw new BatchProcessingException(x);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.io.IBatchProcessing#loadState()
	 */
	public void loadConfig() throws BatchProcessingException {
		// TODO loadState
	}

	/**
	 * @return Returns the status.
	 */
	public synchronized boolean isStatus(int status) {
		return this.status == status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	protected synchronized void setStatus(int status) {
		this.status = status;
		logger.info(statusMsg[status]);
		setChanged();
		notifyObservers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return statusMsg[status];
		// + " Records Read "+inputFile.getCurrentRecord() + " Written
		// "+outputFile.getCurrentRecord();
		// return statusMsg[status] + "\n" + inputFile.toString() + "\n" +
		// outputFile.toString();
	}

	/**
	 * BatchProcessing saves its state on each saveStateFrequency records
	 * 
	 * @return Returns the saveStateFrequency.
	 */
	public synchronized int getSaveStateFrequency() {
		return saveStateFrequency;
	}

	/**
	 * BatchProcessing saves its state on each saveStateFrequency records
	 * 
	 * @param saveStateFrequency
	 *            The saveStateFrequency to set.
	 */
	public synchronized void setSaveStateFrequency(int saveStateFrequency) {
		this.saveStateFrequency = saveStateFrequency;
	}

	public String getTempDir() {
		if (tmpDir == null) {
			GetPropertyAction a = new GetPropertyAction("java.io.tmpdir");
			tmpDir = ((String) AccessController.doPrivileged(a));
		}
		return tmpDir;
	}

	public File createConfigFile() {
		try {
			deleteConfigOnSuccess = true;
			return File.createTempFile(BatchProcessing.class.getName(), ".tmp");
		} catch (IOException x) {
			logger.error(x);
			return null;
		}
		// return new File(new
		// File(getTempDir()),BatchProcessing.class.getName()+".tmp");

	}

	/**
	 * Where to store batchprocessing configuration itself
	 * 
	 * @return Returns the configFile.
	 */
	public File getConfigFile() {
		return configFile;
	}

	/**
	 * @param configFile
	 *            The configFile to set.
	 */
	public void setConfigFile(File configFile) {
		deleteConfigOnSuccess = false;
		this.configFile = configFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		BatchProcessing bp = (BatchProcessing) obj;
		return (status == bp.getStatus()) && (inputFile.equals(bp.inputFile))
				&& (outputFile.equals(bp.outputFile));
	}

	/**
	 * @return Returns the status.
	 */
	public synchronized int getStatus() {
		return status;
	}

	/**
	 * Returns the number of records read from the input file
	 */
	public long getReadRecordsCount() {
		return inputFile.getCurrentRecord() + 1;
	}

	/**
	 * Returns the number of records written to the output file
	 */
	public long getWrittenRecordsCount() {
		return outputFile.getCurrentRecord() + 1;
	}

	/*
	 * 
	 * public void run() { try { start(); } catch (BatchProcessingException x ) {
	 * logger.error(x); } }
	 */
	/**
	 * @return Returns the inputFile.
	 */
	public synchronized FileState getInputFile() {
		return inputFile;
	}

	/**
	 * @return Returns the outputFile.
	 */
	public synchronized FileState getOutputFile() {
		return outputFile;
	}

	public boolean isRunning() {
		return status == STATUS_RUNNING;
	}

	public boolean isPaused() {
		return status == STATUS_PAUSED;
	}

	public boolean isCancelled() {
		return status == STATUS_ABORTED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.io.batch.IBatchProcessing#getDateCreated()
	 */
	public Date getDateCreated() {

		return dateCreated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.io.batch.IBatchProcessing#getDateLastProcessed()
	 */
	public Date getDateLastProcessed() {

		return dateLastSaved;
	}
	
	
}
