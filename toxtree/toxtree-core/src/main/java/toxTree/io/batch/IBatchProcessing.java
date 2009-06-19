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
import java.io.Serializable;
import java.util.Date;


/**
 * Interface for batch processing
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-4
 */
public interface IBatchProcessing  extends Serializable {
	//starts batch processing
	void start() throws BatchProcessingException;
	//pause batch processing
	void pause() throws BatchProcessingException;
	//aborts batch processing
	void cancel() throws BatchProcessingException;
	//
	void close() throws BatchProcessingException;
	//save state (to be loaded later)
	void saveConfig() throws BatchProcessingException;
	//load state
	void loadConfig() throws BatchProcessingException;
	//process record (already read)
	void processRecord() throws BatchProcessingException;
	//	read record from input file, but do not	process 
	void readRecord() throws BatchProcessingException;
	//	write record to output file 
	void writeRecord() throws BatchProcessingException;
	//if has a next record
	boolean hasNextRecord();
	//read count
	long getReadRecordsCount();
	//write count
	long getWrittenRecordsCount();
	
	void setConfigFile(File configFile);
	
	void openInputFile() throws BatchProcessingException;
	void createOutputFile() throws BatchProcessingException;
	void closeInputFile() throws BatchProcessingException;
	void closeOutputFile() throws BatchProcessingException;
	
	boolean isRunning();
	boolean isPaused();
	boolean isCancelled();
	
	/**
	 * @return {@link Date}  when batch job was first created
	 */
	Date getDateCreated();
	/**
	 * @return {@link Date}  when batch job was processed last
	 */	
	Date getDateLastProcessed();
	
}
